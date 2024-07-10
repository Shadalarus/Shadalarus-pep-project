package DAO;

import Util.ConnectionUtil;

import java.sql.*;
import Model.Account;


public class AccountDAO {
    
    public Account createAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            if(account.getPassword().length() < 4 || account.getUsername() == "" || AccountByUsername(account.getUsername()) != null){
                return null;
            }
                    String sql2 = "INSERT INTO account (username, password) VALUES (?, ?);";
                    PreparedStatement ps2 = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
                    ps2.setString(1, account.getUsername());
                    ps2.setString(2, account.getPassword());
                    ps2.executeUpdate();
                    ResultSet pkeyResultSet = ps2.getGeneratedKeys();
                    if(pkeyResultSet.next()){
                        int generated_account_id = (int) pkeyResultSet.getLong(1);
                        return new Account(generated_account_id, account.getUsername(), account.getPassword());
                    }              
            } catch(SQLException e){
                System.out.println(e.getMessage());
            }
        
        return null;

    }

    public Account loginAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection(); 
        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
            
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account AccountByUsername(String username) {
        Connection connection = ConnectionUtil.getConnection(); 
        try {
            String sql = "SELECT * FROM account WHERE username = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account AccountById(int userId) {
        Connection connection = ConnectionUtil.getConnection(); 
        try {
            String sql = "SELECT * FROM account WHERE account_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}
