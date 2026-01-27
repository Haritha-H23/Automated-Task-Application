import { useEffect, useState } from "react";
import api from "../api/api";
import './Sidebar.css';
import { useNavigate } from "react-router-dom";
import AssignmentIcon from '@mui/icons-material/Assignment';
import NotificationImportantIcon from '@mui/icons-material/NotificationImportant';
import SettingsIcon from '@mui/icons-material/Settings';
import Button from '@mui/material/Button';
import AddIcon from '@mui/icons-material/Add';
import StarIcon from '@mui/icons-material/Star';
import React from 'react'

const Sidebar = () => {
    const navigate = useNavigate();
    const [user, setUser] = useState({});
    const BASE_URL = "http://localhost:8080";
   

  const addTask = () => {
    navigate("/dashboard/tasks/new");
  };
  useEffect(() => {
    api.get("/api/users/me")
      .then(res => setUser(res.data))
      .catch(() => console.log("Not logged in"));
  }, []);
  return (
    <div>
        <div className='vertical-menu'>
            <div className="account" onClick={() => navigate("/dashboard/settings")}>
                <img
              src={
                user.profileImage
                  ? `${BASE_URL}${user.profileImage}`
                  : "https://upload.wikimedia.org/wikipedia/commons/7/7c/Profile_avatar_placeholder_large.png"
              }
              alt="avatar"
              className="profile-avatar"
            />
            <div className="profile-info">
                <p className="profile-name">{user.username}</p>
                <p className="profile-email">{user.email}</p>
            </div>
            </div>
        <div className="menu">
          <div className="menu-item">
            <span><AssignmentIcon /></span>
            <a href="/dashboard/today" className="active">Today's Tasks</a>
          </div>
          <div className="menu-item">
            <span><AssignmentIcon /></span>
            <a href="/dashboard/all" className="active">All Tasks</a>
          </div>
          <div className="menu-item">
            <span><StarIcon /></span>
            <a href="/dashboard/important">Important</a>
          </div>
          <div className="menu-item">
            <span><SettingsIcon /></span>
            <a href="/dashboard/settings">Settings</a>
          </div>
          <div className="menu-item">
            <span><NotificationImportantIcon /></span>
            <a href="/dashboard/notifications">Notifications</a>
          </div>
        </div>
        <Button variant="text" startIcon={<AddIcon />}
        sx={{
        color: 'midnightblue',
        backgroundColor: 'rgba(205, 203, 203)',
        '&:hover': {
      backgroundColor: 'rgba(144, 202, 249, 0.5)',
      color: '#bbdefb'
    }}} className="addTask" onClick={addTask}>Add Task</Button>
        </div>
    </div>
  )
}

export default Sidebar
