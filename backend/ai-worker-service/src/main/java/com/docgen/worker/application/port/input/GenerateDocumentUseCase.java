package com.docgen.worker.application.port.input;

import com.docgen.worker.domain.model.GenerationCommand;

public interface GenerateDocumentUseCase {
    void generateDocument(GenerationCommand command);
}
