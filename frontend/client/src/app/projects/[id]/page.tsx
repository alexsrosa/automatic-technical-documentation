"use client";

import { useEffect, useState } from "react";
import axios from "axios";
import Link from "next/link";
import { Project } from "@/domain/models/Project";
import { Document, DocumentType, DocumentStatus } from "@/domain/models/Document";
import { Plus, FileText, ArrowLeft, Loader2 } from "lucide-react";
import { useParams } from "next/navigation";

export default function ProjectDetailsPage() {
  const params = useParams();
  const projectId = params.id as string;
  
  const [project, setProject] = useState<Project | null>(null);
  const [documents, setDocuments] = useState<Document[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [projectRes, documentsRes] = await Promise.all([
          axios.get(`/api/projects/${projectId}`),
          axios.get(`/api/projects/${projectId}/documents`),
        ]);
        setProject(projectRes.data);
        setDocuments(documentsRes.data);
      } catch (err) {
        setError("Failed to load project details");
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    if (projectId) {
      fetchData();
    }
  }, [projectId]);

  if (loading) return <div className="p-8 flex items-center gap-2"><Loader2 className="animate-spin" /> Loading...</div>;
  if (error) return <div className="p-8 text-red-500">{error}</div>;
  if (!project) return <div className="p-8">Project not found</div>;

  return (
    <div className="container mx-auto p-8">
      <Link
        href="/projects"
        className="inline-flex items-center gap-2 text-gray-600 hover:text-gray-900 mb-8"
      >
        <ArrowLeft size={20} />
        Back to Projects
      </Link>

      <div className="mb-8 border-b pb-6">
        <h1 className="text-3xl font-bold mb-2">{project.name}</h1>
        <p className="text-gray-600">{project.description}</p>
        {project.repositoryUrl && (
          <a
            href={project.repositoryUrl}
            target="_blank"
            rel="noopener noreferrer"
            className="text-blue-600 hover:underline mt-2 inline-block"
          >
            {project.repositoryUrl}
          </a>
        )}
      </div>

      <div className="flex justify-between items-center mb-6">
        <h2 className="text-2xl font-bold">Documents</h2>
        <Link
          href={`/projects/${projectId}/documents/new`}
          className="bg-blue-600 text-white px-4 py-2 rounded-md flex items-center gap-2 hover:bg-blue-700 transition-colors"
        >
          <Plus size={20} />
          New Document
        </Link>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {documents.length === 0 ? (
          <div className="col-span-full text-center py-12 text-gray-500 bg-gray-50 rounded-lg border border-dashed border-gray-300">
            <FileText size={48} className="mx-auto mb-4 opacity-50" />
            <p className="text-xl">No documents yet</p>
            <p className="mt-2">Create your first document to start generating</p>
          </div>
        ) : (
          documents.map((doc) => (
            <Link
              key={doc.id}
              href={`/projects/${projectId}/documents/${doc.id}/edit`}
              className="block bg-white p-6 rounded-lg shadow-sm border border-gray-200 hover:shadow-md transition-shadow"
            >
              <div className="flex items-start justify-between mb-4">
                <FileText className="text-blue-500" size={24} />
                <span className={`px-2 py-1 text-xs rounded-full ${
                  doc.status === DocumentStatus.COMPLETED ? 'bg-green-100 text-green-800' :
                  doc.status === DocumentStatus.FAILED ? 'bg-red-100 text-red-800' :
                  doc.status === DocumentStatus.GENERATING ? 'bg-yellow-100 text-yellow-800' :
                  'bg-gray-100 text-gray-800'
                }`}>
                  {doc.status}
                </span>
              </div>
              <h3 className="text-lg font-semibold mb-2">{doc.title}</h3>
              <p className="text-sm text-gray-500 mb-4">{doc.type}</p>
              <div className="text-xs text-gray-400">
                Created: {doc.generatedAt ? new Date(doc.generatedAt).toLocaleDateString() : 'Not generated yet'}
              </div>
            </Link>
          ))
        )}
      </div>
    </div>
  );
}
