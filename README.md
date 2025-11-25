# 🌐 AI Platform – Researcher & Admin Portal  
A complete AI Experimentation & Collaboration Platform built using **Java 17, JSP, Servlets, JDBC, MySQL, Maven, and Tomcat 10**.

Researchers can upload datasets, create experiments, run training jobs, collaborate on projects, and track metrics — while Admins manage users, training jobs, datasets, and experiments.

---

## 🚀 Features

### 👩‍💻 Researcher Functions
- Login & Signup
- Upload datasets
- Create experiments
- Track model metrics (accuracy, status)
- Run training jobs
- View experiment logs
- Real-time collaboration with comments (AJAX auto-refresh)
- Project member management
- Profile page

### 🛡️ Admin Functions
- Manage all users  
- Update user roles  
- Delete users  
- View all datasets  
- View all training jobs  
- View all experiments  
- Full admin dashboard

---

## 🏗️ Technology Stack

- **Java 17**
- **JSP / Servlets**
- **Jakarta EE**
- **JDBC + MySQL**
- **Maven**
- **Tomcat 10**
- **AJAX for real-time comments**

---

## 📦 Installation

### 1️⃣ Clone the Repository
```sh
git clone https://github.com/anshikath305/ai-platform.git
cd ai-platform
 2️⃣ Import Project
Open in:
IntelliJ
Eclipse
VS Code (Java Pack)
Import as Maven Project.
🗄️ Database Setup (MySQL)
Create database:

CREATE DATABASE ai_platform;
Import your SQL schema file.
Update DB credentials in:

src/main/java/com/ai/platform/db/DBConnection.java
Example:

private static final String URL = "jdbc:mysql://localhost:3306/ai_platform";
private static final String USER = "root";
private static final String PASSWORD = "password";

🔨 Build Application

mvn clean package
Generates:

target/ai-platform.war

🚀 Deploy on Tomcat
Copy WAR file:

sudo cp target/ai-platform.war \
/usr/local/tomcat10/apache-tomcat-10.1.49/webapps/
Restart Tomcat:

sudo systemctl restart tomcat

🌐 Access Application

http://localhost:8080/ai-platform/login.jsp

🔑 Default Credentials
Use the credentials in your database. Example:
Admin

Email: admin@example.com
Password: admin

📁 Project Structure

ai-platform/
│
├── src/main/java/com/ai/platform/
│   ├── servlets/
│   ├── dao/
│   ├── model/
│   ├── filters/
│   └── db/
│
├── src/main/webapp/
│   ├── *.jsp
│   ├── assets/css/
│   └── WEB-INF/
│
├── pom.xml
└── README.md

🧪 Testing Checklist
Researcher
✔ Login ✔ Upload datasets ✔ Create experiments ✔ View logs ✔ Training jobs ✔ Real-time comments ✔ Profile page
Admin
✔ Manage users ✔ Update roles ✔ Delete user ✔ View datasets ✔ View experiments ✔ View training jobs ✔ Sidebar navigation


❤️ Author
Anshika Thakur AI Developer & Research Enthusiast GitHub: https://github.com/anshikath305

📜 License
This project is licensed under the MIT License.

