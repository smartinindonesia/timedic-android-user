package id.smartin.org.homecaretimedic;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.model.parammodel.RegisterParam;

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

    private Gson g = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        createTitleBar();
        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSignUp();
            }
        });
    }

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

    private void doSignUp() {
        RegisterParam registerParam = new RegisterParam();
        registerParam.setFirstname(firstName.getText().toString());
        registerParam.setLastname(lastName.getText().toString());
        registerParam.setMiddlename(middleName.getText().toString());
        registerParam.setPassword(password.getText().toString());
        registerParam.setUsername(username.getText().toString());
        registerParam.setPhone(phone.getText().toString());

        if (registerParam.isValidPhone()) {
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
            Toast.makeText(getApplicationContext(), "Nomor HP tidak valid!", Toast.LENGTH_LONG).show();
        }
    }

    private void postData(RegisterParam registerParam) throws UnsupportedEncodingException {

    }
}
