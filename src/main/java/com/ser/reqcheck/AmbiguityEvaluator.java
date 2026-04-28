package com.ser.reqcheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;

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

    /**
     * Sweeps thresholds in [0,1] and returns the EvaluationResult with the best F1.
     * Tie-breakers (in order): higher F1, higher recall, higher precision, lower threshold.
     */
    public static EvaluationResult sweepBestF1(List<AmbiguityRow> predicted, List<GoldAmbiguityRow> gold, double step) {
        double s = (Double.isFinite(step) && step > 0.0) ? step : 0.05;
        // clamp to a sane range to avoid huge loops
        s = Math.max(0.0001, Math.min(0.5, s));

        EvaluationResult best = null;
        for (double t = 0.0; t <= 1.0000001; t += s) {
            double tau = Math.max(0.0, Math.min(1.0, t));
            EvaluationResult cur = evaluate(predicted, gold, tau);
            if (best == null || better(cur, best)) best = cur;
        }
        return best == null ? EvaluationResult.ofCounts(0, 0, 0, 0, 0.0, 0, 0) : best;
    }

    /**
     * Returns the top-K thresholds by the same ordering as {@link #sweepBestF1}.
     * This is useful to show trade-offs (precision/recall) in a report.
     */
    public static List<EvaluationResult> sweepTopK(List<AmbiguityRow> predicted, List<GoldAmbiguityRow> gold, double step, int k) {
        double s = (Double.isFinite(step) && step > 0.0) ? step : 0.05;
        s = Math.max(0.0001, Math.min(0.5, s));
        int topK = Math.max(1, Math.min(50, k));

        List<EvaluationResult> all = new ArrayList<>();
        for (double t = 0.0; t <= 1.0000001; t += s) {
            double tau = Math.max(0.0, Math.min(1.0, t));
            all.add(evaluate(predicted, gold, tau));
        }

        all.sort((a, b) -> {
            if (better(a, b)) return -1;
            if (better(b, a)) return 1;
            return 0;
        });
        return all.subList(0, Math.min(topK, all.size()));
    }

    private static boolean better(EvaluationResult a, EvaluationResult b) {
        if (a.f1() != b.f1()) return a.f1() > b.f1();
        if (a.recall() != b.recall()) return a.recall() > b.recall();
        if (a.precision() != b.precision()) return a.precision() > b.precision();
        return a.threshold() < b.threshold();
    }
}

