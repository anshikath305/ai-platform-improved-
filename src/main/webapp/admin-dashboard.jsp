<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.ai.platform.model.User" %>

<%
    User admin = (User) session.getAttribute("user");
    if (admin == null || !"ADMIN".equalsIgnoreCase(admin.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="assets/css/style.css">
</head>

<body>

<!-- SHARED SIDEBAR -->
<jsp:include page="admin-sidebar.jsp" />

<!-- MAIN CONTENT -->
<div class="content">

    <div class="card">
        <h1>Welcome, <%= admin.getName() %></h1>
        <p>You are logged in as <b>ADMIN</b>.</p>
    </div>

    <div class="stats">
        <div class="stat-card">
            <h3>Total Users</h3>
            <p>128</p>
        </div>

        <div class="stat-card">
            <h3>Experiments</h3>
            <p>56</p>
        </div>

        <div class="stat-card">
            <h3>Datasets</h3>
            <p>24</p>
        </div>

        <div class="stat-card">
            <h3>Active Trainings</h3>
            <p>6</p>
        </div>
    </div>

</div>

</body>
</html>
