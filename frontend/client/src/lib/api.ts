import { useSession } from "next-auth/react";
import axios from "axios";
import { useEffect } from "react";

const api = axios.create({
  // In development, Next.js rewrites /api/bff to http://localhost:4001 via next.config.mjs
  // However, axios on client-side should point to Next.js API routes that proxy to BFF,
  // or directly to BFF if CORS is configured.
  // Given setup: "npm run dev" uses "concurrently" to start server (4001) and client (4000).
  // The client's next.config.mjs likely handles proxying /api/bff to 4001.
  baseURL: "/api/bff", 
});

export const useApi = () => {
  const { data: session } = useSession();

  useEffect(() => {
    const requestInterceptor = api.interceptors.request.use((config) => {
      if (session?.accessToken) {
        config.headers.Authorization = `Bearer ${session.accessToken}`;
      }
      return config;
    });

    return () => {
      api.interceptors.request.eject(requestInterceptor);
    };
  }, [session]);

  return api;
};
