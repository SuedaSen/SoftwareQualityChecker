package com.ser.reqcheck;

import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

public final class Cli {

    public static void main(String[] args) {
        if (args.length < 6 || !"--input".equals(args[0]) || !"--format".equals(args[2]) || !"--out".equals(args[4])) {
            System.err.println("Usage: java ... --input <path> --format csv|txt --out <path> " +
                    "[--out-format md|json] [--out-lang en|tr|both] " +
                    "[--gold <gold.csv> --threshold <0..1>] [--sweep [--sweep-step <step>]] " +
                    "[--max-amb-rows <n>] [--max-conf-rows <n>] [--max-text-len <n>]");
            System.exit(2);
        }
        String inputPath = args[1];
        String formatStr = args[3];
        String outPath = args[5];
        String outFormat = "md";
        String outLang = "en";
        String goldPath = null;
        double threshold = 0.5;
        boolean sweep = false;
        double sweepStep = 0.05;
        int maxAmbRows = 500;
        int maxConfRows = 500;
        int maxTextLen = 400;
        for (int i = 6; i < args.length; ) {
            String a = args[i];
            if ("--sweep".equals(a)) {
                sweep = true;
                i += 1;
                continue;
            }
            if (i + 1 >= args.length) break;
            String v = args[i + 1];
            if ("--out-format".equals(a)) outFormat = v;
            else if ("--out-lang".equals(a)) outLang = v;
            else if ("--gold".equals(a)) goldPath = v;
            else if ("--threshold".equals(a)) threshold = Double.parseDouble(v);
            else if ("--sweep-step".equals(a)) sweepStep = Double.parseDouble(v);
            else if ("--max-amb-rows".equals(a)) maxAmbRows = Integer.parseInt(v);
            else if ("--max-conf-rows".equals(a)) maxConfRows = Integer.parseInt(v);
            else if ("--max-text-len".equals(a)) maxTextLen = Integer.parseInt(v);
            i += 2;
        }

        RequirementsLoader.Format format = "txt".equalsIgnoreCase(formatStr) ? RequirementsLoader.Format.TXT : RequirementsLoader.Format.CSV;
        try {
            List<Requirement> reqs = RequirementsLoader.load(Path.of(inputPath), format);
            AnalysisResult result = Pipeline.analyze(reqs);
            List<AmbiguityRow> ambAll = result.ambiguity().stream()
                    .sorted(Comparator.<AmbiguityRow>comparingDouble(r -> -r.score()).thenComparing(AmbiguityRow::rid))
                    .toList();
            List<AmbiguityRow> amb = ambAll.stream().limit(Math.max(1, maxAmbRows)).toList();
            List<ConflictCandidate> confAll = result.conflicts();
            List<ConflictCandidate> conf = confAll.stream().limit(Math.max(1, maxConfRows)).toList();

            EvaluationResult eval = null;
            EvaluationResult bestEval = null;
            List<EvaluationResult> topSweep = null;
            if (goldPath != null && !goldPath.isBlank()) {
                List<GoldAmbiguityRow> gold = GoldDatasetLoader.loadAmbiguityGoldCsv(Path.of(goldPath));
                eval = AmbiguityEvaluator.evaluate(ambAll, gold, threshold);
                if (sweep) {
                    bestEval = AmbiguityEvaluator.sweepBestF1(ambAll, gold, sweepStep);
                    topSweep = AmbiguityEvaluator.sweepTopK(ambAll, gold, sweepStep, 5);
                }
            }

            Path out = Path.of(outPath);
            Files.createDirectories(out.getParent());
            if ("json".equalsIgnoreCase(outFormat)) {
                String content = ReportWriter.toJson(amb, conf);
                Files.writeString(out, content);
                return;
            }

            if ("both".equalsIgnoreCase(outLang)) {
                String en = ReportWriter.toMarkdownLocalized(amb, conf, eval, bestEval, topSweep, maxTextLen, ambAll.size(), confAll.size(), ReportWriter.Lang.EN);
                String tr = ReportWriter.toMarkdownLocalized(amb, conf, eval, bestEval, topSweep, maxTextLen, ambAll.size(), confAll.size(), ReportWriter.Lang.TR);
                Files.writeString(out, en);
                Files.writeString(siblingWithSuffix(out, "_tr"), tr);
            } else if ("tr".equalsIgnoreCase(outLang)) {
                String tr = ReportWriter.toMarkdownLocalized(amb, conf, eval, bestEval, topSweep, maxTextLen, ambAll.size(), confAll.size(), ReportWriter.Lang.TR);
                Files.writeString(out, tr);
            } else {
                String en = ReportWriter.toMarkdownLocalized(amb, conf, eval, bestEval, topSweep, maxTextLen, ambAll.size(), confAll.size(), ReportWriter.Lang.EN);
                Files.writeString(out, en);
            }
        } catch (IOException | CsvException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private static Path siblingWithSuffix(Path out, String suffix) {
        String name = out.getFileName().toString();
        int dot = name.lastIndexOf('.');
        String newName = dot > 0
                ? name.substring(0, dot) + suffix + name.substring(dot)
                : name + suffix;
        return out.getParent() != null ? out.getParent().resolve(newName) : Path.of(newName);
    }
}
