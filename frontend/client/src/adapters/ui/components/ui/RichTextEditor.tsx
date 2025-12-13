"use client";

import { useEditor, EditorContent } from "@tiptap/react";
import StarterKit from "@tiptap/starter-kit";
import Placeholder from "@tiptap/extension-placeholder";
import { Bold, Italic, List, ListOrdered } from "lucide-react";

interface RichTextEditorProps {
  content: string;
  onChange: (content: string) => void;
  placeholder?: string;
  editable?: boolean;
}

export function RichTextEditor({
  content,
  onChange,
  placeholder = "Write something...",
  editable = true,
}: RichTextEditorProps) {
  const editor = useEditor({
    extensions: [
      StarterKit,
      Placeholder.configure({
        placeholder,
      }),
    ],
    content,
    editable,
    onUpdate: ({ editor }) => {
      onChange(editor.getHTML());
    },
    editorProps: {
      attributes: {
        class:
          "prose prose-sm sm:prose-base lg:prose-lg xl:prose-2xl m-5 focus:outline-none min-h-[100px]",
      },
    },
  });

  if (!editor) {
    return null;
  }

  return (
    <div className="border border-gray-300 rounded-md overflow-hidden bg-white">
      {editable && (
        <div className="bg-gray-50 border-b border-gray-300 p-2 flex gap-2">
          <button
            onClick={() => editor.chain().focus().toggleBold().run()}
            disabled={!editor.can().chain().focus().toggleBold().run()}
            className={`p-1 rounded ${
              editor.isActive("bold") ? "bg-gray-200" : "hover:bg-gray-200"
            }`}
            type="button"
          >
            <Bold size={18} />
          </button>
          <button
            onClick={() => editor.chain().focus().toggleItalic().run()}
            disabled={!editor.can().chain().focus().toggleItalic().run()}
            className={`p-1 rounded ${
              editor.isActive("italic") ? "bg-gray-200" : "hover:bg-gray-200"
            }`}
            type="button"
          >
            <Italic size={18} />
          </button>
          <div className="w-px bg-gray-300 mx-1" />
          <button
            onClick={() => editor.chain().focus().toggleBulletList().run()}
            className={`p-1 rounded ${
              editor.isActive("bulletList") ? "bg-gray-200" : "hover:bg-gray-200"
            }`}
            type="button"
          >
            <List size={18} />
          </button>
          <button
            onClick={() => editor.chain().focus().toggleOrderedList().run()}
            className={`p-1 rounded ${
              editor.isActive("orderedList")
                ? "bg-gray-200"
                : "hover:bg-gray-200"
            }`}
            type="button"
          >
            <ListOrdered size={18} />
          </button>
        </div>
      )}
      <EditorContent editor={editor} className="p-2" />
    </div>
  );
}
