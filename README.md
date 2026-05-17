# 🔐 JWT Authentication System

A production-ready JWT authentication system built with **Spring Boot 3.2.5**, featuring user registration, login, and role-based access control.

---

## 🚀 Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Java | 21 | Programming language |
| Spring Boot | 3.2.5 | Application framework |
| Spring Security | 6.x | Security & route protection |
| Spring Data JPA | 3.2.5 | Database ORM |
| JSON Web Token (JWT) | 0.11.5 | Stateless authentication |
| H2 Database | Runtime | In-memory database |
| BCrypt | Built-in | Password hashing |
| Lombok | Latest | Boilerplate reduction |
| Maven | 3.x | Dependency management |

---

## 📁 Project Structure

```
src/main/java/com/shah/jwt_auth/
│
├── config/
│   └── SecurityConfig.java          # Spring Security rules & configuration
│
├── controller/
│   ├── AuthController.java          # /api/auth/register, /api/auth/login
│   ├── UserController.java          # /api/user/profile
│   └── AdminController.java         # /api/admin/dashboard
│
├── dto/
│   ├── RegisterRequest.java         # Registration request body
│   ├── LoginRequest.java            # Login request body
│   └── AuthResponse.java            # JWT token response
│
├── entity/
│   └── User.java                    # User database entity
│
├── repository/
│   └── UserRepository.java          # Database queries
│
├── security/
│   ├── JwtUtil.java                 # JWT token generation & validation
│   └── JwtAuthFilter.java           # JWT request filter
│
├── service/
│   ├── AuthService.java             # Registration & login logic
│   └── UserDetailsServiceImpl.java  # Spring Security user loader
│
└── JwtAuthApplication.java          # Application entry point
```

---

## ⚙️ How It Works

### Authentication Flow

```
1. User registers  → password hashed with BCrypt → saved to DB → JWT returned
2. User logs in    → credentials validated → JWT returned
3. Protected request → JWT filter extracts token → validates → grants access
```

### JWT Token Structure

```
Header.Payload.Signature
  │       │        │
  │       │        └── HMAC SHA256 signature (server secret)
  │       └─────────── username, issued at, expiry
  └─────────────────── algorithm (HS256)
```

### Request Lifecycle

```
HTTP Request
     │
     ▼
┌─────────────────┐
│  JWT Filter     │  Checks Authorization header on every request
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│ Security Config │  Decides if route is public or protected
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│   Controller    │  Handles the endpoint logic
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│    Service      │  Business logic
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│   Repository    │  Database operations
└─────────────────┘
```

---

## 🛠️ Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.x
- IntelliJ IDEA (recommended)

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/Shah114/jwt-auth.git
cd jwt-auth
```

2. **Run the application**
```bash
./mvnw spring-boot:run
```

3. **App starts on**
```
http://localhost:8080
```

4. **View H2 Database Console**
```
http://localhost:8080/h2-console

JDBC URL:  jdbc:h2:mem:jwtdb
Username:  sa
Password:  (leave empty)
```

---

## 📡 API Endpoints

### Public Endpoints

| Method | Endpoint | Description | Body |
|---|---|---|---|
| POST | `/api/auth/register` | Register new user | `{"username", "email", "password"}` |
| POST | `/api/auth/login` | Login user | `{"username", "password"}` |

### Protected Endpoints (Requires JWT)

| Method | Endpoint | Description | Role Required |
|---|---|---|---|
| GET | `/api/user/profile` | Get user profile | USER |
| GET | `/api/admin/dashboard` | Admin dashboard | ADMIN |

---

## 🧪 Testing with Postman

### 1. Register a User

```http
POST http://localhost:8080/api/auth/register
Content-Type: application/json

{
    "username": "shah",
    "email": "shah@gmail.com",
    "password": "123456"
}
```

**Response:**
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

### 2. Login

```http
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
    "username": "shah",
    "password": "123456"
}
```

**Response:**
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

### 3. Access Protected Route

```http
GET http://localhost:8080/api/user/profile
Authorization: Bearer <your-token-here>
```

**Response:**
```
Welcome shah! This is your profile.
```

---

### 4. Access Admin Route

First update a user's role in H2 console:
```sql
UPDATE users SET role = 'ADMIN' WHERE username = 'admin';
```

Then:
```http
GET http://localhost:8080/api/admin/dashboard
Authorization: Bearer <admin-token-here>
```

**Response:**
```
Welcome Admin admin! This is the admin dashboard.
```

---

## 🔒 Security Features

- **BCrypt** password hashing — passwords never stored in plain text
- **JWT stateless authentication** — no server-side sessions
- **Role-based access control** — USER and ADMIN roles
- **Token expiry** — tokens expire after 24 hours
- **CSRF disabled** — not needed for stateless REST APIs

---

## 🗺️ Roadmap

- [ ] Exception handling with proper error responses
- [ ] Token refresh endpoint
- [ ] User logout with token blacklist
- [ ] Switch to PostgreSQL
- [ ] Input validation with Bean Validation
- [ ] Swagger UI documentation
- [ ] Docker support

---

## 👨‍💻 Author

**Shah** — Built as a learning project to understand JWT authentication in Spring Boot.

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
