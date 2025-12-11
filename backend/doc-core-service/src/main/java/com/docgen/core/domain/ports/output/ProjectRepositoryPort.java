package com.docgen.core.domain.ports.output;

import com.docgen.core.domain.model.Project;
import java.util.Optional;
import java.util.List;

public interface ProjectRepositoryPort {
    Project save(Project project);
    Optional<Project> findById(String id);
    List<Project> findByOwnerId(String ownerId);
    void deleteById(String id);
}
