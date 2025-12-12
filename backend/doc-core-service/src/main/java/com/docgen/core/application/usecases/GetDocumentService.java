package com.docgen.core.application.usecases;

import com.docgen.core.domain.model.Document;
import com.docgen.core.domain.ports.input.GetDocumentUseCase;
import com.docgen.core.domain.ports.output.DocumentRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetDocumentService implements GetDocumentUseCase {

    private final DocumentRepositoryPort documentRepository;

    @Override
    public List<Document> getDocumentsByProject(String projectId) {
        return documentRepository.findByProjectId(projectId);
    }

    @Override
    public Optional<Document> getDocumentById(String id) {
        return documentRepository.findById(id);
    }
}