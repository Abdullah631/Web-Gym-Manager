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
    <div class="form-container">
        <h1>Admin Dashboard</h1>
        <div class="button-container">
            <br>
            <br>
            <a href="Add_Member.jsp" class="form-button">Add Gym Member</a>
            <br>
            <br>
            <a href="Remove_Member.jsp" class="form-button">Remove Gym Member</a>
            <br>
            <br>
            <a href="Search_Member.jsp" class="form-button">Search Gym Member</a>
            <br>
            <br>
            <a href="Update_payment.jsp" class="form-button">Update Payments</a>
            <br>
            <br>
            <a href="Check_payments.jsp" class="form-button">Check Payments</a>
            <br>
            <br>
            <a href="add-new-admin.jsp" class="form-button">Add New Admin</a>
            <br>
            <br>
            <a href="change-admin-password.jsp" class="form-button">Change Password</a>
            <br>
            <br>
            <a href="AdminLogoutServlet" class="form-button">Logout</a>
            <br>
            <br>
        </div>
    </div>
</body>
</html>
