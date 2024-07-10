package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageServices {
    private MessageDAO messageDAO;

    public MessageServices(){
        messageDAO = new MessageDAO();
    }

    public MessageServices(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }
    
    public Message addMessage(Message message) {
        return messageDAO.createMessage(message);
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageByID(int messageID){
        return messageDAO.getMessageByID(messageID);
    }

    public Message deleteMessage(int messageID){
        return messageDAO.deleteMessage(messageID);
    }

    public Message updateMessage(int messageID, Message message){
        return messageDAO.updateMessage(messageID, message);
    }

    public List<Message> getMessageByUser(int userID){
        return messageDAO.getMessageByUser(userID);
    }

}