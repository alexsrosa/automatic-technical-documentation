package com.docgen.core.infrastructure.adapters.input.web;

import com.docgen.core.domain.model.Project;
import com.docgen.core.domain.ports.input.CreateProjectUseCase;
import com.docgen.core.domain.ports.input.GetProjectUseCase;
import com.docgen.core.infrastructure.adapters.input.web.dto.CreateProjectRequest;
import com.docgen.core.infrastructure.adapters.input.web.dto.ProjectResponse;
import com.docgen.core.infrastructure.adapters.input.web.mapper.WebProjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

    @Mock
    private CreateProjectUseCase createProjectUseCase;

    @Mock
    private GetProjectUseCase getProjectUseCase;

    @Mock
    private WebProjectMapper mapper;

    @InjectMocks
    private ProjectController projectController;

    private Project project;
    private ProjectResponse projectResponse;

    @BeforeEach
    void setUp() {
        project = Project.builder()
                .id("proj-1")
                .name("Test Project")
                .build();
        
        projectResponse = ProjectResponse.builder()
                .id("proj-1")
                .name("Test Project")
                .build();
    }

    @Test
    void createProject_ShouldReturnCreated() {
        when(mapper.toDomain(any(CreateProjectRequest.class))).thenReturn(project);
        when(createProjectUseCase.createProject(any(Project.class))).thenReturn(project);
        when(mapper.toResponse(any(Project.class))).thenReturn(projectResponse);

        CreateProjectRequest request = CreateProjectRequest.builder()
                .name("Test Project")
                .build();

        ResponseEntity<ProjectResponse> response = projectController.createProject(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(project.getId(), response.getBody().id());
        verify(createProjectUseCase).createProject(any(Project.class));
    }

    @Test
    void getProjects_ShouldReturnList_WhenNoOwnerId() {
        when(getProjectUseCase.getAllProjects()).thenReturn(List.of());

        ResponseEntity<List<ProjectResponse>> response = projectController.getProjects(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    void getProjects_WithOwnerId_ShouldReturnFilteredList() {
        String ownerId = "user1";
        when(getProjectUseCase.getProjectsByOwner(ownerId)).thenReturn(List.of(project));
        when(mapper.toResponse(any(Project.class))).thenReturn(projectResponse);

        ResponseEntity<List<ProjectResponse>> response = projectController.getProjects(ownerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(project.getId(), response.getBody().get(0).id());
    }

    @Test
    void getProject_ShouldReturnProject_WhenFound() {
        when(getProjectUseCase.getProjectById("proj-1")).thenReturn(Optional.of(project));
        when(mapper.toResponse(any(Project.class))).thenReturn(projectResponse);

        ResponseEntity<ProjectResponse> response = projectController.getProject("proj-1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(project.getId(), response.getBody().id());
    }

    @Test
    void getProject_ShouldReturnNotFound_WhenNotFound() {
        when(getProjectUseCase.getProjectById("proj-1")).thenReturn(Optional.empty());

        ResponseEntity<ProjectResponse> response = projectController.getProject("proj-1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}
