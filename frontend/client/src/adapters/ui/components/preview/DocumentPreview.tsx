"use client";

import ReactMarkdown from "react-markdown";
import remarkGfm from "remark-gfm";
import { MermaidDiagram } from "./MermaidDiagram";

interface DocumentPreviewProps {
  content: string;
}

export function DocumentPreview({ content }: DocumentPreviewProps) {
  return (
    <div className="prose prose-sm sm:prose-base lg:prose-lg xl:prose-2xl max-w-none p-6 bg-white rounded-lg shadow-sm border border-gray-200 h-full overflow-y-auto">
      <ReactMarkdown
        remarkPlugins={[remarkGfm]}
        components={{
          code({ node, inline, className, children, ...props }: any) {
            const match = /language-(\w+)/.exec(className || "");
            const isMermaid = match && match[1] === "mermaid";

            if (!inline && isMermaid) {
              return <MermaidDiagram chart={String(children).replace(/\n$/, "")} />;
            }

            return (
              <code className={className} {...props}>
                {children}
              </code>
            );
          },
        }}
      >
        {content || "*No content generated yet...*"}
      </ReactMarkdown>
    </div>
  );
}
