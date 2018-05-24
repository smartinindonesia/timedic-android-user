package id.smartin.org.homecaretimedic.model.chatcompmodel;

import java.util.List;

/**
 * Created by Hafid on 17/05/2018.
 */

public class ThreadIDProperty {
    private Long createdAt;
    private String createdByUserId;
    private String threadId;
    private ChatUserFst appUser;
    private ChatUserFst adminChat;
    private String threadName;
    private String threadType;// (public/private)
    private ChatID lastChatId;// (id for last chat, use this to lookup in messageThread)

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(String createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getThreadType() {
        return threadType;
    }

    public void setThreadType(String threadType) {
        this.threadType = threadType;
    }

    public ChatID getLastChatId() {
        return lastChatId;
    }

    public void setLastChatId(ChatID lastChatId) {
        this.lastChatId = lastChatId;
    }

    public ChatUserFst getAppUser() {
        return appUser;
    }

    public void setAppUser(ChatUserFst appUser) {
        this.appUser = appUser;
    }

    public ChatUserFst getAdminChat() {
        return adminChat;
    }

    public void setAdminChat(ChatUserFst adminChat) {
        this.adminChat = adminChat;
    }
}
