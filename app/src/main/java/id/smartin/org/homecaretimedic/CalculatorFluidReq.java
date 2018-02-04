package id.smartin.org.homecaretimedic;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import id.smartin.org.homecaretimedic.model.AlphaCalcActivity;
import id.smartin.org.homecaretimedic.tools.CalculatorUtility;

public class CalculatorFluidReq extends AppCompatActivity {
    public static final String TAG = "[CalculatorFluidReq]";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;

    @BindView(R.id.weightTex)
    EditText weightTex;
    @BindView(R.id.btnReset)
    Button btnReset;
    @BindView(R.id.btnCalculate)
    Button btnCalculate;

    SweetAlertDialog sweetAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_fluid_req);
        ButterKnife.bind(this);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetForm();
            }
        });

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateFluidReq();
            }
        });
    }

    private void calculateFluidReq() {
        if (validateForm()) {
            double w = Double.parseDouble(weightTex.getText().toString());
            double result = CalculatorUtility.calculatorFluidReq(w);
            sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE);
            sweetAlertDialog.setTitleText("Hasil").setContentText("Anda membutuhkan " + String.format("%.3f", result) + " cc cairan!");
            sweetAlertDialog.show();
        } else {
            Snackbar.make(mainLayout, "Mohon lengkapi form terlebih dahulu!", Snackbar.LENGTH_LONG).show();
        }
    }

    private boolean validateForm() {
        return !weightTex.getText().toString().equals("");
    }

    private void resetForm() {
        weightTex.setText("");
    }
}
