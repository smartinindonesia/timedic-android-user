package id.smartin.org.homecaretimedic.model.utilitymodel;

import java.io.Serializable;

/**
 * Created by Hafid on 11/05/2018.
 */

public class ChatBuddy implements Serializable {
    private String id;

    public ChatBuddy() {
    }

    public ChatBuddy(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
