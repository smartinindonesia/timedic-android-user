package id.smartin.org.homecaretimedic.model.utilitymodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Hafid on 09/05/2018.
 */

public class ChatUser implements Serializable{
    private String nickname;
    private String profileUrl;
    public String id;
    public String username;
    public String email;
    public Boolean online;
    public String userType;
    public ArrayList<String> room;
    public ArrayList<String> connectedWithId;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public ArrayList<String> getRoom() {
        return room;
    }

    public void setRoom(ArrayList<String> room) {
        this.room = room;
    }

    public boolean isOnline(){
        return online;
    }

    public ArrayList<String> getConnectedWithId() {
        return connectedWithId;
    }

    public void setConnectedWithId(ArrayList<String> connectedWithId) {
        this.connectedWithId = connectedWithId;
    }
}