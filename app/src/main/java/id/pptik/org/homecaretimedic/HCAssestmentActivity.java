package id.pptik.org.homecaretimedic;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import id.pptik.org.homecaretimedic.adapter.AssestmentAdapter;
import id.pptik.org.homecaretimedic.config.Constants;
import id.pptik.org.homecaretimedic.manager.HomecareSessionManager;
import id.pptik.org.homecaretimedic.model.Assestment;
import id.pptik.org.homecaretimedic.tools.HomecareRestClient;

public class HCAssestmentActivity extends AppCompatActivity {
    public static String TAG = "[HCAssestmentActivity]";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.assessment_list)
    RecyclerView recyclerView;

    HomecareSessionManager homecareSessionManager;
    List<Assestment> assestmentList = new ArrayList<>();
    AssestmentAdapter assestmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hcassestment);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent acceptanceIntent = new Intent(HCAssestmentActivity.this, AcceptanceActivity.class);
                startActivity(acceptanceIntent);
                finish();
            }
        });
        createTitleBar();
        homecareSessionManager = new HomecareSessionManager(this, getApplicationContext());
        assestmentAdapter = new AssestmentAdapter(this,assestmentList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(assestmentAdapter);
        populateAssestment();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void createTitleBar() {
        ActionBar mActionbar = getSupportActionBar();
        mActionbar.setDisplayHomeAsUpEnabled(false);
        mActionbar.setDefaultDisplayHomeAsUpEnabled(false);
        mActionbar.setDisplayShowTitleEnabled(false);
        mActionbar.setDisplayShowHomeEnabled(false);
        mActionbar.setDisplayShowCustomEnabled(true);
        View view = getLayoutInflater().inflate(R.layout.action_bar_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        mActionbar.setCustomView(view, params);
    }

    public void populateAssestment() {
        assestmentList.clear();
        Assestment assestment = new Assestment();
        assestment.setId(2);
        assestment.setAssestmentType(new Assestment.AssestmentType(2,"pilihan"));
        assestment.setQuestions("Luka");
        assestment.clearOptionAssestment();
        assestment.addOptionAssestment(new Assestment.OptionAssestment(1,"besar"));
        assestment.addOptionAssestment(new Assestment.OptionAssestment(2,"sedang"));
        assestment.addOptionAssestment(new Assestment.OptionAssestment(3,"kecil"));
        assestmentList.add(assestment);
        Assestment assestment1 = new Assestment();
        assestment1.setId(2);
        assestment1.setAssestmentType(new Assestment.AssestmentType(1,"answer"));
        assestment1.setQuestions("Berapa Umur Pasien?");
        assestmentList.add(assestment1);
        assestmentAdapter.notifyDataSetChanged();
    }
}
