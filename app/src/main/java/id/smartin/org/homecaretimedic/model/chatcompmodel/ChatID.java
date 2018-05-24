package id.smartin.org.homecaretimedic.model.chatcompmodel;

/**
 * Created by Hafid on 17/05/2018.
 */

public class ChatID {
    private String chatKey;
    private String sender;
    private String chatMessage;
    private Long chatTimeStamp;

    public static final int STATUS_SENDING = 0;
    public static final int STATUS_SENT = 1;
    public static final int STATUS_FAILED = 2;
    public static final int STATUS_RECEIVED_BY_USER = 3;
    public static final int STATUS_READ_BY_USER = 4;

    public static final int MESSAGE_SENT = 1;
    public static final int MESSAGE_RECEIVED = 2;

    private int status = STATUS_SENDING;

    public ChatID() {
    }

    public ChatID(String chatKey) {
        this.chatKey = chatKey;
    }

    public String getChatKey() {
        return chatKey;
    }

    public void setChatKey(String chatKey) {
        this.chatKey = chatKey;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    public Long getChatTimeStamp() {
        return chatTimeStamp;
    }

    public void setChatTimeStamp(Long chatTimeStamp) {
        this.chatTimeStamp = chatTimeStamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
