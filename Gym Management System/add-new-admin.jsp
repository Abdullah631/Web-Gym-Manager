<!DOCTYPE html>
<html>

<head>
    <title>User Profile</title>
    <%@ page language="java" import="jakarta.servlet.*,jakarta.servlet.http.*,java.sql.*" %>
        <%@ page contentType="text/html" pageEncoding="UTF-8" session="false" %>
        <link rel="stylesheet" href="style.css">
</head>
<body>
    <% 
    HttpSession sess = request.getSession(false);
    
    if (sess == null || !"Admin".equals(sess.getAttribute("usertype"))) {
        %>
        <div>
        <h1>Access Denied</h1>
        <p>You are not authorized to access this page.</p>
        <a href="Admin_login.jsp">Go to Login</a>
        </div>
        <%
        // Stop further execution
        return;
    }
    %>
    <div class="container">
        <h1>Add New Admin</h1>
        <form id="addAdminForm" action="AddNewAdminServlet" method="POST">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" class="form-input" required>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" class="form-input" required>

            <button type="submit" class="form-button">Add Admin</button>
        </form>
        <br>
        <a href="admin-dashboard.jsp" class="form-button">Back to Dashboard</a>
    </div>
</body>
</html>
