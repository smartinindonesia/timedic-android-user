package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.model.ServicePlace;
import id.smartin.org.homecaretimedic.model.submitmodel.SubmitInfo;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;

public class LocationOnlyActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btnNext)
    Button btnNext;
    @BindView(R.id.pilihKota)
    Spinner pilihKota;

    private List<ServicePlace> servicePlaceList;
    private ArrayAdapter<ServicePlace> adapterServicePlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_only);
        ButterKnife.bind(this);

        createTitleBar();

        servicePlaceList = new ArrayList<ServicePlace>();
        adapterServicePlace = new ArrayAdapter<ServicePlace>(this, android.R.layout.simple_spinner_item, servicePlaceList);
        pilihKota.setAdapter(adapterServicePlace);
        pilihKota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SubmitInfo.selectedServicePlace = (ServicePlace) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapSelectorActivity.class);
                startActivity(intent);
                finish();
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
    protected void onResume() {
        super.onResume();
        populateServicePlaceDummy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        onBackPressed();
        return true;
    }

    public void populateServicePlaceDummy() {
        servicePlaceList.clear();
        ServicePlace s0 = new ServicePlace();
        s0.setIdLocation(1);
        s0.setLatitude(-6.9147);
        s0.setLongitude(107.6098);
        s0.setNameLocation("Bandung");
        servicePlaceList.add(s0);
        adapterServicePlace.notifyDataSetChanged();
    }
}
