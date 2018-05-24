package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.manager.HomecareSessionManager;
import id.smartin.org.homecaretimedic.model.User;
import id.smartin.org.homecaretimedic.model.parammodel.PasswordProfile;
import id.smartin.org.homecaretimedic.tools.AesUtil;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;
import id.smartin.org.homecaretimedic.tools.restservice.APIClient;
import id.smartin.org.homecaretimedic.tools.restservice.UserAPIInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    public static final String TAG = "[ChangePasswordAct]";

    @BindView(R.id.oldPassword)
    EditText oldPassword;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.rePassword)
    EditText retypePassword;
    @BindView(R.id.btnUpdate)
    Button btnUpdate;
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
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        homecareSessionManager = new HomecareSessionManager(this, getApplicationContext());
        userAPIInterface = APIClient.getClientWithToken(homecareSessionManager, getApplicationContext()).create(UserAPIInterface.class);
        createTitleBar();
        user = homecareSessionManager.getUserDetail();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewFaceUtility.hideKeyboard(ChangePasswordActivity.this, password);
                ViewFaceUtility.hideKeyboard(ChangePasswordActivity.this, retypePassword);
                ViewFaceUtility.hideKeyboard(ChangePasswordActivity.this, oldPassword);

                FirebaseUser usr = FirebaseAuth.getInstance().getCurrentUser();
                if (usr != null) {
                    if (usr.getProviders().get(0).equals("password")) {
                        changePasswordFirebase();
                    } else {
                        doUpdatePassword();
                    }
                } else {
                    doUpdatePassword();
                }
            }
        });
        retypePassword.addTextChangedListener(new TextWatcher() {
            @TargetApi(Build.VERSION_CODES.M)
            public void afterTextChanged(Editable s) {
                String pass = password.getText().toString();
                String rePass = retypePassword.getText().toString();
                Integer paddingTop = retypePassword.getPaddingTop();
                Integer paddingBottom = retypePassword.getPaddingBottom();
                Integer paddingLeft = retypePassword.getPaddingLeft();
                Integer paddingRight = retypePassword.getPaddingRight();
                if (s.length() > 0) {
                    if (pass.equals(rePass)) {
                        retypePassword.setBackground(getDrawable(R.drawable.bg_green_rounded_textfield));
                        retypePassword.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.btn_on_text));
                    } else {
                        retypePassword.setBackground(getDrawable(R.drawable.bg_red_rounded_textfield));
                        retypePassword.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.btn_on_text));
                    }
                } else {
                    retypePassword.setBackground(getDrawable(R.drawable.edittext_border));
                    retypePassword.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.text_color));
                }
                retypePassword.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });
        setFonts();
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
        onBackPressed();
        return true;
    }

    private void changePasswordFirebase() {
        PasswordProfile registerParam = new PasswordProfile();
        registerParam.setPassword(password.getText().toString());
        if (!password.getText().toString().trim().equals("")) {
            if (password.getText().toString().equals(retypePassword.getText().toString())) {
                final FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
                final String newpassword = password.getText().toString();
                AuthCredential credential = EmailAuthProvider
                        .getCredential(fuser.getEmail(), oldPassword.getText().toString());

// Prompt the user to re-provide their sign-in credentials
                fuser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            fuser.updatePassword(newpassword)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Snackbar.make(mainLayout, "Password berhasil diganti !", Snackbar.LENGTH_LONG).show();
                                            } else {
                                                if (task.getException().getLocalizedMessage().equals("Password should be at least 6 characters")) {
                                                    Snackbar.make(mainLayout, "Password minimal terdiri dari 6 karakter!", Snackbar.LENGTH_LONG).show();
                                                } else {
                                                    Snackbar.make(mainLayout, "Password gagal diubah!", Snackbar.LENGTH_LONG).show();
                                                }
                                            }
                                        }
                                    });
                        } else {
                            Snackbar.make(mainLayout, "Authentikasi gagal !", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });

            } else {
                Toast.makeText(getApplicationContext(), "Password tidak sesuai, harap diulang kembali!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Password tidak boleh kosong!", Toast.LENGTH_LONG).show();
        }

    }

    public void doUpdatePassword() {
        Log.i(TAG, password.getText().toString());
        Boolean isOldPass = AesUtil.Decrypt(user.getPassword()).equals(oldPassword.getText().toString());
        PasswordProfile registerParam = new PasswordProfile();
        String shahex = AesUtil.Encrypt(password.getText().toString());
        registerParam.setPassword(shahex);
        if (isOldPass) {
            if (!password.getText().toString().trim().equals("")) {
                if (password.getText().toString().equals(retypePassword.getText().toString())) {
                    postData(registerParam);
                } else {
                    Toast.makeText(getApplicationContext(), "Password tidak sesuai, harap diulang kembali!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Password tidak boleh kosong!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Password lama tidak sesuai!", Toast.LENGTH_LONG).show();
        }
    }

    public void postData(PasswordProfile pass) {
        Call<ResponseBody> resp = userAPIInterface.updatePassword(user.getId(), pass);
        resp.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Toast.makeText(getApplicationContext(), "Password berhasil diganti!", Toast.LENGTH_LONG).show();
                    getUserDetail();
                } else {
                    Toast.makeText(getApplicationContext(), "Penggantian password baru gagal!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                call.cancel();
                homecareSessionManager.logout();
            }
        });
    }

    public void getUserDetail() {
        final Call<User> resp = userAPIInterface.getProfile(user.getId());
        resp.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();
                homecareSessionManager.updateProfile(user);
                finish();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                call.cancel();
                Toast.makeText(getApplicationContext(), "Jaringan bermasalah!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setFonts() {
        ArrayList<TextView> arrayList = new ArrayList<>();
        arrayList.add(oldPassword);
        arrayList.add(password);
        arrayList.add(retypePassword);
        arrayList.add(btnUpdate);
        ViewFaceUtility.applyFonts(arrayList, this, "fonts/Dosis-Medium.otf");
    }
}
