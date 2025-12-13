import Link from "next/link";
import { ArrowRight, Book, FileText, Sparkles } from "lucide-react";

export default function Home() {
  return (
    <div className="min-h-screen flex flex-col bg-white">
      {/* Header */}
      <header className="border-b border-gray-100">
        <div className="container mx-auto px-4 h-16 flex items-center justify-between">
          <div className="flex items-center gap-2">
            <Book className="text-blue-600" size={24} />
            <span className="text-xl font-bold text-gray-900">DocGen AI</span>
          </div>
          <div className="flex items-center gap-4">
            <Link
              href="/projects"
              className="text-sm font-medium text-gray-600 hover:text-gray-900"
            >
              Dashboard
            </Link>
          </div>
        </div>
      </header>

      {/* Hero Section */}
      <main className="flex-1 flex flex-col items-center justify-center text-center px-4 py-20 bg-gradient-to-b from-white to-gray-50">
        <div className="max-w-3xl space-y-8">
          <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-blue-50 text-blue-700 text-sm font-medium border border-blue-100">
            <Sparkles size={14} />
            <span>AI-Powered Documentation</span>
          </div>
          
          <h1 className="text-5xl md:text-6xl font-bold tracking-tight text-gray-900">
            Technical Documentation, <br />
            <span className="text-blue-600">Automated.</span>
          </h1>
          
          <p className="text-xl text-gray-600 max-w-2xl mx-auto">
            Transform your requirements into comprehensive technical documentation instantly. 
            Generate architecture diagrams, API specs, and more with the power of AI.
          </p>

          <div className="flex items-center justify-center gap-4 pt-4">
            <Link
              href="/projects"
              className="inline-flex items-center gap-2 px-6 py-3 bg-blue-600 text-white rounded-lg hover:bg-blue-700 font-medium transition-colors"
            >
              Get Started
              <ArrowRight size={18} />
            </Link>
            <Link
              href="https://github.com/alexrosa/automatic-technical-documentation"
              target="_blank"
              className="inline-flex items-center gap-2 px-6 py-3 bg-white text-gray-700 border border-gray-200 rounded-lg hover:bg-gray-50 font-medium transition-colors"
            >
              View on GitHub
            </Link>
          </div>
        </div>

        {/* Feature Grid */}
        <div className="grid md:grid-cols-3 gap-8 mt-24 max-w-5xl w-full text-left">
          <div className="p-6 bg-white rounded-xl border border-gray-100 shadow-sm hover:shadow-md transition-shadow">
            <div className="w-10 h-10 bg-blue-100 rounded-lg flex items-center justify-center mb-4 text-blue-600">
              <FileText size={20} />
            </div>
            <h3 className="text-lg font-semibold mb-2">Requirement Analysis</h3>
            <p className="text-gray-600">
              Input your functional requirements and let our AI analyze and structure them into technical specs.
            </p>
          </div>
          <div className="p-6 bg-white rounded-xl border border-gray-100 shadow-sm hover:shadow-md transition-shadow">
            <div className="w-10 h-10 bg-purple-100 rounded-lg flex items-center justify-center mb-4 text-purple-600">
              <Sparkles size={20} />
            </div>
            <h3 className="text-lg font-semibold mb-2">Diagram Generation</h3>
            <p className="text-gray-600">
              Automatically generate Mermaid.js diagrams for architecture, sequences, and entity relationships.
            </p>
          </div>
          <div className="p-6 bg-white rounded-xl border border-gray-100 shadow-sm hover:shadow-md transition-shadow">
            <div className="w-10 h-10 bg-green-100 rounded-lg flex items-center justify-center mb-4 text-green-600">
              <Book size={20} />
            </div>
            <h3 className="text-lg font-semibold mb-2">Live Preview</h3>
            <p className="text-gray-600">
              Watch your documentation being built in real-time with split-screen preview and instant updates.
            </p>
          </div>
        </div>
      </main>

      {/* Footer */}
      <footer className="border-t border-gray-100 py-8 bg-white">
        <div className="container mx-auto px-4 text-center text-gray-500 text-sm">
          <p>© {new Date().getFullYear()} DocGen AI. All rights reserved.</p>
        </div>
      </footer>
    </div>
  );
}
