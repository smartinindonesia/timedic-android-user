package id.smartin.org.homecaretimedic.model.chatcompmodel;

import java.util.HashMap;

/**
 * Created by Hafid on 17/05/2018.
 */

public class ChatUserRef extends ChatUserFst{
    private String profileUrl;
    private String email;
    private HashMap<String, ConnectedWith> connectedWithChat;

    public ChatUserRef() {
    }

    public ChatUserRef(ChatUserRef user) {
        super(user.getNickname(), user.getId(), user.getOnline(), user.getUserType());
        this.profileUrl = user.getProfileUrl();
        this.email = user.getEmail();
        this.connectedWithChat = user.getConnectedWithChat();
    }

    public ChatUserRef(String nickname, String profileUrl, String id, String username, String email, Boolean online, String userType, HashMap<String, ConnectedWith> connectedWithId) {
        this.profileUrl = profileUrl;
        this.email = email;
        this.connectedWithChat = connectedWithId;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HashMap<String, ConnectedWith> getConnectedWithChat() {
        return connectedWithChat;
    }

    public void setConnectedWithChat(HashMap<String, ConnectedWith> connectedWithChat) {
        this.connectedWithChat = connectedWithChat;
    }

}
