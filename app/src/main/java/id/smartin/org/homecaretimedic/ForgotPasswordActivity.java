package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;

public class ForgotPasswordActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mainLayout)
    LinearLayout mainLayout;
    @BindView(R.id.forgotPasswordTitle)
    TextView forgotPasswordTitle;
    @BindView(R.id.forgotPasswordDesc)
    TextView forgotPasswordDesc;
    @BindView(R.id.emailAddress)
    EditText emailAddress;
    @BindView(R.id.btnResetPass)
    Button btnResetPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        createTitleBar();
        btnResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String email = emailAddress.getText().toString();
                if (email.isEmpty() || email.equals("") || email.equals(null)) {
                    Snackbar.make(mainLayout, "Masukkan alamat email anda!", Snackbar.LENGTH_LONG).show();
                } else {
                    auth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Snackbar.make(mainLayout, "Email reset password telah dikirimkan!", Snackbar.LENGTH_LONG).show();
                                    } else {
                                        Snackbar.make(mainLayout, "Email reset password gagal dikirimkan!", Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
        setFonts();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private void setFonts() {
        ArrayList<TextView> arrayList = new ArrayList<>();
        ArrayList<TextView> arrayListB = new ArrayList<>();
        arrayListB.add(forgotPasswordTitle);
        arrayList.add(forgotPasswordDesc);
        arrayList.add(emailAddress);
        arrayList.add(btnResetPass);
        ViewFaceUtility.applyFonts(arrayList, this, "fonts/Dosis-Medium.otf");
        ViewFaceUtility.applyFonts(arrayListB, this, "fonts/Dosis-ExtraBold.otf");
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
}
