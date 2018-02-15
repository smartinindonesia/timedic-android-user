package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import id.smartin.org.homecaretimedic.adapter.GenderSpinnerAdapter;
import id.smartin.org.homecaretimedic.model.GenderOption;
import id.smartin.org.homecaretimedic.tools.CalculatorUtility;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;

public class CalculatorBBI extends AppCompatActivity {
    public static final String TAG = "[CalculatorBBI]";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.heightText)
    EditText heigthText;
    @BindView(R.id.genderSpin)
    Spinner genderSpinner;
    @BindView(R.id.btnCalculateBBI)
    LinearLayout btnCalculateBBI;
    @BindView(R.id.btnReset)
    LinearLayout btnReset;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;

    GenderSpinnerAdapter adapterGender;
    List<GenderOption> genderOptions;

    SweetAlertDialog sweetAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_bbi);
        ButterKnife.bind(this);
        createTitleBar();
        genderOptions = new ArrayList<>();
        genderOptions.add(new GenderOption(R.drawable.btn_laki_laki, "Laki-laki"));
        genderOptions.add(new GenderOption(R.drawable.btn__perempuan, "Perempuan"));
        adapterGender = new GenderSpinnerAdapter(this, genderOptions);
        genderSpinner.setAdapter(adapterGender);
        btnCalculateBBI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBBI();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetForm();
            }
        });
    }

    private void calculateBBI() {
        if (validateForm()) {
            Float result = CalculatorUtility.calculateBBI(Float.parseFloat(heigthText.getText().toString()), genderSpinner.getSelectedItem().toString());
            sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE);
            sweetAlertDialog.setTitleText("Hasil").setContentText("Berat badan ideal anda " + String.format("%.3f", result) + " kg");
            sweetAlertDialog.show();
        } else {
            Snackbar.make(mainLayout, "Mohon lengkapi form terlebih dahulu!", Snackbar.LENGTH_LONG).show();
        }
    }

    private boolean validateForm(){
        return !heigthText.getText().toString().equals("");
    }

    private void resetForm(){
        heigthText.setText("");
    }

    @SuppressLint("RestrictedApi")
    public void createTitleBar() {
        setSupportActionBar(toolbar);
        ViewFaceUtility.changeToolbarFont(toolbar, this,"fonts/Dosis-Bold.otf", R.color.theme_black);
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
}
