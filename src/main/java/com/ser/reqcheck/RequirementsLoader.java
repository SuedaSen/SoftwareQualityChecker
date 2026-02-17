package com.ser.reqcheck;

import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class RequirementsLoader {

    public enum Format { CSV, TXT, DOC, PDF }

    public static List<Requirement> load(Path path, Format format) throws IOException, CsvException {
        return switch (format) {
            case CSV -> loadCsv(path);
            case TXT -> loadTxt(path);
            case DOC -> loadDoc(path);
            case PDF -> loadPdf(path);
        };
    }

    private static List<Requirement> loadCsv(Path path) throws IOException, CsvException {
        List<Requirement> result = new ArrayList<>();
        try (var reader = Files.newBufferedReader(path);
             var csvReader = new CSVReaderBuilder(reader).build()) {
            List<String[]> rows = csvReader.readAll();
            if (rows.isEmpty()) return result;
            String[] header = rows.get(0);
            int textIdx = indexOf(header, "text");
            int idIdx = indexOf(header, "id");
            if (textIdx < 0) throw new IllegalArgumentException("CSV must contain a 'text' column.");
            for (int i = 1; i < rows.size(); i++) {
                String[] row = rows.get(i);
                if (row.length <= textIdx) continue;
                String text = row[textIdx].trim();
                if (text.isEmpty()) continue;
                String rid = idIdx >= 0 && row.length > idIdx ? row[idIdx].trim() : ("R" + i);
                result.add(new Requirement(rid, text));
            }
        }
        return result;
    }

    private static int indexOf(String[] header, String col) {
        for (int i = 0; i < header.length; i++)
            if (col.equalsIgnoreCase(header[i].trim())) return i;
        return -1;
    }

    private static List<Requirement> loadTxt(Path path) throws IOException {
        List<Requirement> result = new ArrayList<>();
        List<String> lines = Files.readAllLines(path);
        int i = 1;
        for (String line : lines) {
            String t = line.trim();
            if (t.isEmpty()) continue;
            result.add(new Requirement("R" + i, t));
            i++;
        }
        return result;
    }

    /** .doc (Word 97-2003) ve .docx (Word 2007+) — paragraf/parça başına bir gereksinim. */
    private static List<Requirement> loadDoc(Path path) throws IOException {
        String name = path.getFileName().toString().toLowerCase();
        boolean isDocx = name.endsWith(".docx");
        List<String> lines = new ArrayList<>();
        try (InputStream in = Files.newInputStream(path)) {
            if (isDocx) {
                try (XWPFDocument doc = new XWPFDocument(in)) {
                    for (XWPFParagraph p : doc.getParagraphs()) {
                        String t = p.getText().trim();
                        if (!t.isEmpty()) lines.add(t);
                    }
                }
            } else {
                try (WordExtractor extractor = new WordExtractor(in)) {
                    for (String t : extractor.getParagraphText()) {
                        String trimmed = t != null ? t.trim() : "";
                        if (!trimmed.isEmpty()) lines.add(trimmed);
                    }
                }
            }
        }
        List<Requirement> result = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++)
            result.add(new Requirement("R" + (i + 1), lines.get(i)));
        return result;
    }

    /** PDF — metin çıkarılır, satırlara bölünür; her boş olmayan satır bir gereksinim. */
    private static List<Requirement> loadPdf(Path path) throws IOException {
        List<Requirement> result = new ArrayList<>();
        try (PDDocument doc = Loader.loadPDF(path.toFile())) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(doc);
            if (text == null) return result;
            int i = 1;
            for (String line : text.split("\\R")) {
                String t = line.trim();
                if (!t.isEmpty()) {
                    result.add(new Requirement("R" + i, t));
                    i++;
                }
            }
        }
        return result;
    }
}
