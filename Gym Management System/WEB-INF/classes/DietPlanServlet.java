
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class DietPlanServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sess = request.getSession(false);
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setContentType("text/html");
        out.println("<head>");
        out.println("<title>Daily Diet Plan</title>");
        out.println("<link rel='stylesheet' href='style.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='dashboard-container'>");
        if (sess == null || !"Member".equals(sess.getAttribute("usertype"))) {
            out.println("<h1>Access Denied</h1>");
            out.println("<p>You are not authorized to access this page.</p>");
            out.println("<a href='Member_login.jsp'>Go to Login</a>");
            return;
        } else {
            double height = Double.parseDouble(request.getParameter("height"));
            double weight = Double.parseDouble(request.getParameter("weight"));

            double bmi = weight / (height * height);
            String category;
            String dietPlan;

            if (bmi < 18.5) {
                category = "Underweight";
                dietPlan = "<ul>"
                        + "<li>Breakfast: Oatmeal with fruits and nuts</li>"
                        + "<li>Lunch: Grilled chicken with rice and vegetables</li>"
                        + "<li>Dinner: Pasta with lean protein and salad</li>"
                        + "</ul>";
            } else if (bmi >= 18.5 && bmi < 24.9) {
                category = "Normal weight";
                dietPlan = "<ul>"
                        + "<li>Breakfast: Smoothie with banana and almond milk</li>"
                        + "<li>Lunch: Salmon with quinoa and steamed vegetables</li>"
                        + "<li>Dinner: Grilled turkey with roasted sweet potatoes</li>"
                        + "</ul>";
            } else if (bmi >= 25 && bmi < 29.9) {
                category = "Overweight";
                dietPlan = "<ul>"
                        + "<li>Breakfast: Greek yogurt with berries</li>"
                        + "<li>Lunch: Grilled chicken salad with olive oil dressing</li>"
                        + "<li>Dinner: Stir-fried vegetables with tofu</li>"
                        + "</ul>";
            } else {
                category = "Obese";
                dietPlan = "<ul>"
                        + "<li>Breakfast: Scrambled eggs with spinach</li>"
                        + "<li>Lunch: Grilled fish with steamed broccoli</li>"
                        + "<li>Dinner: Grilled chicken breast with a side of green salad</li>"
                        + "</ul>";
            }

            out.println("<h1>Your BMI Results</h1>");
            out.println("<p><strong>BMI:</strong> " + String.format("%.2f", bmi) + "</p>");
            out.println("<p><strong>Category:</strong> " + category + "</p>");
            out.println("<h2>Your Daily Diet Plan:</h2>");
            out.println(dietPlan);
            out.println("<br><a href='diet-plans.jsp' class='form-button'>Back to BMI Form</a>");
            out.println("<br><br><a href='User_dashboard.jsp' class='form-button'>Back to Dashboard</a>");
        }
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

}
