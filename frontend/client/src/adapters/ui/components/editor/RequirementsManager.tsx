"use client";

import { useState } from "react";
import { Plus, Trash2 } from "lucide-react";
import { Requirement, RequirementPriority } from "@/domain/models/Requirement";
import { RichTextEditor } from "@/adapters/ui/components/ui/RichTextEditor";
import { v4 as uuidv4 } from "uuid";

interface RequirementsManagerProps {
  requirements: Requirement[];
  onChange: (requirements: Requirement[]) => void;
}

export function RequirementsManager({
  requirements,
  onChange,
}: RequirementsManagerProps) {
  const addRequirement = () => {
    const newRequirement: Requirement = {
      id: uuidv4(),
      description: "",
      priority: RequirementPriority.MEDIUM,
    };
    onChange([...requirements, newRequirement]);
  };

  const updateRequirement = (id: string, updates: Partial<Requirement>) => {
    const updated = requirements.map((req) =>
      req.id === id ? { ...req, ...updates } : req
    );
    onChange(updated);
  };

  const removeRequirement = (id: string) => {
    onChange(requirements.filter((req) => req.id !== id));
  };

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h3 className="text-lg font-semibold">Requirements</h3>
        <button
          onClick={addRequirement}
          className="flex items-center gap-2 text-blue-600 hover:text-blue-700 font-medium"
          type="button"
        >
          <Plus size={18} />
          Add Requirement
        </button>
      </div>

      {requirements.length === 0 ? (
        <div className="text-center py-8 bg-gray-50 border border-dashed border-gray-300 rounded-lg text-gray-500">
          <p>No requirements added yet.</p>
          <button
            onClick={addRequirement}
            className="mt-2 text-blue-600 hover:underline"
            type="button"
          >
            Add your first requirement
          </button>
        </div>
      ) : (
        <div className="space-y-4">
          {requirements.map((req, index) => (
            <div
              key={req.id}
              className="bg-white border border-gray-200 rounded-lg p-4 shadow-sm"
            >
              <div className="flex justify-between items-start mb-4">
                <span className="text-sm font-medium text-gray-500">
                  Requirement #{index + 1}
                </span>
                <button
                  onClick={() => removeRequirement(req.id)}
                  className="text-red-500 hover:text-red-700 p-1 rounded hover:bg-red-50"
                  title="Remove requirement"
                  type="button"
                >
                  <Trash2 size={18} />
                </button>
              </div>

              <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
                <div className="md:col-span-3">
                  <label className="block text-sm font-medium text-gray-700 mb-1">
                    Description
                  </label>
                  <RichTextEditor
                    content={req.description}
                    onChange={(content) =>
                      updateRequirement(req.id, { description: content })
                    }
                    placeholder="Describe the requirement..."
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">
                    Priority
                  </label>
                  <select
                    value={req.priority}
                    onChange={(e) =>
                      updateRequirement(req.id, {
                        priority: e.target.value as RequirementPriority,
                      })
                    }
                    className="w-full border border-gray-300 rounded-md px-3 py-2 focus:ring-blue-500 focus:border-blue-500 outline-none bg-white"
                  >
                    {Object.values(RequirementPriority).map((priority) => (
                      <option key={priority} value={priority}>
                        {priority}
                      </option>
                    ))}
                  </select>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
