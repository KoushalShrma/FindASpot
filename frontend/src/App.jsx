// Main App Component - yahan par routing aur app structure hai
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Home from './components/common/Home';
import Login from './components/auth/Login';
import Register from './components/auth/Register';
import VerifyOtp from './components/auth/VerifyOtp';
import Dashboard from './components/dashboard/Dashboard';
import ProtectedRoute from './components/common/ProtectedRoute';
import authService from './services/authService';

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          {/* yahan par public routes hain */}
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/verify-otp" element={<VerifyOtp />} />
          
          {/* yahan par protected routes hain */}
          <Route 
            path="/dashboard" 
            element={
              <ProtectedRoute>
                <Dashboard />
              </ProtectedRoute>
            } 
          />
          
          {/* yahan par admin routes hain */}
          <Route 
            path="/admin/dashboard" 
            element={
              <ProtectedRoute requiredRole="admin">
                <div className="min-h-screen flex items-center justify-center">
                  <div className="text-center">
                    <h1 className="text-2xl font-bold">Admin Dashboard</h1>
                    <p className="text-gray-600">Coming Soon...</p>
                    <button 
                      onClick={() => authService.logout()}
                      className="mt-4 bg-red-600 text-white px-4 py-2 rounded-md hover:bg-red-700"
                    >
                      Logout
                    </button>
                  </div>
                </div>
              </ProtectedRoute>
            } 
          />
          
          {/* yahan par manager routes hain */}
          <Route 
            path="/manager/login" 
            element={
              <div className="min-h-screen flex items-center justify-center">
                <div className="text-center">
                  <h1 className="text-2xl font-bold">Manager Login</h1>
                  <p className="text-gray-600">Coming Soon...</p>
                </div>
              </div>
            } 
          />
          
          {/* yahan par 404 aur unauthorized routes hain */}
          <Route 
            path="/unauthorized" 
            element={
              <div className="min-h-screen flex items-center justify-center">
                <div className="text-center">
                  <h1 className="text-2xl font-bold text-red-600">Unauthorized</h1>
                  <p className="text-gray-600">You don't have permission to access this page.</p>
                </div>
              </div>
            } 
          />
          
          {/* yahan par catch-all route hai */}
          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
