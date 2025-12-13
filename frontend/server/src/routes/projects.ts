import { Router } from "express";
import { ProjectController } from "../controllers/ProjectController";

const router = Router();

router.get("/", ProjectController.list);
router.post("/", ProjectController.create);

export default router;
