package com.linkedincontent.backend.ai;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

class PythonApiAiClientTests {

    private final PythonApiAiClient client = new PythonApiAiClient(
            RestClient.builder(),
            new ObjectMapper(),
            "https://example.com",
            "/generate");

    @Test
    void extractsJsonResponseEvenWhenContentTypeIsOctetStream() {
        String content = client.extractGeneratedContent(
                "{\"generatedContent\":\"Generated LinkedIn post\"}",
                MediaType.APPLICATION_OCTET_STREAM);

        assertThat(content).isEqualTo("Generated LinkedIn post");
    }

    @Test
    void extractsSnakeCaseJsonResponse() {
        String content = client.extractGeneratedContent(
                "{\"generated_content\":\"Generated LinkedIn post\"}",
                MediaType.APPLICATION_JSON);

        assertThat(content).isEqualTo("Generated LinkedIn post");
    }

    @Test
    void usesPlainTextResponseWhenBodyIsNotJson() {
        String content = client.extractGeneratedContent(
                "Generated LinkedIn post as plain text",
                MediaType.APPLICATION_OCTET_STREAM);

        assertThat(content).isEqualTo("Generated LinkedIn post as plain text");
    }

    @Test
    void rejectsJsonWithoutGeneratedContent() {
        assertThatThrownBy(() -> client.extractGeneratedContent(
                "{\"message\":\"missing expected field\"}",
                MediaType.APPLICATION_JSON))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("generatedContent");
    }
}
