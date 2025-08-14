// API Client - yahan par backend API ke saath communication ka setup hai
import axios from 'axios';

// yahan par base URL environment variable se get kar rahe hain
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api';

// yahan par axios instance create kar rahe hain
const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  timeout: 10000, // 10 seconds timeout
});

// yahan par request interceptor lagaya hai token add karne ke liye
apiClient.interceptors.request.use(
  (config) => {
    // yahan par localStorage se token get kar rahe hain
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// yahan par response interceptor lagaya hai error handle karne ke liye
apiClient.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    // yahan par unauthorized error handle kar rahe hain
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default apiClient;