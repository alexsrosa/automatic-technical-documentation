package com.docgen.core.domain.ports.input;

import com.docgen.core.domain.model.Project;
import java.util.List;
import java.util.Optional;

public interface GetProjectUseCase {
    List<Project> getProjectsByOwner(String ownerId);
    Optional<Project> getProjectById(String id);
    List<Project> getAllProjects();
}