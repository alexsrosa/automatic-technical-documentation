import { Request, Response } from "express";
import { z } from "zod";
import { CoreServiceGateway } from "../gateways/CoreServiceGateway";

const createProjectSchema = z.object({
  name: z.string().min(3),
  description: z.string().min(10),
  repositoryUrl: z.string().url().optional().or(z.literal("")),
});

export class ProjectController {
  static async list(req: Request, res: Response) {
    const authHeader = req.headers.authorization;
    if (!authHeader || !authHeader.startsWith("Bearer ")) {
      return res.status(401).json({ error: "Unauthorized" });
    }
    
    const token = authHeader.split(" ")[1];

    try {
      const gateway = new CoreServiceGateway(token);
      const projects = await gateway.getProjects();
      return res.json(projects);
    } catch (error) {
      console.error("Error fetching projects:", error);
      return res.status(500).json({ error: "Failed to fetch projects" });
    }
  }

  static async create(req: Request, res: Response) {
    const authHeader = req.headers.authorization;
    if (!authHeader || !authHeader.startsWith("Bearer ")) {
      return res.status(401).json({ error: "Unauthorized" });
    }
    const token = authHeader.split(" ")[1];

    try {
      const validatedData = createProjectSchema.parse(req.body);

      const gateway = new CoreServiceGateway(token);
      const project = await gateway.createProject(validatedData);
      
      return res.status(201).json(project);
    } catch (error) {
      if (error instanceof z.ZodError) {
        return res.status(400).json({ error: error.errors });
      }
      console.error("Error creating project:", error);
      return res.status(500).json({ error: "Failed to create project" });
    }
  }
}
