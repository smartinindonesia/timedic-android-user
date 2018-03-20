package id.smartin.org.homecaretimedic.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import id.smartin.org.homecaretimedic.LoginActivity;
import id.smartin.org.homecaretimedic.model.User;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by Hafid on 9/10/2017.
 */

public class HomecareSessionManager extends SessionManager {

    public static String KEY_PREFERENCES_NAME = "homecare_pref_name";
    public static String KEY_USER_INFOS_JSON = "user_json_infos";
    public static String KEY_USER_TOKEN = "user_token";
    public static String KEY_IS_LOGIN = "isLogin";

    public HomecareSessionManager(Context context) {
        super(context, KEY_PREFERENCES_NAME);
    }

    public HomecareSessionManager(Activity activity, Context context) {
        super(activity, context, KEY_PREFERENCES_NAME);
    }

    public void createLoginSession(User user, String token) {
        super.createLoginSession(user, KEY_USER_INFOS_JSON, KEY_IS_LOGIN, KEY_USER_TOKEN, token);
    }

    public void updateProfile(User user){
        super.updateUserInfo(user, KEY_USER_INFOS_JSON);
    }

    public User getUserDetail() {
        return super.getUserDetail(KEY_USER_INFOS_JSON);
    }

    public String getToken() {
        return super.getToken(KEY_USER_TOKEN);
    }

    public boolean isLogin() {
        return super.isLogin(KEY_IS_LOGIN);
    }

    public void clearToken() {
        super.clearToken(KEY_USER_TOKEN);
    }

    public void hasToken() {
        super.hasToken(KEY_USER_TOKEN);
    }

    public void logout() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
        super.logout(KEY_IS_LOGIN);
    }
}
