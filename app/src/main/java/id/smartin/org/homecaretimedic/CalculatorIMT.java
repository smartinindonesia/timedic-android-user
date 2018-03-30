package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class CalculatorIMT extends AppCompatActivity {
    public static final String TAG = "[CalculatorIMT]";

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
    @BindView(R.id.heightTex)
    EditText heightTex;
    @BindView(R.id.weightTex)
    EditText weightTex;
    @BindView(R.id.ageTex)
    EditText ageTex;

    GenderSpinnerAdapter adapterGender;
    List<GenderOption> genderOptions;

    SweetAlertDialog sweetAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_imt);
        ButterKnife.bind(this);
        createTitleBar();

        genderOptions = new ArrayList<>();
        genderOptions.add(new GenderOption(R.drawable.btn_laki_laki, "Laki-laki"));
        genderOptions.add(new GenderOption(R.drawable.btn__perempuan, "Perempuan"));
        adapterGender = new GenderSpinnerAdapter(this, genderOptions);
        genderSpin.setAdapter(adapterGender);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateIMT();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetForm();
            }
        });
    }

    private void calculateIMT() {
        if (validateForm()) {
            double w = Double.parseDouble(weightTex.getText().toString());
            double h = Double.parseDouble(heightTex.getText().toString());
            double ag = Double.parseDouble(ageTex.getText().toString());
            String gend = genderSpin.getSelectedItem().toString();
            String result = CalculatorUtility.calculatorIMT(w, h);
            sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE);
            sweetAlertDialog.setTitleText("Hasil").setContentText(result);
            sweetAlertDialog.show();
        } else {
            Snackbar.make(mainLayout, "Mohon lengkapi form terlebih dahulu!", Snackbar.LENGTH_LONG).show();
        }
    }

    private boolean validateForm() {
        return !heightTex.getText().toString().equals("") && !weightTex.getText().toString().equals("") && !ageTex.getText().toString().equals("");
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
