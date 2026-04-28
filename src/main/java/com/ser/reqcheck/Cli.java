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
            System.err.println("Usage: java ... --input <path> --format csv|txt --out <path> [--out-format md|json] [--out-lang en|tr|both] [--gold <gold.csv> --threshold <0..1>]");
            System.exit(2);
        }
        String inputPath = args[1];
        String formatStr = args[3];
        String outPath = args[5];
        String outFormat = "md";
        String outLang = "en";
        String goldPath = null;
        double threshold = 0.5;
        for (int i = 6; i + 1 < args.length; i += 2) {
            if ("--out-format".equals(args[i])) outFormat = args[i + 1];
            if ("--out-lang".equals(args[i])) outLang = args[i + 1];
            if ("--gold".equals(args[i])) goldPath = args[i + 1];
            if ("--threshold".equals(args[i])) threshold = Double.parseDouble(args[i + 1]);
        }

        RequirementsLoader.Format format = "txt".equalsIgnoreCase(formatStr) ? RequirementsLoader.Format.TXT : RequirementsLoader.Format.CSV;
        try {
            List<Requirement> reqs = RequirementsLoader.load(Path.of(inputPath), format);
            AnalysisResult result = Pipeline.analyze(reqs);
            List<AmbiguityRow> amb = result.ambiguity().stream()
                    .sorted(Comparator.<AmbiguityRow>comparingDouble(r -> -r.score()).thenComparing(AmbiguityRow::rid))
                    .toList();

            EvaluationResult eval = null;
            if (goldPath != null && !goldPath.isBlank()) {
                List<GoldAmbiguityRow> gold = GoldDatasetLoader.loadAmbiguityGoldCsv(Path.of(goldPath));
                // Evaluate on the same ordering (row-by-row).
                eval = AmbiguityEvaluator.evaluate(result.ambiguity(), gold, threshold);
            }

            Path out = Path.of(outPath);
            Files.createDirectories(out.getParent());
            if ("json".equalsIgnoreCase(outFormat)) {
                String content = ReportWriter.toJson(amb, result.conflicts());
                Files.writeString(out, content);
                return;
            }

            if ("both".equalsIgnoreCase(outLang)) {
                String en = ReportWriter.toMarkdownLocalized(amb, result.conflicts(), eval, ReportWriter.Lang.EN);
                String tr = ReportWriter.toMarkdownLocalized(amb, result.conflicts(), eval, ReportWriter.Lang.TR);
                Files.writeString(out, en);
                Files.writeString(siblingWithSuffix(out, "_tr"), tr);
            } else if ("tr".equalsIgnoreCase(outLang)) {
                String tr = ReportWriter.toMarkdownLocalized(amb, result.conflicts(), eval, ReportWriter.Lang.TR);
                Files.writeString(out, tr);
            } else {
                String en = ReportWriter.toMarkdownLocalized(amb, result.conflicts(), eval, ReportWriter.Lang.EN);
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
