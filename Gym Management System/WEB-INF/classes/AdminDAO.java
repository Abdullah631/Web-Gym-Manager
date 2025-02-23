
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.Random;

public class AdminDAO {

    Connection conn;
    String newPass = null;

    public AdminDAO() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://127.0.0.1/gym";
        conn = DriverManager.getConnection(url, "root", "root");
    }

    boolean AddGymMember(String cnic, String name, int age, String email, String phone_number, String membership_type, String start_date) throws SQLIntegrityConstraintViolationException, Exception {
        String insertMemberSql = "INSERT INTO gym_members (cnic, name, age, email, phone_number, membership_type, start_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(insertMemberSql);
        int success;
        stmt.setString(1, cnic);
        stmt.setString(2, name);
        stmt.setInt(3, age);
        stmt.setString(4, email);
        stmt.setString(5, phone_number);
        stmt.setString(6, membership_type);
        stmt.setDate(7, Date.valueOf(start_date));

        int rowsInserted = stmt.executeUpdate();
        if (rowsInserted > 0) {

            // Generate random password
            newPass = generateRandomPassword();

            // Insert member login credentials
            String insertLoginSql = "INSERT INTO members_login_credentials (username, password) VALUES (?, ?)";
            PreparedStatement loginStmt = conn.prepareStatement(insertLoginSql);
            loginStmt.setString(1, cnic);  // Use CNIC as the username
            loginStmt.setString(2, newPass);  // Set generated password
            int credTable = loginStmt.executeUpdate();
            if (credTable > 0) {
                // Successfully added member, now add to payment table
                String insertPaymentSql = "INSERT INTO payment (cnic, is_paid) VALUES (?, 1)";
                PreparedStatement paymentStmt = conn.prepareStatement(insertPaymentSql);
                paymentStmt.setString(1, cnic);
                success = paymentStmt.executeUpdate();
                return success > 0;
            }
        }

        return false;

    }

    public int removeGymMember(String cnic) throws SQLIntegrityConstraintViolationException, Exception {
        conn.setAutoCommit(false);
        try {
            String checkMemberSql = "SELECT * FROM gym_members WHERE cnic = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkMemberSql)) {
                checkStmt.setString(1, cnic);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (!rs.next()) {
                        return 1; 
                    }
                }
            }
    
            String deletePaymentSql = "DELETE FROM payment WHERE cnic = ?";
            try (PreparedStatement deletePaymentStmt = conn.prepareStatement(deletePaymentSql)) {
                deletePaymentStmt.setString(1, cnic);
                int rowsDeleted = deletePaymentStmt.executeUpdate();
                if (rowsDeleted == 0) {
                    conn.rollback();
                    return 2; 
                }
            }
    
            String deleteCredentialsSql = "DELETE FROM members_login_credentials WHERE username = ?";
            try (PreparedStatement deleteCredentialsStmt = conn.prepareStatement(deleteCredentialsSql)) {
                deleteCredentialsStmt.setString(1, cnic);
                int del = deleteCredentialsStmt.executeUpdate();
                if (del == 0) {
                    conn.rollback();
                    return 2; 
                }
            }
    
            String deleteMemberSql = "DELETE FROM gym_members WHERE cnic = ?";
            try (PreparedStatement deleteMemberStmt = conn.prepareStatement(deleteMemberSql)) {
                deleteMemberStmt.setString(1, cnic);
                int success = deleteMemberStmt.executeUpdate();
                if (success == 0) {
                    conn.rollback();
                    return 2; 
                }
            }
    
            conn.commit();
            return 0; 
    
        } catch (SQLException e) {
            conn.rollback();
            throw e; 
        } finally {
            conn.setAutoCommit(true); 
        }
    }
    
    

    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 8; i++) {  
            int index = random.nextInt(chars.length());
            password.append(chars.charAt(index));
        }
        return password.toString();
    }

    public Boolean AddNewAdmin(String uName, String pass) throws SQLIntegrityConstraintViolationException, Exception {

        String sql = "INSERT INTO admin_users (username, password) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, uName);
        stmt.setString(2, pass);
        int rowsInserted = stmt.executeUpdate();
        return rowsInserted > 0;
    }

    public int login(String uName, String pass, String userType) throws SQLIntegrityConstraintViolationException, Exception {
        String query;
        if (userType.equals("Admin")) {
            query = "SELECT * FROM admin_users WHERE username = ?";
        } else {
            query = "SELECT * FROM members_login_credentials WHERE username = ?";
        }
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, uName);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String dbUsername = rs.getString("username");
            String dbPassword = rs.getString("password");

            
            if (uName.equals(dbUsername) && pass.equals(dbPassword)) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 2;
        }
    }

    public int changePassword(String uName, String cPass, String nPass, String userType) throws SQLIntegrityConstraintViolationException, ClassNotFoundException, SQLException {
        String query;
        String sqlUpdate;
        if (userType.equals("Admin")) {
            query = "SELECT password FROM admin_users WHERE username = ?";
            sqlUpdate = "UPDATE admin_users SET password = ? WHERE username = ?";
        } else {
            query = "SELECT password FROM members_login_credentials WHERE username = ?";
            sqlUpdate = "UPDATE members_login_credentials SET password = ? WHERE username = ?";
        }
        PreparedStatement stmtSelect = conn.prepareStatement(query);
        stmtSelect.setString(1, uName);

        ResultSet rs = stmtSelect.executeQuery();
        if (rs.next()) {
            String dbPassword = rs.getString("password");
            if (!dbPassword.equals(cPass)) {
                return 0;
            }
        } else if (!rs.next()) {
            return 1;
        }
        PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate);
        stmtUpdate.setString(1, nPass);
        stmtUpdate.setString(2, uName);

        int rowsUpdated = stmtUpdate.executeUpdate();
        if (rowsUpdated > 0) {
            return 2;
        } else {
            return 3;
        }
    }

    public int checkPayment(String cnic) throws SQLIntegrityConstraintViolationException, ClassNotFoundException, SQLException {
        String checkMemberSql = "SELECT * FROM payment WHERE cnic = ?";
        PreparedStatement checkMemberStmt = conn.prepareStatement(checkMemberSql);
        checkMemberStmt.setString(1, cnic);
        ResultSet paymentResult = checkMemberStmt.executeQuery();
        if (paymentResult.next()) {
            int isPaid = paymentResult.getInt("is_paid");
            if (isPaid == 1) {
                return 0;
            } else if (isPaid == 0) {
                return 1;
            }
        }
        return 2;

    }

    public boolean receivePayment(String cnic) throws SQLIntegrityConstraintViolationException, ClassNotFoundException, SQLException {
        String updatePaymentSql = "UPDATE payment SET is_paid = 1 WHERE cnic = ?";
        PreparedStatement updateStmt = conn.prepareStatement(updatePaymentSql);
        updateStmt.setString(1, cnic);
        int rowsUpdated = updateStmt.executeUpdate();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updatePayment() throws SQLIntegrityConstraintViolationException, ClassNotFoundException, SQLException {
        String updateIsPaidSql = "UPDATE payment SET is_paid = 0";
        PreparedStatement notPaidStmt = conn.prepareStatement(updateIsPaidSql);
        int rowsUpdated = notPaidStmt.executeUpdate();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean searchMember(String cnic, GymMember g) throws SQLIntegrityConstraintViolationException, ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM gym_members WHERE cnic = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, cnic);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            g.cnic = rs.getString("cnic");
            g.name = rs.getString("name");
            g.age = rs.getInt("age");
            g.email = rs.getString("email");
            g.phoneNumber = rs.getString("phone_number");
            g.membershipType = rs.getString("membership_type");
            g.startDate = rs.getString("start_date");
            return true;
        } else {
            return false;
        }
    }

    /*public static void main(String[] args) {
        
        try {
            System.out.println("before object ");
            AdminDAO ad=new AdminDAO();
            System.out.println("After object ");
            ad.changePassword("admin","1234","1233","Admin");
            System.out.println("After call");

        } catch (Exception e) {
            System.out.println("Error");
        }
    }*/
    public void close() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

}
