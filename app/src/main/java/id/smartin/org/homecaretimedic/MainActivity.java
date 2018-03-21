package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.mainfragment.AccountFragment;
import id.smartin.org.homecaretimedic.mainfragment.HomeFragment;
import id.smartin.org.homecaretimedic.mainfragment.RedeemPointFragment;
import id.smartin.org.homecaretimedic.mainfragment.YourOrderFragment;
import id.smartin.org.homecaretimedic.manager.HomecareSessionManager;
import id.smartin.org.homecaretimedic.model.User;
import id.smartin.org.homecaretimedic.tools.restservice.APIClient;
import id.smartin.org.homecaretimedic.tools.restservice.UserAPIInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static String TAG = "[MainActivity]";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private int[] tabIcons = {
            R.drawable.ic_tab_home,
            R.drawable.ic_tab_yourorder,
            R.drawable.ic_tab_redeempoint,
            R.drawable.ic_tab_account
    };

    private HomecareSessionManager homecareSessionManager;
    private UserAPIInterface userAPIInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        createTitleBar();
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        sendFCMTokenToServer();
    }

    private void sendFCMTokenToServer(){
        homecareSessionManager = new HomecareSessionManager(getParent(), getApplicationContext());
        User user = homecareSessionManager.getUserDetail();
        String initialFCMToken = FirebaseInstanceId.getInstance().getToken();
        user.setPhotoPath(initialFCMToken);
        userAPIInterface = APIClient.getClientWithToken(homecareSessionManager, getApplicationContext()).create(UserAPIInterface.class);
        Call<ResponseBody> services = userAPIInterface.updateUser(user.getId(), user);
        services.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i(TAG, "FCM Token Updated");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "Update FCM Token Failed");
                homecareSessionManager.logout();
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "Home");
        adapter.addFragment(new YourOrderFragment(), "Your Order");
        adapter.addFragment(new RedeemPointFragment(), "Redeem Point");
        adapter.addFragment(new AccountFragment(), "Account");
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @SuppressLint("RestrictedApi")
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

}

