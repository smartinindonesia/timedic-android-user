package id.smartin.org.homecaretimedic.mainfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.AccountSettingActivity;
import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.manager.HomecareSessionManager;

/**
 * Created by Hafid on 8/22/2017.
 */


public class AccountFragment extends Fragment {
    public static String TAG = "[AccountFragment]";

    @BindView(R.id.btnLogout)
    LinearLayout logoutBtn;
    @BindView(R.id.accountSetting)
    TextView accountSetting;
    private HomecareSessionManager homecareSessionManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homecareSessionManager = new HomecareSessionManager(getActivity(), getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View newView = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this,newView);

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
        return newView;
    }
}
