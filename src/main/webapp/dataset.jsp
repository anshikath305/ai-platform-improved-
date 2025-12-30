<%@ page import="java.util.List" %>
<%@ page import="com.ai.platform.model.Dataset" %>
<%@ page import="com.ai.platform.dao.DatasetDAO" %>
<%@ page import="com.ai.platform.model.User" %>

<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    DatasetDAO dao = new DatasetDAO();
    List<Dataset> datasets = dao.getDatasetsByUser(user.getId());
%>

<!DOCTYPE html>
<html>
<head>
    <title>Dataset Management</title>
    <link rel="stylesheet" href="assets/css/style.css">

    <style>
        body {
            font-family: Inter, Arial, sans-serif;
            background: #f5f7fb;
            margin: 0;
            padding: 40px;
        }

        .container {
            max-width: 1100px;
            margin: auto;
        }

        .page-title {
            font-size: 28px;
            font-weight: 700;
            margin-bottom: 6px;
        }

        .page-subtitle {
            color: #6b7280;
            margin-bottom: 30px;
        }

        .card {
            background: #ffffff;
            padding: 28px;
            border-radius: 14px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.08);
            margin-bottom: 40px;
        }

        .card h2 {
            margin-top: 0;
            margin-bottom: 20px;
        }

        .form-group {
            margin-bottom: 18px;
        }

        label {
            display: block;
            font-weight: 600;
            margin-bottom: 8px;
        }

        input[type="text"],
        textarea,
        input[type="file"] {
            width: 100%;
            padding: 12px;
            border-radius: 8px;
            border: 1px solid #d1d5db;
            font-size: 14px;
        }

        textarea {
            resize: vertical;
        }

        button {
            padding: 12px 24px;
            background: linear-gradient(135deg, #2563eb, #1e40af);
            color: white;
            font-weight: 600;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            font-size: 15px;
        }

        button:hover {
            opacity: 0.9;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background: #ffffff;
            border-radius: 14px;
            overflow: hidden;
            box-shadow: 0 10px 25px rgba(0,0,0,0.08);
        }

        th, td {
            padding: 14px 16px;
            text-align: left;
            border-bottom: 1px solid #e5e7eb;
            font-size: 14px;
        }

        th {
            background: #1f2937;
            color: #ffffff;
            font-weight: 600;
        }

        tr:hover {
            background: #f9fafb;
        }

        .btn-link {
            color: #2563eb;
            text-decoration: none;
            font-weight: 600;
        }

        .btn-delete {
            color: #dc2626;
            text-decoration: none;
            font-weight: 600;
        }

        .btn-delete:hover {
            text-decoration: underline;
        }
    </style>
</head>

<body>

<div class="container">

    <div class="page-title">Dataset Management</div>
    <div class="page-subtitle">
        Upload, manage, and reuse datasets for model training
    </div>

    <!-- Upload Dataset -->
    <div class="card">
        <h2>Upload New Dataset</h2>

        <form action="upload-dataset" method="post" enctype="multipart/form-data">

            <div class="form-group">
                <label>Dataset Name</label>
                <input type="text" name="name" required>
            </div>

            <div class="form-group">
                <label>Description</label>
                <textarea name="description" rows="3"></textarea>
            </div>

            <div class="form-group">
                <label>Upload File</label>
                <input type="file" name="dataset_file" required>
            </div>

            <button type="submit">📤 Upload Dataset</button>

        </form>
    </div>

    <!-- Dataset Table -->
    <h2>Your Datasets</h2>

    <table>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>File</th>
            <th>Uploaded At</th>
            <th>Action</th>
        </tr>

        <% if (datasets == null || datasets.isEmpty()) { %>
            <tr>
                <td colspan="6">No datasets uploaded yet.</td>
            </tr>
        <% } else {
            for (Dataset d : datasets) { %>

        <tr>
            <td><%= d.getId() %></td>
            <td><%= d.getName() %></td>
            <td><%= d.getDescription() %></td>
            <td>
                <a class="btn-link" href="<%= d.getFilePath() %>" target="_blank">
                    Download
                </a>
            </td>
            <td><%= d.getUploadedAt() %></td>
            <td>
                <a class="btn-delete"
                   href="delete-dataset?id=<%= d.getId() %>">
                   Delete
                </a>
            </td>
        </tr>

        <% } } %>
    </table>

</div>

</body>
</html>
