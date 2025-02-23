
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class CheckPaymentServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

                HttpSession sess = request.getSession(false);
                PrintWriter out = response.getWriter();
                response.setContentType("text/html");
                out.println("<head>");
                out.println("<title>Check Member Payment Status</title>");
                out.println("<link rel='stylesheet' href='style.css'>");
                out.println("</head>");
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
                int num = admin.checkPayment(cnic);
                if (num == 0) {
                    out.println("<h1>Payment for this month has already been received.</h1>");
                } else if (num == 1) {
                    out.println("<h1>Payment for this month has not been received.</h1>");
                    out.println("<form action='ReceivePaymentServlet' method='POST'>");
                    out.println("<input type='hidden' name='cnic' value='" + cnic + "'>");
                    out.println("<button type='submit'>Receive Payment</button>");
                    out.println("</form>");
                } else {
                    out.println("<h1>No payment record found. Please add payment details first.</h1>");
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
                out.println("</body>");
                out.println("</html>");
            }
        }
    }
}

