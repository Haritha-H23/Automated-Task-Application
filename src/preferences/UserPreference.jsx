import { useEffect, useState } from "react";
import api from "../api/api";
import "./UserPreference.css";
import { Button, TextField } from "@mui/material";
import { useNavigate } from "react-router-dom";

const UserPreference = () => {
  const [user, setUser] = useState({});
  const [image, setImage] = useState(null);
  const [preview, setPreview] = useState(null);
  const BASE_URL = "http://localhost:8080";
  const navigate = useNavigate();

  useEffect(() => {
    api.get("/api/users/me").then(res => setUser(res.data));
  }, []);

  const handleImageSelect = (file) => {
    if (!file) return;
    setImage(file);
    setPreview(URL.createObjectURL(file));
  };

  const uploadProfile = async () => {
    const formData = new FormData();
    formData.append("image", image);

    await api.post("/api/users/uploadProfile", formData, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
        "Content-Type": "multipart/form-data"
      }
    });

    window.location.reload();
  };

  const saveProfile = async () => {
    await api.put(`/api/users/updateUser/${user.id}`, user);
    alert("Profile updated");
  };

  // ✅ LOGOUT
  const handleLogout = () => {
    localStorage.clear();
    navigate("/login");
  };

  return (
    <div className="settings-page">

      <h1 className="settings-title">Profile Settings</h1>

      <div className="settings-wrapper">

        {/* PROFILE CARD */}
        <div className="profile-card">

          {!preview ? (
            <div className="avatar-wrapper">
              <img
                src={
                  user.profileImage
                    ? `${BASE_URL}${user.profileImage}`
                    : "https://upload.wikimedia.org/wikipedia/commons/7/7c/Profile_avatar_placeholder_large.png"
                }
                alt="avatar"
              />
            </div>
          ) : (
            <div className="image-preview-box">
              <img src={preview} alt="preview" />
            </div>
          )}

          {!preview && (
            <label className="upload-label">
              Change Photo
              <input
                type="file"
                hidden
                accept="image/*"
                onChange={(e) => handleImageSelect(e.target.files[0])}
              />
            </label>
          )}

          {preview && (
            <Button
              variant="contained"
              className="upload-confirm-btn"
              onClick={uploadProfile}
            >
              Upload Image
            </Button>
          )}

          <div className="profile-summary">
            <h3>{user.username}</h3>
            <p>{user.email}</p>
            <span className="hint-text">
              Click or drag image to update profile photo
            </span>
          </div>
        </div>

        {/* DETAILS CARD */}
        <div className="details-card">
          <TextField
            label="Username"
            fullWidth
            value={user.username || ""}
            onChange={(e) =>
              setUser({ ...user, username: e.target.value })
            }
          />

          <TextField
            label="Email"
            fullWidth
            disabled
            value={user.email || ""}
          />

          <TextField
            label="Phone"
            fullWidth
            value={user.phone || ""}
            onChange={(e) =>
              setUser({ ...user, phone: e.target.value })
            }
          />

          <div className="account-info">
            Account Created: {user.createdAt?.split("T")[0]}
          </div>

          <Button
            variant="contained"
            className="save-btn"
            onClick={saveProfile}
          >
            Save Changes
          </Button>

          {/* ✅ LOGOUT BUTTON */}
          <Button
            variant="outlined"
            className="logout-btn"
            onClick={handleLogout}
          >
            Logout
          </Button>

        </div>

      </div>
    </div>
  );
};

export default UserPreference;
