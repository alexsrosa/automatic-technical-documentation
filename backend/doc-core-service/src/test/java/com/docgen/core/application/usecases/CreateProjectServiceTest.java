package com.docgen.core.application.usecases;

import com.docgen.core.domain.model.Project;
import com.docgen.core.domain.ports.output.ProjectRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateProjectServiceTest {

    @Mock
    private ProjectRepositoryPort projectRepository;

    @InjectMocks
    private CreateProjectService createProjectService;

    private Project project;

    @BeforeEach
    void setUp() {
        project = Project.builder()
                .name("Test Project")
                .description("Description")
                .repositoryUrl("http://repo.url")
                .ownerId("user1")
                .build();
    }

    @Test
    void createProject_ShouldGenerateIdAndTimestamps_WhenIdIsNull() {
        when(projectRepository.save(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Project created = createProjectService.createProject(project);

        assertNotNull(created.getId());
        assertNotNull(created.getCreatedAt());
        assertNotNull(created.getUpdatedAt());
        verify(projectRepository).save(any(Project.class));
    }

    @Test
    void createProject_ShouldKeepExistingId_WhenIdIsNotNull() {
        project.setId("existing-id");
        when(projectRepository.save(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Project created = createProjectService.createProject(project);

        assertEquals("existing-id", created.getId());
        assertNotNull(created.getCreatedAt());
        assertNotNull(created.getUpdatedAt());
        verify(projectRepository).save(any(Project.class));
    }
}
