package com.docgen.core.infrastructure.adapters.input.web.doc;

import com.docgen.core.infrastructure.adapters.input.web.dto.CreateProjectRequest;
import com.docgen.core.infrastructure.adapters.input.web.dto.ProjectResponse;
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

@Tag(name = "Projects", description = "Project management API")
public interface ProjectControllerDoc {

    @Operation(summary = "Create a new project", description = "Creates a new project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Project created successfully",
                    content = @Content(schema = @Schema(implementation = ProjectResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    ResponseEntity<ProjectResponse> createProject(
            @Parameter(description = "Project creation request", required = true) @RequestBody CreateProjectRequest request);

    @Operation(summary = "Get projects", description = "Retrieves projects, optionally filtered by owner ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of projects",
                    content = @Content(schema = @Schema(implementation = ProjectResponse.class)))
    })
    @GetMapping
    ResponseEntity<List<ProjectResponse>> getProjects(
            @Parameter(description = "Owner ID to filter projects") @RequestParam(required = false) String ownerId);

    @Operation(summary = "Get project by ID", description = "Retrieves a specific project by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project found",
                    content = @Content(schema = @Schema(implementation = ProjectResponse.class))),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    @GetMapping("/{id}")
    ResponseEntity<ProjectResponse> getProject(
            @Parameter(description = "Project ID", required = true) @PathVariable String id);
}