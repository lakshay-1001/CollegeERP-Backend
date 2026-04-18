import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { apiUrl, readJson } from "../api/client";

export default function Login() {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);

  const normalizeRole = (raw: unknown) => {
    const role = String(raw ?? "").trim().toUpperCase();
    if (!role) return "";
    // common backend patterns: ROLE_ADMIN, admin, Admin, etc.
    return role.startsWith("ROLE_") ? role.slice("ROLE_".length) : role;
  };

  const handleLogin = async () => {
    if (!email || !password) {
      alert("Please enter email and password");
      return;
    }

    setLoading(true);

    try {
      const res = await fetch(apiUrl("/auth/login"), {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          email,
          password,
        }),
      });

      const data = (await readJson(res)) as {
        token?: string;
        role?: unknown;
        message?: string;
      };

      console.log("LOGIN RESPONSE:", data);

      if (!res.ok) {
        alert(data.message || "Invalid credentials");
        return;
      }

      if (!data.token) {
        alert("Login succeeded but no token was returned");
        return;
      }

      localStorage.setItem("token", data.token);

      // ✅ Save role (important)
      const role = normalizeRole(data.role);
      localStorage.setItem("role", role);

      // ✅ Redirect
      if (role === "STUDENT") {
        navigate("/student");
      } else if (role === "TEACHER") {
        navigate("/teacher");
      } else if (role === "ADMIN") {
        navigate("/admin/dashboard");
      } else {
        alert("Unknown role returned from server");
      }

    } catch (err) {
      console.error(err);
      alert("Server error");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="h-screen flex items-center justify-center bg-gray-100">

      <div className="bg-white p-8 rounded-lg shadow w-full max-w-md">

        <h2 className="text-xl font-semibold text-center mb-6">
          Login
        </h2>

        <input
          type="email"
          placeholder="Email"
          className="w-full p-3 border rounded mb-4"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />

        <input
          type="password"
          placeholder="Password"
          className="w-full p-3 border rounded mb-4"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <button
          onClick={handleLogin}
          disabled={loading}
          className="w-full py-3 bg-blue-600 text-white rounded hover:bg-blue-700"
        >
          {loading ? "Logging in..." : "Login"}
        </button>

      </div>
    </div>
  );
}