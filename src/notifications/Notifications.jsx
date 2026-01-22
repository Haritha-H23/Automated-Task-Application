import { useEffect, useState } from "react";
import api from "../api/api";

export default function Notifications() {
  const [list, setList] = useState([]);

  useEffect(() => {
    api.get("/api/notifications").then(res => setList(res.data));
  }, []);

  return (
    <div>
      <h3>Notifications</h3>
      {list.map(n => <p key={n.id}>🔔 {n.message}</p>)}
    </div>
  );
}
