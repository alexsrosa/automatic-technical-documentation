import { NextRequest, NextResponse } from "next/server";
import { getServerSession } from "next-auth";
import { authOptions } from "@/lib/auth";
import axios from "axios";

export async function POST(
  req: NextRequest,
  { params }: { params: { id: string; docId: string } }
) {
  const session = await getServerSession(authOptions);
  if (!session?.accessToken) {
    return NextResponse.json({ error: "Unauthorized" }, { status: 401 });
  }

  try {
    const response = await axios.post(
      `${process.env.BACKEND_API_URL}/documents/${params.docId}/generate`,
      {},
      {
        headers: {
          Authorization: `Bearer ${session.accessToken}`,
        },
      }
    );
    return NextResponse.json(response.data, { status: 202 });
  } catch (error) {
    console.error("Error requesting generation:", error);
    return NextResponse.json(
      { error: "Failed to request generation" },
      { status: 500 }
    );
  }
}
