"use client";

import { useEffect, useRef } from "react";
import mermaid from "mermaid";

mermaid.initialize({
  startOnLoad: false,
  theme: "default",
  securityLevel: "loose",
});

interface MermaidDiagramProps {
  chart: string;
}

export function MermaidDiagram({ chart }: MermaidDiagramProps) {
  const ref = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (ref.current && chart) {
      mermaid.render(`mermaid-${Math.random().toString(36).substr(2, 9)}`, chart).then(({ svg }) => {
        if (ref.current) {
          ref.current.innerHTML = svg;
        }
      }).catch((err) => {
        console.error("Mermaid rendering error:", err);
        if (ref.current) {
          ref.current.innerHTML = `<div class="text-red-500 text-sm p-2 border border-red-200 rounded bg-red-50">Error rendering diagram</div>`;
        }
      });
    }
  }, [chart]);

  return <div ref={ref} className="mermaid overflow-x-auto py-4" />;
}
