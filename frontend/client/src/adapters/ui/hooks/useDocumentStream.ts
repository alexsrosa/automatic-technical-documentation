import { useState, useEffect } from "react";

export function useDocumentStream(docId: string) {
  const [content, setContent] = useState<string>("");
  const [isConnected, setIsConnected] = useState(false);

  useEffect(() => {
    if (!docId) return;

    const eventSource = new EventSource(`/api/projects/stream?docId=${docId}`);

    eventSource.onopen = () => {
      setIsConnected(true);
      console.log("SSE connected");
    };

    eventSource.onmessage = (event) => {
      try {
        const data = JSON.parse(event.data);
        if (data.content) {
          setContent(data.content);
        }
      } catch (e) {
        // If it's just raw text update
        setContent(event.data);
      }
    };

    eventSource.onerror = (err) => {
      console.error("SSE error:", err);
      eventSource.close();
      setIsConnected(false);
    };

    return () => {
      eventSource.close();
    };
  }, [docId]);

  return { content, isConnected };
}
