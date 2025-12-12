package com.docgen.core.application.usecases;

import com.docgen.core.domain.model.Document;
import com.docgen.core.domain.ports.input.CreateDocumentUseCase;
import com.docgen.core.domain.ports.output.DocumentRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateDocumentService implements CreateDocumentUseCase {

    private final DocumentRepositoryPort documentRepository;

    @Override
    public Document createDocument(Document document) {
        if (document.getId() == null) {
            document.setId(UUID.randomUUID().toString());
        }
        document.setStatus(Document.DocumentStatus.PENDING);
        return documentRepository.save(document);
    }
}