package com.ser.reqcheck;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class ReportWriter {

    public enum Lang { EN, TR }

    public static String toMarkdown(List<AmbiguityRow> ambiguity, List<ConflictCandidate> conflicts) {
        return toMarkdown(ambiguity, conflicts, null);
    }

    public static String toMarkdown(List<AmbiguityRow> ambiguity, List<ConflictCandidate> conflicts, EvaluationResult eval) {
        return toMarkdownLocalized(ambiguity, conflicts, eval, Lang.EN);
    }

    public static String toMarkdownLocalized(List<AmbiguityRow> ambiguity, List<ConflictCandidate> conflicts, EvaluationResult eval, Lang lang) {
        StringBuilder sb = new StringBuilder();

        String title = (lang == Lang.TR) ? "Gereksinim Kalite Raporu" : "Requirement Quality Report";
        String evalTitle = (lang == Lang.TR) ? "Değerlendirme (Belirsizlik)" : "Evaluation (Ambiguity)";
        String ambTitle = (lang == Lang.TR) ? "Belirsizlik bulguları" : "Ambiguity findings";
        String confTitle = (lang == Lang.TR) ? "Tutarsızlık adayları" : "Inconsistency candidates";
        String noConf = (lang == Lang.TR) ? "Aday tutarsızlık bulunmadı." : "No candidate conflicts found.";

        String thId = (lang == Lang.TR) ? "ID" : "ID";
        String thScore = (lang == Lang.TR) ? "Skor" : "Score";
        String thText = (lang == Lang.TR) ? "Metin" : "Text";
        String thReasons = (lang == Lang.TR) ? "Gerekçe" : "Reasons";
        String thLeft = (lang == Lang.TR) ? "Sol" : "Left";
        String thRight = (lang == Lang.TR) ? "Sağ" : "Right";
        String thSim = (lang == Lang.TR) ? "Benzerlik" : "Similarity";
        String thKind = (lang == Lang.TR) ? "Tür" : "Kind";
        String thEvidence = (lang == Lang.TR) ? "Kanıt" : "Evidence";

        sb.append("# ").append(title).append("\n\n");

        if (eval != null) {
            sb.append("## ").append(evalTitle).append("\n\n");
            sb.append("- ").append((lang == Lang.TR) ? "Eşik (threshold)" : "Threshold").append(": ").append(String.format("%.2f", eval.threshold())).append("\n");
            sb.append("- Precision: ").append(String.format("%.3f", eval.precision())).append("\n");
            sb.append("- Recall: ").append(String.format("%.3f", eval.recall())).append("\n");
            sb.append("- F1: ").append(String.format("%.3f", eval.f1())).append("\n");
            sb.append("- TP/FP/FN/TN: ").append(eval.tp()).append("/")
                    .append(eval.fp()).append("/")
                    .append(eval.fn()).append("/")
                    .append(eval.tn()).append("\n");
            sb.append("- ").append((lang == Lang.TR) ? "Değerlendirilen satır" : "Evaluated rows").append(": ")
                    .append(eval.evaluatedRows())
                    .append(" (").append((lang == Lang.TR) ? "atlanmış" : "skipped").append(": ").append(eval.skippedRows()).append(")\n\n");
        }

        sb.append("## ").append(ambTitle).append("\n\n");
        sb.append("| ").append(thId).append(" | ").append(thScore).append(" | ").append(thText).append(" | ").append(thReasons).append(" |\n");
        sb.append("|---|---:|---|---|\n");
        ambiguity.stream()
                .sorted(Comparator.<AmbiguityRow>comparingDouble(r -> -r.score()).thenComparing(AmbiguityRow::rid))
                .forEach(r -> {
                    String text = r.text().replace("\n", " ").trim();
                    String reasons = String.join("; ", r.reasons());
                    sb.append("| ").append(r.rid()).append(" | ").append(String.format("%.2f", r.score()))
                            .append(" | ").append(text).append(" | ").append(reasons).append(" |\n");
                });

        sb.append("\n## ").append(confTitle).append("\n\n");
        if (conflicts.isEmpty()) {
            sb.append(noConf).append("\n");
            return sb.toString();
        }
        sb.append("| ").append(thLeft).append(" | ").append(thRight).append(" | ").append(thSim).append(" | ").append(thKind).append(" | ").append(thEvidence).append(" |\n");
        sb.append("|---|---|---:|---|---|\n");
        for (ConflictCandidate c : conflicts) {
            sb.append("| ").append(c.leftId()).append(" | ").append(c.rightId())
                    .append(" | ").append(String.format("%.2f", c.similarity()))
                    .append(" | ").append(c.kind()).append(" | ").append(c.evidence()).append(" |\n");
        }
        return sb.toString();
    }

    public static String toJson(List<AmbiguityRow> ambiguity, List<ConflictCandidate> conflicts) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n  \"ambiguity\": [\n");
        String amb = ambiguity.stream()
                .map(r -> "    {\"rid\":\"" + escape(r.rid()) + "\",\"text\":\"" + escape(r.text()) + "\",\"score\":" + r.score() + ",\"reasons\":" + jsonList(r.reasons()) + "}")
                .collect(Collectors.joining(",\n"));
        sb.append(amb).append("\n  ],\n  \"conflicts\": [\n");
        String conf = conflicts.stream()
                .map(c -> "    {\"leftId\":\"" + escape(c.leftId()) + "\",\"rightId\":\"" + escape(c.rightId()) + "\",\"similarity\":" + c.similarity() + ",\"kind\":\"" + escape(c.kind()) + "\",\"evidence\":\"" + escape(c.evidence()) + "\"}")
                .collect(Collectors.joining(",\n"));
        sb.append(conf).append("\n  ]\n}\n");
        return sb.toString();
    }

    private static String jsonList(List<String> list) {
        return list.stream().map(s -> "\"" + escape(s) + "\"").collect(Collectors.joining(",", "[", "]"));
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }
}
