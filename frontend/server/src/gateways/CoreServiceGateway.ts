import axios, { AxiosInstance } from "axios";

export class CoreServiceGateway {
  private client: AxiosInstance;

  constructor(accessToken: string) {
    this.client = axios.create({
      baseURL: process.env.BACKEND_API_URL,
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    });
  }

  async getProjects() {
    // MOCK DATA FOR DEVELOPMENT (Backend connection fallback)
    try {
      const response = await this.client.get("/projects");
      return response.data;
    } catch (error) {
      console.warn("Backend unavailable, returning mock projects");
      return [
        {
          id: "1",
          name: "E-Commerce Platform",
          description: "Microservices based e-commerce solution with inventory and order management.",
          ownerId: "user-1",
          createdAt: new Date().toISOString(),
          updatedAt: new Date().toISOString(),
          repositoryUrl: "https://github.com/example/ecommerce"
        },
        {
          id: "2",
          name: "Payment Gateway Integration",
          description: "Integration service for multiple payment providers (Stripe, PayPal).",
          ownerId: "user-1",
          createdAt: new Date(Date.now() - 86400000).toISOString(),
          updatedAt: new Date().toISOString(),
          repositoryUrl: "https://github.com/example/payments"
        }
      ];
    }
  }

  async createProject(data: any) {
    // MOCK DATA FOR DEVELOPMENT
    try {
      const response = await this.client.post("/projects", data);
      return response.data;
    } catch (error) {
       console.warn("Backend unavailable, returning mock created project");
       return {
         ...data,
         id: Math.random().toString(36).substring(7),
         ownerId: "user-1",
         createdAt: new Date().toISOString(),
         updatedAt: new Date().toISOString()
       };
    }
  }

  async getProject(id: string) {
    // Assuming backend has this endpoint, though currently frontend doesn't use it directly in list
    // This is a placeholder for future use or detail view
    return {}; 
  }
}
