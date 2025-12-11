package com.docgen.core.domain.ports.input;

import com.docgen.core.domain.model.Document;

public interface RequestGenerationUseCase {
    void requestGeneration(String documentId);
}
