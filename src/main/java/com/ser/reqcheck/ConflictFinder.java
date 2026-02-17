package com.ser.reqcheck;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class ConflictFinder {

    private static final Pattern[] NEGATORS = {
            Pattern.compile("\\bshall not\\b", Pattern.CASE_INSENSITIVE),
            Pattern.compile("\\bmust not\\b", Pattern.CASE_INSENSITIVE),
            Pattern.compile("\\bshould not\\b", Pattern.CASE_INSENSITIVE),
            Pattern.compile("\\bnot be\\b", Pattern.CASE_INSENSITIVE),
            Pattern.compile("\\bnot possible\\b", Pattern.CASE_INSENSITIVE),
            Pattern.compile("\\bno\\b", Pattern.CASE_INSENSITIVE),
            Pattern.compile("\\bnever\\b", Pattern.CASE_INSENSITIVE)
    };
    private static final Pattern PERCENT = Pattern.compile("(\\d+(?:\\.\\d+)?)\\s*%");
    private static final Pattern TIME_UNIT = Pattern.compile(
            "(\\d+(?:\\.\\d+)?)\\s*(ms|milliseconds?|s|sec(?:ond)?s?|minutes?|min|hours?|hrs?|days?|weeks?|months?|years?)",
            Pattern.CASE_INSENSITIVE);

    public static List<ConflictCandidate> findConflicts(
            List<Requirement> requirements,
            double minSimilarity,
            double highSimilarity) {
        return findConflicts(requirements, minSimilarity, highSimilarity, null);
    }

    /**
     * @param similarityMatrix if null, TF-IDF cosine is used (rule-based fallback)
     */
    public static List<ConflictCandidate> findConflicts(
            List<Requirement> requirements,
            double minSimilarity,
            double highSimilarity,
            double[][] similarityMatrix) {
        if (requirements.size() < 2) return List.of();
        List<String> ids = requirements.stream().map(Requirement::rid).toList();
        List<String> texts = requirements.stream().map(Requirement::text).toList();
        double[][] S = similarityMatrix != null ? similarityMatrix : TfIdfCosine.similarityMatrix(texts);

        List<ConflictCandidate> candidates = new ArrayList<>();
        int n = requirements.size();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double sim = S[i][j];
                if (sim < minSimilarity) continue;
                String ida = ids.get(i), idb = ids.get(j);
                String a = texts.get(i), b = texts.get(j);

                boolean negA = hasNegation(a), negB = hasNegation(b);
                if (negA != negB) {
                    candidates.add(new ConflictCandidate(ida, idb, sim, "negation_conflict",
                            "One contains negation, the other does not."));
                }
                Set<String> numsA = extractNumbers(a), numsB = extractNumbers(b);
                if (!numsA.isEmpty() && !numsB.isEmpty() && !numsA.equals(numsB)) {
                    candidates.add(new ConflictCandidate(ida, idb, sim, "numeric_conflict",
                            "Different numbers: " + sorted(numsA) + " vs " + sorted(numsB)));
                }
                if (sim >= highSimilarity) {
                    candidates.add(new ConflictCandidate(ida, idb, sim, "high_similarity_review",
                            "High textual similarity; review for redundancy/contradiction."));
                }
            }
        }
        Map<String, ConflictCandidate> uniq = new LinkedHashMap<>();
        for (ConflictCandidate c : candidates) {
            String key = c.leftId() + "|" + c.rightId() + "|" + c.kind();
            if (!uniq.containsKey(key) || uniq.get(key).similarity() < c.similarity())
                uniq.put(key, c);
        }
        return uniq.values().stream()
                .sorted(Comparator.<ConflictCandidate>comparingDouble(c -> -c.similarity())
                        .thenComparing(ConflictCandidate::kind)
                        .thenComparing(ConflictCandidate::leftId)
                        .thenComparing(ConflictCandidate::rightId))
                .toList();
    }

    private static boolean hasNegation(String text) {
        for (Pattern p : NEGATORS)
            if (p.matcher(text).find()) return true;
        return false;
    }

    private static Set<String> extractNumbers(String text) {
        Set<String> out = new HashSet<>();
        String lower = text.toLowerCase();
        Matcher m = PERCENT.matcher(lower);
        while (m.find()) out.add(m.group(1) + "%");
        m = TIME_UNIT.matcher(lower);
        while (m.find()) out.add(m.group(1) + m.group(2));
        return out;
    }

    private static String sorted(Set<String> set) {
        return set.stream().sorted().collect(Collectors.joining(", ", "[", "]"));
    }
}
