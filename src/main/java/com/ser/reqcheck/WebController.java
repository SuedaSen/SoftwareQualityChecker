package com.ser.reqcheck;

import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WebController {

    @GetMapping("/")
    public String index(Model model) {
        String defaultText = String.join("\n",
                "The system shall respond to user requests within 2 seconds under normal load.",
                "The system shall respond to user requests within 5 seconds under normal load.",
                "The system should provide fast performance.",
                "Password reset shall not be possible via email.",
                "The system shall allow password reset via email.");
        model.addAttribute("defaultText", defaultText);
        model.addAttribute("result", null);
        model.addAttribute("mlEnabled", Pipeline.isMlSimilarityEnabled());
        return "index";
    }

    @PostMapping("/analyze")
    public String analyze(
            @RequestParam(value = "requirements", required = false) String requirements,
            @RequestParam(value = "file", required = false) MultipartFile file,
            Model model) throws IOException, CsvException {

        List<Requirement> reqs = new ArrayList<>();

        if (file != null && !file.isEmpty()) {
            String name = file.getOriginalFilename();
            String lower = name != null ? name.toLowerCase() : "";
            RequirementsLoader.Format format;
            String suffix;
            if (lower.endsWith(".csv")) {
                format = RequirementsLoader.Format.CSV;
                suffix = ".csv";
            } else if (lower.endsWith(".doc") || lower.endsWith(".docx")) {
                format = RequirementsLoader.Format.DOC;
                suffix = lower.endsWith(".docx") ? ".docx" : ".doc";
            } else if (lower.endsWith(".pdf")) {
                format = RequirementsLoader.Format.PDF;
                suffix = ".pdf";
            } else {
                format = RequirementsLoader.Format.TXT;
                suffix = ".txt";
            }
            Path temp = Files.createTempFile("reqcheck-", suffix);
            try {
                file.transferTo(temp);
                reqs = RequirementsLoader.load(temp, format);
            } finally {
                Files.deleteIfExists(temp);
            }
        }

        if (reqs.isEmpty() && requirements != null) {
            // Metin alanından satır satır al
            for (String line : requirements.split("\n")) {
                String t = line.trim();
                if (!t.isEmpty()) reqs.add(new Requirement("R" + (reqs.size() + 1), t));
            }
        }

        // Textarea'da gösterilecek metin (yüklenen veya girilen)
        String defaultText = reqs.stream()
                .map(Requirement::text)
                .collect(Collectors.joining("\n"));
        if (defaultText.isEmpty() && requirements != null)
            defaultText = requirements;
        model.addAttribute("defaultText", defaultText);

        if (reqs.isEmpty()) {
            model.addAttribute("result", new ViewResult(List.of(), List.of(), reqs));
            model.addAttribute("requirementCount", 0);
            model.addAttribute("mlEnabled", Pipeline.isMlSimilarityEnabled());
            return "index";
        }

        AnalysisResult result = Pipeline.analyze(reqs);
        List<AmbiguityRow> sortedAmb = result.ambiguity().stream()
                .sorted(Comparator.<AmbiguityRow>comparingDouble(r -> -r.score()).thenComparing(AmbiguityRow::rid))
                .collect(Collectors.toList());
        model.addAttribute("result", new ViewResult(sortedAmb, result.conflicts(), reqs));
        model.addAttribute("requirementCount", reqs.size());
        model.addAttribute("mlEnabled", Pipeline.isMlSimilarityEnabled());
        return "index";
    }
}
