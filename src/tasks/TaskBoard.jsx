import { useEffect, useState } from "react";
import api from "../api/api";
import {
  Checkbox,
  TextField,
  Select,
  MenuItem
} from "@mui/material";

const TaskBoard = () => {
  const [tasks, setTasks] = useState([]);
  const [search, setSearch] = useState("");
  const [sortBy, setSortBy] = useState("date");

  useEffect(() => {
    api.get("/api/tasks/allTasks")
      .then(res => setTasks(res.data))
      .catch(err => console.error(err));
  }, []);

  const toggleStatus = async (task) => {
    const updatedTask = {
      ...task,
      status: task.status === "COMPLETED" ? "PENDING" : "COMPLETED"
    };

    const res = await api.put(`/api/tasks/${task.id}`, updatedTask);

    setTasks(prev =>
      prev.map(t => (t.id === task.id ? res.data : t))
    );
  };

  const filteredTasks = tasks
    .filter(task =>
      task.title?.toLowerCase().includes(search.toLowerCase())
    )
    .sort((a, b) => {
      if (a.status !== b.status) {
        return a.status === "COMPLETED" ? 1 : -1;
      }

      if (sortBy === "priority") {
        const order = { HIGH: 1, MEDIUM: 2, LOW: 3 };
        return order[a.priority] - order[b.priority];
      }

      return new Date(a.dueDate || 0) - new Date(b.dueDate || 0);
    });

  return (
    <div className="taskboard-container">

      {/* Header */}
      <div className="taskboard-header">
        <h2>Tasks</h2>

        <div className="taskboard-controls">
          <TextField
            placeholder="Search tasks..."
            size="small"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            className="search-bar"
          />
          <Select
            size="small"
            value={sortBy}
            onChange={(e) => setSortBy(e.target.value)}
          >
            <MenuItem value="date">Sort by Date</MenuItem>
            <MenuItem value="priority">Sort by Priority</MenuItem>
          </Select>
        </div>
      </div>

      {/* Task List */}
      <div className="task-list">
        {filteredTasks.map(task => (
          <div
            key={task.id}
            className={`task-row ${
              task.status === "COMPLETED" ? "completed" : ""
            }`}
          >
            <Checkbox
              checked={task.status === "COMPLETED"}
              onChange={() => toggleStatus(task)}
            />

            <div className="task-main">
              <span className="task-title">{task.title}</span>
              {task.description && (
                <span className="task-desc">{task.description}</span>
              )}
            </div>

            <div className="task-meta">
              <span className={`priority ${task.priority.toLowerCase()}`}>
                {task.priority}
              </span>

              {task.dueDate && (
                <span className="due-date">
                  {task.dueDate}
                </span>
              )}
            </div>
          </div>
        ))}
      </div>

    </div>
  );
};

export default TaskBoard;
