package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.manager.HomecareSessionManager;
import id.smartin.org.homecaretimedic.model.Caregiver;
import id.smartin.org.homecaretimedic.model.CaregiverOrder;
import id.smartin.org.homecaretimedic.model.parammodel.CaregiverRateParam;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;
import id.smartin.org.homecaretimedic.tools.restservice.APIClient;
import id.smartin.org.homecaretimedic.tools.restservice.HomecareCaregiverAPIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateCaregiverActivity extends AppCompatActivity {
    public static final String TAG = "[RateCaregiverActivity]";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.profilePic)
    ImageView profilePic;
    @BindView(R.id.caregiverName)
    TextView caregiverName;
    @BindView(R.id.caregiverDescription)
    TextView caregiverDescription;
    @BindView(R.id.caregiverPrevRate)
    TextView caregiverPrevRate;
    @BindView(R.id.rateLayout)
    LinearLayout rateLayout;
    @BindView(R.id.rating)
    RatingBar rating;
    @BindView(R.id.comment)
    EditText comment;
    @BindView(R.id.charIndices)
    TextView charIndices;
    @BindView(R.id.submitRate)
    Button submitRate;
    @BindView(R.id.instruction)
    TextView instruction;

    CaregiverRateParam caregiverRateParam = new CaregiverRateParam();
    CaregiverOrder caregiverOrder;
    Caregiver caregiverInfo;

    private HomecareCaregiverAPIInterface homecareCaregiverAPIInterface;
    private HomecareSessionManager homecareSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_caregiver);
        ButterKnife.bind(this);
        createTitleBar();
        homecareSessionManager = new HomecareSessionManager(this, getApplicationContext());
        homecareCaregiverAPIInterface = APIClient.getClientWithToken(homecareSessionManager, getApplicationContext()).create(HomecareCaregiverAPIInterface.class);
        comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                charIndices.setText(editable.length() + "/255");
            }
        });
        submitRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postRate();
            }
        });
        caregiverOrder = (CaregiverOrder) getIntent().getSerializableExtra("caregiver");
        fillTheForm();
        setFonts();
        //getCaregiverInfos();
    }

    @SuppressLint("RestrictedApi")
    public void createTitleBar() {
        setSupportActionBar(toolbar);
        ViewFaceUtility.changeToolbarFont(toolbar, this, "fonts/BalooBhaina-Regular.ttf", R.color.theme_black);
        ActionBar mActionbar = getSupportActionBar();
        mActionbar.setDisplayHomeAsUpEnabled(true);
        mActionbar.setDefaultDisplayHomeAsUpEnabled(true);
        mActionbar.setDisplayShowHomeEnabled(true);
        mActionbar.setDisplayShowTitleEnabled(true);
        mActionbar.setDisplayShowCustomEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private void postRate() {
        caregiverRateParam.setRate((double) rating.getNumStars());
        caregiverRateParam.setComment(comment.getText().toString());
    }

    /*
    private void getCaregiverInfos() {
        if (caregiverOrder != null) {
            Call<Caregiver> resp = homecareCaregiverAPIInterface.getCaregiver(caregiverOrder.getCaregiverId());
            resp.enqueue(new Callback<Caregiver>() {
                @Override
                public void onResponse(Call<Caregiver> call, Response<Caregiver> response) {
                    Log.i(TAG, "Lewat Sini");
                    Log.i(TAG, response.raw().toString());
                    caregiverInfo = response.body();
                    fillTheForm();
                }

                @Override
                public void onFailure(Call<Caregiver> call, Throwable t) {
                    homecareSessionManager.logout();
                }
            });
        }
    }
    */

    private void fillTheForm() {
        /*
        String name = "";
        if (caregiverInfo.getFrontName())
        name =  + " " + caregiverInfo.getMiddleName() + " " + caregiverInfo.getLastName()
        */
        caregiverName.setText(caregiverOrder.getCaregiverName());
    }

    private void setFonts(){
        ViewFaceUtility.applyFont(caregiverName, this, "fonts/Dosis-Bold.otf");
        ArrayList<TextView> arrayList = new ArrayList<>();
        arrayList.add(caregiverDescription);
        arrayList.add(caregiverPrevRate);
        arrayList.add(comment);
        arrayList.add(charIndices);
        arrayList.add(submitRate);
        arrayList.add(instruction);
        ViewFaceUtility.applyFonts(arrayList, this, "fonts/Dosis-Medium.otf");
    }

}
