package id.pptik.org.homecaretimedic.tools;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.entity.mime.Header;
import id.pptik.org.homecaretimedic.config.Constants;

/**
 * Created by Hafid on 9/24/2017.
 */

public class HomecareRestClient {
    public static final String TAG = "[HomecareRestClient]";
    private static AsyncHttpClient client;

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client = new AsyncHttpClient();
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void get(String url, String token, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client = new AsyncHttpClient();
        client.addHeader("Authorization", "Bearer "+token);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client = new AsyncHttpClient();
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, String token, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client = new AsyncHttpClient();
        client.addHeader("Authorization", "Bearer "+token);
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return Constants.BASE_URL + relativeUrl;
    }
}
