export default function TeacherChat() {
  return (
    <div className="h-[80vh] flex bg-white rounded-2xl shadow overflow-hidden">

      {/* GROUPS */}
      <div className="w-1/3 border-r p-4 bg-gray-50">
        <h3 className="font-semibold mb-4">Groups</h3>
        <div className="p-3 rounded hover:bg-green-100 cursor-pointer">
          Class A
        </div>
      </div>

      {/* CHAT */}
      <div className="flex-1 flex flex-col">

        <div className="p-4 border-b font-semibold">
          Class A
        </div>

        <div className="flex-1 p-4 overflow-y-auto">
          <div className="bg-green-100 p-2 rounded inline-block">
            Welcome students!
          </div>
        </div>

        {/* INPUT */}
        <div className="p-4 border-t flex gap-2">
          <input
            placeholder="Type message..."
            className="flex-1 border p-2 rounded"
          />
          <button className="bg-green-600 text-white px-4 rounded">
            Send
          </button>
        </div>

      </div>
    </div>
  );
}