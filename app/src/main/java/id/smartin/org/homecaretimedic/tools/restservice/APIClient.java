package id.smartin.org.homecaretimedic.tools.restservice;

import android.content.Context;
import android.util.Log;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;

import javax.annotation.Nullable;

import id.smartin.org.homecaretimedic.config.Constants;
import id.smartin.org.homecaretimedic.manager.HomecareSessionManager;
import okhttp3.Authenticator;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hafid on 1/2/2018.
 */

public class APIClient {
    public static final String TAG = "[APIClient]";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }


    public static Retrofit getClientWithToken(HomecareSessionManager sessionManager, Context context) {
        CookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        HttpLoggingInterceptor loggingHeader = new HttpLoggingInterceptor();
        HttpLoggingInterceptor loggingBody = new HttpLoggingInterceptor();
        loggingHeader.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        loggingBody.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .addInterceptor(new SessionRequestInterceptor(sessionManager))
                .authenticator(new AuthenticatorSession(sessionManager))
                .addInterceptor(loggingHeader)
                .addInterceptor(loggingBody)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }
}