import { useEffect, useState } from "react";
import api from "../api/api";
import "./Notifications.css";

const Notifications = () => {
  const [notifications, setNotifications] = useState([]);

  useEffect(() => {
    api.get("/api/notifications")
      .then(res => setNotifications(res.data))
      .catch(err => console.error(err));
  }, []);

  const getIcon = (type) => {
    switch (type) {
      case "DUE_SOON":
        return "⏰";
      case "OVERDUE":
        return "⚠️";
      case "COMPLETED":
        return "✅";
      case "DAILY_SUMMARY":
        return "📋";
      default:
        return "🔔";
    }
  };

  return (
    <div className="notifications-page">
      <h2 className="notifications-title">Notifications</h2>

      {notifications.length === 0 ? (
        <div className="no-notifications">
          🎉 No notifications right now
        </div>
      ) : (
        <div className="notification-list">
          {notifications.map((n) => (
            <div key={n.id} className={`notification-card ${n.type.toLowerCase()}`}>
              <div className="notification-icon">
                {getIcon(n.type)}
              </div>

              <div className="notification-content">
                <p className="notification-text">{n.message}</p>
                <span className="notification-time">
                  {new Date(n.createdAt).toLocaleString()}
                </span>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default Notifications;
