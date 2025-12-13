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
    const response = await this.client.get("/projects");
    return response.data;
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
