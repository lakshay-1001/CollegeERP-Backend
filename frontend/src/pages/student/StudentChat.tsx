export default function StudentChat() {
  return (
    <div className="h-[80vh] flex bg-white rounded-2xl shadow overflow-hidden">

      {/* GROUP LIST */}
      <div className="w-1/3 border-r p-4 bg-gray-50">

        <h3 className="font-semibold mb-4">Groups</h3>

        <div className="p-3 rounded-lg hover:bg-blue-100 cursor-pointer transition">
          Class A
        </div>

      </div>

      {/* CHAT AREA */}
      <div className="flex-1 flex flex-col">

        <div className="p-4 border-b font-semibold">
          Class A
        </div>

        <div className="flex-1 overflow-y-auto p-4 space-y-3">

          <div>
            <p className="text-xs text-gray-500">Teacher</p>
            <div className="bg-blue-100 p-2 rounded-lg inline-block">
              Assignment due tomorrow
            </div>
          </div>

        </div>

        <div className="p-3 text-center text-gray-400 text-sm">
          Students cannot send messages
        </div>

      </div>

    </div>
  );
}