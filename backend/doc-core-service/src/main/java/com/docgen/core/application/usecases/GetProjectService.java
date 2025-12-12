package com.docgen.core.application.usecases;

import com.docgen.core.domain.model.Project;
import com.docgen.core.domain.ports.input.GetProjectUseCase;
import com.docgen.core.domain.ports.output.ProjectRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetProjectService implements GetProjectUseCase {

    private final ProjectRepositoryPort projectRepository;

    @Override
    public List<Project> getProjectsByOwner(String ownerId) {
        return projectRepository.findByOwnerId(ownerId);
    }

    @Override
    public Optional<Project> getProjectById(String id) {
        return projectRepository.findById(id);
    }

    @Override
    public List<Project> getAllProjects() {
        return List.of();
    }
}