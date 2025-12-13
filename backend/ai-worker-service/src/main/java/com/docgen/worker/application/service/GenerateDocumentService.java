package com.docgen.worker.application.service;

import com.docgen.worker.application.port.input.GenerateDocumentUseCase;
import com.docgen.worker.domain.model.GenerationCommand;
import com.docgen.worker.domain.prompts.PromptBuilder;
import com.docgen.worker.infrastructure.client.CoreServiceClient;
import com.docgen.worker.infrastructure.client.dto.RequirementDto;
import com.docgen.worker.infrastructure.client.dto.UpdateDocumentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenerateDocumentService implements GenerateDocumentUseCase {

    private final ChatClient chatClient;
    private final PromptBuilder promptBuilder;
    private final CoreServiceClient coreServiceClient;

    @Override
    public void generateDocument(GenerationCommand command) {
        log.info("Starting generation for document: {}", command.documentId());
        
        try {
            String systemText = promptBuilder.buildSystemPrompt(command.type());
            String userText = promptBuilder.buildUserPrompt(command);
            
            Prompt prompt = new Prompt(List.of(
                new SystemMessage(systemText),
                new UserMessage(userText)
            ));
            
            var response = chatClient.call(prompt);
            String content = response.getResult().getOutput().getContent();
            
            log.info("Generation completed for document: {}", command.documentId());
            
            // Map requirements back to DTOs
            List<RequirementDto> reqDtos = command.requirements().stream()
                .map(r -> new RequirementDto(r.id(), r.description(), r.priority()))
                .collect(Collectors.toList());

            // Send result back to Core Service
            UpdateDocumentRequest updateRequest = new UpdateDocumentRequest(
                command.title(),
                command.type(),
                reqDtos,
                content,
                "COMPLETED"
            );
            
            coreServiceClient.updateDocument(command.documentId(), updateRequest);
            log.info("Document updated successfully in Core Service");
            
        } catch (Exception e) {
            log.error("Failed to generate document: {}", command.documentId(), e);
            try {
                // Map requirements back to DTOs
                List<RequirementDto> reqDtos = command.requirements().stream()
                    .map(r -> new RequirementDto(r.id(), r.description(), r.priority()))
                    .collect(Collectors.toList());
                    
                UpdateDocumentRequest failureRequest = new UpdateDocumentRequest(
                    command.title(),
                    command.type(),
                    reqDtos,
                    null,
                    "FAILED"
                );
                coreServiceClient.updateDocument(command.documentId(), failureRequest);
            } catch (Exception updateEx) {
                log.error("Failed to update document status to FAILED", updateEx);
            }
        }
    }
}
