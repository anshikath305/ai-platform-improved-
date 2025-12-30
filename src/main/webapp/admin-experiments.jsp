<%@ page import="java.util.*" %>
<%@ page import="com.ai.platform.model.Experiment" %>
<%@ page import="com.ai.platform.dao.ExperimentDAO" %>
<%@ page import="com.ai.platform.model.User" %>

<%
    User admin = (User) session.getAttribute("user");
    if (admin == null || !"ADMIN".equalsIgnoreCase(admin.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }

    ExperimentDAO dao = new ExperimentDAO();
    List<Experiment> experiments = dao.getAllExperiments();
    if (experiments == null) experiments = new ArrayList<>();
%>

<!DOCTYPE html>
<html>
<head>
    <title>Admin - Experiments</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>

<body>

<!-- SHARED SIDEBAR -->
<jsp:include page="admin-sidebar.jsp" />

<div class="content">

    <h2>All Experiments</h2>
    <p>All experiments created by users.</p>

    <table>
        <tr>
            <th>ID</th>
            <th>User</th>
            <th>Title</th>
            <th>Status</th>
            <th>Accuracy</th>
            <th>Created At</th>
            <th>Action</th>
        </tr>

        <% if (experiments.isEmpty()) { %>
            <tr>
                <td colspan="7">No experiments found.</td>
            </tr>
        <% } else {
            for (Experiment exp : experiments) {

                String status = (exp.getStatus() != null) ? exp.getStatus() : "PENDING";
                float accuracy = exp.getAccuracy();
                String createdAt = (exp.getCreatedAt() != null) ? exp.getCreatedAt() : "-";
        %>

        <tr>
            <td><%= exp.getId() %></td>
            <td><%= exp.getUserName() %></td>
            <td><%= exp.getTitle() %></td>
            <td>
                <span class="status <%= status.toLowerCase() %>">
                    <%= status %>
                </span>
            </td>
            <td><%= accuracy %> %</td>
            <td><%= createdAt %></td>
            <td>
                <a class="btn-delete"
                   href="delete-experiment?id=<%= exp.getId() %>">
                   Delete
                </a>
            </td>
        </tr>

        <% } } %>
    </table>

</div>

</body>
</html>
