
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.Random;

public class AddGymMemberServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sess = request.getSession(false);
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<head>");
        out.println("<title>Add New Gym Member</title>");
        out.println("<link rel='stylesheet' href='style.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='dashboard-container'>");
        if (sess == null || !"Admin".equals(sess.getAttribute("usertype"))) {
            out.println("<h1>Access Denied</h1>");
            out.println("<p>You are not authorized to access this page.</p>");
            out.println("<a href='Admin_login.html'>Go to Login</a>");
            return;
        } else {
            String cnic = request.getParameter("cnic");
            String name = request.getParameter("name");
            int age = Integer.parseInt(request.getParameter("age"));
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String membershipType = request.getParameter("membershipType");
            String startDate = request.getParameter("startDate");

            try {
                AdminDAO admin = new AdminDAO();
                if (admin.AddGymMember(cnic, name, age, email, phone, membershipType, startDate)) {
                    String password = admin.newPass;
                    out.println("<h2>Login Details:</h2>");
                    out.println("<p><strong>Username (CNIC):</strong> " + cnic + "</p>");
                    out.println("<p><strong>Password:</strong> " + password + "</p>");
                    out.println("<p>Please note the login credentials and provide them to the new user.</p>");
                } else {
                    out.println("<h1>Failed to add member. Please try again.</h1>");
                }

            } catch (SQLIntegrityConstraintViolationException e) {
                out.println("<h1>Error: Duplicate CNIC, email, or phone number. Please check the details and try again.</h1>");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                out.println("MySQL Driver not found.");
            } catch (SQLException e) {
                e.printStackTrace();
                out.println("<h1>Database error occurred. Please try again later.</h1>");
            } catch (Exception e) {
                e.printStackTrace();
                out.println("<h1>Unexpected error occurred. Please try again later.</h1>");
            } finally {
                out.println("<br><a href='admin-dashboard.jsp'>Back to Dashboard</a>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            }
        }
    }

}
