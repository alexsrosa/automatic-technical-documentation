import { render, screen, waitFor } from "@testing-library/react";
import ProjectsPage from "./page";
import { useSession } from "next-auth/react";
import { useApi } from "@/lib/api";

// Mock NextAuth
jest.mock("next-auth/react");

// Mock API Hook
jest.mock("@/lib/api");

// Mock Next.js Link
jest.mock("next/link", () => {
  return ({ children, href }: { children: React.ReactNode; href: string }) => {
    return <a href={href}>{children}</a>;
  };
});

// Mock Lucide icons
jest.mock("lucide-react", () => ({
  Plus: () => <span>PlusIcon</span>,
  Folder: () => <span>FolderIcon</span>,
  AlertCircle: () => <span>AlertIcon</span>,
  Search: () => <span>SearchIcon</span>,
  Clock: () => <span>ClockIcon</span>,
  Calendar: () => <span>CalendarIcon</span>,
}));

describe("ProjectsPage", () => {
  const mockApi = {
    get: jest.fn(),
  };

  beforeEach(() => {
    jest.clearAllMocks();
    (useSession as jest.Mock).mockReturnValue({ 
      data: { accessToken: "mock-token" },
      status: "authenticated" 
    });
    (useApi as jest.Mock).mockReturnValue(mockApi);
  });

  it("renders loading state initially", () => {
    mockApi.get.mockImplementation(() => new Promise(() => {})); // Never resolves
    render(<ProjectsPage />);
    // Updated expectation to match the actual spinner implementation
    const spinner = document.querySelector(".animate-spin");
    expect(spinner).toBeInTheDocument();
  });

  it("renders projects when API call succeeds", async () => {
    const projects = [
      {
        id: "1",
        name: "Test Project",
        description: "Test Description",
        createdAt: "2023-01-01T00:00:00Z",
        updatedAt: "2023-01-02T00:00:00Z",
      },
    ];
    mockApi.get.mockResolvedValue({ data: projects });

    render(<ProjectsPage />);

    await waitFor(() => {
      expect(screen.getByText("Test Project")).toBeInTheDocument();
    });
    expect(screen.getByText("Test Description")).toBeInTheDocument();
  });

  it("renders mock data when API call fails", async () => {
    mockApi.get.mockRejectedValue(new Error("Network Error"));

    render(<ProjectsPage />);

    await waitFor(() => {
      // Should show the fallback mock data
      expect(screen.getByText("E-Commerce Platform")).toBeInTheDocument();
      expect(screen.getByText("Microservices based e-commerce solution")).toBeInTheDocument();
    });
  });

  it("renders empty state when no projects found", async () => {
    mockApi.get.mockResolvedValue({ data: [] });

    render(<ProjectsPage />);

    await waitFor(() => {
      expect(screen.getByText("No projects found")).toBeInTheDocument();
    });
  });
});
