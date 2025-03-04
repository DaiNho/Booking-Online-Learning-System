package dal;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import models.Account;

public class SignUpDAO {

    private Connection con;

    public SignUpDAO() {
        try {
            con = new DBContext().connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean signUp(Account account) {
        if (isDuplicateAccount(account.getUserName())) {
            return false; 
        }
//         MD_5 md5 = new MD_5();
//        String hashedPassword = md5.getMd5(account.getPassword());

        
       String sql = "INSERT INTO Accounts ([user_name], gmail, full_name, pass_word, dob, sex, address, phone, role_id, status_id, avatar) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, account.getUserName());
            statement.setString(2, account.getGmail());
            statement.setString(3, account.getFullName());
            statement.setString(4, account.getPassword());
            statement.setDate(5, account.getDob());
            statement.setBoolean(6, account.isSex());
            statement.setString(7, account.getAddress());
            statement.setString(8, account.getPhone());
            statement.setInt(9, account.getRoleId());
            statement.setInt(10, account.getStatusId());
            statement.setString(11, account.getAvatar());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean insertMentee(String account) {
       String sql = "INSERT INTO Mentees (mentee_name) VALUES (?)";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, account);
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
  
    public boolean insertMenter(String account) {
       String sql = "INSERT INTO Mentors (mentor_name, rate) VALUES (?, 0)";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, account);
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
  
    public boolean isDuplicateAccount( String userName) {
        String query = "SELECT COUNT(*) FROM Accounts Where user_name = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, userName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);

                    return count > 0; 
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false; 
    }
    public static void main(String[] args) {
        
    
    Account testAccount = new Account();
    testAccount.setUserName("testusers");
    testAccount.setGmail("vuvinhpc1235@gmail.com");
    testAccount.setFullName("Test User");
    testAccount.setPassword("testpassword");
    testAccount.setDob(Date.valueOf("2000-01-01"));
    testAccount.setSex(true);
    testAccount.setAddress("123 Test Street");
    testAccount.setPhone("123456789");
    testAccount.setRoleId(1); 
    testAccount.setStatusId(1); 
   SignUpDAO s = new SignUpDAO();
    boolean success = s.signUp(testAccount);

    if (success) {
        System.out.println("Thêm tài khoản thành công.");
    } else {
        System.out.println("Thêm tài khoản thất bại.");
    }
}
}
