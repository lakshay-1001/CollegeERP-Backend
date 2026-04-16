export default function Features() {
  const services = [
    {
      title: "Class Updates",
      desc: "Stay informed with announcements from teachers",
    },
    {
      title: "Group Communication",
      desc: "Receive messages and updates in your class groups",
    },
    {
      title: "Assignments & Notes",
      desc: "Access shared study materials easily",
    },
    {
      title: "Secure Access",
      desc: "Login safely with your college credentials",
    },
  ];

  return (
    <div className="py-20 px-6 md:px-12 bg-white text-center">
      <h2 className="text-2xl md:text-3xl font-semibold text-gray-800 mb-12">
        Student Services
      </h2>

      <div className="grid sm:grid-cols-2 md:grid-cols-4 gap-6">
        {services.map((s, i) => (
          <div
            key={i}
            className="p-6 border rounded-lg bg-gray-50 hover:shadow-md transition"
          >
            <h3 className="text-md font-semibold text-blue-600">
              {s.title}
            </h3>
            <p className="text-gray-600 mt-2 text-sm">
              {s.desc}
            </p>
          </div>
        ))}
      </div>
    </div>
  );
}