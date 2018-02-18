package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.model.submitmodel.SubmitInfo;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;

public class AcceptanceActivity extends AppCompatActivity {
    public static String TAG = "[AcceptanceActivity]";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.selectedLayanan)
    TextView selectedLayanan;
    @BindView(R.id.selectedLokasi)
    TextView selectedLocation;
    @BindView(R.id.selectedGPSPos)
    TextView selectedGPSPos;
    @BindView(R.id.selectedKetLokasi)
    TextView selectedGPSLocInfo;
    @BindView(R.id.selectedTanggal)
    TextView selectedDate;
    @BindView(R.id.selectedJamLayanan)
    TextView selectedHour;
    @BindView(R.id.btnSubmitForm)
    Button submitTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptance);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        createTitleBar();

        selectedLayanan.setText(SubmitInfo.selectedHomecareService.getServiceName());
        selectedLocation.setText(SubmitInfo.selectedServicePlace.getNameLocation());
        selectedGPSPos.setText("("+SubmitInfo.selectedPlaceInfo.getLatitude()+","+SubmitInfo.selectedPlaceInfo.getLongitude()+")");
        selectedGPSLocInfo.setText(SubmitInfo.selectedPlaceInfo.getAdditionInfo());
        selectedDate.setText(SubmitInfo.selectedDateTime.getDate());
        selectedHour.setText(SubmitInfo.selectedDateTime.getTime());

        submitTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @SuppressLint("RestrictedApi")
    public void createTitleBar() {
        setSupportActionBar(toolbar);
        ViewFaceUtility.changeToolbarFont(toolbar, this, "fonts/Dosis-Bold.otf", R.color.theme_black);
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

    public void sendTransaction(){

    }
}
