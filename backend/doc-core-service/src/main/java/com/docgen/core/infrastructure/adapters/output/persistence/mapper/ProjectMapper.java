package com.docgen.core.infrastructure.adapters.output.persistence.mapper;

import com.docgen.core.domain.model.Project;
import com.docgen.core.infrastructure.adapters.output.persistence.entity.ProjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;

@Mapper(componentModel = "spring", imports = Collections.class)
public interface ProjectMapper {
    
    ProjectEntity toEntity(Project domain);
    
    @Mapping(target = "documents", expression = "java(Collections.emptyList())")
    Project toDomain(ProjectEntity entity);
}