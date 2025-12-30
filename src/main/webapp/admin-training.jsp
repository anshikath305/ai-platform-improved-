<%@ page import="java.util.*" %>
<%@ page import="com.ai.platform.dao.TrainingJobDAO" %>
<%@ page import="com.ai.platform.dao.UserDAO" %>
<%@ page import="com.ai.platform.model.TrainingJob" %>
<%@ page import="com.ai.platform.model.User" %>

<%
    User admin = (User) session.getAttribute("user");
    if (admin == null || !"ADMIN".equalsIgnoreCase(admin.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }

    TrainingJobDAO dao = new TrainingJobDAO();
    List<TrainingJob> jobs = dao.getAllTrainingJobs();
    if (jobs == null) jobs = new ArrayList<>();

    UserDAO userDAO = new UserDAO();
%>

<!DOCTYPE html>
<html>
<head>
    <title>Admin - Training Jobs</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>

<body>

<!-- SHARED SIDEBAR -->
<jsp:include page="admin-sidebar.jsp" />

<div class="content">

    <h2>All Training Jobs</h2>
    <p>All model training jobs executed by users.</p>

    <table>
        <tr>
            <th>ID</th>
            <th>User</th>
            <th>Dataset</th>
            <th>Model</th>
            <th>Status</th>
            <th>Accuracy</th>
            <th>Created At</th>
            <th>Action</th>
        </tr>

        <% if (jobs.isEmpty()) { %>
            <tr>
                <td colspan="8">No training jobs found.</td>
            </tr>
        <% } else {
            for (TrainingJob job : jobs) {

                User u = userDAO.getUserById(job.getCreatedBy());
                String username = (u != null && u.getName() != null) ? u.getName() : "Unknown";

                String status = (job.getStatus() != null) ? job.getStatus() : "PENDING";
                float accuracy = job.getAccuracy();
                String createdAt = (job.getCreatedAt() != null) ? job.getCreatedAt().toString() : "-";
        %>

        <tr>
            <td><%= job.getId() %></td>
            <td><%= username %></td>
            <td><%= job.getDatasetId() %></td>
            <td><%= job.getModelName() %></td>
            <td>
                <span class="status <%= status.toLowerCase() %>">
                    <%= status %>
                </span>
            </td>
            <td><%= accuracy %> %</td>
            <td><%= createdAt %></td>
            <td>
                <a class="btn-delete" href="delete-training?id=<%= job.getId() %>">
                    Delete
                </a>
            </td>
        </tr>

        <% } } %>
    </table>

</div>

</body>
</html>
