package com.docgen.core.infrastructure.adapters.output.persistence;

import com.docgen.core.domain.model.Document;
import com.docgen.core.domain.ports.output.DocumentRepositoryPort;
import com.docgen.core.infrastructure.adapters.output.persistence.entity.DocumentEntity;
import com.docgen.core.infrastructure.adapters.output.persistence.mapper.DocumentMapper;
import com.docgen.core.infrastructure.adapters.output.persistence.repository.SpringDataDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MongoDocumentAdapter implements DocumentRepositoryPort {

    private final SpringDataDocumentRepository repository;
    private final DocumentMapper mapper;

    @Override
    public Document save(Document document) {
        DocumentEntity entity = mapper.toEntity(document);
        DocumentEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Document> findById(String id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Document> findByProjectId(String projectId) {
        return repository.findByProjectId(projectId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
