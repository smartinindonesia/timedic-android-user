package id.smartin.org.homecaretimedic.tools.restservice;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Hafid on 1/2/2018.
 */

public class SessionRequestInterceptor implements Interceptor {
    public static final String TAG = "[SessionRequestInter]";
    private String token;

    public SessionRequestInterceptor() {

    }

    public SessionRequestInterceptor(String token) {
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Log.i(TAG, "Interceptor called " + request.headers().toString());
        Request newRequest = request.newBuilder()
                .header("Authorization", token)
                .header("Cache-Control", "no-cache")
                .removeHeader("Accept-Encoding")
                .removeHeader("User-Agent")
                .removeHeader("Connection")
                .build();
        Log.i(TAG, "Interceptor called -" + newRequest.headers().toString());
        return chain.proceed(newRequest);
    }
}
