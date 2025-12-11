package com.docgen.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Requirement {
    private String id;
    private String description;
    private Priority priority;
    
    public enum Priority {
        LOW,
        MEDIUM,
        HIGH,
        CRITICAL
    }
}
