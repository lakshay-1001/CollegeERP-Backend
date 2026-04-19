export default function StudentHome() {
  const cards = [
    { title: "My Classes", desc: "View enrolled classes" },
    { title: "Group Chats", desc: "See class discussions" },
    { title: "Announcements", desc: "Latest updates" },
    { title: "Assignments", desc: "Track your tasks" },
  ];

  return (
    <div>

      <h2 className="text-3xl font-bold text-gray-800 mb-8">
        Welcome Back 👋
      </h2>

      <div className="grid sm:grid-cols-2 lg:grid-cols-4 gap-6">
        {cards.map((c, i) => (
          <div
            key={i}
            className="p-6 rounded-2xl bg-white shadow hover:shadow-xl hover:-translate-y-1 transition duration-300"
          >
            <h3 className="font-semibold text-lg text-blue-600">
              {c.title}
            </h3>
            <p className="text-gray-500 text-sm mt-2">
              {c.desc}
            </p>
          </div>
        ))}
      </div>

    </div>
  );
}