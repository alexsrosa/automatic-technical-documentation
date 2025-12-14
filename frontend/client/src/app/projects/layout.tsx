"use client";

import Link from "next/link";
import { usePathname } from "next/navigation";
import { signOut, useSession } from "next-auth/react";
import { Book, LogOut, User, LayoutGrid } from "lucide-react";

export default function ProjectsLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  const pathname = usePathname();
  const { data: session } = useSession();

  return (
    <div className="min-h-screen bg-gray-50 flex flex-col">
      {/* Navigation Bar */}
      <header className="bg-white border-b border-gray-200 sticky top-0 z-10">
        <div className="container mx-auto px-4 h-16 flex items-center justify-between">
          <div className="flex items-center gap-8">
            <Link href="/" className="flex items-center gap-2">
              <Book className="text-blue-600" size={24} />
              <span className="text-xl font-bold text-gray-900">DocGen AI</span>
            </Link>
            
            <nav className="hidden md:flex items-center gap-1">
              <Link 
                href="/projects" 
                className={`px-3 py-2 rounded-md text-sm font-medium transition-colors ${
                  pathname === "/projects" || pathname.startsWith("/projects/") 
                    ? "bg-blue-50 text-blue-700" 
                    : "text-gray-600 hover:text-gray-900 hover:bg-gray-50"
                }`}
              >
                <div className="flex items-center gap-2">
                  <LayoutGrid size={18} />
                  <span>Projects</span>
                </div>
              </Link>
            </nav>
          </div>

          <div className="flex items-center gap-4">
            <div className="hidden sm:flex items-center gap-3 pl-4 border-l border-gray-200">
              <div className="text-right">
                <p className="text-sm font-medium text-gray-900">
                  {session?.user?.name || "User"}
                </p>
                <p className="text-xs text-gray-500">
                  {session?.user?.email}
                </p>
              </div>
              <div className="w-8 h-8 bg-blue-100 rounded-full flex items-center justify-center text-blue-600">
                <User size={16} />
              </div>
            </div>
            
            <button
              onClick={() => signOut({ callbackUrl: "/" })}
              className="p-2 text-gray-500 hover:text-red-600 hover:bg-red-50 rounded-md transition-colors"
              title="Sign Out"
            >
              <LogOut size={20} />
            </button>
          </div>
        </div>
      </header>

      {/* Main Content */}
      <main className="flex-1">
        {children}
      </main>
    </div>
  );
}
