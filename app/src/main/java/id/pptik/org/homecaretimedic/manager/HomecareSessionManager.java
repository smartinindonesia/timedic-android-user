package id.pptik.org.homecaretimedic.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import id.pptik.org.homecaretimedic.LoginActivity;
import id.pptik.org.homecaretimedic.model.User;

/**
 * Created by Hafid on 9/10/2017.
 */

public class HomecareSessionManager extends SessionManager{

    public static String KEY_PREFERENCES_NAME = "homecare_pref_name";
    public static String KEY_USER_INFOS_JSON = "user_json_infos";
    public static String KEY_IS_LOGIN = "isLogin";

    public HomecareSessionManager(Activity activity, Context context){
        super(activity, context, KEY_PREFERENCES_NAME);
    }

    public void createLoginSession(User user){
        super.createLoginSession(user, KEY_USER_INFOS_JSON, KEY_IS_LOGIN);
    }

    public User getUserDetail(){
        return super.getUserDetail(KEY_USER_INFOS_JSON);
    }

    public boolean isLogin(){
        return super.isLogin(KEY_IS_LOGIN);
    }

    public void logout(){
        Intent intent = new Intent(getContext(), LoginActivity.class);
        getContext().startActivity(intent);
        super.logout(KEY_IS_LOGIN);
    }
}
