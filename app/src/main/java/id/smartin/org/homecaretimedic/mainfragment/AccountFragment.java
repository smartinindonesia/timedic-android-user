package id.smartin.org.homecaretimedic.mainfragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.AccountSettingActivity;
import id.smartin.org.homecaretimedic.ChangePasswordActivity;
import id.smartin.org.homecaretimedic.ContactUsActivity;
import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.config.Constants;
import id.smartin.org.homecaretimedic.manager.HomecareSessionManager;
import id.smartin.org.homecaretimedic.model.AppSetting;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;


/**
 * Created by Hafid on 8/22/2017.
 */


public class AccountFragment extends Fragment {
    public static String TAG = "[AccountFragment]";

    @BindView(R.id.accountLayout)
    LinearLayout accountLayout;
    @BindView(R.id.btnLogout)
    LinearLayout logoutBtn;
    @BindView(R.id.btnRateApp)
    LinearLayout btnRateApp;
    @BindView(R.id.btnChangePassword)
    LinearLayout btnChangePassword;
    @BindView(R.id.btnAccountSetting)
    LinearLayout btnAccountSetting;
    @BindView(R.id.btnPushNotification)
    RelativeLayout btnPushNotif;
    @BindView(R.id.turnNotification)
    Switch notifToggle;
    //@BindView(R.id.btnPrivacy)
    //LinearLayout btnPrivacy;
    @BindView(R.id.btnTermAndCond)
    LinearLayout btnTermAndCond;
    @BindView(R.id.btnContactUs)
    LinearLayout btnContactUs;

    @BindView(R.id.accountSetting)
    TextView accountSetting;
    @BindView(R.id.changePasswordText)
    TextView changePasswordText;
    //@BindView(R.id.languageText)
    //TextView languageText;
    @BindView(R.id.pushNotifText)
    TextView pushNotifText;
    @BindView(R.id.termAndCondText)
    TextView termAndCondText;
    //@BindView(R.id.policyAndPrivacyText)
    //TextView policyAndPrivacyText;
    @BindView(R.id.rateAppText)
    TextView rateAppText;
    @BindView(R.id.logoutText)
    TextView logoutText;
    @BindView(R.id.contactUsText)
    TextView contactUsText;

    private HomecareSessionManager homecareSessionManager;
    private AppSetting appSetting;

    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homecareSessionManager = new HomecareSessionManager(getActivity(), getContext());
        appSetting = homecareSessionManager.getSetting();
        googleLoginInit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View newView = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this, newView);
        if (appSetting.getActive()) {
            notifToggle.setChecked(true);
        } else {
            notifToggle.setChecked(false);
        }
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homecareSessionManager.logout();
                signOut();
            }
        });
        btnAccountSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AccountSettingActivity.class);
                startActivity(intent);
            }
        });
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        notifToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                appSetting.setActive(b);
                homecareSessionManager.setSetting(appSetting);
                if (b) {
                    Snackbar.make(accountLayout, "Notifikasi diaktifkan!", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(accountLayout, "Notifikasi dimatikan!", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        btnRateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoMarketPlace();
            }
        });

        /*
        btnPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUrl(Constants.PRIVACY_STATEMENT);
            }
        });
        */

        btnTermAndCond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUrl(Constants.PRIVACY_STATEMENT);
            }
        });
        btnContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ContactUsActivity.class);
                startActivity(intent);
            }
        });
        setFonts();
        return newView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            Log.d(TAG, "Visible now");
            if (!isVisibleToUser) {
                Log.d(TAG, "Not visible anymore.  Stopping audio.");
                // TODO stop audio playback
            }
        }
    }

    private void gotoMarketPlace() {
        Uri uri = Uri.parse("market://details?id=" + getContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(
                Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getContext().getPackageName())));
        }
    }

    private void openUrl(String url){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void googleLoginInit() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .enableAutoManage(getActivity(), new GoogleApiClient.OnConnectionFailedListener() {
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

                    }
                });
        LoginManager.getInstance().logOut();
    }

    private void setFonts(){
        ArrayList<TextView> arrayList = new ArrayList<>();
        arrayList.add(accountSetting);
        arrayList.add(changePasswordText);
        //arrayList.add(languageText);
        arrayList.add(pushNotifText);
        arrayList.add(termAndCondText);
        //arrayList.add(policyAndPrivacyText);
        arrayList.add(rateAppText);
        arrayList.add(logoutText);
        arrayList.add(contactUsText);
        ViewFaceUtility.applyFonts(arrayList, getActivity(), "fonts/Dosis-Medium.otf");
    }
}
