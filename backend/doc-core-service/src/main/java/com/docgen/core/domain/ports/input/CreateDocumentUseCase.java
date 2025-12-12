package com.docgen.core.domain.ports.input;

import com.docgen.core.domain.model.Document;

public interface CreateDocumentUseCase {
    Document createDocument(Document document);
}
