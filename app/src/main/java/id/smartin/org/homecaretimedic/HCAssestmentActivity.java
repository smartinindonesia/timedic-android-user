package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.Snackbar;
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
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.adapter.AssestmentAdapter;
import id.smartin.org.homecaretimedic.manager.HomecareSessionManager;
import id.smartin.org.homecaretimedic.model.Assessment;
import id.smartin.org.homecaretimedic.model.responsemodel.AssessmentResponse;
import id.smartin.org.homecaretimedic.model.submitmodel.SubmitInfo;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;
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
    @BindView(R.id.mainLayout)
    LinearLayout mainLayout;

    private HomecareSessionManager homecareSessionManager;
    private List<Assessment> assessmentList = new ArrayList<>();
    private AssestmentAdapter assestmentAdapter;
    private HomecareAssessmentAPIInterface homecareAssessmentAPIInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hcassestment);
        ButterKnife.bind(this);
        createTitleBar();

        homecareSessionManager = new HomecareSessionManager(this, getApplicationContext());
        homecareAssessmentAPIInterface = APIClient.getClientWithToken(homecareSessionManager, getApplicationContext()).create(HomecareAssessmentAPIInterface.class);

        homecareSessionManager = new HomecareSessionManager(this, getApplicationContext());
        assestmentAdapter = new AssestmentAdapter(this, assessmentList);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(assestmentAdapter);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (assestmentAdapter.isAssessmentFullfilled()) {
                    SubmitInfo.assessmentList = assestmentAdapter.getAssesmentAnswerParams();
                    Intent acceptanceIntent = new Intent(HCAssestmentActivity.this, DateTimePickActivity.class);
                    startActivity(acceptanceIntent);
                    finish();
                } else {
                    Snackbar.make(mainLayout, getResources().getString(R.string.null_assessment), Snackbar.LENGTH_LONG).show();
                }
            }
        });

        populateSelectedAssessment();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void populateSelectedAssessment() {
        Log.i(TAG, "Tagged SERVICE ID " + SubmitInfo.selectedHomecareService.getId());
        Call<List<AssessmentResponse>> services = homecareAssessmentAPIInterface.getAssessmentByIdService((long) SubmitInfo.selectedHomecareService.getId());
        services.enqueue(new Callback<List<AssessmentResponse>>() {
            @Override
            public void onResponse(Call<List<AssessmentResponse>> call, Response<List<AssessmentResponse>> response) {
                Log.i(TAG, response.raw().toString());
                if (response.code() == 200) {
                    assessmentList.clear();
                    for (int i = 0; i < response.body().size(); i++) {
                        Assessment assessmentItem = response.body().get(i).getAssessment();
                        if (assessmentItem.getStatusActive() == 1) {
                            assessmentList.add(assessmentItem);
                        }
                        Log.i(TAG, response.body().get(i).toString());
                    }
                    assestmentAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<AssessmentResponse>> call, Throwable t) {
                assessmentList.clear();
                assestmentAdapter.notifyDataSetChanged();
                call.cancel();
            }
        });
    }

    @SuppressLint("RestrictedApi")
    public void createTitleBar() {
        setSupportActionBar(toolbar);
        ViewFaceUtility.changeToolbarFont(toolbar, this, "fonts/Dosis-Bold.otf", R.color.theme_black);
        ActionBar mActionbar = getSupportActionBar();
        mActionbar.setDisplayHomeAsUpEnabled(true);
        mActionbar.setDefaultDisplayHomeAsUpEnabled(true);
        mActionbar.setDisplayShowHomeEnabled(true);
        mActionbar.setDisplayShowTitleEnabled(true);
        mActionbar.setDisplayShowCustomEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        onBackPressed();
        return true;
    }

}
