export default function TaskCard({ task }) {
  return (
    <div style={{
      background: "#fff",
      padding: "12px",
      marginTop: "10px",
      borderRadius: "8px",
      display: "flex",
      justifyContent: "space-between"
    }}>
      <div>
        <input type="checkbox" /> {task.title}
        <div style={{ fontSize: "12px", color: "#777" }}>
          Due: {task.dueDate || "None"}
        </div>
      </div>
      <span>{task.priority}</span>
    </div>
  );
}
