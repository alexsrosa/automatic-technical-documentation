package com.docgen.worker.domain.prompts;

import com.docgen.worker.domain.model.GenerationCommand;
import com.docgen.worker.domain.model.Requirement;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PromptBuilder {

    public String buildSystemPrompt(String type) {
        return switch (type) {
            case "TECHNICAL_SPEC" -> """
                You are a Senior Software Architect. 
                Generate a comprehensive Technical Specification based on the provided requirements.
                Structure the document with:
                1. Executive Summary
                2. System Overview
                3. Architecture Design
                4. Component Details
                5. Data Model
                6. API Specifications
                7. Non-Functional Requirements
                
                Use Markdown formatting. Use Mermaid.js for diagrams where appropriate (wrap in ```mermaid code blocks).
                """;
            case "API_DOCS" -> """
                You are a Technical Writer specializing in API documentation.
                Generate a comprehensive API Reference based on the requirements.
                Include:
                - Endpoints
                - Request/Response examples
                - Error codes
                - Authentication details
                
                Use Markdown formatting.
                """;
            case "ARCHITECTURE_DIAGRAM" -> """
                You are a System Architect.
                Generate a set of Mermaid.js diagrams representing the system architecture based on the requirements.
                Include:
                - Context Diagram (C4 Level 1)
                - Container Diagram (C4 Level 2)
                - Sequence Diagrams for key flows
                
                Output ONLY the Mermaid code blocks wrapped in ```mermaid ```.
                Add brief explanations before each diagram.
                """;
            default -> "You are a technical documentation assistant. Generate detailed documentation in Markdown.";
        };
    }

    public String buildUserPrompt(GenerationCommand command) {
        String reqs = command.requirements().stream()
            .map(r -> "- [" + r.priority() + "] " + r.description())
            .collect(Collectors.joining("\n"));

        return String.format("""
            Project Context: %s
            Document Title: %s
            
            Functional Requirements:
            %s
            
            Please generate the content now.
            """, command.projectId(), command.title(), reqs);
    }
}
