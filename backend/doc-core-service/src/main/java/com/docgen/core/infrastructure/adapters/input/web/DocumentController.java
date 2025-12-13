package com.docgen.core.infrastructure.adapters.input.web;

import com.docgen.core.domain.model.Document;
import com.docgen.core.domain.ports.input.CreateDocumentUseCase;
import com.docgen.core.domain.ports.input.GetDocumentUseCase;
import com.docgen.core.domain.ports.input.RequestGenerationUseCase;
import com.docgen.core.domain.ports.input.UpdateDocumentUseCase;
import com.docgen.core.infrastructure.adapters.input.web.doc.DocumentControllerDoc;
import com.docgen.core.infrastructure.adapters.input.web.dto.CreateDocumentRequest;
import com.docgen.core.infrastructure.adapters.input.web.dto.DocumentResponse;
import com.docgen.core.infrastructure.adapters.input.web.dto.UpdateDocumentRequest;
import com.docgen.core.infrastructure.adapters.input.web.mapper.WebDocumentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentController implements DocumentControllerDoc {

    private final RequestGenerationUseCase requestGenerationUseCase;
    private final CreateDocumentUseCase createDocumentUseCase;
    private final UpdateDocumentUseCase updateDocumentUseCase;
    private final GetDocumentUseCase getDocumentUseCase;
    private final WebDocumentMapper mapper;

    @Override
    public ResponseEntity<DocumentResponse> createDocument(CreateDocumentRequest request) {
        Document document = mapper.toDomain(request);
        Document saved = createDocumentUseCase.createDocument(document);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(saved));
    }

    @Override
    public ResponseEntity<Void> requestGeneration(String id) {
        requestGenerationUseCase.requestGeneration(id);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<List<DocumentResponse>> getDocuments(String projectId) {
        List<Document> documents = getDocumentUseCase.getDocumentsByProject(projectId);
        return ResponseEntity.ok(documents.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<DocumentResponse> getDocument(String id) {
        return getDocumentUseCase.getDocumentById(id)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<DocumentResponse> updateDocument(String id, UpdateDocumentRequest request) {
        return getDocumentUseCase.getDocumentById(id)
                .map(existingDocument -> {
                    Document documentToUpdate = mapper.updateDomain(existingDocument, request);
                    Document saved = updateDocumentUseCase.updateDocument(documentToUpdate);
                    return ResponseEntity.ok(mapper.toResponse(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}