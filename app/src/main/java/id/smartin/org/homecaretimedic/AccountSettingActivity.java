package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.manager.HomecareSessionManager;
import id.smartin.org.homecaretimedic.model.User;
import id.smartin.org.homecaretimedic.model.parammodel.RegisterParam;
import id.smartin.org.homecaretimedic.model.parammodel.UserProfile;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;
import id.smartin.org.homecaretimedic.tools.restservice.APIClient;
import id.smartin.org.homecaretimedic.tools.restservice.UserAPIInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountSettingActivity extends AppCompatActivity {
    public static final String TAG = "[AccountSettingAct]";

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.firstName)
    EditText firstName;
    @BindView(R.id.middleName)
    EditText middleName;
    @BindView(R.id.lastName)
    EditText lastName;
    @BindView(R.id.phoneUser)
    EditText phone;
    @BindView(R.id.btnEdit)
    Button btnEdit;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;

    private UserAPIInterface userAPIInterface;
    private HomecareSessionManager homecareSessionManager;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);
        ButterKnife.bind(this);
        homecareSessionManager = new HomecareSessionManager(this, getApplicationContext());
        userAPIInterface = APIClient.getClientWithToken(homecareSessionManager, getApplicationContext()).create(UserAPIInterface.class);
        createTitleBar();
        user = homecareSessionManager.getUserDetail();
        fillTheForm();
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doEditProfile();
            }
        });
        getUserDetail();
    }

    public void getUserDetail() {
        final Call<User> resp = userAPIInterface.getProfile(user.getId());
        resp.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();
                homecareSessionManager.updateProfile(user);
                fillTheForm();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    public void fillTheForm() {
        username.setText(user.getUsername());
        firstName.setText(user.getFrontName());
        middleName.setText(user.getMiddleName());
        lastName.setText(user.getLastName());
        phone.setText(user.getPhoneNumber());
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
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

    private void doEditProfile() {
        UserProfile registerParam = new UserProfile();
        registerParam.setId(user.getId());
        registerParam.setFrontName(firstName.getText().toString());
        registerParam.setLastName(lastName.getText().toString());
        registerParam.setMiddleName(middleName.getText().toString());
        registerParam.setPhoneNumber(phone.getText().toString());

        if (registerParam.isValidPhone()) {
            try {
                postData(registerParam);
            } catch (UnsupportedEncodingException e) {
                Toast.makeText(getApplicationContext(), "Parameter tidak benar!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Nomor HP tidak valid!", Toast.LENGTH_LONG).show();
        }
    }

    private void postData(UserProfile registerParam) throws UnsupportedEncodingException {
        final Call<ResponseBody> resp = userAPIInterface.updateProfile(registerParam.getId(), registerParam);
        resp.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i(TAG, response.code() + " Error");
                if (response.code() == 200) {
                    Toast.makeText(getApplicationContext(), "Profil telah diubah!", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Profil gagal diubah!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "Failed");
                Toast.makeText(getApplicationContext(), "Profil gagal diubah!", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }
}
