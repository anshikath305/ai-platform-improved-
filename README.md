# 🌐 AI Platform – Researcher & Admin Portal

A **full-stack AI Experimentation & Collaboration Platform** built using  
**Java 17, JSP, Servlets, JDBC, MySQL, Maven, and Tomcat 10**.

The platform enables **Researchers** to manage datasets, experiments, and AI training jobs with real-time feedback, while **Admins** oversee users, experiments, and system operations through a dedicated dashboard.

---

## ✨ What’s New & Improved

- ✅ Premium UI redesign for Login, Signup, Admin & Researcher dashboards  
- ✅ Strict Model–View–Controller (MVC) architecture  
- ✅ Dedicated Service layer for AI execution & business logic  
- ✅ JDBC Transaction Management (commit / rollback)  
- ✅ Role-based access control using Servlet Filters  
- ✅ Separate Admin & Researcher portals  
- ✅ Training History with live auto-refresh  
- ✅ Progress bars & status badges  
- ✅ Centralized error handling (403 / 404 / 500 pages)  
- ✅ Clean GitHub repository (`target/` ignored properly)  
- ✅ Production-ready structure  

---

## 🚀 Features

### 👩‍💻 Researcher Capabilities
- Secure Login & Signup  
- Premium Researcher Dashboard  
- Upload & manage datasets  
- Start AI model training (CNN, RNN, Transformer, Decision Tree, etc.)  
- Live Training History with progress visualization  
- Create and manage experiments  
- Track experiment metrics (accuracy, status)  
- View experiment logs  
- Project collaboration with comments (AJAX auto-refresh)  
- Profile management  

---

### 🛡️ Admin Capabilities
- Secure Admin Dashboard  
- View & manage all users  
- Update user roles (ADMIN / RESEARCHER)  
- View & delete datasets  
- Monitor all training jobs  
- View all experiments  
- Admin-only protected routes  
- Sidebar-based navigation  

---

## 🎨 UI Enhancements (Premium Look & Feel)

- Modern card-based dashboards  
- Unified color palette & typography  
- Styled tables, badges, and buttons  
- Status-based color coding (PENDING / RUNNING / COMPLETED / FAILED)  
- Progress bars for training accuracy  
- Improved login & signup UX  
- Responsive layout structure  

---

## 🏗️ Architecture & Design

### 🧩 Model–View–Controller (MVC)

The application strictly follows **MVC**:

**Model**
- `User`, `Dataset`, `Experiment`, `TrainingJob`

**View**
- JSP pages only for UI rendering  
- No business logic inside JSPs  

**Controller**
- Servlets handle routing, sessions, and request flow  

**DAO Layer**
- All database access isolated into DAO classes  

---

### 🧠 Service Layer (Business & AI Logic Isolation)

All **AI execution and business logic** is isolated into **dedicated Service classes**, ensuring strict MVC separation.

📁 Location:
src/main/java/com/ai/platform/service/

yaml
Copy code

This ensures:
- Controllers stay lightweight  
- Logic is reusable and testable  
- Clean separation of concerns  

---

### 🔐 JDBC Transaction Management

The platform implements **explicit JDBC transactions** to maintain data integrity.

Example use cases:
- Creating a training job + inserting logs atomically  
- Saving experiment data consistently  

```java
conn.setAutoCommit(false);
// insert training job
// insert experiment log
conn.commit();
On failure:

java
Copy code
conn.rollback();
✅ Prevents partial writes
✅ Ensures atomic operations
✅ Maintains database consistency

🛡️ Security & Role-Based Access Control
Session-based authentication

Role-based authorization via RoleFilter

ADMIN & RESEARCHER routes strictly protected

Unauthorized access redirected to error pages

⚙️ Error Handling
Centralized ExceptionFilter

Custom error pages:

error403.jsp

error404.jsp

error500.jsp

Server-side error logging

🏗️ Technology Stack
Java 17

JSP / Servlets (Jakarta EE)

JDBC

MySQL

Maven

Apache Tomcat 10

HTML / CSS (Premium UI)

AJAX (real-time comments)

Servlet Filters

📦 Installation & Setup
1️⃣ Clone Repository
bash
Copy code
git clone https://github.com/anshikath305/ai-platform-improved-.git
cd ai-platform
2️⃣ Import Project
Open as Maven Project in:

IntelliJ IDEA

Eclipse

VS Code (Java Extension Pack)

3️⃣ Database Setup (MySQL)
Create database:

sql
Copy code
CREATE DATABASE ai_platform;
Import your SQL schema.

Update credentials in:

swift
Copy code
src/main/java/com/ai/platform/db/DBConnection.java
Example:

java
Copy code
private static final String URL = "jdbc:mysql://localhost:3306/ai_platform";
private static final String USER = "root";
private static final String PASSWORD = "password";
4️⃣ Build Project
bash
Copy code
mvn clean package
WAR file:

bash
Copy code
target/ai-platform.war
5️⃣ Deploy on Tomcat 10
bash
Copy code
cp target/ai-platform.war /usr/local/tomcat10/apache-tomcat-10.1.49/webapps/
Restart Tomcat.

🌐 Application URLs
Login

bash
Copy code
http://localhost:8080/ai-platform/login.jsp
Admin Dashboard

bash
Copy code
/admin-dashboard.jsp
Researcher Dashboard

bash
Copy code
/researcher-dashboard.jsp
🔐 Authentication & Roles
ADMIN
Full system access

Manage users, roles, datasets, experiments, training jobs

RESEARCHER
Upload datasets

Run training jobs

Create & monitor experiments

All routes are protected via RoleFilter.

📁 Project Structure
swift
Copy code
ai-platform/
│
├── src/main/java/com/ai/platform/
│   ├── dao/
│   ├── model/
│   ├── servlets/
│   ├── filters/
│   ├── service/
│   ├── util/
│   └── db/
│
├── src/main/webapp/
│   ├── admin-*.jsp
│   ├── researcher-*.jsp
│   ├── assets/css/
│   ├── error403.jsp
│   ├── error404.jsp
│   ├── error500.jsp
│   └── WEB-INF/
│
├── pom.xml
├── .gitignore
└── README.md
🧪 Testing Checklist
Researcher
✔ Login / Signup
✔ Upload datasets
✔ Start training jobs
✔ View training history
✔ Track experiment metrics
✔ Real-time updates
✔ Premium UI navigation

Admin
✔ Admin login
✔ View & manage users
✔ Update user roles
✔ View datasets
✔ View experiments
✔ View training jobs
✔ Sidebar navigation

❤️ Author
Anshika Thakur & Sumit Kumar Ratna 
AI Developer & Research Enthusiast

GitHub:
👉 https://github.com/anshikath305

📜 License
This project is licensed under the MIT License.

