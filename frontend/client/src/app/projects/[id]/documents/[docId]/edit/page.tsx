"use client";

import { useEffect, useState, useCallback } from "react";
import axios from "axios";
import Link from "next/link";
import { Document, DocumentType, DocumentStatus } from "@/domain/models/Document";
import { Requirement } from "@/domain/models/Requirement";
import { ArrowLeft, Save, Loader2, Play, Eye, EyeOff, Download } from "lucide-react";
import { useParams, useRouter } from "next/navigation";
import { RequirementsManager } from "@/adapters/ui/components/editor/RequirementsManager";
import { DocumentPreview } from "@/adapters/ui/components/preview/DocumentPreview";
import { RefinementPanel } from "@/adapters/ui/components/editor/RefinementPanel";
import { toast, Toaster } from "sonner";
import { useDocumentStream } from "@/adapters/ui/hooks/useDocumentStream";

export default function EditDocumentPage() {
  const params = useParams();
  const projectId = params.id as string;
  const docId = params.docId as string;
  const router = useRouter();
  
  const [document, setDocument] = useState<Document | null>(null);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [generating, setGenerating] = useState(false);
  const [refining, setRefining] = useState(false);
  const [showPreview, setShowPreview] = useState(true);

  // SSE Hook
  const { content: streamedContent, isConnected } = useDocumentStream(docId);

  // Update document content when stream data arrives
  useEffect(() => {
    if (streamedContent && document) {
        setDocument(prev => prev ? { ...prev, content: streamedContent } : null);
    }
  }, [streamedContent]);

  const fetchDocument = useCallback(async () => {
    try {
      const response = await axios.get(`/api/projects/${projectId}/documents/${docId}`);
      setDocument(response.data);
    } catch (err) {
      toast.error("Failed to load document");
      console.error(err);
    } finally {
      setLoading(false);
    }
  }, [projectId, docId]);

  useEffect(() => {
    if (docId) {
      fetchDocument();
    }
  }, [docId, fetchDocument]);

  const handleSave = async () => {
    if (!document) return;

    setSaving(true);
    try {
      await axios.put(`/api/projects/${projectId}/documents/${docId}`, {
        title: document.title,
        type: document.type,
        requirements: document.requirements || [],
      });
      toast.success("Document saved successfully");
    } catch (err) {
      toast.error("Failed to save document");
      console.error(err);
    } finally {
      setSaving(false);
    }
  };

  const handleGenerate = async () => {
      if (!document) return;
      
      // Save first
      await handleSave();

      setGenerating(true);
      try {
          await axios.post(`/api/projects/${projectId}/documents/${docId}/generate`);
          toast.success("Generation started");
          // Optionally redirect or poll status
      } catch (err) {
          toast.error("Failed to start generation");
          console.error(err);
      } finally {
          setGenerating(false);
      }
  };

  const handleRefine = async (instruction: string) => {
    setRefining(true);
    try {
      await axios.post(`/api/projects/${projectId}/documents/${docId}/refine`, {
        instruction
      });
      toast.success("Refinement started");
    } catch (err) {
      toast.error("Failed to refine document");
      console.error(err);
    } finally {
      setRefining(false);
    }
  };

  const handleExport = (format: 'pdf' | 'md') => {
      // In a real app, this would trigger a backend download
      // For now, we can just download the markdown content
      if (!document?.content) return;
      
      const blob = new Blob([document.content], { type: 'text/markdown' });
      const url = URL.createObjectURL(blob);
      const a = window.document.createElement('a');
      a.href = url;
      a.download = `${document.title.replace(/\s+/g, '_')}.md`;
      a.click();
      URL.revokeObjectURL(url);
      toast.success("Document exported");
  };

  if (loading) return <div className="p-8 flex items-center gap-2"><Loader2 className="animate-spin" /> Loading...</div>;
  if (!document) return <div className="p-8">Document not found</div>;

  return (
    <div className="container mx-auto p-4 h-screen flex flex-col">
      <Toaster position="top-right" />
      
      <div className="flex justify-between items-center mb-4 shrink-0">
        <div className="flex items-center gap-4">
          <Link
            href={`/projects/${projectId}`}
            className="text-gray-600 hover:text-gray-900"
          >
            <ArrowLeft size={20} />
          </Link>
          <div>
            <h1 className="text-xl font-bold">{document.title}</h1>
            <span className="text-xs text-gray-500">{document.type}</span>
          </div>
        </div>
        <div className="flex items-center gap-3">
          <button
            onClick={() => setShowPreview(!showPreview)}
            className="flex items-center gap-2 px-3 py-1.5 text-sm bg-gray-100 rounded-md hover:bg-gray-200 text-gray-700"
          >
            {showPreview ? <EyeOff size={16} /> : <Eye size={16} />}
            {showPreview ? "Hide Preview" : "Show Preview"}
          </button>
           <button
            onClick={() => handleExport('md')}
            className="flex items-center gap-2 px-3 py-1.5 text-sm bg-gray-100 rounded-md hover:bg-gray-200 text-gray-700"
            title="Export Markdown"
          >
            <Download size={16} />
          </button>
          <button
            onClick={handleSave}
            disabled={saving}
            className="flex items-center gap-2 px-3 py-1.5 text-sm bg-white border border-gray-300 rounded-md hover:bg-gray-50 text-gray-700 disabled:opacity-50"
          >
            {saving ? <Loader2 size={16} className="animate-spin" /> : <Save size={16} />}
            Save
          </button>
          <button
            onClick={handleGenerate}
            disabled={generating || saving}
            className="flex items-center gap-2 px-3 py-1.5 text-sm bg-blue-600 text-white rounded-md hover:bg-blue-700 disabled:opacity-50"
          >
             {generating ? <Loader2 size={16} className="animate-spin" /> : <Play size={16} />}
            Generate
          </button>
        </div>
      </div>

      <div className="flex-1 flex gap-4 overflow-hidden">
        <div className={`flex-1 flex flex-col overflow-y-auto ${showPreview ? 'w-1/2' : 'w-full'}`}>
          <div className="bg-white rounded-lg shadow-sm border border-gray-200 p-6 mb-4">
            <h2 className="text-lg font-semibold mb-4">Requirements</h2>
            <RequirementsManager 
                requirements={document.requirements || []} 
                onChange={(newReqs) => setDocument({ ...document, requirements: newReqs })}
            />
          </div>
        </div>
        
        {showPreview && (
          <div className="w-1/2 flex flex-col overflow-hidden bg-gray-50 border border-gray-200 rounded-lg">
             <div className="p-3 border-b bg-white font-medium text-sm flex justify-between items-center">
                <span>Document Preview</span>
                <span className={`text-xs px-2 py-0.5 rounded-full ${
                  document.status === DocumentStatus.COMPLETED ? 'bg-green-100 text-green-800' :
                  document.status === DocumentStatus.GENERATING ? 'bg-yellow-100 text-yellow-800' :
                  'bg-gray-100 text-gray-800'
                }`}>{document.status}</span>
             </div>
             <div className="flex-1 overflow-y-auto p-4">
                <DocumentPreview content={document.content || ""} />
             </div>
             <RefinementPanel onRefine={handleRefine} isRefining={refining} />
          </div>
        )}
      </div>
    </div>
  );
}
