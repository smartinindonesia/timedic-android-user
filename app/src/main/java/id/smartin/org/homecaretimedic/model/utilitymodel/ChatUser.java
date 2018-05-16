package id.smartin.org.homecaretimedic.model.utilitymodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

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
    public HashMap<String, ChatBuddy> connectedWithId;

    public ChatUser() {
    }

    public ChatUser(ChatUser user){
        this.nickname = user.getNickname();
        this.profileUrl = user.getProfileUrl();
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getUsername();
        this.online = user.isOnline();
        this.userType = user.getUserType();
        this.room = user.getRoom();
        this.connectedWithId = user.getConnectedWithId();
    }

    public ChatUser(String nickname, String profileUrl, String id, String username, String email, Boolean online, String userType, ArrayList<String> room, HashMap<String, ChatBuddy> connectedWithId) {
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.id = id;
        this.username = username;
        this.email = email;
        this.online = online;
        this.userType = userType;
        this.room = room;
        this.connectedWithId = connectedWithId;
    }

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

    public HashMap<String, ChatBuddy> getConnectedWithId() {
        return connectedWithId;
    }

    public void setConnectedWithId(HashMap<String, ChatBuddy> connectedWithId) {
        this.connectedWithId = connectedWithId;
    }
}