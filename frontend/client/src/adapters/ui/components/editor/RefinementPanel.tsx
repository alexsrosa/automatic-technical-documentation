import { useState } from "react";
import { Send, Loader2 } from "lucide-react";

interface RefinementPanelProps {
  onRefine: (instruction: string) => Promise<void>;
  isRefining: boolean;
}

export function RefinementPanel({ onRefine, isRefining }: RefinementPanelProps) {
  const [instruction, setInstruction] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!instruction.trim()) return;
    
    await onRefine(instruction);
    setInstruction("");
  };

  return (
    <div className="border-t border-gray-200 p-4 bg-gray-50">
      <h3 className="text-sm font-semibold text-gray-700 mb-2">Refine Documentation</h3>
      <form onSubmit={handleSubmit} className="flex gap-2">
        <input
          type="text"
          value={instruction}
          onChange={(e) => setInstruction(e.target.value)}
          placeholder="e.g., 'Add more details about the authentication flow'"
          className="flex-1 px-3 py-2 text-sm border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
          disabled={isRefining}
        />
        <button
          type="submit"
          disabled={isRefining || !instruction.trim()}
          className="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 disabled:opacity-50 flex items-center gap-2"
        >
          {isRefining ? <Loader2 size={16} className="animate-spin" /> : <Send size={16} />}
          Refine
        </button>
      </form>
    </div>
  );
}
