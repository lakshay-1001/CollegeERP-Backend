import { useNavigate } from "react-router-dom";

export default function Navbar() {
  const navigate = useNavigate();

  return (
    <div className="w-full px-6 md:px-12 py-4 flex items-center justify-between bg-white border-b">

      <h1
        className="text-lg font-semibold text-blue-700 cursor-pointer"
        onClick={() => navigate("/")}
      >
        College Portal
      </h1>

      <button
        onClick={() => navigate("/login")}
        className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
      >
        Login
      </button>
    </div>
  );
}