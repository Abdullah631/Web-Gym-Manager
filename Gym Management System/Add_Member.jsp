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
            <br>
            <br>
            <br>
            <h1>Add Gym Member</h1>
            <form id="addMemberForm" action="AddGymMemberServlet" method="POST">
                <label for="cnic">CNIC Number (13 digits):</label>
                <input type="text" id="cnic" name="cnic" class="form-input" maxlength="13" pattern="\d{13}" title="Enter a valid 13-digit CNIC number" required>
        
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" class="form-input" required>
        
                <label for="age">Age:</label>
                <input type="number" id="age" name="age" class="form-input" min="1" required>
        
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" class="form-input" required>
        
                <label for="phone">Phone Number:</label>
                <input type="text" id="phone" name="phone" class="form-input" pattern="\d{10,15}" title="Enter a valid phone number (10-15 digits)" required>
        
                <label for="membershipType">Membership Type:</label>
                <select id="membershipType" name="membershipType" class="form-input" required>
                    <option value="Basic">Basic</option>
                    <option value="Premium">Premium</option>
                    <option value="VIP">VIP</option>
                </select>
        
                <label for="startDate">Start Date:</label>
                <input type="date" id="startDate" name="startDate" class="form-input" required>
        
                <button type="submit" class="form-button">Add Member</button>
            </form>
            <p id="message" class="success-message"></p>
            <a href="admin-dashboard.jsp" class="form-button back-button">Back to Dashboard</a>
            <br>
        </div>
        
    
        <script>
            document.getElementById('addMemberForm').addEventListener('submit', function(event) {
                const cnic = document.getElementById('cnic').value.trim();
                if (!/^\d{13}$/.test(cnic)) {
                    event.preventDefault();
                    alert('CNIC must be a 13-digit number.');
                }
            });
        </script>
    </body>
    </html>