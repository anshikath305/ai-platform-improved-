<%@ page import="com.ai.platform.model.User" %>

<%
    User admin = (User) session.getAttribute("user");
%>

<div class="sidebar">
    <h2>Admin Panel</h2>

    <a href="admin-dashboard.jsp">Dashboard</a>
    <a href="admin-users.jsp">Users</a>
    <a href="admin-datasets.jsp">Datasets</a>
    <a href="admin-training.jsp">Training Jobs</a>
    <a href="admin-experiments.jsp">Experiments</a>

    <hr style="border-color:#374151; margin:20px 0;">

    <a class="logout" href="logout.jsp">Logout</a>
</div>
