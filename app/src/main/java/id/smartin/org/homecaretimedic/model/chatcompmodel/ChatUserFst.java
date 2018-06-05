package id.smartin.org.homecaretimedic.model.chatcompmodel;

import java.io.Serializable;

/**
 * Created by Hafid on 21/05/2018.
 */

public class ChatUserFst implements Serializable{
    private String nickname;
    private String id;
    private Boolean online;
    private String userType;

    public ChatUserFst() {
    }

    public ChatUserFst(String nickname, String id, Boolean online, String userType) {
        this.nickname = nickname;
        this.id = id;
        this.online = online;
        this.userType = userType;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getId() == ((ChatUserFst)obj).getId();
    }
}
