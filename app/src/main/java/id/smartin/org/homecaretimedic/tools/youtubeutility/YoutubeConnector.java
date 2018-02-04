package id.smartin.org.homecaretimedic.tools.youtubeutility;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.common.io.BaseEncoding;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import id.smartin.org.homecaretimedic.config.Constants;

/**
 * Created by Hafid on 12/24/2017.
 */

public class YoutubeConnector {
    public static final String TAG = "[YoutubeConnector]";
    private YouTube youtube;
    private YouTube.Search.List query;
    private Context context;

    public YoutubeConnector(final Context context) {
        this.context = context;
        youtube = new YouTube.Builder(new NetHttpTransport(),
                new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest hr) throws IOException {
                String packageName = context.getPackageName();
                String SHA1 = getSHA1(packageName);

                hr.getHeaders().set("X-Android-Package", packageName);
                hr.getHeaders().set("X-Android-Cert", SHA1);
            }
        }).setApplicationName(Constants.YOUTUBE_PROJECT_NAME).build();

        /*
        try {
            query = youtube.search().list("id,snippet");
            query.setKey(Constants.YOUTUBE_API_KEY);
            query.setType("video");
            query.setFields("items(id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url)");
        } catch (IOException e) {
            Log.d("YC", "Could not initialize: " + e);
        }
        */
    }

    public List<Video> getListByChannelId(String channelId) {
        List<Video> items = new ArrayList<Video>();
        try {
            query = youtube.search().list("id,snippet");
            query.setKey(Constants.YOUTUBE_API_KEY);
            query.setType("video");
            query.setFields("items(id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url)");
            query.setChannelId(channelId);
            SearchListResponse response = query.execute();
            List<SearchResult> results = response.getItems();
            String resultId = idVideos(results);
            VideoListResponse responseVid = youtube.videos().list("snippet, contentDetails, statistics").setId(resultId).execute();
            Log.i(TAG, responseVid.toString());
            for (int i = 0; i < responseVid.getItems().size(); i++) {
                Log.i(TAG, "ITEM " + items.get(i).getSnippet().getTitle());
                items.add(responseVid.getItems().get(i));
            }
            return items;
        } catch (IOException e) {
            Log.d("YC", "Could not search: " + e);
            return items;
        }
    }

    private String idVideos(List<SearchResult> results) {
        String id = "";
        for (int i = 0; i < results.size(); i++) {
            SearchResult result = results.get(i);
            if (i < results.size() - 1) {
                id = id + result.getId().getVideoId() + ",";
            } else {
                id = id + result.getId().getVideoId();
            }
        }
        Log.i(TAG, id);
        return id;
    }

    private String getSHA1(String packageName) {
        try {
            Signature[] signatures = this.context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES).signatures;
            for (Signature signature : signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA-1");
                md.update(signature.toByteArray());
                return BaseEncoding.base16().encode(md.digest());
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
