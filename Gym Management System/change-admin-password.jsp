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
        <h1>Change Password</h1>
        <form id="changePasswordForm" action="ChangeAdminPasswordServlet" method="POST">
            <label for="currentPassword">Current Password:</label>
            <input type="password" id="currentPassword" name="currentPassword" class="form-input" required>

            <label for="newPassword">New Password:</label>
            <input type="password" id="newPassword" name="newPassword" class="form-input" required>

            <label for="confirmNewPassword">Confirm New Password:</label>
            <input type="password" id="confirmNewPassword" class="form-input" required>

            <button type="submit" class="form-button">Reset Password</button>
        </form>
        <br>
        <a href="admin-dashboard.jsp" class="form-button">Back to Dashboard</a>
    </div>

    <script>
        document.getElementById('changePasswordForm').addEventListener('submit', function (event) {
            const newPassword = document.getElementById('newPassword').value;
            const confirmNewPassword = document.getElementById('confirmNewPassword').value;

            if (newPassword !== confirmNewPassword) {
                event.preventDefault();
                alert("New password and confirmation password do not match. Please try again.");
            }
        });
    </script>
</body>
</html>
