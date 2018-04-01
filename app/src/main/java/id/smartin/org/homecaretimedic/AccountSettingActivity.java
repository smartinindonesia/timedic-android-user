package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.adapter.GenderSpinnerAdapter;
import id.smartin.org.homecaretimedic.manager.HomecareSessionManager;
import id.smartin.org.homecaretimedic.model.GenderOption;
import id.smartin.org.homecaretimedic.model.User;
import id.smartin.org.homecaretimedic.model.parammodel.RegisterParam;
import id.smartin.org.homecaretimedic.model.parammodel.UserProfile;
import id.smartin.org.homecaretimedic.model.submitmodel.PickedDateTime;
import id.smartin.org.homecaretimedic.tools.ConverterUtility;
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
    @BindView(R.id.emailAddress)
    TextView emailAddress;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    @BindView(R.id.selectDOB)
    ImageButton selectDob;
    @BindView(R.id.dateOfBirth)
    EditText dob;
    @BindView(R.id.genderSpin)
    Spinner genderSpin;
    @BindView(R.id.profPic)
    ImageView profPic;

    private DatePickerDialog datePickerDialog;
    private PickedDateTime pickedDateTime;
    private UserAPIInterface userAPIInterface;
    private HomecareSessionManager homecareSessionManager;
    private User user;

    GenderSpinnerAdapter adapterGender;
    List<GenderOption> genderOptions;

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
        selectDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int day = mcurrentTime.get(Calendar.DAY_OF_MONTH) + 2;
                int month = mcurrentTime.get(Calendar.MONTH);
                int year = mcurrentTime.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(AccountSettingActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mcurrentTime.get(Calendar.YEAR), mcurrentTime.get(Calendar.MONTH), mcurrentTime.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setTitle("Pilih tanggal pelayanan");
                datePickerDialog.show();
                dob.setText(day + "-" + (month + 1) + "-" + year);
            }
        });

        genderOptions = new ArrayList<>();
        genderOptions.add(new GenderOption(R.drawable.btn_laki_laki, "Laki-Laki"));
        genderOptions.add(new GenderOption(R.drawable.btn__perempuan, "Perempuan"));
        adapterGender = new GenderSpinnerAdapter(this, genderOptions);
        genderSpin.setAdapter(adapterGender);

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
                fillTheForm();
            }
        });
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        emailAddress.addTextChangedListener(new TextWatcher() {
            @TargetApi(Build.VERSION_CODES.M)
            public void afterTextChanged(Editable s) {
                String email = emailAddress.getText().toString().trim();
                if (s.length() > 0) {
                    if (email.matches(emailPattern)) {
                        emailAddress.setBackground(getDrawable(R.drawable.bg_green_rounded_textfield));
                        emailAddress.setTextColor(getColor(R.color.btn_on_text));
                    } else {
                        emailAddress.setBackground(getDrawable(R.drawable.bg_red_rounded_textfield));
                        emailAddress.setTextColor(getColor(R.color.btn_on_text));
                    }
                } else {
                    emailAddress.setBackground(getDrawable(R.drawable.bg_gray_rounded_textfield));
                    emailAddress.setTextColor(getColor(R.color.text_color));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });
        phone.addTextChangedListener(new TextWatcher() {
            @TargetApi(Build.VERSION_CODES.M)
            public void afterTextChanged(Editable s) {
                String num = phone.getText().toString().trim();
                if (s.length() > 0) {
                    if (android.util.Patterns.PHONE.matcher(num).matches()) {
                        phone.setBackground(getDrawable(R.drawable.bg_green_rounded_textfield));
                        phone.setTextColor(getColor(R.color.btn_on_text));
                    } else {
                        phone.setBackground(getDrawable(R.drawable.bg_red_rounded_textfield));
                        phone.setTextColor(getColor(R.color.btn_on_text));
                    }
                } else {
                    phone.setBackground(getDrawable(R.drawable.bg_gray_rounded_textfield));
                    phone.setTextColor(getColor(R.color.text_color));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });
    }

    public void fillTheForm() {
        username.setText(user.getUsername());
        username.setEnabled(false);
        firstName.setText(user.getFrontName());
        middleName.setText(user.getMiddleName());
        lastName.setText(user.getLastName());
        phone.setText(user.getPhoneNumber());
        emailAddress.setText(user.getEmail());
        dob.setText(ConverterUtility.getDateString(user.getDateBirth()));
        if (user.getGender() != null) {
            if (user.getGender().equals("Laki-Laki")) {
                genderSpin.setSelection(0);
            } else {
                genderSpin.setSelection(1);
            }
        } else {
            genderSpin.setSelection(0);
        }
        if (user.getPhotoPath() != null) {
            profPic.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(user.getPhotoPath()).apply(new RequestOptions()
                    .circleCropTransform()
                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .thumbnail(0.5f)
                    .into(profPic);
        } else {
            profPic.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
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
        registerParam.setEmail(emailAddress.getText().toString());
        Long dobs = ConverterUtility.getTimeStamp(dob.getText().toString(), "dd-MM-yyyy");
        registerParam.setDateBirth(dobs);
        registerParam.setGender(genderSpin.getSelectedItem().toString());

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
