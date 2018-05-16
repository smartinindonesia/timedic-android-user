package id.smartin.org.homecaretimedic.model.utilitymodel;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

/**
 * Created by Hafid on 11/05/2018.
 */

public class ChatUserStats extends ChatUser implements Serializable{
    private int newMessagesCount;
    public ValueEventListener eventListener;
    public DatabaseReference fdb;

    public ChatUserStats(ChatUser user, int newMessagesCount) {
        super(user);
        this.newMessagesCount = newMessagesCount;
    }

    public Integer getNewMessagesCount() {
        return newMessagesCount;
    }

    public void setNewMessagesCount(int newMessagesCount) {
        this.newMessagesCount = newMessagesCount;
    }

    public void setParent(ChatUser chatUser){
    }
}
