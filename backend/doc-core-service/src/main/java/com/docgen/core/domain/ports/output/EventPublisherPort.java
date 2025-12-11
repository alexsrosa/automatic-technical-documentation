package com.docgen.core.domain.ports.output;

import com.docgen.core.domain.model.Document;

public interface EventPublisherPort {
    void publishDocumentGenerationRequest(Document document);
}
