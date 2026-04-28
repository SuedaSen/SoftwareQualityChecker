package com.ser.reqcheck;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AmbiguityEvaluator {

    /** Evaluates ambiguity detection by thresholding `AmbiguityRow.score`. */
    public static EvaluationResult evaluate(List<AmbiguityRow> predicted, List<GoldAmbiguityRow> gold, double threshold) {
        Map<String, Boolean> goldById = new HashMap<>();
        for (GoldAmbiguityRow g : gold) goldById.put(g.rid(), g.ambiguous());

        int tp = 0, fp = 0, fn = 0, tn = 0;
        int evaluated = 0;
        int skipped = 0;
        for (AmbiguityRow p : predicted) {
            Boolean goldAmbBoxed = goldById.get(p.rid());
            if (goldAmbBoxed == null) {
                skipped++;
                continue;
            }

            boolean goldAmb = goldAmbBoxed;
            boolean predAmb = p.score() >= threshold;
            evaluated++;

            if (predAmb && goldAmb) tp++;
            else if (predAmb) fp++;
            else if (goldAmb) fn++;
            else tn++;
        }
        return EvaluationResult.ofCounts(tp, fp, fn, tn, threshold, evaluated, skipped);
    }
}

