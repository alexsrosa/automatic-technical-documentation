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
}));

describe("ProjectsPage", () => {
  const mockApi = {
    get: jest.fn(),
  };

  beforeEach(() => {
    jest.clearAllMocks();
    (useSession as jest.Mock).mockReturnValue({ data: { accessToken: "mock-token" } });
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
      },
    ];
    mockApi.get.mockResolvedValue({ data: projects });

    render(<ProjectsPage />);

    await waitFor(() => {
      expect(screen.getByText("Test Project")).toBeInTheDocument();
    });
    expect(screen.getByText("Test Description")).toBeInTheDocument();
  });

  it("renders error message when API call fails", async () => {
    mockApi.get.mockRejectedValue(new Error("Network Error"));

    render(<ProjectsPage />);

    await waitFor(() => {
      expect(screen.getByText("Failed to load projects")).toBeInTheDocument();
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
