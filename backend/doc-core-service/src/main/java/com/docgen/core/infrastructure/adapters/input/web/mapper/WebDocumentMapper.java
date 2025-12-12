package com.docgen.core.infrastructure.adapters.input.web.mapper;

import com.docgen.core.domain.model.Document;
import com.docgen.core.infrastructure.adapters.input.web.dto.CreateDocumentRequest;
import com.docgen.core.infrastructure.adapters.input.web.dto.DocumentResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WebDocumentMapper {

    Document toDomain(CreateDocumentRequest request);

    DocumentResponse toResponse(Document domain);
}