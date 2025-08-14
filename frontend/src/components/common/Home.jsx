// Home Page Component - yahan par landing page hai
import { Link } from 'react-router-dom';

const Home = () => {
  return (
    <div className="min-h-screen bg-gray-50">
      {/* yahan par header section hai */}
      <div className="bg-gradient-to-r from-blue-800 to-blue-600 text-white">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
          <div className="text-center">
            <h1 className="text-4xl font-bold sm:text-6xl">Find-A-Spot</h1>
            <p className="mt-3 text-xl sm:text-2xl">Smart Parking, Simplified</p>
            <p className="mt-4 text-lg opacity-90">
              Book parking spots in real-time. Never worry about finding parking again.
            </p>
            <div className="mt-8 space-x-4">
              <Link
                to="/register"
                className="inline-block bg-white text-blue-600 px-8 py-3 rounded-full font-semibold hover:bg-gray-100 transition"
              >
                Get Started
              </Link>
              <Link
                to="/login"
                className="inline-block border-2 border-white text-white px-8 py-3 rounded-full font-semibold hover:bg-white hover:text-blue-600 transition"
              >
                Login
              </Link>
            </div>
          </div>
        </div>
      </div>

      {/* yahan par features section hai */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
        <div className="text-center mb-12">
          <h2 className="text-3xl font-bold text-gray-900">Why Choose Find-A-Spot?</h2>
          <p className="mt-4 text-lg text-gray-600">Experience the future of parking</p>
        </div>

        <div className="grid md:grid-cols-3 gap-8">
          <div className="text-center p-6 bg-white rounded-lg shadow-md">
            <div className="text-4xl mb-4">🚗</div>
            <h3 className="text-xl font-semibold mb-2">Real-time Availability</h3>
            <p className="text-gray-600">View live parking slot availability and book instantly.</p>
          </div>

          <div className="text-center p-6 bg-white rounded-lg shadow-md">
            <div className="text-4xl mb-4">🔒</div>
            <h3 className="text-xl font-semibold mb-2">Secure & Verified</h3>
            <p className="text-gray-600">Safe parking spaces with vehicle surveillance and verification.</p>
          </div>

          <div className="text-center p-6 bg-white rounded-lg shadow-md">
            <div className="text-4xl mb-4">⚡</div>
            <h3 className="text-xl font-semibold mb-2">Fast & Simple</h3>
            <p className="text-gray-600">Quick booking system with multiple payment options.</p>
          </div>
        </div>
      </div>

      {/* yahan par manager section hai */}
      <div className="bg-white py-16">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
          <h2 className="text-3xl font-bold text-gray-900 mb-4">Are You a Parking Manager?</h2>
          <p className="text-lg text-gray-600 mb-6">
            Access your parking lot dashboard and manage slots in real-time.
          </p>
          <Link
            to="/manager/login"
            className="inline-block bg-blue-600 text-white px-8 py-3 rounded-full font-semibold hover:bg-blue-700 transition"
          >
            Manager Login
          </Link>
        </div>
      </div>

      {/* yahan par footer hai */}
      <footer className="bg-gray-900 text-white py-8">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
          <p>&copy; 2025 Find-A-Spot. All rights reserved.</p>
          <p className="mt-2 text-gray-400">Smart Parking, Simplified</p>
        </div>
      </footer>
    </div>
  );
};

export default Home;