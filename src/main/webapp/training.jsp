<%@ page import="java.util.List" %>
<%@ page import="com.ai.platform.dao.DatasetDAO" %>
<%@ page import="com.ai.platform.model.Dataset" %>
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
    <title>Start Model Training</title>
    <link rel="stylesheet" href="assets/css/style.css">

    <style>
        body {
            font-family: Inter, Arial, sans-serif;
            background: #f5f7fb;
            margin: 0;
            padding: 40px;
        }

        .container {
            max-width: 900px;
            margin: auto;
        }

        .page-title {
            font-size: 28px;
            font-weight: 700;
            margin-bottom: 8px;
        }

        .page-subtitle {
            color: #6b7280;
            margin-bottom: 30px;
        }

        .card {
            background: #ffffff;
            padding: 30px;
            border-radius: 14px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.08);
        }

        .form-group {
            margin-bottom: 22px;
        }

        label {
            display: block;
            font-weight: 600;
            margin-bottom: 8px;
        }

        select, textarea {
            width: 100%;
            padding: 12px;
            border-radius: 8px;
            border: 1px solid #d1d5db;
            font-size: 14px;
        }

        textarea {
            resize: vertical;
        }

        .hint {
            font-size: 13px;
            color: #6b7280;
            margin-top: 6px;
        }

        .actions {
            margin-top: 30px;
            display: flex;
            justify-content: flex-end;
        }

        button {
            padding: 12px 26px;
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
    </style>
</head>

<body>

<div class="container">

    <div class="page-title">Start New Model Training</div>
    <div class="page-subtitle">
        Configure dataset, model, and parameters to launch a training job
    </div>

    <div class="card">

        <form action="train-model" method="post">

            <div class="form-group">
                <label>Select Dataset</label>
                <select name="dataset_id" required>
                    <option value="">-- Select Dataset --</option>
                    <% for (Dataset d : datasets) { %>
                        <option value="<%= d.getId() %>"><%= d.getName() %></option>
                    <% } %>
                </select>
            </div>

            <div class="form-group">
                <label>Select Model</label>
                <select name="model_name" required>
                    <option value="CNN">CNN</option>
                    <option value="RNN">RNN</option>
                    <option value="Transformer">Transformer</option>
                    <option value="DecisionTree">Decision Tree</option>
                </select>
            </div>

            <div class="form-group">
                <label>Training Parameters</label>
                <textarea
                        name="parameters"
                        rows="4"
                        placeholder='{"epochs":10, "lr":0.001}'></textarea>
                <div class="hint">
                    Use JSON format for flexibility (epochs, learning rate, batch size, etc.)
                </div>
            </div>

            <div class="actions">
                <button type="submit">🚀 Start Training</button>
            </div>

        </form>

    </div>

</div>

</body>
</html>
