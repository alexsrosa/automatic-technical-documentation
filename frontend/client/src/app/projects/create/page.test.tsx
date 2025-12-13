import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import CreateProjectPage from "./page";
import { useRouter } from "next/navigation";
import { useSession } from "next-auth/react";
import { useApi } from "@/lib/api";

// Mock NextAuth
jest.mock("next-auth/react");

// Mock API Hook
jest.mock("@/lib/api");

// Mock Next.js Navigation
jest.mock("next/navigation", () => ({
  useRouter: jest.fn(),
}));

// Mock Next.js Link
jest.mock("next/link", () => {
  return ({ children, href }: { children: React.ReactNode; href: string }) => {
    return <a href={href}>{children}</a>;
  };
});

// Mock Lucide icons
jest.mock("lucide-react", () => ({
  ArrowLeft: () => <span>ArrowLeftIcon</span>,
}));

describe("CreateProjectPage", () => {
  const mockPush = jest.fn();
  const mockApi = {
    post: jest.fn(),
  };

  beforeEach(() => {
    jest.clearAllMocks();
    (useRouter as jest.Mock).mockReturnValue({ push: mockPush });
    (useSession as jest.Mock).mockReturnValue({ data: { accessToken: "mock-token" } });
    (useApi as jest.Mock).mockReturnValue(mockApi);
  });

  it("renders the form correctly", () => {
    render(<CreateProjectPage />);
    expect(screen.getByText("Create New Project")).toBeInTheDocument();
    expect(screen.getByLabelText("Project Name")).toBeInTheDocument();
    expect(screen.getByLabelText("Description")).toBeInTheDocument();
    expect(screen.getByLabelText("Repository URL (Optional)")).toBeInTheDocument();
    expect(screen.getByRole("button", { name: "Create Project" })).toBeInTheDocument();
  });

  it("validates form inputs", async () => {
    const user = userEvent.setup();
    render(<CreateProjectPage />);

    // Click submit without filling required fields
    await user.click(screen.getByRole("button", { name: "Create Project" }));

    await waitFor(() => {
      expect(screen.getByText("Name must be at least 3 characters")).toBeInTheDocument();
      expect(screen.getByText("Description must be at least 10 characters")).toBeInTheDocument();
    });
  });

  it("submits the form successfully", async () => {
    const user = userEvent.setup();
    mockApi.post.mockResolvedValue({ data: { id: "123" } });

    render(<CreateProjectPage />);

    await user.type(screen.getByLabelText("Project Name"), "New Project");
    await user.type(screen.getByLabelText("Description"), "A detailed description for the project");
    await user.type(screen.getByLabelText("Repository URL (Optional)"), "https://github.com/test/repo");

    await user.click(screen.getByRole("button", { name: "Create Project" }));

    await waitFor(() => {
      expect(mockApi.post).toHaveBeenCalledWith("/projects", {
        name: "New Project",
        description: "A detailed description for the project",
        repositoryUrl: "https://github.com/test/repo",
      });
      expect(mockPush).toHaveBeenCalledWith("/projects");
    });
  });

  it("handles submission error", async () => {
    const user = userEvent.setup();
    mockApi.post.mockRejectedValue(new Error("API Error"));

    render(<CreateProjectPage />);

    await user.type(screen.getByLabelText("Project Name"), "New Project");
    await user.type(screen.getByLabelText("Description"), "A detailed description for the project");

    await user.click(screen.getByRole("button", { name: "Create Project" }));

    await waitFor(() => {
      expect(screen.getByText("Failed to create project. Please try again.")).toBeInTheDocument();
    });
  });
});
