package com.docgen.core.infrastructure.adapters.output.persistence.mapper;

import com.docgen.core.domain.model.Document;
import com.docgen.core.domain.model.Project;
import com.docgen.core.infrastructure.adapters.output.persistence.entity.DocumentEntity;
import com.docgen.core.infrastructure.adapters.output.persistence.entity.ProjectEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MapperTest {

    private final ProjectMapper projectMapper = Mappers.getMapper(ProjectMapper.class);
    private final DocumentMapper documentMapper = Mappers.getMapper(DocumentMapper.class);

    @Test
    void testProjectMapper() {
        // Domain to Entity
        Project project = Project.builder()
                .id("1")
                .name("Name")
                .description("Desc")
                .createdAt(LocalDateTime.now())
                .build();

        ProjectEntity entity = projectMapper.toEntity(project);
        assertNotNull(entity);
        assertEquals(project.getId(), entity.getId());
        assertEquals(project.getName(), entity.getName());
        assertEquals(project.getDescription(), entity.getDescription());
        assertEquals(project.getCreatedAt(), entity.getCreatedAt());

        // Entity to Domain
        ProjectEntity entityInput = new ProjectEntity();
        entityInput.setId("2");
        entityInput.setName("Name2");
        
        Project domain = projectMapper.toDomain(entityInput);
        assertNotNull(domain);
        assertEquals(entityInput.getId(), domain.getId());
        assertEquals(entityInput.getName(), domain.getName());
        
        // Null checks
        assertNull(projectMapper.toEntity(null));
        assertNull(projectMapper.toDomain(null));
    }

    @Test
    void testDocumentMapper() {
        // Domain to Entity
        Document document = Document.builder()
                .id("1")
                .projectId("p1")
                .title("Title")
                .type(Document.DocumentType.README)
                .build();

        DocumentEntity entity = documentMapper.toEntity(document);
        assertNotNull(entity);
        assertEquals(document.getId(), entity.getId());
        assertEquals(document.getProjectId(), entity.getProjectId());
        assertEquals(document.getTitle(), entity.getTitle());
        assertEquals(document.getType(), entity.getType());

        // Entity to Domain
        DocumentEntity entityInput = new DocumentEntity();
        entityInput.setId("2");
        entityInput.setTitle("Title2");
        
        Document domain = documentMapper.toDomain(entityInput);
        assertNotNull(domain);
        assertEquals(entityInput.getId(), domain.getId());
        assertEquals(entityInput.getTitle(), domain.getTitle());
        
        // Null checks
        assertNull(documentMapper.toEntity(null));
        assertNull(documentMapper.toDomain(null));
    }
}
