package com.ser.reqcheck;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class OpenAiExplainClient {

    private static final URI CHAT_COMPLETIONS = URI.create("https://api.openai.com/v1/chat/completions");
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final HttpClient http;
    private final String apiKey;
    private final String model;

    public OpenAiExplainClient(String apiKey, String model) {
        this.http = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.apiKey = apiKey;
        this.model = (model == null || model.isBlank()) ? "gpt-4o-mini" : model;
    }

    public String explain(String prompt) throws Exception {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("model", model);
        payload.put("temperature", 0.2);
        payload.put("messages", List.of(
                Map.of("role", "system",
                        "content", "You are a requirements quality assistant. Follow the requested output format exactly."),
                Map.of("role", "user", "content", prompt)
        ));

        String body = MAPPER.writeValueAsString(payload);
        HttpRequest req = HttpRequest.newBuilder()
                .uri(CHAT_COMPLETIONS)
                .timeout(Duration.ofSeconds(30))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() < 200 || resp.statusCode() >= 300) {
            throw new RuntimeException("OpenAI error (" + resp.statusCode() + "): " + trim(resp.body(), 800));
        }

        JsonNode root = MAPPER.readTree(resp.body());
        JsonNode content = root.at("/choices/0/message/content");
        if (content.isMissingNode() || content.asText().isBlank()) {
            throw new RuntimeException("OpenAI response missing content.");
        }
        return content.asText();
    }

    private static String trim(String s, int max) {
        if (s == null) return "";
        if (s.length() <= max) return s;
        return s.substring(0, max) + "...";
    }
}

