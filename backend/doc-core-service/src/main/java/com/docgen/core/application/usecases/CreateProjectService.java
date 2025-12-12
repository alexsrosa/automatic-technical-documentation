package com.docgen.core.application.usecases;

import com.docgen.core.domain.model.Project;
import com.docgen.core.domain.ports.input.CreateProjectUseCase;
import com.docgen.core.domain.ports.output.ProjectRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateProjectService implements CreateProjectUseCase {

    private final ProjectRepositoryPort projectRepository;

    @Override
    @Transactional
    public Project createProject(Project project) {
        if (project.getId() == null) {
            project.setId(UUID.randomUUID().toString());
        }
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());
        return projectRepository.save(project);
    }
}
