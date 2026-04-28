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
                    "[--gold <gold.csv> --threshold <0..1>] [--sweep [--sweep-step <step>]]");
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
            i += 2;
        }

        RequirementsLoader.Format format = "txt".equalsIgnoreCase(formatStr) ? RequirementsLoader.Format.TXT : RequirementsLoader.Format.CSV;
        try {
            List<Requirement> reqs = RequirementsLoader.load(Path.of(inputPath), format);
            AnalysisResult result = Pipeline.analyze(reqs);
            List<AmbiguityRow> amb = result.ambiguity().stream()
                    .sorted(Comparator.<AmbiguityRow>comparingDouble(r -> -r.score()).thenComparing(AmbiguityRow::rid))
                    .toList();

            EvaluationResult eval = null;
            EvaluationResult bestEval = null;
            List<EvaluationResult> topSweep = null;
            if (goldPath != null && !goldPath.isBlank()) {
                List<GoldAmbiguityRow> gold = GoldDatasetLoader.loadAmbiguityGoldCsv(Path.of(goldPath));
                eval = AmbiguityEvaluator.evaluate(result.ambiguity(), gold, threshold);
                if (sweep) {
                    bestEval = AmbiguityEvaluator.sweepBestF1(result.ambiguity(), gold, sweepStep);
                    topSweep = AmbiguityEvaluator.sweepTopK(result.ambiguity(), gold, sweepStep, 5);
                }
            }

            Path out = Path.of(outPath);
            Files.createDirectories(out.getParent());
            if ("json".equalsIgnoreCase(outFormat)) {
                String content = ReportWriter.toJson(amb, result.conflicts());
                Files.writeString(out, content);
                return;
            }

            if ("both".equalsIgnoreCase(outLang)) {
                String en = ReportWriter.toMarkdownLocalized(amb, result.conflicts(), eval, bestEval, topSweep, ReportWriter.Lang.EN);
                String tr = ReportWriter.toMarkdownLocalized(amb, result.conflicts(), eval, bestEval, topSweep, ReportWriter.Lang.TR);
                Files.writeString(out, en);
                Files.writeString(siblingWithSuffix(out, "_tr"), tr);
            } else if ("tr".equalsIgnoreCase(outLang)) {
                String tr = ReportWriter.toMarkdownLocalized(amb, result.conflicts(), eval, bestEval, topSweep, ReportWriter.Lang.TR);
                Files.writeString(out, tr);
            } else {
                String en = ReportWriter.toMarkdownLocalized(amb, result.conflicts(), eval, bestEval, topSweep, ReportWriter.Lang.EN);
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
