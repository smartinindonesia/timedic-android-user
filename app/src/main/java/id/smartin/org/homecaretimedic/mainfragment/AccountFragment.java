package id.smartin.org.homecaretimedic.mainfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.AccountSettingActivity;
import id.smartin.org.homecaretimedic.ChangePasswordActivity;
import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.manager.HomecareSessionManager;
import id.smartin.org.homecaretimedic.model.AppSetting;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Hafid on 8/22/2017.
 */


public class AccountFragment extends Fragment {
    public static String TAG = "[AccountFragment]";

    @BindView(R.id.accountLayout)
    LinearLayout accountLayout;
    @BindView(R.id.btnLogout)
    LinearLayout logoutBtn;
    @BindView(R.id.accountSetting)
    TextView accountSetting;
    @BindView(R.id.btnChangePassword)
    LinearLayout btnChangePassword;
    @BindView(R.id.btnPushNotification)
    RelativeLayout btnPushNotif;
    @BindView(R.id.turnNotification)
    Switch notifToggle;

    private HomecareSessionManager homecareSessionManager;
    private AppSetting appSetting;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homecareSessionManager = new HomecareSessionManager(getActivity(), getContext());
        appSetting = homecareSessionManager.getSetting();
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
            }
        });
        accountSetting.setOnClickListener(new View.OnClickListener() {
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
        return newView;
    }
}
