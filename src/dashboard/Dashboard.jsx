import Sidebar from "./Sidebar";
import { Outlet } from "react-router-dom";
import "./Dashboard.css";
import NotificationListener from "../notifications/NotificationListener";

const Dashboard = () => {
  return (
    <div className="dashboard-layout">
      <Sidebar />
      <NotificationListener />
      <div className="dashboard-content">
        <Outlet />
      </div>
    </div>
  );
};

export default Dashboard;
