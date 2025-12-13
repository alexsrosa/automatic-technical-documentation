package com.docgen.worker.infrastructure.client;

import com.docgen.worker.infrastructure.client.dto.UpdateDocumentRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.http.MediaType;

@Component
public class CoreServiceClient {

    private final RestClient restClient;

    public CoreServiceClient(@Value("${app.core-service.url:http://localhost:8080}") String coreServiceUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(coreServiceUrl)
                .build();
    }

    public void updateDocument(String documentId, UpdateDocumentRequest request) {
        restClient.put()
                .uri("/api/v1/documents/{id}", documentId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }
}
