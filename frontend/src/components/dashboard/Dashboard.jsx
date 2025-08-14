// Dashboard Component - yahan par customer dashboard hai
import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import authService from '../../services/authService';

const Dashboard = () => {
  const [user, setUser] = useState(null);

  // yahan par user data load kar rahe hain
  useEffect(() => {
    const currentUser = authService.getCurrentUser();
    setUser(currentUser);
  }, []);

  // yahan par logout handle kar rahe hain
  const handleLogout = () => {
    authService.logout();
  };

  if (!user) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="text-center">
          <div className="animate-spin rounded-full h-32 w-32 border-b-2 border-blue-600 mx-auto"></div>
          <p className="mt-4 text-lg text-gray-600">Loading...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-100">
      {/* yahan par header hai */}
      <header className="bg-white shadow">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center py-6">
            <div>
              <h1 className="text-3xl font-bold text-gray-900">Find-A-Spot</h1>
              <p className="text-sm text-gray-500">Welcome back, {user.name}!</p>
            </div>
            <button
              onClick={handleLogout}
              className="bg-red-600 text-white px-4 py-2 rounded-md hover:bg-red-700 transition"
            >
              Logout
            </button>
          </div>
        </div>
      </header>

      {/* yahan par main content hai */}
      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {/* yahan par quick actions section hai */}
        <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
          <div className="bg-white p-6 rounded-lg shadow">
            <div className="text-center">
              <div className="text-3xl mb-3">🚗</div>
              <h3 className="text-lg font-semibold mb-2">Book Parking</h3>
              <p className="text-gray-600 text-sm mb-4">Find and book available parking spots</p>
              <Link
                to="/book-parking"
                className="inline-block bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 transition"
              >
                Book Now
              </Link>
            </div>
          </div>

          <div className="bg-white p-6 rounded-lg shadow">
            <div className="text-center">
              <div className="text-3xl mb-3">📋</div>
              <h3 className="text-lg font-semibold mb-2">My Bookings</h3>
              <p className="text-gray-600 text-sm mb-4">View and manage your bookings</p>
              <Link
                to="/my-bookings"
                className="inline-block bg-green-600 text-white px-4 py-2 rounded-md hover:bg-green-700 transition"
              >
                View Bookings
              </Link>
            </div>
          </div>

          <div className="bg-white p-6 rounded-lg shadow">
            <div className="text-center">
              <div className="text-3xl mb-3">💳</div>
              <h3 className="text-lg font-semibold mb-2">Payment History</h3>
              <p className="text-gray-600 text-sm mb-4">Check your payment history</p>
              <Link
                to="/payment-history"
                className="inline-block bg-purple-600 text-white px-4 py-2 rounded-md hover:bg-purple-700 transition"
              >
                View Payments
              </Link>
            </div>
          </div>

          <div className="bg-white p-6 rounded-lg shadow">
            <div className="text-center">
              <div className="text-3xl mb-3">👤</div>
              <h3 className="text-lg font-semibold mb-2">Profile</h3>
              <p className="text-gray-600 text-sm mb-4">Update your profile information</p>
              <Link
                to="/profile"
                className="inline-block bg-gray-600 text-white px-4 py-2 rounded-md hover:bg-gray-700 transition"
              >
                Edit Profile
              </Link>
            </div>
          </div>
        </div>

        {/* yahan par recent activity section hai */}
        <div className="bg-white rounded-lg shadow">
          <div className="px-6 py-4 border-b border-gray-200">
            <h2 className="text-xl font-semibold text-gray-900">Recent Activity</h2>
          </div>
          <div className="p-6">
            <div className="text-center py-8">
              <div className="text-4xl mb-4">📊</div>
              <p className="text-gray-500">No recent activity to display</p>
              <p className="text-sm text-gray-400 mt-2">
                Your recent bookings and activities will appear here
              </p>
            </div>
          </div>
        </div>

        {/* yahan par quick stats section hai */}
        <div className="grid md:grid-cols-3 gap-6 mt-8">
          <div className="bg-white p-6 rounded-lg shadow text-center">
            <h3 className="text-lg font-semibold text-gray-900 mb-2">Total Bookings</h3>
            <p className="text-3xl font-bold text-blue-600">0</p>
          </div>
          
          <div className="bg-white p-6 rounded-lg shadow text-center">
            <h3 className="text-lg font-semibold text-gray-900 mb-2">Amount Spent</h3>
            <p className="text-3xl font-bold text-green-600">₹0</p>
          </div>
          
          <div className="bg-white p-6 rounded-lg shadow text-center">
            <h3 className="text-lg font-semibold text-gray-900 mb-2">Favorite Locations</h3>
            <p className="text-3xl font-bold text-purple-600">0</p>
          </div>
        </div>
      </main>
    </div>
  );
};

export default Dashboard;