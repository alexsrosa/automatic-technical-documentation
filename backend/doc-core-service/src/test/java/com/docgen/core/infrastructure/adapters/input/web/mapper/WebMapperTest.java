package com.docgen.core.infrastructure.adapters.input.web.mapper;

import com.docgen.core.domain.model.Document;
import com.docgen.core.domain.model.Project;
import com.docgen.core.infrastructure.adapters.input.web.dto.CreateDocumentRequest;
import com.docgen.core.infrastructure.adapters.input.web.dto.CreateProjectRequest;
import com.docgen.core.infrastructure.adapters.input.web.dto.DocumentResponse;
import com.docgen.core.infrastructure.adapters.input.web.dto.ProjectResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WebMapperTest {

    private final WebProjectMapper projectMapper = Mappers.getMapper(WebProjectMapper.class);
    private final WebDocumentMapper documentMapper = Mappers.getMapper(WebDocumentMapper.class);

    @Test
    void testProjectMapping() {
        CreateProjectRequest request = CreateProjectRequest.builder()
                .name("Project Name")
                .description("Desc")
                .repositoryUrl("http://repo.com")
                .ownerId("user1")
                .build();

        Project domain = projectMapper.toDomain(request);
        
        assertNotNull(domain);
        assertEquals(request.name(), domain.getName());
        assertEquals(request.description(), domain.getDescription());
        assertEquals(request.repositoryUrl(), domain.getRepositoryUrl());
        assertEquals(request.ownerId(), domain.getOwnerId());

        ProjectResponse response = projectMapper.toResponse(domain);
        assertNotNull(response);
        assertEquals(domain.getName(), response.name());
        assertEquals(domain.getDescription(), response.description());
    }

    @Test
    void testDocumentMapping() {
        CreateDocumentRequest request = CreateDocumentRequest.builder()
                .projectId("p1")
                .title("Doc Title")
                .type(Document.DocumentType.README)
                .build();

        Document domain = documentMapper.toDomain(request);
        
        assertNotNull(domain);
        assertEquals(request.projectId(), domain.getProjectId());
        assertEquals(request.title(), domain.getTitle());
        assertEquals(request.type(), domain.getType());

        DocumentResponse response = documentMapper.toResponse(domain);
        assertNotNull(response);
        assertEquals(domain.getTitle(), response.title());
        assertEquals(domain.getType(), response.type());
    }
}