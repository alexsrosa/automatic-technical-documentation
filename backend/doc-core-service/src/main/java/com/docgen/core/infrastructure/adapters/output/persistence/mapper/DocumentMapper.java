package com.docgen.core.infrastructure.adapters.output.persistence.mapper;

import com.docgen.core.domain.model.Document;
import com.docgen.core.infrastructure.adapters.output.persistence.entity.DocumentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocumentMapper {
    
    DocumentEntity toEntity(Document domain);
    
    Document toDomain(DocumentEntity entity);
}