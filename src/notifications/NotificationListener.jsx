import { useEffect } from "react";
import toast from "react-hot-toast";
import api from "../api/api";

const NotificationListener = () => {

  useEffect(() => {
    const interval = setInterval(fetchNotifications, 20000);
    fetchNotifications();

    return () => clearInterval(interval);
  }, []);

  const fetchNotifications = async () => {
    try {
      const res = await api.get("/api/notifications/unread");

      res.data.forEach(async (notif) => {

        toast(notif.message, {
          icon: getIcon(notif.type),
          duration: 5000
        });
        await api.put(`/api/notifications/${notif.id}/read`);
      });
    } catch (err) {
      console.error(err);
    }
  };

  return null;
};

const getIcon = (type) => {
  switch (type) {
    case "DUE_SOON": return "⏳";
    case "OVERDUE": return "⚠️";
    case "COMPLETED": return "✅";
    case "DAILY_SUMMARY": return "📊";
    default: return "🔔";
  }
};

export default NotificationListener;
