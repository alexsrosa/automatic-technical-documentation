package com.docgen.core.application.usecases;

import com.docgen.core.domain.model.Document;
import com.docgen.core.domain.ports.input.RequestGenerationUseCase;
import com.docgen.core.domain.ports.output.DocumentRepositoryPort;
import com.docgen.core.domain.ports.output.EventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RequestGenerationService implements RequestGenerationUseCase {

    private final DocumentRepositoryPort documentRepository;
    private final EventPublisherPort eventPublisher;

    @Override
    @Transactional
    public void requestGeneration(String documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found: " + documentId));

        document.setStatus(Document.DocumentStatus.GENERATING);
        documentRepository.save(document);

        eventPublisher.publishDocumentGenerationRequest(document);
    }
}
