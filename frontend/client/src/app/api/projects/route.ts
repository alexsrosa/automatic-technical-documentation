import { NextRequest } from "next/server";
import { ProjectController } from "@/adapters/api/controllers/ProjectController";

export async function GET(req: NextRequest) {
  return ProjectController.list(req);
}

export async function POST(req: NextRequest) {
  return ProjectController.create(req);
}
