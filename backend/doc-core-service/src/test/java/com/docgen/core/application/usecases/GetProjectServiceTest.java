package com.docgen.core.application.usecases;

import com.docgen.core.domain.model.Project;
import com.docgen.core.domain.ports.output.ProjectRepositoryPort;
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
class GetProjectServiceTest {

    @Mock
    private ProjectRepositoryPort projectRepository;

    @InjectMocks
    private GetProjectService getProjectService;

    @Test
    void getProjectsByOwner_ShouldReturnList() {
        Project p = Project.builder().id("1").ownerId("u1").build();
        when(projectRepository.findByOwnerId("u1")).thenReturn(List.of(p));

        List<Project> result = getProjectService.getProjectsByOwner("u1");

        assertEquals(1, result.size());
        assertEquals(p, result.get(0));
    }

    @Test
    void getProjectById_ShouldReturnOptional() {
        Project p = Project.builder().id("1").build();
        when(projectRepository.findById("1")).thenReturn(Optional.of(p));

        Optional<Project> result = getProjectService.getProjectById("1");

        assertTrue(result.isPresent());
        assertEquals(p, result.get());
    }

    @Test
    void getAllProjects_ShouldReturnEmptyList() {
        List<Project> result = getProjectService.getAllProjects();
        assertTrue(result.isEmpty());
    }
}