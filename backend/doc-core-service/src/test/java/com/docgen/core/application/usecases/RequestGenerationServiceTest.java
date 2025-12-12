package com.docgen.core.application.usecases;

import com.docgen.core.domain.model.Document;
import com.docgen.core.domain.ports.output.DocumentRepositoryPort;
import com.docgen.core.domain.ports.output.EventPublisherPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequestGenerationServiceTest {

    @Mock
    private DocumentRepositoryPort documentRepository;

    @Mock
    private EventPublisherPort eventPublisher;

    @InjectMocks
    private RequestGenerationService requestGenerationService;

    private Document document;

    @BeforeEach
    void setUp() {
        document = Document.builder()
                .id("doc-1")
                .projectId("proj-1")
                .status(Document.DocumentStatus.PENDING)
                .build();
    }

    @Test
    void requestGeneration_ShouldUpdateStatusAndPublishEvent_WhenDocumentExists() {
        when(documentRepository.findById("doc-1")).thenReturn(Optional.of(document));
        when(documentRepository.save(any(Document.class))).thenAnswer(i -> i.getArgument(0));

        requestGenerationService.requestGeneration("doc-1");

        assertEquals(Document.DocumentStatus.GENERATING, document.getStatus());
        verify(documentRepository).save(document);
        verify(eventPublisher).publishDocumentGenerationRequest(document);
    }

    @Test
    void requestGeneration_ShouldThrowException_WhenDocumentNotFound() {
        when(documentRepository.findById("doc-1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> requestGenerationService.requestGeneration("doc-1"));

        verify(documentRepository, never()).save(any());
        verify(eventPublisher, never()).publishDocumentGenerationRequest(any());
    }
}
