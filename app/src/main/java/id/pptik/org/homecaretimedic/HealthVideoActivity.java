package id.pptik.org.homecaretimedic;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.pptik.org.homecaretimedic.adapter.HealthVideoAdapter;
import id.pptik.org.homecaretimedic.customuicompt.RecyclerTouchListener;
import id.pptik.org.homecaretimedic.model.HealthVideo;

public class HealthVideoActivity extends AppCompatActivity {

    public static String TAG = "[HealthVideoActivity]";
    private HealthVideoAdapter healthVideoAdapter;
    private List<HealthVideo> videoList = new ArrayList<>();

    @BindView(R.id.video_list)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_video);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        createHealthVideo();
        healthVideoAdapter = new HealthVideoAdapter(getApplicationContext(), videoList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(healthVideoAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                HealthVideo video = videoList.get(position);
                Toast.makeText(getApplicationContext(), video.getJudulVideo(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        healthVideoAdapter.notifyDataSetChanged();
    }

    private void createHealthVideo(){
        HealthVideo vid1 = new HealthVideo("Gizi Seimbang","", "Gizi Seimbang");
        HealthVideo vid2 = new HealthVideo("Cara Hidup Sehat","","Cara Hidup Sehat");
        videoList.add(vid1);
        videoList.add(vid2);
    }
}
