package id.smartin.org.homecaretimedic.model.responsemodel;

import com.google.gson.annotations.SerializedName;

import id.smartin.org.homecaretimedic.model.User;

/**
 * Created by Hafid on 1/2/2018.
 */

public class LoginResponse {

    @SerializedName("user")
    private User user;
    @SerializedName("token")
    private String token;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "user=" + user +
                ", token='" + token + '\'' +
                '}';
    }
}
