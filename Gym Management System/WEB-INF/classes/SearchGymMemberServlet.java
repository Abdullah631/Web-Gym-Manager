import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class SearchGymMemberServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sess = request.getSession(false);
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html>");
        out.println("<head><title>Search Gym Member</title></head>");
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
                AdminDAO admin=new AdminDAO();
                GymMember g=new GymMember();
                boolean var=admin.searchMember(cnic, g);
                if (var) {
                    out.println("<h1>Member Details</h1>");
                    out.println("<p><strong>CNIC:</strong> " + g.cnic + "</p>");
                    out.println("<p><strong>Name:</strong> " + g.name + "</p>");
                    out.println("<p><strong>Age:</strong> " + g.age + "</p>");
                    out.println("<p><strong>Email:</strong> " + g.email + "</p>");
                    out.println("<p><strong>Phone Number:</strong> " + g.phoneNumber + "</p>");
                    out.println("<p><strong>Membership Type:</strong> " + g.membershipType + "</p>");
                    out.println("<p><strong>Start Date:</strong> " + g.startDate + "</p>");
                } else {
                    out.println("<h1>No member found with this CNIC.</h1>");
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
