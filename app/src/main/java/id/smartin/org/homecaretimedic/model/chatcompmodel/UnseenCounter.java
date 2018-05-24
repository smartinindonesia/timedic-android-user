package id.smartin.org.homecaretimedic.model.chatcompmodel;

/**
 * Created by Hafid on 21/05/2018.
 */

public class UnseenCounter {
    private String userId;
    private Integer unseenCount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getUnseenCount() {
        return unseenCount;
    }

    public void setUnseenCount(Integer unseenCount) {
        this.unseenCount = unseenCount;
    }
}
