package com.ser.reqcheck;

import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class GoldDatasetLoader {

    /**
     * Loads a gold dataset for ambiguity evaluation.
     *
     * Expected CSV header columns:
     * - text (required)
     * - ambiguous (required): true/false, 1/0, yes/no
     * - id (optional)
     */
    public static List<GoldAmbiguityRow> loadAmbiguityGoldCsv(Path path) throws IOException, CsvException {
        List<GoldAmbiguityRow> result = new ArrayList<>();
        try (var reader = Files.newBufferedReader(path);
             var csvReader = new CSVReaderBuilder(reader).build()) {
            List<String[]> rows = csvReader.readAll();
            if (rows.isEmpty()) return result;
            String[] header = rows.get(0);
            int textIdx = indexOf(header, "text");
            int ambIdx = indexOf(header, "ambiguous");
            int idIdx = indexOf(header, "id");
            if (textIdx < 0) throw new IllegalArgumentException("Gold CSV must contain a 'text' column.");
            if (ambIdx < 0) throw new IllegalArgumentException("Gold CSV must contain an 'ambiguous' column.");

            for (int i = 1; i < rows.size(); i++) {
                String[] row = rows.get(i);
                if (row.length <= Math.max(textIdx, ambIdx)) continue;
                String text = safe(row, textIdx).trim();
                if (text.isEmpty()) continue;
                String rid = idIdx >= 0 ? safe(row, idIdx).trim() : ("R" + i);
                boolean ambiguous = parseBool(safe(row, ambIdx));
                result.add(new GoldAmbiguityRow(rid, text, ambiguous));
            }
        }
        return result;
    }

    private static String safe(String[] row, int idx) {
        return (idx >= 0 && idx < row.length && row[idx] != null) ? row[idx] : "";
    }

    private static int indexOf(String[] header, String col) {
        for (int i = 0; i < header.length; i++) {
            if (col.equalsIgnoreCase(header[i].trim())) return i;
        }
        return -1;
    }

    private static boolean parseBool(String raw) {
        String v = raw == null ? "" : raw.trim().toLowerCase();
        return v.equals("true") || v.equals("1") || v.equals("yes") || v.equals("y") || v.equals("t");
    }
}

