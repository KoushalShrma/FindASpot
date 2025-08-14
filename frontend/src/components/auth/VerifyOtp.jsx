// OTP Verification Component - yahan par email OTP verify karne ka form hai
import { useState, useEffect } from 'react';
import { useNavigate, useSearchParams, Link } from 'react-router-dom';
import authService from '../../services/authService';

const VerifyOtp = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const email = searchParams.get('email');
  
  // yahan par component state manage kar rahe hain
  const [otp, setOtp] = useState('');
  const [loading, setLoading] = useState(false);
  const [resendLoading, setResendLoading] = useState(false);
  const [message, setMessage] = useState({ type: '', text: '' });
  const [countdown, setCountdown] = useState(0);

  // yahan par email check kar rahe hain
  useEffect(() => {
    if (!email) {
      navigate('/register');
    }
  }, [email, navigate]);

  // yahan par countdown timer manage kar rahe hain
  useEffect(() => {
    let timer;
    if (countdown > 0) {
      timer = setTimeout(() => setCountdown(countdown - 1), 1000);
    }
    return () => clearTimeout(timer);
  }, [countdown]);

  // yahan par OTP input change handle kar rahe hain
  const handleOtpChange = (e) => {
    const value = e.target.value.replace(/\D/g, ''); // Only digits
    if (value.length <= 4) {
      setOtp(value);
    }
  };

  // yahan par OTP verification handle kar rahe hain
  const handleVerifyOtp = async (e) => {
    e.preventDefault();
    
    if (otp.length !== 4) {
      setMessage({ type: 'error', text: 'Please enter a valid 4-digit OTP' });
      return;
    }

    setLoading(true);
    setMessage({ type: '', text: '' });

    try {
      // yahan par OTP verification API call kar rahe hain
      const response = await authService.verifyOtp(email, otp);
      
      if (response.success) {
        setMessage({ type: 'success', text: response.message });
        // yahan par login page par redirect kar rahe hain
        setTimeout(() => {
          navigate('/login');
        }, 2000);
      } else {
        setMessage({ type: 'error', text: response.message });
      }
    } catch (error) {
      setMessage({ type: 'error', text: error.message || 'OTP verification failed' });
    } finally {
      setLoading(false);
    }
  };

  // yahan par OTP resend handle kar rahe hain
  const handleResendOtp = async () => {
    if (countdown > 0) return;

    setResendLoading(true);
    setMessage({ type: '', text: '' });

    try {
      // yahan par OTP resend API call kar rahe hain
      const response = await authService.resendOtp(email);
      
      if (response.success) {
        setMessage({ type: 'success', text: response.message });
        setCountdown(60); // 60 seconds countdown
        setOtp(''); // Clear current OTP
      } else {
        setMessage({ type: 'error', text: response.message });
      }
    } catch (error) {
      setMessage({ type: 'error', text: error.message || 'Failed to resend OTP' });
    } finally {
      setResendLoading(false);
    }
  };

  if (!email) {
    return null; // Will redirect to register
  }

  return (
    <div className="min-h-screen bg-gray-100 flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-md w-full space-y-8">
        {/* yahan par header hai */}
        <div>
          <h2 className="mt-6 text-center text-3xl font-extrabold text-gray-900">
            Verify Your Email
          </h2>
          <p className="mt-2 text-center text-sm text-gray-600">
            We've sent a 4-digit OTP to
          </p>
          <p className="text-center text-sm font-medium text-blue-600">
            {email}
          </p>
        </div>

        {/* yahan par OTP verification form hai */}
        <form className="mt-8 space-y-6" onSubmit={handleVerifyOtp}>
          <div>
            <label htmlFor="otp" className="block text-sm font-medium text-gray-700">
              Enter OTP
            </label>
            <input
              id="otp"
              name="otp"
              type="text"
              required
              value={otp}
              onChange={handleOtpChange}
              className="mt-1 block w-full px-3 py-3 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 text-center text-lg font-mono tracking-widest"
              placeholder="0000"
              maxLength="4"
            />
            <p className="mt-1 text-xs text-gray-500">
              Enter the 4-digit code sent to your email
            </p>
          </div>

          {/* yahan par message display kar rahe hain */}
          {message.text && (
            <div className={`p-4 rounded-md ${
              message.type === 'success' 
                ? 'bg-green-50 text-green-800 border border-green-200' 
                : 'bg-red-50 text-red-800 border border-red-200'
            }`}>
              {message.text}
            </div>
          )}

          {/* yahan par verify button hai */}
          <div>
            <button
              type="submit"
              disabled={loading || otp.length !== 4}
              className="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 disabled:opacity-50 disabled:cursor-not-allowed"
            >
              {loading ? 'Verifying...' : 'Verify OTP'}
            </button>
          </div>

          {/* yahan par resend OTP section hai */}
          <div className="text-center">
            <p className="text-sm text-gray-600">
              Didn't receive the OTP?
            </p>
            {countdown > 0 ? (
              <p className="text-sm text-gray-500">
                Resend available in {countdown} seconds
              </p>
            ) : (
              <button
                type="button"
                onClick={handleResendOtp}
                disabled={resendLoading}
                className="font-medium text-blue-600 hover:text-blue-500 disabled:opacity-50"
              >
                {resendLoading ? 'Sending...' : 'Resend OTP'}
              </button>
            )}
          </div>

          {/* yahan par back to register link hai */}
          <div className="text-center">
            <Link to="/register" className="text-sm font-medium text-blue-600 hover:text-blue-500">
              ← Back to Registration
            </Link>
          </div>
        </form>
      </div>
    </div>
  );
};

export default VerifyOtp;