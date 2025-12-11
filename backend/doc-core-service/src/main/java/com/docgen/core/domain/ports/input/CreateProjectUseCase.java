package com.docgen.core.domain.ports.input;

import com.docgen.core.domain.model.Project;
import java.util.List;

public interface CreateProjectUseCase {
    Project createProject(Project project);
}
