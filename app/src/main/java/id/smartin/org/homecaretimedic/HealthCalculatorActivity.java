package id.smartin.org.homecaretimedic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HealthCalculatorActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btnBBI)
    Button btnBbi;
    @BindView(R.id.btnTaksirHamil)
    Button btnPregnantCalc;
    @BindView(R.id.btnKebCairan)
    Button btnFluidReq;
    @BindView(R.id.btnKebKalori)
    Button btnCaloryNeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_calculator);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        btnBbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthCalculatorActivity.this, CalculatorBBI.class);
                startActivity(intent);
            }
        });
        btnPregnantCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthCalculatorActivity.this, CalculatorPregnancyEst.class);
                startActivity(intent);
            }
        });
        btnFluidReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthCalculatorActivity.this, CalculatorFluidReq.class);
                startActivity(intent);
            }
        });
        btnCaloryNeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthCalculatorActivity.this, CalculatorCaloricNeeds.class);
                startActivity(intent);
            }
        });
    }
}
