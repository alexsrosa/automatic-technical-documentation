import { NextRequest, NextResponse } from "next/server";
import { getServerSession } from "next-auth";
import { authOptions } from "@/lib/auth";
import { z } from "zod";
import { CoreServiceGateway } from "../gateways/CoreServiceGateway";

const createProjectSchema = z.object({
  name: z.string().min(3),
  description: z.string().min(10),
  repositoryUrl: z.string().url().optional().or(z.literal("")),
});

export class ProjectController {
  static async list(req: NextRequest) {
    const session = await getServerSession(authOptions);
    if (!session?.accessToken) {
      return NextResponse.json({ error: "Unauthorized" }, { status: 401 });
    }

    try {
      const gateway = new CoreServiceGateway(session.accessToken as string);
      const projects = await gateway.getProjects();
      return NextResponse.json(projects);
    } catch (error) {
      console.error("Error fetching projects:", error);
      return NextResponse.json(
        { error: "Failed to fetch projects" },
        { status: 500 }
      );
    }
  }

  static async create(req: NextRequest) {
    const session = await getServerSession(authOptions);
    if (!session?.accessToken) {
      return NextResponse.json({ error: "Unauthorized" }, { status: 401 });
    }

    try {
      const body = await req.json();
      const validatedData = createProjectSchema.parse(body);

      const gateway = new CoreServiceGateway(session.accessToken as string);
      const project = await gateway.createProject(validatedData);
      
      return NextResponse.json(project, { status: 201 });
    } catch (error) {
      if (error instanceof z.ZodError) {
        return NextResponse.json({ error: error.errors }, { status: 400 });
      }
      console.error("Error creating project:", error);
      return NextResponse.json(
        { error: "Failed to create project" },
        { status: 500 }
      );
    }
  }
}
