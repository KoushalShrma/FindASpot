// Authentication API Service - yahan par authentication related API calls hain
import apiClient from './apiClient';

// yahan par authentication service define kar rahe hain
const authService = {
  // yahan par user registration ka method hai
  register: async (userData) => {
    try {
      const response = await apiClient.post('/auth/register', userData);
      return response.data;
    } catch (error) {
      throw error.response?.data || { success: false, message: 'Registration failed' };
    }
  },

  // yahan par OTP verification ka method hai
  verifyOtp: async (email, otp) => {
    try {
      const response = await apiClient.post('/auth/verify-otp', { email, otp });
      return response.data;
    } catch (error) {
      throw error.response?.data || { success: false, message: 'OTP verification failed' };
    }
  },

  // yahan par user login ka method hai
  login: async (email, password) => {
    try {
      const response = await apiClient.post('/auth/login', { email, password });
      if (response.data.success && response.data.data.token) {
        // yahan par token aur user data localStorage mein save kar rahe hain
        localStorage.setItem('token', response.data.data.token);
        localStorage.setItem('user', JSON.stringify(response.data.data));
      }
      return response.data;
    } catch (error) {
      throw error.response?.data || { success: false, message: 'Login failed' };
    }
  },

  // yahan par OTP resend ka method hai
  resendOtp: async (email) => {
    try {
      const response = await apiClient.post('/auth/resend-otp', null, {
        params: { email }
      });
      return response.data;
    } catch (error) {
      throw error.response?.data || { success: false, message: 'Failed to resend OTP' };
    }
  },

  // yahan par logout ka method hai
  logout: () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    window.location.href = '/login';
  },

  // yahan par current user get karne ka method hai
  getCurrentUser: () => {
    try {
      const userStr = localStorage.getItem('user');
      return userStr ? JSON.parse(userStr) : null;
    } catch (error) {
      console.error('Failed to parse user data:', error);
      return null;
    }
  },

  // yahan par authentication status check karne ka method hai
  isAuthenticated: () => {
    const token = localStorage.getItem('token');
    const user = authService.getCurrentUser();
    return !!(token && user);
  }
};

export default authService;