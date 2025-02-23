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
        <h2>Search Gym Member</h2>
        <form action="SearchGymMemberServlet" method="POST">
            <label for="cnic">Enter CNIC (Username):</label>
            <input type="text" id="cnic" name="cnic" class="form-input" required pattern="\d{13}" title="Please enter a valid 13-digit CNIC.">

            <button type="submit" class="form-button">Search</button>
        </form>

        <div id="result"></div>

        <div class="actions">
            <br>
            <a href="admin-dashboard.jsp" class="form-button back-button">Back to Dashboard</a>
        </div>
    </div>

    <script>
        document.querySelector("form").addEventListener("submit", function(event) {
            const cnic = document.getElementById("cnic").value.trim();

            // CNIC validation: ensuring exactly 13 digits
            if (!/^\d{13}$/.test(cnic)) {
                alert("Please enter a valid 13-digit CNIC.");
                event.preventDefault();
            }
        });
    </script>
</body>
</html>
