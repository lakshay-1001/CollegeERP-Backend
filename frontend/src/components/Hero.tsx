export default function Hero() {
  return (
    <div className="px-6 md:px-12 py-20 bg-blue-50 text-center">

      <h1 className="text-3xl md:text-5xl font-bold text-gray-800">
        College Management Portal
      </h1>

      <p className="mt-4 text-gray-600 max-w-xl mx-auto">
        Access your classes, communicate with teachers, and stay updated with your college activities.
      </p>

      <div className="mt-8">
        <button className="px-6 py-3 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition">
          Access Portal
        </button>
      </div>

    </div>
  );
}