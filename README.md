# 🛡️ Insurance Management System

A Spring Boot-based mini Insurance Management System that manages **Policies**, **Billing**, and **Claims** with real-time validations and clean modular architecture. 
Built for learning real-world architecture and backend interview preparation.

## 🚀 Features

### ✅ Policy Module
- Add new policies for customers
- Store policy number, type, status, etc.
- Fetch policies by ID or policy number

### 💰 Billing Module
- Generate bills only for active policies
- Automatically assign bill numbers
- Prevent duplicate or invalid billing
- Get bills based on policy number

### 📝 Claims Module
- File claims only if:
  - Policy exists and is active
  - Related bill is paid
- Retrieve claims by claim ID or policy number

### ⚙️ Core Implementations
- RESTful API design using Spring Boot
- Real-time validation across modules (Policy → Billing → Claims)
- Centralized Exception Handling
- Logging using SLF4J and Logback
- Database query optimization using stored procedures
- Excel macro integration for report validation (optional)

---

## 🧪 API Testing

All endpoints are tested using **Postman**.
- Includes full CRUD for each module
- Validation of business rules before bill or claim creation

👉 You can import the [Postman Collection here](./postman/InsuranceSystem.postman_collection.json) (if uploaded).

---

## 🛠️ Tech Stack

- **Backend**: Java 17, Spring Boot
- **Database**: MySQL
- **Build Tool**: Maven
- **Testing**: JUnit, Postman
- **IDE**: Spring Tool Suite (STS)


## 🧠 Learning Goals

- Master service-to-service data validation
- Build robust, scalable REST APIs
- Gain real-time business logic understanding (Insurance domain)
- Learn exception handling, logging, and stored procedure use

---

## 📈 Future Enhancements

- 🔐 JWT-based user authentication
- 📤 Email notifications (Spring Mail)
- 📄 PDF generation for bills
- 📊 Admin dashboard APIs for insights
- 🧾 CSV/Excel export of policies and claims

---

## 🤝 Contributing

This project is for learning purposes and open to contributions. Feel free to fork and enhance it!

---

## 📞 Contact

Created by Akhilesh Chaurasia 
📧 Email: akhileshchaurasiya548@gmail.com

