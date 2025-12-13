export enum RequirementPriority {
  LOW = "LOW",
  MEDIUM = "MEDIUM",
  HIGH = "HIGH",
  CRITICAL = "CRITICAL",
}

export interface Requirement {
  id: string;
  description: string;
  priority: RequirementPriority;
}
