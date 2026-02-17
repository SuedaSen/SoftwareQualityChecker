package com.ser.reqcheck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * GloVe-style word vectors (one line per word: "word v1 v2 ...").
 * Used for ML-based semantic similarity when available.
 */
public final class WordVectorStore {

    private static final Pattern SPACE = Pattern.compile("\\s+");
    private final Map<String, float[]> vectors;
    private final int dimension;

    public WordVectorStore(Map<String, float[]> vectors, int dimension) {
        this.vectors = Map.copyOf(vectors);
        this.dimension = dimension;
    }

    public float[] get(String word) {
        return vectors.get(word != null ? word.toLowerCase() : null);
    }

    public int dimension() { return dimension; }
    public int size() { return vectors.size(); }

    /**
     * Load from GloVe-style text (lines: "word v1 v2 ...").
     * Returns empty optional on any error.
     */
    public static WordVectorStore load(Path path) {
        if (path == null || !Files.isRegularFile(path)) return null;
        try (var reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            return parse(reader);
        } catch (IOException e) {
            return null;
        }
    }

    public static WordVectorStore loadFromStream(InputStream in) {
        if (in == null) return null;
        try (var reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            return parse(reader);
        } catch (IOException e) {
            return null;
        }
    }

    private static WordVectorStore parse(BufferedReader reader) throws IOException {
        Map<String, float[]> vectors = new HashMap<>();
        int dim = -1;
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;
            String[] parts = SPACE.split(line, 2);
            if (parts.length < 2) continue;
            String[] values = SPACE.split(parts[1].trim());
            float[] vec = new float[values.length];
            for (int i = 0; i < values.length; i++) {
                try {
                    vec[i] = Float.parseFloat(values[i]);
                } catch (NumberFormatException e) {
                    continue;
                }
            }
            if (dim < 0) dim = vec.length;
            if (vec.length == dim) vectors.put(parts[0].toLowerCase(), vec);
        }
        if (vectors.isEmpty() || dim <= 0) return null;
        return new WordVectorStore(vectors, dim);
    }
}
