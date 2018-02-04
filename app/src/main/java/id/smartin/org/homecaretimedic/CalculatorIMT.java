package id.smartin.org.homecaretimedic;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import id.smartin.org.homecaretimedic.model.AlphaCalcActivity;
import id.smartin.org.homecaretimedic.tools.CalculatorUtility;

public class CalculatorIMT extends AppCompatActivity {
    public static final String TAG = "[CalculatorIMT]";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    @BindView(R.id.btnReset)
    Button btnReset;
    @BindView(R.id.btnCalculate)
    Button btnCalculate;

    @BindView(R.id.genderSpin)
    Spinner genderSpin;
    @BindView(R.id.heightTex)
    EditText heightTex;
    @BindView(R.id.weightTex)
    EditText weightTex;
    @BindView(R.id.ageTex)
    EditText ageTex;

    ArrayAdapter<CharSequence> adapterGender;
    SweetAlertDialog sweetAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_imt);
        ButterKnife.bind(this);

        adapterGender = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

}
