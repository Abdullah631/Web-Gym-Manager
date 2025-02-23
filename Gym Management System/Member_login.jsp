<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Login</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="container">
        <h1>User Login</h1>
        <form id="loginForm" action="MemberLoginServlet" method="POST">
            <label for="cnic">CNIC Number (13 digits):</label>
            <input type="text" id="username" name="username" class="form-input" maxlength="13" pattern="\d{13}" title="Enter a valid 13-digit CNIC number" required>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" class="form-input" required>

            <button type="submit" class="form-button">Login</button>
        </form>
        <p id="message" class="error-message"></p>
        <a href="index.jsp" class="form-button">Back to Homepage</a>
    </div>
    <script>
        document.getElementById('loginForm').addEventListener('submit', function(event) {
            const username = document.getElementById('username').value.trim();
            const password = document.getElementById('password').value.trim();

            if (!username || !password) {
                event.preventDefault();
                document.getElementById('error-message').textContent = 'Both fields are required.';
                return;
            }
        });
    </script>
</body>
</html>
