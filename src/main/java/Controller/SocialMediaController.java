package Controller;

import Service.MessageServices;
import Service.AccountServices;
import Model.Account;
import Model.Message;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountServices accountServices;
    MessageServices messageServices;

    public SocialMediaController(){
        this.accountServices = new AccountServices();
        this.messageServices = new MessageServices();
    }
   
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::newUser);
        app.post("/login", this::userLogin);
        app.post("/messages", this::newMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessageById);
        app.delete("/messages/{message_id}", this::deleteMessageById);
        app.patch("/messages/{message_id}", this::updateMessageById);
        app.get("/accounts/{account_id}/messages", this::getMessagesByUser);

        return app;
    }

    /**
     * 
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     */
    private void newUser(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account newAccount = accountServices.addAccount(account);
        if(newAccount!=null){
            ctx.json(mapper.writeValueAsString(newAccount));
            ctx.status(200);
        }else{
            ctx.status(400);
        }
    }

    private void userLogin(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account logIn = accountServices.loginAccount(account);
        if(logIn!=null){
            ctx.json(mapper.writeValueAsString(logIn));
        }else{
            ctx.status(401);
        }
    }

    private void newMessage(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message newMessage = messageServices.addMessage(message);
        if(newMessage!=null){
            ctx.json(mapper.writeValueAsString(newMessage));
            ctx.status(200);
        }else{
            ctx.status(400);
        }
    }

    private void getAllMessages(Context ctx) {
        List<Message> messages = messageServices.getAllMessages();
        ctx.json(messages);
    }

    private void getMessageById(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageServices.getMessageByID(id);
        if(message != null) {
            ctx.json(mapper.writeValueAsString(message));
        }
        ctx.status(200);
    }

    private void deleteMessageById(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageServices.deleteMessage(id);
        if(message != null) {
            ctx.json(mapper.writeValueAsString(message));
        }
        ctx.status(200);
    }

    private void updateMessageById(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message newMessage = messageServices.updateMessage(id, message);
        if(newMessage != null) {
            ctx.json(mapper.writeValueAsString(newMessage));
            ctx.status(200);
        }else{
            ctx.status(400);
        }
    }

    private void getMessagesByUser(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int userId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageServices.getMessageByUser(userId);
        if(messages != null) {
            ctx.json(mapper.writeValueAsString(messages));
        }
        ctx.status(200);
    }


}