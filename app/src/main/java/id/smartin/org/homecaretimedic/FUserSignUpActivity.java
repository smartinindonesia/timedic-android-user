package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.Constants;
import cn.pedant.SweetAlert.SweetAlertDialog;
import id.smartin.org.homecaretimedic.model.User;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;

public class FUserSignUpActivity extends AppCompatActivity {
    public static final String TAG = "[FUserSignUpActivity]";

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.firstName)
    EditText firstName;
    @BindView(R.id.middleName)
    EditText middleName;
    @BindView(R.id.lastName)
    EditText lastName;
    @BindView(R.id.phoneUser)
    EditText phone;
    @BindView(R.id.chkAgreement)
    CheckBox checkAgreement;
    @BindView(R.id.signUP)
    Button signUP;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    @BindView(R.id.agreementLink)
    TextView agreementLink;

    private User user;

    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private SweetAlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuser_sign_up);
        ButterKnife.bind(this);
        user = (User) getIntent().getSerializableExtra("fbase_user");
        Log.i(TAG, user.getFrontName());
        googleLoginInit();
        createTitleBar();
        agreementLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUrl(id.smartin.org.homecaretimedic.config.Constants.TERM_AND_COND);
            }
        });
        fillTheForm();
    }

    @SuppressLint("RestrictedApi")
    public void createTitleBar() {
        setSupportActionBar(toolbar);
        ViewFaceUtility.changeToolbarFont(toolbar, this,"fonts/Dosis-Bold.otf", R.color.theme_black);
        ActionBar mActionbar = getSupportActionBar();
        mActionbar.setDisplayHomeAsUpEnabled(true);
        mActionbar.setDefaultDisplayHomeAsUpEnabled(true);
        mActionbar.setDisplayShowHomeEnabled(true);
        mActionbar.setDisplayShowTitleEnabled(true);
        mActionbar.setDisplayShowCustomEnabled(true);
    }

    public void fillTheForm(){
        firstName.setText(user.getFrontName());
        middleName.setText(user.getMiddleName());
        lastName.setText(user.getLastName());
        phone.setText(user.getPhoneNumber());
    }

    private void openProgress(){
        progressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDialog.setTitleText("Loading...");
        progressDialog.setContentText("Proses Login!");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
    }

    private void closeProgress(){
        progressDialog.dismiss();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        signOut();
        finish();
        super.onBackPressed();
    }

    private void googleLoginInit() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        finish();
                    }
                });
    }

    private void openUrl(String url){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
