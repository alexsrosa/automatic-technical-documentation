import { useSession } from "next-auth/react";
import axios from "axios";
import { useEffect } from "react";

const api = axios.create({
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
