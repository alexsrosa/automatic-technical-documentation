package com.docgen.core.application.usecases;

import com.docgen.core.domain.model.Document;
import com.docgen.core.domain.ports.output.DocumentRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateDocumentServiceTest {

    @Mock
    private DocumentRepositoryPort documentRepository;

    @InjectMocks
    private CreateDocumentService createDocumentService;

    @Test
    void createDocument_ShouldGenerateIdAndSetStatus_WhenNotProvided() {
        Document input = Document.builder().title("Doc").build();
        
        when(documentRepository.save(any(Document.class))).thenAnswer(i -> i.getArgument(0));

        Document created = createDocumentService.createDocument(input);

        assertNotNull(created.getId());
        assertEquals(Document.DocumentStatus.PENDING, created.getStatus());
        verify(documentRepository).save(any(Document.class));
    }
}