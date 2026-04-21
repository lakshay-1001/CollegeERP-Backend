export default function TeacherHome() {
  const cards = [
    { title: "My Groups", desc: "Manage your classes" },
    { title: "Send Messages", desc: "Broadcast to students" },
    { title: "Students", desc: "View members" },
    { title: "Announcements", desc: "Share updates" },
  ];

  return (
    <div>

      <h2 className="text-3xl font-bold mb-8">
        Welcome Teacher 👨‍🏫
      </h2>

      <div className="grid sm:grid-cols-2 lg:grid-cols-4 gap-6">
        {cards.map((c, i) => (
          <div
            key={i}
            className="p-6 bg-white rounded-2xl shadow hover:shadow-xl hover:-translate-y-1 transition"
          >
            <h3 className="font-semibold text-green-600">
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