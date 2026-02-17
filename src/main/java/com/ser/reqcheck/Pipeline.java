package com.ser.reqcheck;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public final class Pipeline {

    private static final double MIN_SIM = 0.45;
    private static final double HIGH_SIM = 0.65;

    private static final WordVectorStore WORD_VECTORS = loadWordVectors();

    private static WordVectorStore loadWordVectors() {
        try (InputStream in = Pipeline.class.getResourceAsStream("/wordvectors.txt")) {
            WordVectorStore s = WordVectorStore.loadFromStream(in);
            return s != null && s.size() > 0 ? s : null;
        } catch (Exception e) {
            return null;
        }
    }

    public static AnalysisResult analyze(List<Requirement> requirements) {
        List<AmbiguityRow> ambiguity = new ArrayList<>();
        for (Requirement r : requirements) {
            AmbiguityFinding finding = AmbiguityAnalyzer.analyze(r.text());
            ambiguity.add(new AmbiguityRow(r.rid(), r.text(), finding.score(), finding.reasons()));
        }
        List<String> texts = requirements.stream().map(Requirement::text).toList();
        double[][] simMatrix = EmbeddingSimilarity.similarityMatrix(texts, WORD_VECTORS);
        if (simMatrix == null) simMatrix = TfIdfCosine.similarityMatrix(texts);
        List<ConflictCandidate> conflicts = ConflictFinder.findConflicts(requirements, MIN_SIM, HIGH_SIM, simMatrix);
        return new AnalysisResult(List.copyOf(ambiguity), conflicts);
    }

    /** Whether ML (word-embedding) similarity is in use. */
    public static boolean isMlSimilarityEnabled() {
        return WORD_VECTORS != null;
    }
}
