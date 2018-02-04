package id.smartin.org.homecaretimedic;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import id.smartin.org.homecaretimedic.tools.CalculatorUtility;

public class CalculatorBBI extends AppCompatActivity {
    public static final String TAG = "[CalculatorBBI]";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.heightText)
    EditText heigthText;
    @BindView(R.id.genderSpin)
    Spinner genderSpinner;
    @BindView(R.id.btnCalculateBBI)
    Button btnCalculateBBI;
    @BindView(R.id.btnReset)
    Button btnReset;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;

    ArrayAdapter<CharSequence> adapterGender;
    SweetAlertDialog sweetAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_bbi);
        ButterKnife.bind(this);

        adapterGender = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
}
