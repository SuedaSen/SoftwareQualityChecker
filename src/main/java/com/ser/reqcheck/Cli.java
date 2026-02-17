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
            System.err.println("Usage: java ... --input <path> --format csv|txt --out <path> [--out-format md|json]");
            System.exit(2);
        }
        String inputPath = args[1];
        String formatStr = args[3];
        String outPath = args[5];
        String outFormat = "md";
        for (int i = 6; i + 1 < args.length; i += 2) {
            if ("--out-format".equals(args[i])) outFormat = args[i + 1];
        }

        RequirementsLoader.Format format = "txt".equalsIgnoreCase(formatStr) ? RequirementsLoader.Format.TXT : RequirementsLoader.Format.CSV;
        try {
            List<Requirement> reqs = RequirementsLoader.load(Path.of(inputPath), format);
            AnalysisResult result = Pipeline.analyze(reqs);
            List<AmbiguityRow> amb = result.ambiguity().stream()
                    .sorted(Comparator.<AmbiguityRow>comparingDouble(r -> -r.score()).thenComparing(AmbiguityRow::rid))
                    .toList();
            Path out = Path.of(outPath);
            Files.createDirectories(out.getParent());
            String content = "json".equalsIgnoreCase(outFormat)
                    ? ReportWriter.toJson(amb, result.conflicts())
                    : ReportWriter.toMarkdown(amb, result.conflicts());
            Files.writeString(out, content);
        } catch (IOException | CsvException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
