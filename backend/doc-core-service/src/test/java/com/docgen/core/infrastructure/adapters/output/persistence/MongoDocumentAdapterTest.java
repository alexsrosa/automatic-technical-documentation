package com.docgen.core.infrastructure.adapters.output.persistence;

import com.docgen.core.domain.model.Document;
import com.docgen.core.infrastructure.adapters.output.persistence.entity.DocumentEntity;
import com.docgen.core.infrastructure.adapters.output.persistence.mapper.DocumentMapper;
import com.docgen.core.infrastructure.adapters.output.persistence.repository.SpringDataDocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MongoDocumentAdapterTest {

    @Mock
    private SpringDataDocumentRepository repository;

    @Mock
    private DocumentMapper mapper;

    @InjectMocks
    private MongoDocumentAdapter adapter;

    private Document document;
    private DocumentEntity entity;

    @BeforeEach
    void setUp() {
        document = Document.builder().id("doc-1").projectId("proj-1").build();
        entity = DocumentEntity.builder().id("doc-1").projectId("proj-1").build();
    }

    @Test
    void save_ShouldReturnSavedDocument() {
        when(mapper.toEntity(document)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(document);

        Document result = adapter.save(document);

        assertEquals(document, result);
        verify(repository).save(entity);
    }

    @Test
    void findById_ShouldReturnDocument() {
        when(repository.findById("doc-1")).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(document);

        Optional<Document> result = adapter.findById("doc-1");

        assertTrue(result.isPresent());
        assertEquals(document, result.get());
    }

    @Test
    void findByProjectId_ShouldReturnList() {
        when(repository.findByProjectId("proj-1")).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(document);

        List<Document> result = adapter.findByProjectId("proj-1");

        assertEquals(1, result.size());
        assertEquals(document, result.get(0));
    }
}
