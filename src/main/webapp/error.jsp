<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <style>
        body { 
            font-family: Arial; 
            background:#ffeeee; 
            padding:40px; 
            color:#b30000;
        }
        .box{
            background:white;
            padding:20px;
            border-radius:8px;
            box-shadow:0 2px 5px rgba(0,0,0,0.1);
            width:400px;
        }
    </style>
</head>
<body>

<div class="box">
    <h2>Something went wrong ❗</h2>
    
    <% String msg = request.getParameter("msg"); %>

    <% if(msg != null){ %>
        <p><%= msg %></p>
    <% } else { %>
        <p>Please try again later.</p>
    <% } %>

    <a href="index.jsp">Go Home</a>
</div>

</body>
</html>
