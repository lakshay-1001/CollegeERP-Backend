import { Outlet, useNavigate } from "react-router-dom";

export default function StudentLayout() {
  const navigate = useNavigate();

  const logout = () => {
    localStorage.clear();
    navigate("/");
  };

  return (
    <div className="flex h-screen bg-gradient-to-br from-gray-100 to-blue-50">

      {/* SIDEBAR */}
      <div className="w-64 bg-white/80 backdrop-blur-lg shadow-lg p-5 hidden md:block">

        <h2 className="text-2xl font-bold text-blue-600 mb-10">
          🎓 Student
        </h2>

        <div className="flex flex-col gap-4 text-gray-700">

          <button onClick={() => navigate("/student")} className="hover:text-blue-600 transition">
            Dashboard
          </button>

          <button onClick={() => navigate("/student/chat")} className="hover:text-blue-600 transition">
            Chats
          </button>

          <button onClick={() => navigate("/student/profile")} className="hover:text-blue-600 transition">
            Profile
          </button>

        </div>
      </div>

      {/* MAIN */}
      <div className="flex-1 flex flex-col">

        {/* HEADER */}
        <div className="h-16 bg-white/70 backdrop-blur-lg shadow flex justify-between items-center px-6">

          <h1 className="font-semibold text-gray-800">
            Dashboard
          </h1>

          <div className="flex items-center gap-4">

            <div className="text-right hidden sm:block">
              <p className="text-sm font-medium">Lakshay</p>
              <p className="text-xs text-gray-500">Student</p>
            </div>

            <div className="w-10 h-10 bg-blue-500 rounded-full flex items-center justify-center text-white">
              L
            </div>

            <button
              onClick={logout}
              className="text-red-500 text-sm hover:underline"
            >
              Logout
            </button>

          </div>
        </div>

        {/* CONTENT */}
        <div className="p-6 overflow-auto">
          <Outlet />
        </div>

      </div>
    </div>
  );
}