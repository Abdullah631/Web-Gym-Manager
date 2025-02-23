import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class UpdatePaymentServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sess = request.getSession(false);
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html>");
        out.println("<head><title>Update Monthly Payments</title></head>");
        out.println("<link rel='stylesheet' href='style.css'>");
        out.println("<body>");
        out.println("<div class='dashboard-container'>");
        if (sess == null || !"Admin".equals(sess.getAttribute("usertype"))) {
            out.println("<h1>Access Denied</h1>");
            out.println("<p>You are not authorized to access this page.</p>");
            out.println("<a href='Admin_login.jsp'>Go to Login</a>");
            return;
        } else {
            try {
                AdminDAO admin =new  AdminDAO();
                if (admin.updatePayment()) {
                    out.println("<h1>Payment For this Month is successfully reset!</h1>");
                } else {
                    out.println("<h1>Failed to update payments(No Member in Gym).</h1>");
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
