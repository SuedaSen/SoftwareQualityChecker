package com.ser.reqcheck;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Best-effort extractor for the ABZ 2024 Mechanical Lung Ventilator (MLV) SRS PDFs.
 *
 * It tries to extract requirements with ids like: FUN.20, GUI.50.2, CONT.46, AL.38.1, etc.
 * Output CSV has columns: id,text
 */
public final class MlvRequirementsExtractor {

    // Example ids in the MLV SRS: FUN.4, GUI.50.2, CONT.46, AL.38.1
    private static final Pattern REQ_ID = Pattern.compile("\\b([A-Z]{2,5}\\.[0-9]+(?:\\.[0-9]+)?)\\b");

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.println("Usage: java ... com.ser.reqcheck.MlvRequirementsExtractor <input.pdf> <out.csv>");
            System.exit(2);
        }
        Path in = Path.of(args[0]);
        Path out = Path.of(args[1]);
        Files.createDirectories(out.getParent() == null ? Path.of(".") : out.getParent());

        List<Requirement> reqs = extract(in);
        writeCsv(out, reqs);
        System.out.println("Extracted requirements: " + reqs.size());
    }

    public static List<Requirement> extract(Path pdfPath) throws IOException {
        String text;
        try (PDDocument doc = Loader.loadPDF(pdfPath.toFile())) {
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);
            text = stripper.getText(doc);
        }

        String[] lines = text.split("\\R");
        Map<String, StringBuilder> byId = new LinkedHashMap<>();
        String currentId = null;

        for (String raw : lines) {
            String line = raw == null ? "" : raw.trim();
            if (line.isEmpty()) continue;

            // Skip obvious headers/footers and index-like noise
            if (line.startsWith("-- ") && line.contains(" of ")) continue;
            if (line.equalsIgnoreCase("Version History")) continue;

            Matcher m = REQ_ID.matcher(line);
            if (m.find()) {
                // Start a new requirement chunk when the line contains an id token.
                String id = m.group(1);

                // Heuristic: if the id is followed by text on the same line, treat the remainder as the first fragment.
                String after = line.substring(m.end()).trim();
                currentId = id;
                byId.computeIfAbsent(currentId, k -> new StringBuilder());
                if (!after.isEmpty()) {
                    byId.get(currentId).append(after);
                }
                continue;
            }

            // Continuation lines: append to the current id (best-effort).
            if (currentId != null) {
                StringBuilder sb = byId.get(currentId);
                if (sb.length() > 0) sb.append(' ');
                sb.append(line);
            }
        }

        List<Requirement> out = new ArrayList<>();
        for (var e : byId.entrySet()) {
            String rid = e.getKey();
            String reqText = e.getValue().toString()
                    .replaceAll("\\s+", " ")
                    .trim();
            if (!reqText.isEmpty()) out.add(new Requirement(rid, reqText));
        }
        return out;
    }

    private static void writeCsv(Path out, List<Requirement> reqs) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("id,text\n");
        for (Requirement r : reqs) {
            sb.append(escapeCsv(r.rid())).append(',').append(escapeCsv(r.text())).append('\n');
        }
        Files.writeString(out, sb.toString());
    }

    private static String escapeCsv(String s) {
        if (s == null) return "\"\"";
        String v = s.replace("\"", "\"\"");
        return "\"" + v + "\"";
    }
}

