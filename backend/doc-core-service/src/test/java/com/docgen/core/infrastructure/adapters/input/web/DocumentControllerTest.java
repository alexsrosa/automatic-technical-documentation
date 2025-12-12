package com.docgen.core.infrastructure.adapters.input.web;

import com.docgen.core.domain.model.Document;
import com.docgen.core.domain.ports.input.CreateDocumentUseCase;
import com.docgen.core.domain.ports.input.GetDocumentUseCase;
import com.docgen.core.domain.ports.input.RequestGenerationUseCase;
import com.docgen.core.infrastructure.adapters.input.web.dto.CreateDocumentRequest;
import com.docgen.core.infrastructure.adapters.input.web.dto.DocumentResponse;
import com.docgen.core.infrastructure.adapters.input.web.mapper.WebDocumentMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentControllerTest {

    @Mock
    private RequestGenerationUseCase requestGenerationUseCase;

    @Mock
    private CreateDocumentUseCase createDocumentUseCase;

    @Mock
    private GetDocumentUseCase getDocumentUseCase;

    @Mock
    private WebDocumentMapper mapper;

    @InjectMocks
    private DocumentController documentController;

    private Document document;
    private DocumentResponse documentResponse;

    @BeforeEach
    void setUp() {
        document = Document.builder()
                .id("doc-1")
                .projectId("proj-1")
                .title("Test Doc")
                .type(Document.DocumentType.README)
                .status(Document.DocumentStatus.PENDING)
                .build();
        
        documentResponse = DocumentResponse.builder()
                .id("doc-1")
                .projectId("proj-1")
                .title("Test Doc")
                .type(Document.DocumentType.README)
                .status(Document.DocumentStatus.PENDING)
                .build();
    }

    @Test
    void createDocument_ShouldReturnCreated() {
        when(mapper.toDomain(any(CreateDocumentRequest.class))).thenReturn(document);
        when(createDocumentUseCase.createDocument(any(Document.class))).thenReturn(document);
        when(mapper.toResponse(any(Document.class))).thenReturn(documentResponse);

        CreateDocumentRequest request = CreateDocumentRequest.builder()
                .projectId("proj-1")
                .title("Test Doc")
                .type(Document.DocumentType.README)
                .build();

        ResponseEntity<DocumentResponse> response = documentController.createDocument(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody().id());
        assertEquals(Document.DocumentStatus.PENDING, response.getBody().status());
        verify(createDocumentUseCase).createDocument(any(Document.class));
    }

    @Test
    void requestGeneration_ShouldReturnAccepted() {
        doNothing().when(requestGenerationUseCase).requestGeneration("doc-1");

        ResponseEntity<Void> response = documentController.requestGeneration("doc-1");

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(requestGenerationUseCase).requestGeneration("doc-1");
    }

    @Test
    void getDocuments_ShouldReturnList() {
        when(getDocumentUseCase.getDocumentsByProject("proj-1")).thenReturn(List.of(document));
        when(mapper.toResponse(any(Document.class))).thenReturn(documentResponse);

        ResponseEntity<List<DocumentResponse>> response = documentController.getDocuments("proj-1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(document.getId(), response.getBody().get(0).id());
    }

    @Test
    void getDocument_ShouldReturnDocument_WhenFound() {
        when(getDocumentUseCase.getDocumentById("doc-1")).thenReturn(Optional.of(document));
        when(mapper.toResponse(any(Document.class))).thenReturn(documentResponse);

        ResponseEntity<DocumentResponse> response = documentController.getDocument("doc-1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(document.getId(), response.getBody().id());
    }

    @Test
    void getDocument_ShouldReturnNotFound_WhenNotFound() {
        when(getDocumentUseCase.getDocumentById("doc-1")).thenReturn(Optional.empty());

        ResponseEntity<DocumentResponse> response = documentController.getDocument("doc-1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}
