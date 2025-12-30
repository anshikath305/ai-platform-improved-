<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String error = request.getParameter("error");
    String logout = request.getParameter("logout");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Login | AI Platform</title>
    <meta charset="UTF-8">

    <link rel="stylesheet" href="assets/css/style.css">

    <style>
        body {
            margin: 0;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            background: linear-gradient(135deg, #0f172a, #1e293b);
            font-family: "Segoe UI", sans-serif;
        }

        .login-card {
            width: 380px;
            background: #ffffff;
            padding: 35px;
            border-radius: 12px;
            box-shadow: 0 20px 40px rgba(0,0,0,0.25);
        }

        .login-card h2 {
            margin: 0 0 10px;
            text-align: center;
            color: #111827;
        }

        .login-card p {
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

        input {
            width: 100%;
            padding: 10px 12px;
            margin-top: 6px;
            border-radius: 6px;
            border: 1px solid #d1d5db;
            font-size: 14px;
        }

        input:focus {
            outline: none;
            border-color: #2563eb;
            box-shadow: 0 0 0 2px rgba(37,99,235,0.2);
        }

        .btn-login {
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

        .btn-login:hover {
            background: #1e40af;
        }

        .msg {
            margin-top: 15px;
            text-align: center;
            font-size: 14px;
        }

        .error {
            color: #dc2626;
        }

        .success {
            color: #16a34a;
        }

        .signup {
            margin-top: 20px;
            text-align: center;
            font-size: 14px;
        }

        .signup a {
            color: #2563eb;
            text-decoration: none;
            font-weight: 600;
        }
    </style>
</head>

<body>

<div class="login-card">
    <h2>Welcome Back 👋</h2>
    <p>Sign in to your AI Platform account</p>

    <form action="login" method="post">

        <label>Email</label>
        <input type="email" name="email" required placeholder="you@example.com">

        <label>Password</label>
        <input type="password" name="password" required placeholder="••••••••">

        <button class="btn-login" type="submit">Login</button>
    </form>

    <% if ("1".equals(error)) { %>
        <div class="msg error">Invalid email or password</div>
    <% } %>

    <% if ("1".equals(logout)) { %>
        <div class="msg success">You have been logged out successfully</div>
    <% } %>

    <div class="signup">
        Don’t have an account?
        <a href="signup.jsp">Create one</a>
    </div>
</div>

</body>
</html>
