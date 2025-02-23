
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class RemoveGymMemberServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sess = request.getSession(false);
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html>");
        out.println("<head><title>Remove Gym Member</title></head>");
        out.println("<link rel='stylesheet' href='style.css'>");
        out.println("<body>");
        out.println("<div class='dashboard-container'>");
        if (sess == null || !"Admin".equals(sess.getAttribute("usertype"))) {
            out.println("<h1>Access Denied</h1>");
            out.println("<p>You are not authorized to access this page.</p>");
            out.println("<a href='Admin_login.jsp'>Go to Login</a>");
            return;
        } else {
            String cnic = request.getParameter("cnic");
            try {
                

                try {
                    AdminDAO admin = new AdminDAO();
                    int num = admin.removeGymMember(cnic);
                    if (num == 0) {
                        out.println("<h1>Member removed successfully from all records!</h1>");
                    } else if (num == 2) {
                        out.println("<h1>Failed to remove member from gym records. Please try again.</h1>");
                    } else {
                        out.println("<h1>No member found with the provided CNIC.</h1>");
                    }
                } catch (SQLException e) {
                    out.println("<h1>Failed to remove member from all records. Please try again later.</h1>");
                    e.printStackTrace();
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
                out.println("<a href='admin-dashboard.jsp'>Back to Dashboard</a>");
                out.println("</div>");
                out.println("</body></html>");
            }
        }
    }
}
