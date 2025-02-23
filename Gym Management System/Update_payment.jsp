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
        <h2>Update Monthly Payment Status</h2>
        <form action="UpdatePaymentServlet" method="POST">
            <button type="submit">Update Payment</button>
        </form>
        <div class="actions">
            <br>
            <a href="admin-dashboard.jsp" class="form-button back-button">Back to Dashboard</a>
        </div>
    </div>
</body>
</html>
