package id.pptik.org.homecaretimedic.mainfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.pptik.org.homecaretimedic.HealthCalculatorActivity;
import id.pptik.org.homecaretimedic.HealthProductActivity;
import id.pptik.org.homecaretimedic.HealthVideoActivity;
import id.pptik.org.homecaretimedic.HomecareActivity;
import id.pptik.org.homecaretimedic.MedicalRecordActivity;
import id.pptik.org.homecaretimedic.MedicineReminderActivity;
import id.pptik.org.homecaretimedic.R;

/**
 * Created by Hafid on 8/22/2017.
 */

public class HomeFragment extends Fragment {

    @BindView(R.id.btnHomecare)
    ImageButton btnHomecare;
    @BindView(R.id.btnMedicalRecord)
    ImageButton btnMedicalRec;
    @BindView(R.id.btnHealthCalculator)
    ImageButton btnHealthCalc;
    @BindView(R.id.btnHealthProduct)
    ImageButton btnHealthProd;
    @BindView(R.id.btnHealthVideo)
    ImageButton btnHealthVid;
    @BindView(R.id.btnMedicineReminder)
    ImageButton btnMedicineRemind;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vwInflater = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, vwInflater);
        btnMedicalRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MedicalRecordActivity.class);
                startActivity(intent);
            }
        });
        btnHomecare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomecareActivity.class);
                startActivity(intent);
            }
        });
        btnHealthCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HealthCalculatorActivity.class);
                startActivity(intent);
            }
        });
        btnHealthProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HealthProductActivity.class);
                startActivity(intent);
            }
        });
        btnHealthVid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HealthVideoActivity.class);
                startActivity(intent);
            }
        });
        btnMedicineRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MedicineReminderActivity.class);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return vwInflater;
    }
}
