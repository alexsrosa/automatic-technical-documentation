"use client";

import { signIn } from "next-auth/react";
import { useSearchParams } from "next/navigation";
import { Book, ShieldCheck } from "lucide-react";
import { useState, Suspense } from "react";

function SignInContent() {
  const searchParams = useSearchParams();
  const callbackUrl = searchParams.get("callbackUrl") || "/projects";
  const [isLoading, setIsLoading] = useState(false);

  const handleSignIn = async () => {
    setIsLoading(true);
    await signIn("keycloak", { callbackUrl });
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50 px-4">
      <div className="max-w-md w-full space-y-8 bg-white p-8 rounded-xl shadow-sm border border-gray-100">
        <div className="text-center">
          <div className="mx-auto h-12 w-12 bg-blue-100 rounded-full flex items-center justify-center text-blue-600 mb-4">
            <Book size={24} />
          </div>
          <h2 className="text-3xl font-bold text-gray-900">Welcome back</h2>
          <p className="mt-2 text-sm text-gray-600">
            Sign in to your account to continue
          </p>
        </div>

        <div className="mt-8 space-y-6">
          <button
            onClick={handleSignIn}
            disabled={isLoading}
            className="group relative w-full flex justify-center py-3 px-4 border border-transparent text-sm font-medium rounded-lg text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
          >
            <span className="absolute left-0 inset-y-0 flex items-center pl-3">
              <ShieldCheck
                className="h-5 w-5 text-blue-500 group-hover:text-blue-400"
                aria-hidden="true"
              />
            </span>
            {isLoading ? "Redirecting..." : "Sign in with SSO"}
          </button>
        </div>

        <div className="mt-6 text-center text-xs text-gray-500">
          <p>
            By signing in, you agree to our Terms of Service and Privacy Policy.
          </p>
        </div>
      </div>
    </div>
  );
}

export default function SignIn() {
  return (
    <Suspense fallback={<div>Loading...</div>}>
      <SignInContent />
    </Suspense>
  );
}
