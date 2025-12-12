package com.docgen.core.infrastructure.adapters.output.persistence;

import com.docgen.core.domain.model.Project;
import com.docgen.core.infrastructure.adapters.output.persistence.entity.ProjectEntity;
import com.docgen.core.infrastructure.adapters.output.persistence.mapper.ProjectMapper;
import com.docgen.core.infrastructure.adapters.output.persistence.repository.SpringDataProjectRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MongoProjectAdapterTest {

    @Mock
    private SpringDataProjectRepository repository;

    @Mock
    private ProjectMapper mapper;

    @InjectMocks
    private MongoProjectAdapter adapter;

    private Project project;
    private ProjectEntity entity;

    @BeforeEach
    void setUp() {
        project = Project.builder().id("proj-1").build();
        entity = ProjectEntity.builder().id("proj-1").build();
    }

    @Test
    void save_ShouldReturnSavedProject() {
        when(mapper.toEntity(project)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(project);

        Project result = adapter.save(project);

        assertEquals(project, result);
        verify(repository).save(entity);
    }

    @Test
    void findById_ShouldReturnProject() {
        when(repository.findById("proj-1")).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(project);

        Optional<Project> result = adapter.findById("proj-1");

        assertTrue(result.isPresent());
        assertEquals(project, result.get());
    }

    @Test
    void findByOwnerId_ShouldReturnList() {
        when(repository.findByOwnerId("user1")).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(project);

        List<Project> result = adapter.findByOwnerId("user1");

        assertEquals(1, result.size());
        assertEquals(project, result.get(0));
    }

    @Test
    void deleteById_ShouldCallRepository() {
        doNothing().when(repository).deleteById("proj-1");

        adapter.deleteById("proj-1");

        verify(repository).deleteById("proj-1");
    }
}
