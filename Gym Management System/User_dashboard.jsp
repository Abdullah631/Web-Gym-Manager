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
    
    if (sess == null || !"Member".equals(sess.getAttribute("usertype"))) {
        %>
        <div>
        <h1>Access Denied</h1>
        <p>You are not authorized to access this page.</p>
        <a href="Member_login.jsp">Go to Login</a>
        </div>
        <%
        // Stop further execution
        return;
    }
    %>
    <div class="form-container">
        <h1>Member Dashboard</h1>
        <div class="button-container">
            <br>
            <br>
            <a href="diet-plans.jsp" class="form-button">Diet Plan</a>
            <br>
            <br>
            <a href="workout.jsp" class="form-button">Workout</a>
            <br>
            <br>
            <a href="change-member-password.jsp" class="form-button">Change Password</a>
            <br>
            <br>
            <a href="MemberLogoutServlet" class="form-button">Logout</a>
            <br>
            <br>
        </div>
    </div>
</body>
</html>
