// Protected Route Component - yahan par authentication check kar rahe hain
import { Navigate } from 'react-router-dom';
import authService from '../../services/authService';

const ProtectedRoute = ({ children, requiredRole = null }) => {
  const isAuthenticated = authService.isAuthenticated();
  const user = authService.getCurrentUser();

  // yahan par authentication check kar rahe hain
  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  // yahan par role-based access check kar rahe hain
  if (requiredRole && user?.role !== requiredRole) {
    return <Navigate to="/unauthorized" replace />;
  }

  return children;
};

export default ProtectedRoute;