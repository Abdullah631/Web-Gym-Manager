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
        <h2>Remove Gym Member</h2>
        <form action="RemoveGymMemberServlet" method="POST">
            <label for="cnic">CNIC (Username):</label>
            <input type="text" id="cnic" name="cnic" class="form-input" pattern="\d{13}" maxlength="13" title="Enter a valid 13-digit CNIC" required>

            <button type="submit" class="form-button">Remove Member</button>
			<br>
			<br>
        </form>

        <div class="actions">
            <br>
            <a href="admin-dashboard.jsp" class="form-button back-button">Back to Dashboard</a>
        </div>
    </div>

    <script>
        // Client-side validation for the CNIC field
        document.querySelector("form").addEventListener("submit", function(event) {
            const cnic = document.getElementById("cnic").value.trim();

            if (!/^\d{13}$/.test(cnic)) {
                alert("Please enter a valid 13-digit CNIC.");
                event.preventDefault();
            }
        });
    </script>
</body>
</html>
