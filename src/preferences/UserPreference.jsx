import { useState, useEffect } from "react";
import api from "../api/api";
import "./UserPreference.css";
import { Switch, Button, TextField } from "@mui/material";

const UserPreference = () => {
  const [user, setUser] = useState({});
  const [darkMode, setDarkMode] = useState(false);
  const [preview, setPreview] = useState(null);

  useEffect(() => {
    api.get("/api/users/me").then(res => {
      setUser(res.data);
    });
  }, []);

  const handleSave = async () => {
    await api.put("/api/users/updateUser/{id}", {
      username: user.username
    });
    alert("Settings updated");
  };

  return (
    <div className="settings-container">
      <h2>Profile Settings</h2>

      {/* Profile Picture */}
      <div className="settings-section">
        <img
          src={preview || "https://upload.wikimedia.org/wikipedia/commons/7/7c/Profile_avatar_placeholder_large.png"}
          className="settings-avatar"
          alt="avatar"
        />
        <input
          type="file"
          accept="image/*"
          onChange={(e) =>
            setPreview(URL.createObjectURL(e.target.files[0]))
          }
        />
      </div>

      {/* Username */}
      <div className="settings-section">
        <TextField
          label="Username"
          fullWidth
          value={user.username || ""}
          onChange={(e) =>
            setUser({ ...user, username: e.target.value })
          }
        />
      </div>

      {/* Email (read only) */}
      <div className="settings-section">
        <TextField
          label="Email"
          fullWidth
          value={user.email || ""}
          disabled
        />
      </div>

      {/* Theme */}
      <div className="settings-row">
        <span>Dark Mode</span>
        <Switch
          checked={darkMode}
          onChange={() => setDarkMode(!darkMode)}
        />
      </div>

      {/* Actions */}
      <div className="settings-actions">
        <Button variant="contained" onClick={handleSave}>
          Save Changes
        </Button>
        <Button color="error">Logout</Button>
      </div>
    </div>
  );
};

export default UserPreference;
