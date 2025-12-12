package com.docgen.core.infrastructure.adapters.output.messaging;

import com.docgen.core.domain.model.Document;
import com.docgen.core.infrastructure.config.RabbitMQConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RabbitMQEventPublisherTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private RabbitMQEventPublisher publisher;

    @Test
    void publishDocumentGenerationRequest_ShouldSendMessage() {
        Document document = Document.builder().id("doc-1").build();

        publisher.publishDocumentGenerationRequest(document);

        verify(rabbitTemplate).convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                document
        );
    }
}
