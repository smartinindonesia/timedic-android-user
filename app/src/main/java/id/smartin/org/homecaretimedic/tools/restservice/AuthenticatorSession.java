package id.smartin.org.homecaretimedic.tools.restservice;

import java.io.IOException;

import javax.annotation.Nullable;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Created by Hafid on 1/2/2018.
 */

public class AuthenticatorSession implements Authenticator {

    private String token;

    public AuthenticatorSession() {

    }

    public AuthenticatorSession(String token) {
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Nullable
    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        return response.request().newBuilder()
                .header("Authorization", token)
                .header("Cache-Control", "no-cache")
                .removeHeader("Accept-Encoding")
                .removeHeader("User-Agent")
                .removeHeader("Connection")
                .build();
    }
}
