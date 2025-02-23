
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class AdminLogoutServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
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
            request.getSession().invalidate();
            response.sendRedirect("Admin_login.jsp");
        }
    }
}
