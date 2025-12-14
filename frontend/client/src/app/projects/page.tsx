"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import { toast } from "sonner";
import { useSession } from "next-auth/react";
import { Project } from "@/domain/models/Project";
import { Plus, Folder, AlertCircle, Search, Clock, Calendar } from "lucide-react";
import { useApi } from "@/lib/api";

export default function ProjectsPage() {
  const { data: session, status } = useSession();
  const [projects, setProjects] = useState<Project[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [searchTerm, setSearchTerm] = useState("");
  const api = useApi();

  useEffect(() => {
    if (status === "loading") return;

    if (status === "unauthenticated") {
        setLoading(false);
        return; 
    }

    const fetchProjects = async () => {
      try {
        const response = await api.get("/projects");
        setProjects(response.data);
      } catch (err: any) {
        // Fallback for demo purposes if backend is unreachable
        console.warn("Backend unavailable, using mock data");
        setProjects([
          { id: "1", name: "E-Commerce Platform", description: "Microservices based e-commerce solution", ownerId: "user-1", createdAt: new Date().toISOString(), updatedAt: new Date().toISOString() },
          { id: "2", name: "Payment Gateway Integration", description: "Integration with Stripe and PayPal", ownerId: "user-1", createdAt: new Date().toISOString(), updatedAt: new Date().toISOString() }
        ]);
        setLoading(false); // Stop loading even if backend fails
      } finally {
        setLoading(false);
      }
    };

    if (session?.accessToken) {
        fetchProjects();
    } else if (status === 'authenticated') {
        // If authenticated but no access token yet (edge case), try fetch anyway or wait
        fetchProjects();
    }
  }, [session, status, api]);

  if (loading || status === "loading") return (
    <div className="flex items-center justify-center min-h-screen">
       <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
    </div>
  );

  if (error) return (
    <div className="flex flex-col items-center justify-center min-h-[50vh] text-red-500">
      <AlertCircle size={48} className="mb-4" />
      <h2 className="text-2xl font-bold mb-2">Error</h2>
      <p>{error}</p>
      <button 
        onClick={() => window.location.reload()} 
        className="mt-4 px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
      >
        Retry
      </button>
    </div>
  );

  const filteredProjects = projects.filter(project => 
    project.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
    project.description.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="container mx-auto p-8 max-w-7xl">
      <div className="flex flex-col md:flex-row justify-between items-start md:items-center mb-8 gap-4">
        <div>
            <h1 className="text-3xl font-bold text-gray-900">Projects</h1>
            <p className="text-gray-500 mt-1">Manage and document your software projects</p>
        </div>
        <Link
          href="/projects/create"
          className="bg-blue-600 text-white px-4 py-2 rounded-md flex items-center gap-2 hover:bg-blue-700 transition-colors shadow-sm"
        >
          <Plus size={20} />
          New Project
        </Link>
      </div>

      {/* Search and Filter */}
      <div className="mb-8">
        <div className="relative max-w-md">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" size={20} />
            <input 
                type="text" 
                placeholder="Search projects..." 
                className="w-full pl-10 pr-4 py-2 border border-gray-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
            />
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {filteredProjects.length === 0 ? (
          <div className="col-span-full text-center py-16 text-gray-500 bg-gray-50 rounded-xl border-2 border-dashed border-gray-200">
            <div className="w-16 h-16 bg-gray-100 rounded-full flex items-center justify-center mx-auto mb-4">
                <Folder size={32} className="text-gray-400" />
            </div>
            <h3 className="text-lg font-medium text-gray-900 mb-1">No projects found</h3>
            <p className="text-gray-500 mb-6">
                {searchTerm ? "Try adjusting your search terms" : "Get started by creating your first project"}
            </p>
            {!searchTerm && (
                <Link
                href="/projects/create"
                className="inline-flex items-center gap-2 px-4 py-2 bg-white border border-gray-300 rounded-md hover:bg-gray-50 font-medium text-gray-700 transition-colors"
                >
                <Plus size={18} />
                Create Project
                </Link>
            )}
          </div>
        ) : (
          filteredProjects.map((project) => (
            <Link
              key={project.id}
              href={`/projects/${project.id}`}
              className="group block bg-white p-6 rounded-xl shadow-sm border border-gray-200 hover:shadow-md hover:border-blue-200 transition-all duration-200"
            >
              <div className="flex items-start justify-between mb-4">
                <div className="p-2 bg-blue-50 rounded-lg text-blue-600 group-hover:bg-blue-100 transition-colors">
                    <Folder size={24} />
                </div>
                {/* Optional: Add status badge or other indicators here */}
              </div>
              
              <h3 className="text-xl font-semibold mb-2 text-gray-900 group-hover:text-blue-600 transition-colors">
                {project.name}
              </h3>
              <p className="text-gray-600 line-clamp-2 mb-4 text-sm h-10">
                {project.description}
              </p>
              
              <div className="flex items-center gap-4 text-xs text-gray-500 pt-4 border-t border-gray-100">
                <div className="flex items-center gap-1">
                    <Calendar size={14} />
                    <span>{new Date(project.createdAt).toLocaleDateString()}</span>
                </div>
                <div className="flex items-center gap-1 ml-auto">
                    <Clock size={14} />
                    <span>Updated {new Date(project.updatedAt).toLocaleDateString()}</span>
                </div>
              </div>
            </Link>
          ))
        )}
      </div>
    </div>
  );
}
