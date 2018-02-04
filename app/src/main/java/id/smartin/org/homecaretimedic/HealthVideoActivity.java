package id.smartin.org.homecaretimedic;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.google.api.services.youtube.model.Video;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import id.smartin.org.homecaretimedic.adapter.HealthVideoAdapter;
import id.smartin.org.homecaretimedic.config.Constants;
import id.smartin.org.homecaretimedic.customuicompt.GridSpacingItemDecoration;
import id.smartin.org.homecaretimedic.customuicompt.RecyclerTouchListener;
import id.smartin.org.homecaretimedic.model.HealthVideo;
import id.smartin.org.homecaretimedic.tools.youtubeutility.YoutubeConnector;

public class HealthVideoActivity extends AppCompatActivity {

    public static String TAG = "[HealthVideoActivity]";
    private HealthVideoAdapter healthVideoAdapter;
    private List<Video> videoList = new ArrayList<>();

    @BindView(R.id.video_list)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private SweetAlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_video);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        //createHealthVideo();
        healthVideoAdapter = new HealthVideoAdapter(this, videoList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, GridSpacingItemDecoration.dpToPx(10), true, 0));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(healthVideoAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Video video = videoList.get(position);
                Intent intent = new Intent(HealthVideoActivity.this, VideoPlayerActivity.class);
                intent.putExtra("video_obj", new HealthVideo(video));
                startActivity(intent);
                Toast.makeText(getApplicationContext(), video.getSnippet().getTitle(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        healthVideoAdapter.notifyDataSetChanged();
        initProgressDialog();
        new DownloadListVideo().execute();
    }


    private class DownloadListVideo extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            YoutubeConnector yc = new YoutubeConnector(HealthVideoActivity.this);
            videoList.clear();
            List<Video> hv = yc.getListByChannelId(Constants.YOUTUBE_CHANNEL);
            for(int i = 0; i < hv.size(); i++){
                videoList.add(hv.get(i));
                Log.i(TAG, "LiVid! "+videoList.get(i).toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            healthVideoAdapter.notifyDataSetChanged();
        }
    }

    public void initProgressDialog(){
        progressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDialog.setTitleText("Loading...");
        progressDialog.setContentText("Sedang menunggu daftar video");
        progressDialog.setCanceledOnTouchOutside(true);
    }

}
