
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class AddNewAdminServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sess = request.getSession(false);
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<head>");
        out.println("<title>Add New Admin</title>");
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
            String username = request.getParameter("username");
            String password = request.getParameter("password");


            try {
                AdminDAO admin = new AdminDAO();
                if (admin.AddNewAdmin(username, password)) {
                    out.println("<h1>New admin added successfully!</h1>");
                } else {
                    out.println("<h1>Failed to add admin. Please try again.</h1>");
                }
            } catch (SQLIntegrityConstraintViolationException e) {
                out.println("<h1>Error: Username already exists. Please choose another username.</h1>");
            } catch (SQLException e) {
                e.printStackTrace();
                out.println("<h1>Database error occurred. Please try again later.</h1>");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                out.println("MySQL Driver not found.");
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
