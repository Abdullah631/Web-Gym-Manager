<!DOCTYPE html>
<html>

<head>
    <title>User Profile</title>
    <%@ page language="java" import="jakarta.servlet.*,jakarta.servlet.http.*,java.sql.*" %>
        <%@ page contentType="text/html" pageEncoding="UTF-8" session="false" %>
        <link rel="stylesheet" href="styles.css">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Workout Plan</title>
    <link rel="stylesheet" href="style.css">
    <script>
        let timer = 0;
        let interval;
        let isPaused = false;

        function startTimer() {
            if (!interval) {
                interval = setInterval(() => {
                    timer++;
                    document.getElementById('timer').textContent = formatTime(timer);
                }, 1000);
            }
        }

        function pauseTimer() {
            clearInterval(interval);
            interval = null;
        }

        function togglePauseResume() {
            if (isPaused) {
                startTimer();
                document.getElementById('pauseResumeButton').textContent = 'Pause';
            } else {
                pauseTimer();
                document.getElementById('pauseResumeButton').textContent = 'Resume';
            }
            isPaused = !isPaused;
        }

        function endExercise(difficulty) {
            pauseTimer();
            const caloriesBurnt = calculateCaloriesBurnt(timer, difficulty);
            document.getElementById('workoutDetails').innerHTML = `
                <h2>Congratulations!</h2>
                <p>Total Workout Time: \${formatTime(timer)}</p>
                <p>Calories Burnt: \${caloriesBurnt} kcal</p>
            `;
        }

        function calculateCaloriesBurnt(time, difficulty) {
            let burnRate; // Calories burnt per minute
            switch (difficulty) {
                case 'easy': burnRate = 5; break;
                case 'medium': burnRate = 8; break;
                case 'hard': burnRate = 12; break;
                default: burnRate = 5; break;
            }
            return Math.round((time / 60) * burnRate);
        }

        function formatTime(seconds) {
            const mins = Math.floor(seconds / 60);
            const secs = seconds % 60;
            return `\${mins}m \${secs}s`;
        }

        function showWorkoutPlan(difficulty) {
            let workoutPlan;
            switch (difficulty) {
                case 'easy':
                    workoutPlan = `
                        <ul>
                            <li>2 Sets of The Following:</li>
                            <li>Push-ups: 10 reps</li>
                            <li>Squats: 15 reps</li>
                            <li>Plank: 30 seconds</li>
                            <li>Rest Time: 60 seconds between sets</li>
                        </ul>`;
                    break;
                case 'medium':
                    workoutPlan = `
                        <ul>
                            <li>4 Sets of The Following:</li>
                            <li>Push-ups: 15 reps</li>
                            <li>Squats: 20 reps</li>
                            <li>Plank: 45 seconds</li>
                            <li>Rest Time: 45 seconds between sets</li>
                        </ul>`;
                    break;
                case 'hard':
                    workoutPlan = `
                        <ul>
                            <li>6 Sets of The Following:</li>
                            <li>Push-ups: 20 reps</li>
                            <li>Squats: 25 reps</li>
                            <li>Plank: 60 seconds</li>
                            <li>Rest Time: 30 seconds between sets</li>
                        </ul>`;
                    break;
                default:
                    workoutPlan = `<p>Invalid workout plan selected.</p>`;
            }

            const workoutTitle = `\${difficulty.charAt(0).toUpperCase() + difficulty.slice(1)} Full Body Workout Plan`;

            document.getElementById('workoutDetails').innerHTML = `
                <h2>\${workoutTitle}</h2>
                \${workoutPlan}
                <p>Workout Timer: <span id="timer">0m 0s</span></p>
                <button onclick="startTimer()">Start</button>
                <button id="pauseResumeButton" onclick="togglePauseResume()">Pause</button>
                <button onclick="endExercise('${difficulty}')">End</button>
            `;
        }
    </script>

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
        <h1>Choose Your Workout Plan</h1>
        <button class="form-button" onclick="showWorkoutPlan('easy')">Easy</button>
        <button class="form-button" onclick="showWorkoutPlan('medium')">Medium</button>
        <button class="form-button" onclick="showWorkoutPlan('hard')">Hard</button>
        <br><br>
        <div id="workoutDetails"></div>
        <br>
        <a href="User_dashboard.jsp" class="form-button">Back to Dashboard</a>
    </div>
</body>

</html>
