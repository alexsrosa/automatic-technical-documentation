import { Requirement } from "./Requirement";

export enum DocumentType {
  TECHNICAL_SPEC = "TECHNICAL_SPEC",
  API_DOCS = "API_DOCS",
  README = "README",
  ARCHITECTURE_DIAGRAM = "ARCHITECTURE_DIAGRAM",
}

export enum DocumentStatus {
  PENDING = "PENDING",
  GENERATING = "GENERATING",
  COMPLETED = "COMPLETED",
  FAILED = "FAILED",
}

export interface Document {
  id: string;
  projectId: string;
  title: string;
  content: string;
  type: DocumentType;
  status: DocumentStatus;
  requirements: Requirement[];
  generatedAt?: string;
}
