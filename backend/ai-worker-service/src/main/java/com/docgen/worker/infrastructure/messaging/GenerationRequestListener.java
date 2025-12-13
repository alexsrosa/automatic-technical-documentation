package com.docgen.worker.infrastructure.messaging;

import com.docgen.worker.application.port.input.GenerateDocumentUseCase;
import com.docgen.worker.infrastructure.config.RabbitMQConfig;
import com.docgen.worker.infrastructure.messaging.dto.DocumentMessage;
import com.docgen.worker.infrastructure.messaging.mapper.MessageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GenerationRequestListener {

    private final GenerateDocumentUseCase generateDocumentUseCase;
    private final MessageMapper mapper;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void handleGenerationRequest(DocumentMessage message) {
        log.info("Received generation request for document: {}", message.id());
        try {
            var command = mapper.toCommand(message);
            generateDocumentUseCase.generateDocument(command);
        } catch (Exception e) {
            log.error("Error processing generation request", e);
            // In a real scenario, we might want to send to a DLQ or update status to FAILED
        }
    }
}
