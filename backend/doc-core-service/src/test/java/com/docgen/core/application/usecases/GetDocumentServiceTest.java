package com.docgen.core.application.usecases;

import com.docgen.core.domain.model.Document;
import com.docgen.core.domain.ports.output.DocumentRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetDocumentServiceTest {

    @Mock
    private DocumentRepositoryPort documentRepository;

    @InjectMocks
    private GetDocumentService getDocumentService;

    @Test
    void getDocumentsByProject_ShouldReturnList() {
        Document doc = Document.builder().id("1").projectId("p1").build();
        when(documentRepository.findByProjectId("p1")).thenReturn(List.of(doc));

        List<Document> result = getDocumentService.getDocumentsByProject("p1");

        assertEquals(1, result.size());
        assertEquals(doc, result.get(0));
    }

    @Test
    void getDocumentById_ShouldReturnOptional() {
        Document doc = Document.builder().id("1").build();
        when(documentRepository.findById("1")).thenReturn(Optional.of(doc));

        Optional<Document> result = getDocumentService.getDocumentById("1");

        assertTrue(result.isPresent());
        assertEquals(doc, result.get());
    }
}