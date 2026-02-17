package com.ser.reqcheck;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class AmbiguityAnalyzer {

    private static final Pattern[] OPTIONALITY = compile(
            "\\bmay\\b", "\\bmight\\b", "\\bcan\\b", "\\bcould\\b", "\\bshould\\b",
            "\\bpreferably\\b", "\\bif possible\\b", "\\bwhere applicable\\b");
    private static final Pattern[] VAGUE = compile(
            "\\bfast\\b", "\\befficient\\b", "\\buser[- ]friendly\\b", "\\bintuitive\\b",
            "\\beasy to use\\b", "\\brobust\\b", "\\bsecure\\b", "\\bscalable\\b", "\\bhigh quality\\b");
    private static final Pattern[] OPEN_ENDED = compile(
            "\\bas needed\\b", "\\bwhen necessary\\b", "\\bfrom time to time\\b",
            "\\bregularly\\b", "\\bperiodically\\b", "\\bsoon\\b", "\\bimmediately\\b");
    private static final Pattern PASSIVE = Pattern.compile(
            "\\b(shall|is|are|was|were)\\s+be\\s+\\w+ed\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern PRONOUN = Pattern.compile(
            "\\b(it|they|this|that|these|those)\\b", Pattern.CASE_INSENSITIVE);

    private static Pattern[] compile(String... regexes) {
        Pattern[] out = new Pattern[regexes.length];
        for (int i = 0; i < regexes.length; i++)
            out[i] = Pattern.compile(regexes[i], Pattern.CASE_INSENSITIVE);
        return out;
    }

    public static AmbiguityFinding analyze(String text) {
        if (text == null || text.isBlank())
            return new AmbiguityFinding(0.0, List.of("Empty requirement text."));
        String t = text.trim();
        List<String> reasons = new ArrayList<>();
        double score = 0.0;

        if (matchesAny(OPTIONALITY, t)) {
            score += 0.35;
            reasons.add("Optionality/weak modal verbs (e.g., may/should/could).");
        }
        if (matchesAny(VAGUE, t)) {
            score += 0.25;
            reasons.add("Vague quality adjectives (e.g., fast/intuitive/robust).");
        }
        if (matchesAny(OPEN_ENDED, t)) {
            score += 0.2;
            reasons.add("Open-ended temporal phrases (e.g., as needed/immediately).");
        }
        if (PASSIVE.matcher(t).find()) {
            score += 0.1;
            reasons.add("Possible passive voice (may hide actor).");
        }
        var pronounMatcher = PRONOUN.matcher(t);
        int pronouns = 0;
        while (pronounMatcher.find()) pronouns++;
        if (pronouns >= 2 || (pronouns == 1 && t.split("\\s+").length <= 10)) {
            score += 0.1;
            reasons.add("Potentially ambiguous pronouns (it/this/they).");
        }
        score = Math.max(0.0, Math.min(1.0, score));
        if (reasons.isEmpty()) reasons.add("No common ambiguity cues detected.");
        return new AmbiguityFinding(score, List.copyOf(reasons));
    }

    private static boolean matchesAny(Pattern[] patterns, String text) {
        for (Pattern p : patterns)
            if (p.matcher(text).find()) return true;
        return false;
    }
}
