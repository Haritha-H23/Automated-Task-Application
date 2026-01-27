import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Register from "./auth/Register";
import Login from "./auth/Login";
import Dashboard from "./dashboard/Dashboard";
import TaskPanel from "./tasks/TaskPanel";
import TaskForm from "./tasks/TaskForm";
import Notifications from "./notifications/Notifications";
import UserPreference from "./preferences/UserPreference";
import { Toaster } from "react-hot-toast";

function App() {
  return (
    <>
    <Toaster position="top-right" />
    <BrowserRouter>
      <Routes>

        <Route path="/" element={<Register />} />
        <Route path="/login" element={<Login />} />

        <Route path="/dashboard" element={<Dashboard />}>

          <Route index element={<Navigate to="today" />} />

          <Route path="today" element={<TaskPanel mode="today" />} />
          <Route path="all" element={<TaskPanel mode="all" />} />
          <Route path="important" element={<TaskPanel mode="important" />} />

          <Route path="tasks/new" element={<TaskForm />} />
          <Route path="notifications" element={<Notifications />} />
          <Route path="settings" element={<UserPreference />} />

        </Route>

      </Routes>
    </BrowserRouter>
    </>
  );
}

export default App;
