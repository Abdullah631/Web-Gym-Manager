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
    <div class="container">
        <h1>Calculate Your BMI and Get a Diet Plan</h1>
        <form id="dietPlanForm" action="DietPlanServlet" method="POST">
            <label for="height">Height (in meters):</label>
            <input type="number" id="height" name="height" step="0.01" class="form-input" required>

            <label for="weight">Weight (in kilograms):</label>
            <input type="number" id="weight" name="weight" step="0.1" class="form-input" required>

            <button type="submit" class="form-button">Get Diet Plan</button>
        </form>
        <br>
        <a href="User_dashboard.jsp" class="form-button">Back to Dashboard</a>
    </div>
</body>
</html>
