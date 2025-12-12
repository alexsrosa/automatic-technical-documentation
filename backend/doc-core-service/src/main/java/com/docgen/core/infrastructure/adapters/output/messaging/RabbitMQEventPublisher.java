package com.docgen.core.infrastructure.adapters.output.messaging;

import com.docgen.core.domain.model.Document;
import com.docgen.core.domain.ports.output.EventPublisherPort;
import com.docgen.core.infrastructure.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQEventPublisher implements EventPublisherPort {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishDocumentGenerationRequest(Document document) {
        log.info("Publishing document generation request for documentId: {}", document.getId());
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                document
        );
        log.info("Message published successfully");
    }
}
