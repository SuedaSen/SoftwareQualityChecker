package com.ser.reqcheck;

public record EvaluationResult(
        int tp,
        int fp,
        int fn,
        int tn,
        double precision,
        double recall,
        double f1,
        double threshold,
        int evaluatedRows,
        int skippedRows
) {
    public static EvaluationResult ofCounts(int tp, int fp, int fn, int tn, double threshold, int evaluatedRows, int skippedRows) {
        double precision = (tp + fp) == 0 ? 0.0 : (double) tp / (tp + fp);
        double recall = (tp + fn) == 0 ? 0.0 : (double) tp / (tp + fn);
        double f1 = (precision + recall) == 0.0 ? 0.0 : (2.0 * precision * recall) / (precision + recall);
        return new EvaluationResult(tp, fp, fn, tn, precision, recall, f1, threshold, evaluatedRows, skippedRows);
    }
}

