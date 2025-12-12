package com.docgen.core.infrastructure.adapters.input.web.doc;

import com.docgen.core.infrastructure.adapters.input.web.dto.CreateDocumentRequest;
import com.docgen.core.infrastructure.adapters.input.web.dto.DocumentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Documents", description = "Document management API")
public interface DocumentControllerDoc {

    @Operation(summary = "Create a new document", description = "Creates a new document in a project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Document created successfully",
                    content = @Content(schema = @Schema(implementation = DocumentResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    ResponseEntity<DocumentResponse> createDocument(
            @Parameter(description = "Document creation request", required = true) @RequestBody CreateDocumentRequest request);

    @Operation(summary = "Request document generation", description = "Triggers the generation process for a document")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Generation request accepted"),
            @ApiResponse(responseCode = "404", description = "Document not found")
    })
    @PostMapping("/{id}/generate")
    ResponseEntity<Void> requestGeneration(
            @Parameter(description = "Document ID", required = true) @PathVariable String id);

    @Operation(summary = "Get documents", description = "Retrieves documents filtered by project ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of documents",
                    content = @Content(schema = @Schema(implementation = DocumentResponse.class)))
    })
    @GetMapping
    ResponseEntity<List<DocumentResponse>> getDocuments(
            @Parameter(description = "Project ID", required = true) @RequestParam String projectId);

    @Operation(summary = "Get document by ID", description = "Retrieves a specific document by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Document found",
                    content = @Content(schema = @Schema(implementation = DocumentResponse.class))),
            @ApiResponse(responseCode = "404", description = "Document not found")
    })
    @GetMapping("/{id}")
    ResponseEntity<DocumentResponse> getDocument(
            @Parameter(description = "Document ID", required = true) @PathVariable String id);
}