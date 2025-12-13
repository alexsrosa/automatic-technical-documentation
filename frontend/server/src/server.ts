import express from "express";
import cors from "cors";
import morgan from "morgan";
import dotenv from "dotenv";
import projectRoutes from "./routes/projects";

dotenv.config();

const app = express();
const port = process.env.PORT || 4001;

app.use(cors());
app.use(express.json());
app.use(morgan("dev"));

app.use("/projects", projectRoutes);

app.get("/health", (req, res) => {
  res.json({ status: "ok" });
});

if (require.main === module) {
  app.listen(port, () => {
    console.log(`BFF Server running on port ${port}`);
  });
}

export default app;
