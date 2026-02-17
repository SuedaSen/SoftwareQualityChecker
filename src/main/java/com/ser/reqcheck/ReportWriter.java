package com.ser.reqcheck;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class ReportWriter {

    public static String toMarkdown(List<AmbiguityRow> ambiguity, List<ConflictCandidate> conflicts) {
        StringBuilder sb = new StringBuilder();
        sb.append("# Requirement Quality Report\n\n## Ambiguity findings\n\n");
        sb.append("| ID | Score | Text | Reasons |\n|---|---:|---|---|\n");
        ambiguity.stream()
                .sorted(Comparator.<AmbiguityRow>comparingDouble(r -> -r.score()).thenComparing(AmbiguityRow::rid))
                .forEach(r -> {
                    String text = r.text().replace("\n", " ").trim();
                    String reasons = String.join("; ", r.reasons());
                    sb.append("| ").append(r.rid()).append(" | ").append(String.format("%.2f", r.score()))
                            .append(" | ").append(text).append(" | ").append(reasons).append(" |\n");
                });
        sb.append("\n## Inconsistency candidates\n\n");
        if (conflicts.isEmpty()) {
            sb.append("No candidate conflicts found.\n");
            return sb.toString();
        }
        sb.append("| Left | Right | Similarity | Kind | Evidence |\n|---|---|---:|---|---|\n");
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
