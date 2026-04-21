export default function TeacherGroups() {
  return (
    <div className="bg-white p-6 rounded-2xl shadow">

      <div className="flex justify-between mb-4">
        <h2 className="text-xl font-semibold">My Groups</h2>

        <button className="bg-green-600 text-white px-4 py-2 rounded-lg">
          + Create Group
        </button>
      </div>

      <div className="space-y-3">

        <div className="p-4 border rounded-lg flex justify-between">
          <span>Class A</span>
          <button className="text-sm text-red-500">Delete</button>
        </div>

      </div>

    </div>
  );
}