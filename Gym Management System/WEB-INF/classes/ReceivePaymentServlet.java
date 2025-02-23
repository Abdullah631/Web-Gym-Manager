
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class ReceivePaymentServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sess = request.getSession(false);
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html>");
        out.println("<head><title>Receive Current Month Payment</title></head>");
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
                AdminDAO admin = new AdminDAO();

                if (admin.receivePayment(cnic)) {
                    out.println("<h1>Payment received successfully!</h1>");
                } else {
                    out.println("<h1>Failed to receive payment. Please try again later.</h1>");
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
