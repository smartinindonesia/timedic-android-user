package id.pptik.org.homecaretimedic.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import id.pptik.org.homecaretimedic.model.User;

/**
 * Created by Hafid on 9/11/2017.
 */

public class RekmedSessionManager extends SessionManager{
    public static String TAG = "[RekmedSessionManager]";

    public static String KEY_PREFERENCES_NAME = "rekmed_pref_name";
    public static String KEY_USER_INFOS_JSON = "user_json_infos";
    public static String KEY_IS_LOGIN = "isLogin";

    public RekmedSessionManager(Activity activity, Context context){
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
        super.logout(KEY_IS_LOGIN);
    }
}
