import { NextRequest, NextResponse } from "next/server";
import { getServerSession } from "next-auth";
import { authOptions } from "@/lib/auth";
import axios from "axios";
import { z } from "zod";
import { DocumentType } from "@/domain/models/Document";
import { RequirementPriority } from "@/domain/models/Requirement";

const updateDocumentSchema = z.object({
  title: z.string().min(3).optional(),
  type: z.nativeEnum(DocumentType).optional(),
  requirements: z.array(z.object({
    id: z.string(),
    description: z.string(),
    priority: z.nativeEnum(RequirementPriority),
  })).optional(),
});

export async function PUT(
  req: NextRequest,
  { params }: { params: { id: string; docId: string } }
) {
  const session = await getServerSession(authOptions);
  if (!session?.accessToken) {
    return NextResponse.json({ error: "Unauthorized" }, { status: 401 });
  }

  try {
    const body = await req.json();
    const validatedData = updateDocumentSchema.parse(body);

    const response = await axios.put(
      `${process.env.BACKEND_API_URL}/documents/${params.docId}`,
      validatedData,
      {
        headers: {
          Authorization: `Bearer ${session.accessToken}`,
        },
      }
    );
    return NextResponse.json(response.data);
  } catch (error) {
    if (error instanceof z.ZodError) {
      return NextResponse.json({ error: error.errors }, { status: 400 });
    }
    console.error("Error updating document:", error);
    return NextResponse.json(
      { error: "Failed to update document" },
      { status: 500 }
    );
  }
}

export async function GET(
  req: NextRequest,
  { params }: { params: { id: string; docId: string } }
) {
  const session = await getServerSession(authOptions);
  if (!session?.accessToken) {
    return NextResponse.json({ error: "Unauthorized" }, { status: 401 });
  }

  try {
    const response = await axios.get(
      `${process.env.BACKEND_API_URL}/documents/${params.docId}`,
      {
        headers: {
          Authorization: `Bearer ${session.accessToken}`,
        },
      }
    );
    return NextResponse.json(response.data);
  } catch (error) {
    console.error("Error fetching document:", error);
    return NextResponse.json(
      { error: "Failed to fetch document" },
      { status: 500 }
    );
  }
}
