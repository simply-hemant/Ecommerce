# 🛒 Simply Buy - E-commerce Backend

A robust and scalable **Spring Boot** backend for a full-featured e-commerce platform. Supports **user**, **seller**, and **admin** roles with secure authentication, product and order management, coupon/deal systems, and payment integrations.

---

Welcome to Simply Buy!

Ecommerce Backend : Service is Live!

Check GitHub for Postman Documentation:
Link : https://github.com/simply-hemant/ecommerce-backend

---

documentation link - https://documenter.getpostman.com/view/39898850/2sB3B7MYtF
---

## 💡 Key Features

### 🔐 Authentication & Authorization
- User and Seller registration & login
- **OAuth2 (Google Login)** for users
- JWT-based secure token system
- Role-based access control
- Email verification & password reset

### 🛍️ Ecommerce Modules
- Product and Category management
- Wishlist and Cart management
- Coupon and Deal system
- Checkout flow with multiple addresses

### 💳 Payments
- Integration with **Razorpay** and **Stripe**
- Secure order creation and tracking
- Payment verification and status updates

### 📦 Order Management
- Place, track, and cancel orders
- View order history per user
- Admin and Seller-level order controls

### 📊 Dashboards
- Admin Dashboard (user, seller, coupon, deal, product management)
- Seller Dashboard (manage own products, track orders, view reports)

---

## 🛠️ Tech Stack

- **Java 17**, **Spring Boot 3**
- **Spring Security + JWT**
- **JPA/Hibernate**, **MySQL**
- **Lombok**, **ModelMapper**, **MapStruct**
- **Stripe** and **Razorpay** SDKs
- **JavaMailSender** for email operations

---

## 📁 Package Structure

com.simply.ecommerce
├── controller
├── service
├── serviceimpl
├── model
├── modeldto
├── repository
├── exception
├── enums
├── config (security configs)
└── utils



---

## 📦 Setup Instructions

### 🔧 Backend
```bash
git clone https://github.com/your-username/ecommerce-backend.git
cd ecommerce-backend
./mvnw spring-boot:run

