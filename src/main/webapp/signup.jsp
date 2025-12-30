<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String error = request.getParameter("error");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Sign Up | AI Platform</title>
    <meta charset="UTF-8">

    <link rel="stylesheet" href="assets/css/style.css">

    <style>
        body {
            margin: 0;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            background: linear-gradient(135deg, #020617, #1e293b);
            font-family: "Segoe UI", sans-serif;
        }

        .signup-card {
            width: 420px;
            background: #ffffff;
            padding: 35px;
            border-radius: 12px;
            box-shadow: 0 20px 40px rgba(0,0,0,0.25);
        }

        .signup-card h2 {
            margin: 0 0 8px;
            text-align: center;
            color: #111827;
        }

        .signup-card p {
            text-align: center;
            color: #6b7280;
            margin-bottom: 25px;
        }

        label {
            display: block;
            margin-top: 15px;
            font-weight: 600;
            color: #374151;
        }

        input, select {
            width: 100%;
            padding: 10px 12px;
            margin-top: 6px;
            border-radius: 6px;
            border: 1px solid #d1d5db;
            font-size: 14px;
        }

        input:focus, select:focus {
            outline: none;
            border-color: #2563eb;
            box-shadow: 0 0 0 2px rgba(37,99,235,0.2);
        }

        .btn-signup {
            width: 100%;
            margin-top: 25px;
            padding: 12px;
            border: none;
            border-radius: 6px;
            background: #2563eb;
            color: white;
            font-size: 15px;
            font-weight: 600;
            cursor: pointer;
        }

        .btn-signup:hover {
            background: #1e40af;
        }

        .error {
            margin-top: 15px;
            text-align: center;
            color: #dc2626;
            font-size: 14px;
        }

        .login-link {
            margin-top: 20px;
            text-align: center;
            font-size: 14px;
        }

        .login-link a {
            color: #2563eb;
            font-weight: 600;
            text-decoration: none;
        }
    </style>
</head>

<body>

<div class="signup-card">
    <h2>Create Account 🚀</h2>
    <p>Join the AI Platform and start experimenting</p>

    <form action="signup" method="post">

        <label>Full Name</label>
        <input type="text" name="name" required placeholder="Your full name">

        <label>Email</label>
        <input type="email" name="email" required placeholder="you@example.com">

        <label>Password</label>
        <input type="password" name="password" required placeholder="••••••••">

        <label>Role</label>
        <select name="role" required>
            <option value="">Select role</option>
            <option value="RESEARCHER">Researcher</option>
            <option value="ADMIN">Admin</option>
        </select>

        <button class="btn-signup" type="submit">
            Create Account
        </button>
    </form>

    <% if ("1".equals(error)) { %>
        <div class="error">
            Signup failed. Email may already exist.
        </div>
    <% } %>

    <div class="login-link">
        Already have an account?
        <a href="login.jsp">Login</a>
    </div>
</div>

</body>
</html>
