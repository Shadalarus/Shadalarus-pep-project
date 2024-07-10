package DAO;

import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Model.Message;

public class MessageDAO {

    public Message createMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            AccountDAO doesItExist = new AccountDAO();
            if(message.getMessage_text().length() > 255 || doesItExist.AccountById(message.getPosted_by()) == null || message.getMessage_text() == ""){
                return null;
            }
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());
            ps.executeUpdate();
            ResultSet pkeyResultSet = ps.getGeneratedKeys();
                    if(pkeyResultSet.next()){
                        int generated_message_id = (int) pkeyResultSet.getLong(1);
                        return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
                    }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>(); 
        try {
            String sql = "SELECT * FROM message;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessageByID(int messageID) {
        Connection connection = ConnectionUtil.getConnection();
        Message message = new Message(); 
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, messageID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                return message;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessage(int messageID) {
        Connection connection = ConnectionUtil.getConnection();
        Message message = new Message(); 
        try {
            message = getMessageByID(messageID);
            String sql = "DELETE FROM message WHERE message_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, messageID);
            ps.executeUpdate(sql);
            
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return message;
    }

    public Message updateMessage(int messageID, Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            MessageDAO messageDAO = new MessageDAO();
            if(message.getMessage_text().length() > 255 || messageDAO.getMessageByID(messageID) == null || message.getMessage_text() == ""){
                return null;
            }
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, message.getMessage_text());
            ps.setInt(2, messageID);
            ps.executeUpdate();
            return new Message(messageID, messageDAO.getMessageByID(messageID).getPosted_by(), message.getMessage_text(), messageDAO.getMessageByID(messageID).getTime_posted_epoch() );
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getMessageByUser(int userID) {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>(); 
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
                return messages;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

}
