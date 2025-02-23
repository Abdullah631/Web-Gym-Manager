<!DOCTYPE html>
<head>
    <title>Admin Login</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="container">
        <h1>Admin Login</h1>
        <form id="loginForm" action="AdminLoginServlet" method="POST">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" class="form-input" required>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" class="form-input" required>

            <button type="submit" class="form-button">Login</button>
        </form>
        <p id="error-message" class="error-message"></p>
        <a href="index.jsp" class="form-button back-button">Back to Home</a>
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
            sessionStorage.setItem('username', username);
        });
    </script>
</body>
</html>
