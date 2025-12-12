package com.docgen.core.infrastructure.adapters.input.web;

import com.docgen.core.domain.model.Project;
import com.docgen.core.domain.ports.input.CreateProjectUseCase;
import com.docgen.core.domain.ports.input.GetProjectUseCase;
import com.docgen.core.infrastructure.adapters.input.web.doc.ProjectControllerDoc;
import com.docgen.core.infrastructure.adapters.input.web.dto.CreateProjectRequest;
import com.docgen.core.infrastructure.adapters.input.web.dto.ProjectResponse;
import com.docgen.core.infrastructure.adapters.input.web.mapper.WebProjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController implements ProjectControllerDoc {

    private final CreateProjectUseCase createProjectUseCase;
    private final GetProjectUseCase getProjectUseCase;
    private final WebProjectMapper mapper;

    @Override
    public ResponseEntity<ProjectResponse> createProject(CreateProjectRequest request) {
        Project project = mapper.toDomain(request);
        Project created = createProjectUseCase.createProject(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(created));
    }

    @Override
    public ResponseEntity<List<ProjectResponse>> getProjects(String ownerId) {
        List<Project> projects;
        if (ownerId != null) {
            projects = getProjectUseCase.getProjectsByOwner(ownerId);
        } else {
            projects = getProjectUseCase.getAllProjects();
        }
        return ResponseEntity.ok(projects.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<ProjectResponse> getProject(String id) {
        return getProjectUseCase.getProjectById(id)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}