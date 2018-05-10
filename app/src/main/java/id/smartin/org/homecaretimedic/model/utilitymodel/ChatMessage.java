package id.smartin.org.homecaretimedic.model.utilitymodel;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Hafid on 4/18/2018.
 */

public class ChatMessage implements Serializable {

    private String messageText;
    private String receiver;
    private Long messageTime;

    public static final int STATUS_SENDING = 0;
    public static final int STATUS_SENT = 1;
    public static final int STATUS_FAILED = 2;
    public static final int STATUS_RECEIVED_BY_USER = 3;
    public static final int STATUS_READ_BY_USER = 4;

    public static final int MESSAGE_SENT = 1;
    public static final int MESSAGE_RECEIVED = 2;

    private int status = STATUS_SENDING;
    private String sender;

    public ChatMessage(String messageText, String sender, String receiver) {
        this.messageText = messageText;
        this.sender = sender;
        this.receiver = receiver;
        // Initialize to current time
        messageTime = new Date().getTime();
    }

    public ChatMessage(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Long messageTime) {
        this.messageTime = messageTime;
    }

}