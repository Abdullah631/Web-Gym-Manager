
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class ChangeMemberPasswordServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sess = request.getSession(false);
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<head>");
        out.println("<title>Change Password</title>");
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
            String username = (String) sess.getAttribute("username");
            String currentPassword = request.getParameter("currentPassword");
            String newPassword = request.getParameter("newPassword");
            String userType = (String) sess.getAttribute("usertype");

            try {
                AdminDAO admin = new AdminDAO();
                int num = admin.changePassword(username, currentPassword, newPassword, userType);
                if (num == 0) {
                    out.println("<h1>Error: Current password is incorrect.</h1>");
                    out.println("<a href='change-member-password.jsp'>Try Again</a>");
                    return;

                } else if (num == 1) {
                    out.println("<h1>Error: User not found.</h1>");
                    return;
                } else if (num == 2) {
                    out.println("<h1>Password changed successfully!</h1>");
                } else {
                    out.println("<h1>Error: Failed to change password. Please try again.</h1>");
                }
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
                out.println("<a href='User_dashboard.jsp'>Back to Dashboard</a>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            }
        }
    }
}
