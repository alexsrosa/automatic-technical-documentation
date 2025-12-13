package com.docgen.core.application.usecases;

import com.docgen.core.domain.model.Document;
import com.docgen.core.domain.ports.input.UpdateDocumentUseCase;
import com.docgen.core.domain.ports.output.DocumentRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateDocumentService implements UpdateDocumentUseCase {

    private final DocumentRepositoryPort documentRepository;

    @Override
    public Document updateDocument(Document document) {
        if (document.getId() == null) {
            throw new IllegalArgumentException("Document ID cannot be null for update");
        }
        // Ideally we should check if it exists, but save usually handles it.
        // Also we might want to merge fields if partial update, but for now we assume full update.
        return documentRepository.save(document);
    }
}
