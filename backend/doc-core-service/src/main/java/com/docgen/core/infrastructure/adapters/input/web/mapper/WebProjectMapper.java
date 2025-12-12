package com.docgen.core.infrastructure.adapters.input.web.mapper;

import com.docgen.core.domain.model.Project;
import com.docgen.core.infrastructure.adapters.input.web.dto.CreateProjectRequest;
import com.docgen.core.infrastructure.adapters.input.web.dto.ProjectResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WebProjectMapper {

    Project toDomain(CreateProjectRequest request);

    ProjectResponse toResponse(Project domain);
}