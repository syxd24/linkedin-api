package com.linkedincontent.backend.ai;

import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.linkedincontent.backend.post.dto.GeneratePostRequest;

@Primary
@Component
public class PythonApiAiClient implements AiClient {

    private static final Logger log = LoggerFactory.getLogger(PythonApiAiClient.class);
    private static final String AI_SERVICE_UNAVAILABLE = "AI service is currently unavailable. Please try again later.";
    private static final int BODY_PREVIEW_LIMIT = 500;

    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final String generateUrl;

    public PythonApiAiClient(
            RestClient.Builder restClientBuilder,
            ObjectMapper objectMapper,
            @Value("${ai.python.base-url}") String baseUrl,
            @Value("${ai.python.generate-path}") String generatePath) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000);
        requestFactory.setReadTimeout(30000);

        this.restClient = restClientBuilder.requestFactory(requestFactory).build();
        this.objectMapper = objectMapper;
        this.generateUrl = buildGenerateUrl(baseUrl, generatePath);
    }

    @Override
    public String generatePost(GeneratePostRequest request) {
        try {
            log.info("Python generate URL: {}", generateUrl);
            log.info("Sending request to Python AI API");

            return restClient.post()
                    .uri(generateUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(PythonAiRequest.from(request))
                    .exchange((clientRequest, clientResponse) -> {
                        HttpStatusCode statusCode = clientResponse.getStatusCode();
                        MediaType contentType = clientResponse.getHeaders().getContentType();
                        String responseBody = new String(clientResponse.getBody().readAllBytes(), StandardCharsets.UTF_8);

                        log.info("Python AI API response status: {}", statusCode.value());
                        log.info("Python AI API response content type: {}", contentType);

                        if (statusCode.isError()) {
                            log.warn("Python AI API returned error response. Body preview: {}", preview(responseBody));
                            throw new IllegalStateException(AI_SERVICE_UNAVAILABLE);
                        }

                        return extractGeneratedContent(responseBody, contentType);
                    });
        }
        catch (RestClientException exception) {
            log.warn("Python AI API call failed: {}", exception.getMessage());
            throw new IllegalStateException(AI_SERVICE_UNAVAILABLE, exception);
        }
        catch (IllegalStateException exception) {
            log.warn("Python AI API response handling failed: {}", exception.getMessage());
            if (AI_SERVICE_UNAVAILABLE.equals(exception.getMessage())) {
                throw exception;
            }
            throw new IllegalStateException(AI_SERVICE_UNAVAILABLE, exception);
        }
    }

    String extractGeneratedContent(String responseBody, MediaType contentType) {
        if (!StringUtils.hasText(responseBody)) {
            log.info("Python AI API generatedContent received: false");
            throw new IllegalStateException("Python API response was empty");
        }

        if (isJsonResponse(responseBody, contentType)) {
            return extractGeneratedContentFromJson(responseBody);
        }

        log.info("Python AI API returned plain text response");
        return responseBody.trim();
    }

    private String extractGeneratedContentFromJson(String responseBody) {
        PythonAiResponse response;

        try {
            response = objectMapper.readValue(responseBody, PythonAiResponse.class);
        }
        catch (JsonProcessingException exception) {
            log.warn("Failed to parse Python AI API JSON response. Body preview: {}", preview(responseBody));
            throw new IllegalStateException("Python API response could not be parsed", exception);
        }

        String generatedContent = response == null ? null : response.getGeneratedContent();

        if (StringUtils.hasText(generatedContent)) {
            log.info("Python AI API generatedContent received: true");
            return generatedContent;
        }

        log.info("Python AI API generatedContent received: false");
        log.warn("Python API JSON response did not contain generatedContent. Body preview: {}", preview(responseBody));
        throw new IllegalStateException("Python API response did not contain generatedContent");
    }

    private boolean isJsonResponse(String responseBody, MediaType contentType) {
        if (contentType != null && (MediaType.APPLICATION_JSON.includes(contentType)
                || contentType.getSubtype().endsWith("+json"))) {
            return true;
        }

        String trimmedBody = responseBody.trim();
        return trimmedBody.startsWith("{") || trimmedBody.startsWith("[");
    }

    private static String preview(String responseBody) {
        if (!StringUtils.hasText(responseBody)) {
            return "";
        }

        String normalizedBody = responseBody.replaceAll("\\s+", " ").trim();
        if (normalizedBody.length() <= BODY_PREVIEW_LIMIT) {
            return normalizedBody;
        }

        return normalizedBody.substring(0, BODY_PREVIEW_LIMIT) + "...";
    }

    private String buildGenerateUrl(String baseUrl, String generatePath) {
        String normalizedBaseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        String normalizedGeneratePath = generatePath.startsWith("/") ? generatePath : "/" + generatePath;
        return normalizedBaseUrl + normalizedGeneratePath;
    }

    private record PythonAiRequest(
            String topic,
            String tone,
            String postType,
            String targetAudience,
            String optionalContext) {

        private static PythonAiRequest from(GeneratePostRequest request) {
            return new PythonAiRequest(
                    request.getTopic(),
                    request.getTone(),
                    request.getPostType(),
                    request.getTargetAudience(),
                    request.getOptionalContext());
        }
    }
}
