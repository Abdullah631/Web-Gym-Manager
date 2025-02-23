import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class MemberLoginServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Start HTML response
        out.println("<html>");
        out.println("<head><title>Member Login</title></head>");
        out.println("<link rel='stylesheet' href='style.css'>");
        out.println("<body>");
        out.println("<div class='dashboard-container'>");
        try {
            AdminDAO admin = new AdminDAO();
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            int num = admin.login(username,password,"Member");
            if (num == 0) {
                session.setAttribute("usertype", "Member");
                session.setAttribute("username", username);
                RequestDispatcher dispatcher = request.getRequestDispatcher("User_dashboard.jsp");
                dispatcher.forward(request, response);
            } else if (num == 1) {
                out.println("<h1>Incorrect username or password.</h1>");
            } else {
                out.println("<h1>No user found with the provided username.</h1>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<h1>Database Error: " + e.getMessage() + "</h1>");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h1>Unexpected Error: " + e.getMessage() + "</h1>");
        } finally {
            out.println("<a href='Member_login.jsp'>Back to Login</a>");
            out.println("</div>");
            out.println("</body></html>");
        }
    }
}
