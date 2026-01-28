import { useEffect, useState } from "react";
import api from "../api/api";
import { useNavigate } from "react-router-dom";
import {
  TextField,
  Button,
  MenuItem
} from "@mui/material";
import "./TaskForm.css";

const TaskForm = () => {
  const navigate = useNavigate();
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [task, setTask] = useState({
    title: "",
    description: "",
    priority: "MEDIUM",
    dueDate: "",
    status:"PENDING",
  });

  const [recentTasks, setRecentTasks] = useState([]);

  useEffect(() => {
    api.get(`/api/tasks/allTasks?page=${page}&size=10`)
      .then(res => setRecentTasks(res.data.content))
      .catch(() => console.log("Failed to load recent tasks"));
  }, [page]);

  const handleChange = (e) => {
    setTask({ ...task, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    await api.post("/api/tasks", task);
    navigate("/dashboard");
  };

  return (
    <div className="task-form-layout">

      {/* LEFT PANEL */}
      <div className="task-form-container">
        <h1>Add New Task</h1>

        <TextField
          label="Title"
          name="title"
          value={task.title}
          onChange={handleChange}
          required
          fullWidth
        />

        <TextField
          label="Description"
          name="description"
          value={task.description}
          onChange={handleChange}
          multiline
          rows={4}
          fullWidth
        />

        <TextField
          select
          label="Priority"
          name="priority"
          value={task.priority}
          onChange={handleChange}
          fullWidth
        >
          <MenuItem value="LOW">Low</MenuItem>
          <MenuItem value="MEDIUM">Medium</MenuItem>
          <MenuItem value="HIGH">High</MenuItem>
        </TextField>

        <TextField
          type="date"
          label="Due Date"
          name="dueDate"
          value={task.dueDate}
          onChange={handleChange}
          InputLabelProps={{ shrink: true }}
          fullWidth
        />

        <Button
          variant="contained"
          className="create-btn"
          onClick={handleSubmit}
        >
          CREATE TASK
        </Button>
      </div>

      {/* RIGHT PANEL */}
      <div className="recent-tasks-panel">
        <h3>Recent Tasks</h3>

        {recentTasks.length === 0 ? (
          <p className="empty-text">No recent tasks</p>
        ) : (
          recentTasks
          .filter(task => task.status === "PENDING")
          .map(task => (
            <div key={task.id} className="recent-task-card">
              <p className="recent-title">{task.title}</p>
              <span className={`priority ${task.priority.toLowerCase()}`}>
                {task.priority}
              </span>
            </div>
          ))
        )}
      </div>

    </div>
  );
};

export default TaskForm;
