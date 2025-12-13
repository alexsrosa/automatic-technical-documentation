export interface Project {
  id: string;
  name: string;
  description: string;
  repositoryUrl?: string;
  ownerId: string;
  createdAt: string;
  updatedAt: string;
}

export interface CreateProjectInput {
  name: string;
  description: string;
  repositoryUrl?: string;
}
