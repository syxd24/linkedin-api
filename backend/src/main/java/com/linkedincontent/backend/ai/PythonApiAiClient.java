package com.linkedincontent.backend.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
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

    private final RestClient restClient;
    private final String generateUrl;

    public PythonApiAiClient(
            RestClient.Builder restClientBuilder,
            @Value("${ai.python.base-url}") String baseUrl,
            @Value("${ai.python.generate-path}") String generatePath) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000);
        requestFactory.setReadTimeout(30000);

        this.restClient = restClientBuilder.requestFactory(requestFactory).build();
        this.generateUrl = buildGenerateUrl(baseUrl, generatePath);
    }

    @Override
    public String generatePost(GeneratePostRequest request) {
        try {
            log.info("Python generate URL: {}", generateUrl);
            log.info("Sending request to Python AI API");

            PythonAiResponse response = restClient.post()
                    .uri(generateUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(PythonAiRequest.from(request))
                    .retrieve()
                    .body(PythonAiResponse.class);

            return extractGeneratedContent(response);
        }
        catch (RestClientException exception) {
            log.warn("Python AI API call failed: {}", exception.getMessage());
            throw new IllegalStateException(AI_SERVICE_UNAVAILABLE, exception);
        }
    }

    private String extractGeneratedContent(PythonAiResponse response) {
        String generatedContent = response == null ? null : response.getGeneratedContent();

        if (StringUtils.hasText(generatedContent)) {
            log.info("Python AI API generatedContent received: true");
            return generatedContent;
        }

        log.info("Python AI API generatedContent received: false");
        throw new IllegalStateException("Python API response did not contain generatedContent");
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
