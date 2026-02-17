package com.ser.reqcheck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * ML-based similarity: sentence = average of word vectors (GloVe-style), then cosine.
 * Falls back to zero similarity for sentences with no known words.
 */
public final class EmbeddingSimilarity {

    private static final Pattern WORD = Pattern.compile("\\W+");
    private static final Set<String> STOP = Set.of(
            "a", "an", "the", "and", "or", "in", "on", "at", "to", "for", "of", "with", "by",
            "is", "are", "be", "it", "its", "this", "that");

    /**
     * Returns similarity matrix when store is available; otherwise null (caller uses TF-IDF).
     */
    public static double[][] similarityMatrix(List<String> texts, WordVectorStore store) {
        if (store == null || store.size() == 0) return null;
        List<float[]> embeddings = new ArrayList<>(texts.size());
        for (String text : texts) embeddings.add(sentenceEmbedding(text, store));
        int n = embeddings.size();
        double[][] S = new double[n][n];
        for (int i = 0; i < n; i++) {
            S[i][i] = 1.0;
            for (int j = i + 1; j < n; j++) {
                double sim = cosine(embeddings.get(i), embeddings.get(j));
                S[i][j] = S[j][i] = sim;
            }
        }
        return S;
    }

    private static float[] sentenceEmbedding(String text, WordVectorStore store) {
        if (text == null || text.isBlank()) return zeroVec(store.dimension());
        List<String> words = Arrays.stream(WORD.split(text.toLowerCase()))
                .filter(w -> w.length() > 1 && !STOP.contains(w))
                .distinct()
                .toList();
        float[] sum = new float[store.dimension()];
        int count = 0;
        for (String w : words) {
            float[] v = store.get(w);
            if (v != null && v.length == sum.length) {
                for (int i = 0; i < sum.length; i++) sum[i] += v[i];
                count++;
            }
        }
        if (count == 0) return zeroVec(store.dimension());
        for (int i = 0; i < sum.length; i++) sum[i] /= count;
        return l2Normalize(sum);
    }

    private static float[] zeroVec(int dim) {
        float[] z = new float[dim];
        z[0] = 1f;
        return l2Normalize(z);
    }

    private static float[] l2Normalize(float[] v) {
        double norm = 0;
        for (float x : v) norm += x * x;
        norm = Math.sqrt(norm);
        if (norm <= 0) return v;
        float[] out = new float[v.length];
        for (int i = 0; i < v.length; i++) out[i] = (float) (v[i] / norm);
        return out;
    }

    private static double cosine(float[] a, float[] b) {
        double dot = 0;
        for (int i = 0; i < a.length; i++) dot += a[i] * b[i];
        return Math.max(0, Math.min(1, dot));
    }
}
