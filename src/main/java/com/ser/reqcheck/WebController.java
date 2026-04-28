package com.ser.reqcheck;

import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class WebController {

    private static final SimpleRateLimiter EXPLAIN_LIMITER = new SimpleRateLimiter(
            parseIntEnv("EXPLAIN_RPM", 30)
    );

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

    @PostMapping("/explain")
    @ResponseBody
    public Map<String, Object> explain(@RequestBody Map<String, Object> body, HttpServletRequest request) {
        String prompt = body == null ? null : (String) body.get("prompt");
        if (prompt == null || prompt.isBlank()) {
            return Map.of("ok", false, "error", "Missing prompt.");
        }
        if (prompt.length() > 12000) {
            return Map.of("ok", false, "error", "Prompt too long.");
        }

        // Optional token guard (recommended for public deployments)
        String requiredToken = System.getenv("EXPLAIN_TOKEN");
        if (requiredToken != null && !requiredToken.isBlank()) {
            String provided = request.getHeader("X-Explain-Token");
            if (provided == null || !provided.equals(requiredToken)) {
                return Map.of("ok", false, "error", "Unauthorized: missing/invalid access code.");
            }
        }

        // Basic IP rate limiting (best-effort; single-instance)
        String clientKey = "ip:" + clientIp(request);
        if (!EXPLAIN_LIMITER.allow(clientKey)) {
            return Map.of("ok", false, "error", "Rate limit exceeded. Try again in a minute.");
        }

        String apiKey = System.getenv("OPENAI_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            return Map.of(
                    "ok", false,
                    "error", "OPENAI_API_KEY is not set. Configure it to enable automatic explanations."
            );
        }
        String model = System.getenv("OPENAI_MODEL"); // optional override

        try {
            OpenAiExplainClient client = new OpenAiExplainClient(apiKey, model);
            String response = client.explain(prompt);
            Map<String, Object> out = new LinkedHashMap<>();
            out.put("ok", true);
            out.put("response", response);
            return out;
        } catch (Exception e) {
            return Map.of("ok", false, "error", e.getMessage());
        }
    }

    private static int parseIntEnv(String name, int def) {
        try {
            String v = System.getenv(name);
            if (v == null || v.isBlank()) return def;
            return Integer.parseInt(v.trim());
        } catch (Exception e) {
            return def;
        }
    }

    private static String clientIp(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isBlank()) {
            // first IP in the chain
            int comma = xff.indexOf(',');
            return (comma >= 0 ? xff.substring(0, comma) : xff).trim();
        }
        String xrip = request.getHeader("X-Real-IP");
        if (xrip != null && !xrip.isBlank()) return xrip.trim();
        return request.getRemoteAddr();
    }
}
