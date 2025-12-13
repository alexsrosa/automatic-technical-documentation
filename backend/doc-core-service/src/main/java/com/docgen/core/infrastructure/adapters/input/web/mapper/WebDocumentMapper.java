package com.docgen.core.infrastructure.adapters.input.web.mapper;

import com.docgen.core.domain.model.Document;
import com.docgen.core.domain.model.Requirement;
import com.docgen.core.infrastructure.adapters.input.web.dto.CreateDocumentRequest;
import com.docgen.core.infrastructure.adapters.input.web.dto.DocumentResponse;
import com.docgen.core.infrastructure.adapters.input.web.dto.RequirementDto;
import com.docgen.core.infrastructure.adapters.input.web.dto.UpdateDocumentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WebDocumentMapper {

    Document toDomain(CreateDocumentRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projectId", ignore = true)
    @Mapping(target = "generatedAt", ignore = true)
    Document updateDomain(@MappingTarget Document document, UpdateDocumentRequest request);

    Requirement toDomain(RequirementDto dto);

    DocumentResponse toResponse(Document domain);
}
