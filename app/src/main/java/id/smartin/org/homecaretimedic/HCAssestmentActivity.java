package id.smartin.org.homecaretimedic;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.adapter.AssestmentAdapter;
import id.smartin.org.homecaretimedic.manager.HomecareSessionManager;
import id.smartin.org.homecaretimedic.model.Assessment;
import id.smartin.org.homecaretimedic.model.responsemodel.AssessmentResponse;
import id.smartin.org.homecaretimedic.tools.restservice.APIClient;
import id.smartin.org.homecaretimedic.tools.restservice.HomecareAssessmentAPIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HCAssestmentActivity extends AppCompatActivity {
    public static String TAG = "[HCAssestmentActivity]";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.assessment_list)
    RecyclerView recyclerView;

    private HomecareSessionManager homecareSessionManager;
    private List<Assessment> assessmentList = new ArrayList<>();
    private AssestmentAdapter assestmentAdapter;
    private HomecareAssessmentAPIInterface homecareAssessmentAPIInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hcassestment);
        ButterKnife.bind(this);

        homecareSessionManager = new HomecareSessionManager(this, getApplicationContext());
        homecareAssessmentAPIInterface = APIClient.getClientWithToken(homecareSessionManager.getToken(), getApplicationContext()).create(HomecareAssessmentAPIInterface.class);

        setSupportActionBar(toolbar);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent acceptanceIntent = new Intent(HCAssestmentActivity.this, DateTimePickActivity.class);
                startActivity(acceptanceIntent);
                finish();
            }
        });
        createTitleBar();
        homecareSessionManager = new HomecareSessionManager(this, getApplicationContext());
        assestmentAdapter = new AssestmentAdapter(this, assessmentList);
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

    public void populateSelectedAssessment(){
        Call<List<AssessmentResponse>> services = homecareAssessmentAPIInterface.getAllAssessmentData();
        services.enqueue(new Callback<List<AssessmentResponse>>() {
            @Override
            public void onResponse(Call<List<AssessmentResponse>> call, Response<List<AssessmentResponse>> response) {
                Log.i(TAG, response.raw().toString());
                if (response.code() == 200) {
                    assessmentList.clear();
                    for (int i = 0; i < response.body().size(); i++) {
                        assessmentList.add(response.body().get(i).getAssessment());
                        Log.i(TAG, response.body().get(i).toString());
                    }
                    assestmentAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<AssessmentResponse>> call, Throwable t) {

            }
        });
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
        assessmentList.clear();
        Assessment assessment = new Assessment();
        assessment.setId(2);
        assessment.setAssestmentType(new Assessment.AssessmentType(2,"pilihan"));
        assessment.setQuestions("Luka");
        assessment.clearOptionAssestment();
        assessmentList.add(assessment);
        Assessment assessment1 = new Assessment();
        assessment1.setId(2);
        assessment1.setAssestmentType(new Assessment.AssessmentType(1,"answer"));
        assessment1.setQuestions("Berapa Umur Pasien?");
        assessmentList.add(assessment1);
        assestmentAdapter.notifyDataSetChanged();
    }
}
