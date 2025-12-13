package com.docgen.worker.infrastructure.messaging.mapper;

import com.docgen.worker.domain.model.GenerationCommand;
import com.docgen.worker.domain.model.Requirement;
import com.docgen.worker.infrastructure.messaging.dto.DocumentMessage;
import com.docgen.worker.infrastructure.messaging.dto.RequirementMessage;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MessageMapper {

    public GenerationCommand toCommand(DocumentMessage message) {
        return new GenerationCommand(
            message.id(),
            message.projectId(),
            message.title(),
            message.type(),
            message.requirements() == null ? java.util.Collections.emptyList() : 
            message.requirements().stream()
                .map(this::toDomain)
                .collect(Collectors.toList())
        );
    }

    private Requirement toDomain(RequirementMessage msg) {
        return new Requirement(
            msg.id(),
            msg.description(),
            msg.priority()
        );
    }
}
