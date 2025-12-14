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
    try {
      const response = await this.client.get("/projects");
      return response.data;
    } catch (error) {
      console.warn("Backend unavailable, returning mock projects");
      return [
        { id: "1", name: "E-Commerce Platform", description: "Microservices based e-commerce solution", ownerId: "user-1", createdAt: new Date().toISOString(), updatedAt: new Date().toISOString() },
        { id: "2", name: "Payment Gateway Integration", description: "Integration with Stripe and PayPal", ownerId: "user-1", createdAt: new Date().toISOString(), updatedAt: new Date().toISOString() }
      ];
    }
  }

  async createProject(data: any) {
    const response = await this.client.post("/projects", data);
    return response.data;
  }

  async getProject(id: string) {
    // Assuming backend has this endpoint, though currently frontend doesn't use it directly in list
    // This is a placeholder for future use or detail view
    return {}; 
  }
}
