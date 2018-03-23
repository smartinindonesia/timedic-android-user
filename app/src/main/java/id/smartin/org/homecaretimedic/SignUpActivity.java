package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.config.Constants;
import id.smartin.org.homecaretimedic.model.parammodel.RegisterParam;
import id.smartin.org.homecaretimedic.tools.AesUtil;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;
import id.smartin.org.homecaretimedic.tools.restservice.APIClient;
import id.smartin.org.homecaretimedic.tools.restservice.UserAPIInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    public static final String TAG = "[SignUpActivity]";

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
    @BindView(R.id.emailAddress)
    EditText emailAddress;

    private UserAPIInterface userAPIInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        userAPIInterface = APIClient.getClient().create(UserAPIInterface.class);
        createTitleBar();
        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSignUp();
            }
        });
        agreementLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUrl(id.smartin.org.homecaretimedic.config.Constants.TERM_AND_COND);
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

    private void doSignUp() {
        RegisterParam registerParam = new RegisterParam();
        registerParam.setFirstname(firstName.getText().toString());
        registerParam.setLastname(lastName.getText().toString());
        registerParam.setMiddlename(middleName.getText().toString());
        String shahex = AesUtil.Encrypt(password.getText().toString());
        registerParam.setPassword(shahex);
        registerParam.setUsername(username.getText().toString());
        registerParam.setPhone(phone.getText().toString());
        registerParam.setEmail(emailAddress.getText().toString());

        if (registerParam.isValidPhone()) {
            if (registerParam.isValidEmail()) {
                if (checkAgreement.isChecked()) {
                    try {
                        postData(registerParam);
                    } catch (UnsupportedEncodingException e) {
                        Toast.makeText(getApplicationContext(), "Parameter tidak benar!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Anda belum menyetujui pernyataan persetujuan!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Email tidak valid!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Nomor HP tidak valid!", Toast.LENGTH_LONG).show();
        }
    }

    private void postData(RegisterParam registerParam) throws UnsupportedEncodingException {
        final Call<ResponseBody> resp = userAPIInterface.registerUser(registerParam);
        resp.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i(TAG, response.code() + " Error");
                if (response.code() == 201) {
                    Toast.makeText(getApplicationContext(), "Pendaftaran user baru berhasil dilakukan! Silahkan login untuk melanjutkan", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Snackbar.make(mainLayout, "Pendaftaran user baru gagal!", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "Failed");
                call.cancel();
            }
        });
    }

    private void openUrl(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
