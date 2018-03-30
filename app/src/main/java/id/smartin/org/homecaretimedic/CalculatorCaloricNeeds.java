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
import id.smartin.org.homecaretimedic.adapter.AlphaCalcSpinnerAdapter;
import id.smartin.org.homecaretimedic.adapter.GenderSpinnerAdapter;
import id.smartin.org.homecaretimedic.model.AlphaCalcActivity;
import id.smartin.org.homecaretimedic.model.GenderOption;
import id.smartin.org.homecaretimedic.tools.CalculatorUtility;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;

public class CalculatorCaloricNeeds extends AppCompatActivity {
    public static final String TAG = "[CalculatorCaloricNeeds]";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    @BindView(R.id.btnReset)
    LinearLayout btnReset;
    @BindView(R.id.btnCalculate)
    LinearLayout btnCalculate;
    @BindView(R.id.genderSpin)
    Spinner genderSpin;
    @BindView(R.id.activitySpin)
    Spinner activitySpin;
    @BindView(R.id.heightTex)
    EditText heightTex;
    @BindView(R.id.weightTex)
    EditText weightTex;
    @BindView(R.id.ageTex)
    EditText ageTex;

    GenderSpinnerAdapter adapterGender;
    List<GenderOption> genderOptions;

    AlphaCalcSpinnerAdapter adapterActivity;
    List<AlphaCalcActivity> alphaCalcActivities = new ArrayList<>();

    SweetAlertDialog sweetAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_caloric_needs);
        ButterKnife.bind(this);
        createTitleBar();

        genderOptions = new ArrayList<>();
        genderOptions.add(new GenderOption(R.drawable.btn_laki_laki, "Laki-laki"));
        genderOptions.add(new GenderOption(R.drawable.btn__perempuan, "Perempuan"));
        adapterGender = new GenderSpinnerAdapter(this, genderOptions);
        genderSpin.setAdapter(adapterGender);

        initAlphaCalc();
        adapterActivity = new AlphaCalcSpinnerAdapter(this, alphaCalcActivities);
        activitySpin.setAdapter(adapterActivity);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCaloricNeeds();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetForm();
            }
        });
    }

    private void calculateCaloricNeeds() {
        if (validateForm()) {
            double w = Double.parseDouble(weightTex.getText().toString());
            double h = Double.parseDouble(heightTex.getText().toString());
            double ag = Double.parseDouble(ageTex.getText().toString());
            String gend = genderSpin.getSelectedItem().toString();
            float alphaValue = ((AlphaCalcActivity) activitySpin.getSelectedItem()).getAlphaValue();
            Double result = CalculatorUtility.calculateCaloriesNeed(w, h, ag, gend, alphaValue);
            sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE);
            sweetAlertDialog.setTitleText("Hasil").setContentText("Anda membutuhkan " + String.format("%.3f", result) + " kalori!");
            sweetAlertDialog.show();
        } else {
            Snackbar.make(mainLayout, "Mohon lengkapi form terlebih dahulu!", Snackbar.LENGTH_LONG).show();
        }
    }

    private boolean validateForm() {
        return !heightTex.getText().toString().equals("") && !weightTex.getText().toString().equals("") && !ageTex.getText().toString().equals("");
    }

    private void initAlphaCalc() {
        alphaCalcActivities.clear();
        AlphaCalcActivity tidakAktif = new AlphaCalcActivity(0, "Tidak Aktif", 1.2f, R.drawable.btn_aktivitas);
        AlphaCalcActivity aktivitasRingan = new AlphaCalcActivity(1, "Aktivitas Ringan", 1.375f, R.drawable.btn_aktivitas);
        AlphaCalcActivity aktivitasSedang = new AlphaCalcActivity(2, "Aktivitas Sedang", 1.55f, R.drawable.btn_aktivitas);
        AlphaCalcActivity aktivitasBerat = new AlphaCalcActivity(3, "Aktivitas Berat", 1.725f, R.drawable.btn_aktivitas);
        AlphaCalcActivity aktivitasSgtBerat = new AlphaCalcActivity(4, "Aktivitas Sangat Berat", 1.9f, R.drawable.btn_aktivitas);
        alphaCalcActivities.add(tidakAktif);
        alphaCalcActivities.add(aktivitasRingan);
        alphaCalcActivities.add(aktivitasBerat);
        alphaCalcActivities.add(aktivitasSedang);
        alphaCalcActivities.add(aktivitasSgtBerat);
    }

    private void resetForm() {
        heightTex.setText("");
        weightTex.setText("");
        ageTex.setText("");
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
}
