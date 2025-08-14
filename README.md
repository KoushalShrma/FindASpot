# FindASpot - Smart Parking Management System

FindASpot एक आधुनिक parking management system है जो real-time slot monitoring, secure reservations, और admin control provide करता है।

## 🚀 Technology Stack

### Backend
- **Java 17** with **Spring Boot 3.2.1**
- **Spring Security** with JWT authentication
- **Spring Data JPA** with **MySQL**
- **Flyway** for database migrations
- **Spring Mail** for email OTP verification
- **Maven** for dependency management

### Frontend
- **React 18** with **Vite**
- **React Router** for navigation
- **Axios** for API communication
- **Tailwind CSS** for styling
- **Modern ES6+** JavaScript

### Database
- **MySQL 8.0+** for production
- Flyway migrations for schema management
- Proper indexing for performance

## ✨ Key Features

- 👤 **User Management**: Registration, login with email OTP verification
- 🚗 **Real-time Parking**: Live slot availability and booking
- 💳 **Payment Integration**: Multiple payment methods support
- 🔐 **Security**: JWT tokens, password encryption, input validation
- 📱 **Responsive Design**: Mobile-first responsive interface
- 🌐 **Multi-role Support**: Customer, Admin, and Manager roles
- 📊 **Admin Dashboard**: User management, parking lot control, reports
- 🏢 **Manager Portal**: Parking lot management and monitoring

## 🛠️ Setup Instructions

### Prerequisites
- Java 17 or higher
- Node.js 18 or higher
- MySQL 8.0 or higher
- Maven 3.8+
- Git

### Backend Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/KoushalShrma/FindASpot.git
   cd FindASpot/backend
   ```

2. **Configure environment variables**
   ```bash
   cp .env.example .env
   # Edit .env file with your configuration
   ```

3. **Environment Variables**
   ```env
   # Database Configuration
   DB_HOST=localhost
   DB_PORT=3306
   DB_NAME=findaspot
   DB_USER=root
   DB_PASSWORD=your_database_password

   # SMTP Email Configuration
   SMTP_HOST=smtp.gmail.com
   SMTP_PORT=587
   SMTP_USERNAME=your_email@gmail.com
   SMTP_PASSWORD=your_app_password
   SMTP_FROM=noreply@findaspot.com
   SMTP_USE_TLS=true

   # JWT Configuration
   JWT_SECRET=your_super_secret_jwt_key_change_in_production
   ```

4. **Setup MySQL Database**
   ```sql
   CREATE DATABASE findaspot;
   CREATE USER 'findaspot_user'@'localhost' IDENTIFIED BY 'your_password';
   GRANT ALL PRIVILEGES ON findaspot.* TO 'findaspot_user'@'localhost';
   FLUSH PRIVILEGES;
   ```

5. **Run the backend**
   ```bash
   mvn spring-boot:run
   ```

### Frontend Setup

1. **Navigate to frontend directory**
   ```bash
   cd ../frontend
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Configure environment**
   ```bash
   cp .env.example .env
   # Edit .env file if needed (default should work for local development)
   ```

4. **Start development server**
   ```bash
   npm run dev
   ```

### Default URLs
- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8080/api
- **Backend Health**: http://localhost:8080/api/auth/health

## 🔐 Default Test Accounts

### Admin Account
- **Email**: admin@findaspot.com
- **Password**: password123

### Customer Account
- **Email**: customer@findaspot.com
- **Password**: password123

## 📚 API Documentation

### Authentication Endpoints
```
POST /api/auth/register          # User registration
POST /api/auth/verify-otp        # Email OTP verification  
POST /api/auth/login             # User login
POST /api/auth/resend-otp        # Resend OTP
GET  /api/auth/health            # Service health check
```

### Parking Endpoints
```
GET  /api/parking/lots           # Get all parking lots
GET  /api/parking/lots/{id}      # Get specific parking lot
GET  /api/parking/lots/{id}/slots # Get real-time slot info
GET  /api/parking/search         # Search parking lots
```

### Admin Endpoints (Requires Admin Role)
```
GET  /api/admin/dashboard        # Admin dashboard data
GET  /api/admin/users            # Get all users
POST /api/admin/parking-lots     # Create parking lot
DELETE /api/admin/users/{id}     # Delete user
GET  /api/admin/reports          # Generate reports
```

## 🐳 Docker Setup

### Using Docker Compose (Recommended)
```bash
# Start all services
docker-compose up -d

# Stop all services  
docker-compose down

# View logs
docker-compose logs -f
```

### Manual Docker Build
```bash
# Build backend
cd backend
docker build -t findaspot-backend .

# Build frontend
cd ../frontend  
docker build -t findaspot-frontend .
```

## 🧪 Testing

### Backend Tests
```bash
cd backend
mvn test
```

### Frontend Tests
```bash
cd frontend
npm test
```

### Integration Testing
Use the included Postman collection in `/docs/postman/` for API testing.

## 📦 Deployment

### Production Build
```bash
# Backend
cd backend
mvn clean package -Pprod

# Frontend
cd frontend
npm run build
```

### Environment Variables for Production
Ensure all environment variables are properly set in your production environment. Never commit real credentials to the repository.

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 Migration from Flask

This application was migrated from a Python Flask application to Java Spring Boot + React. Key improvements:

- **Security**: Enhanced with Spring Security and JWT
- **Scalability**: Better architecture with Spring Boot
- **Performance**: Optimized database queries and caching
- **Maintainability**: Clean separation of concerns
- **Modern UI**: React-based SPA instead of server-rendered templates

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Failed**
   - Check MySQL is running
   - Verify database credentials
   - Ensure database exists

2. **Email OTP Not Sending**
   - Check SMTP configuration
   - Verify email credentials
   - Check firewall/security settings

3. **Frontend API Calls Failing**
   - Ensure backend is running on port 8080
   - Check CORS configuration
   - Verify API base URL in frontend

### Logs
- Backend logs: Check console output or `logs/` directory
- Frontend logs: Check browser developer console

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Team

- **Development**: Full-stack development with modern technologies
- **Security**: Implemented secure authentication and authorization
- **Database**: Optimized schema design and migrations
- **UI/UX**: Modern, responsive React interface

## 🔗 Links

- **GitHub Repository**: https://github.com/KoushalShrma/FindASpot
- **API Documentation**: Available after starting the backend
- **Live Demo**: Coming soon...

---

Made with ❤️ by the FindASpot team. Smart Parking, Simplified!