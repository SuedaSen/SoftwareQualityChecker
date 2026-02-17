package com.ser.reqcheck;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

final class TfIdfCosine {

    private static final Set<String> STOP = Set.of(
            "a", "an", "the", "and", "or", "but", "in", "on", "at", "to", "for", "of", "with", "by", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "do", "does", "did", "will", "would", "could", "should", "may", "might", "must", "shall", "can", "this", "that", "these", "those", "it", "its");

    static double[][] similarityMatrix(List<String> texts) {
        List<Map<String, Double>> tfidf = tfidfVectors(texts);
        int n = tfidf.size();
        double[][] S = new double[n][n];
        for (int i = 0; i < n; i++) {
            S[i][i] = 1.0;
            for (int j = i + 1; j < n; j++) {
                double sim = cosine(tfidf.get(i), tfidf.get(j));
                S[i][j] = S[j][i] = sim;
            }
        }
        return S;
    }

    private static List<Map<String, Double>> tfidfVectors(List<String> texts) {
        List<List<String>> tokenized = texts.stream()
                .map(TfIdfCosine::tokenize)
                .toList();
        int N = texts.size();
        Map<String, Integer> df = new HashMap<>();
        for (List<String> doc : tokenized) {
            for (String t : new HashSet<>(doc))
                df.merge(t, 1, Integer::sum);
        }
        List<Map<String, Double>> vectors = new ArrayList<>();
        for (List<String> doc : tokenized) {
            Map<String, Double> tf = new HashMap<>();
            for (String t : doc)
                tf.merge(t, 1.0, Double::sum);
            Map<String, Double> vec = new HashMap<>();
            for (Map.Entry<String, Double> e : tf.entrySet()) {
                String t = e.getKey();
                double idf = Math.log((N + 1.0) / (df.getOrDefault(t, 0) + 1.0)) + 1.0;
                vec.put(t, e.getValue() * idf);
            }
            vectors.add(vec);
        }
        return vectors;
    }

    private static List<String> tokenize(String text) {
        if (text == null || text.isBlank()) return List.of();
        String[] words = Pattern.compile("\\W+").split(text.toLowerCase());
        return Arrays.stream(words)
                .filter(w -> w.length() > 1 && !STOP.contains(w))
                .collect(Collectors.toList());
    }

    private static double cosine(Map<String, Double> a, Map<String, Double> b) {
        double dot = 0, normA = 0, normB = 0;
        Set<String> all = new HashSet<>(a.keySet());
        all.addAll(b.keySet());
        for (String k : all) {
            double va = a.getOrDefault(k, 0.0);
            double vb = b.getOrDefault(k, 0.0);
            dot += va * vb;
            normA += va * va;
            normB += vb * vb;
        }
        if (normA == 0 || normB == 0) return 0.0;
        return dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
