package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.adapter.HomecareServiceAdapter;
import id.smartin.org.homecaretimedic.customuicompt.GridSpacingItemDecoration;
import id.smartin.org.homecaretimedic.manager.HomecareSessionManager;
import id.smartin.org.homecaretimedic.model.HomecareService;
import id.smartin.org.homecaretimedic.model.ServicePlace;
import id.smartin.org.homecaretimedic.model.submitmodel.SubmitInfo;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;
import id.smartin.org.homecaretimedic.tools.restservice.APIClient;
import id.smartin.org.homecaretimedic.tools.restservice.HomecareServiceAPIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LayananLokasiActivity extends AppCompatActivity {
    public static String TAG = "[LayananLokasiActivity]";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pilihKota)
    Spinner pilihKota;
    @BindView(R.id.recycler_homestay)
    RecyclerView recyclerViewHomestay;
    @BindView(R.id.recycler_homevisit)
    RecyclerView recyclerViewHomevisit;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private List<HomecareService> homecareServiceListHomevisit;
    private List<HomecareService> homecareServiceListHomestay;
    private List<ServicePlace> servicePlaceList;
    private HomecareServiceAdapter adapterServiceHomevisit, adapterServiceHomestay;
    private ArrayAdapter<ServicePlace> adapterServicePlace;
    private HomecareSessionManager homecareSessionManager;

    private HomecareServiceAPIInterface homecareServiceAPIInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layanan_lokasi);
        ButterKnife.bind(this);

        createTitleBar();
        homecareSessionManager = new HomecareSessionManager(this, getApplicationContext());
        homecareServiceAPIInterface = APIClient.getClientWithToken(homecareSessionManager, getApplicationContext()).create(HomecareServiceAPIInterface.class);

        homecareServiceListHomevisit = new ArrayList<HomecareService>();
        homecareServiceListHomestay = new ArrayList<HomecareService>();
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
        recyclerViewHomestay.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);

        recyclerViewHomestay.setLayoutManager(layoutManager);
        recyclerViewHomestay.addItemDecoration(new GridSpacingItemDecoration(3, GridSpacingItemDecoration.dpToPx(1), true, 0));
        recyclerViewHomestay.setItemAnimator(new DefaultItemAnimator());
        adapterServiceHomestay = new HomecareServiceAdapter(this, getApplicationContext(), homecareServiceListHomestay);
        recyclerViewHomestay.setAdapter(adapterServiceHomestay);

        recyclerViewHomevisit.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(getApplicationContext(), 3);
        recyclerViewHomevisit.setLayoutManager(layoutManager2);
        recyclerViewHomevisit.addItemDecoration(new GridSpacingItemDecoration(3, GridSpacingItemDecoration.dpToPx(1), true, 0));
        recyclerViewHomevisit.setItemAnimator(new DefaultItemAnimator());
        adapterServiceHomevisit = new HomecareServiceAdapter(this, getApplicationContext(), homecareServiceListHomevisit);
        recyclerViewHomevisit.setAdapter(adapterServiceHomevisit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateLayanan();
            }
        });
        populateLayanan();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateServicePlaceDummy();
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

    public void populateLayanan() {
        Call<ArrayList<HomecareService>> services = homecareServiceAPIInterface.getHomecareServices();
        services.enqueue(new Callback<ArrayList<HomecareService>>() {
            @Override
            public void onResponse(Call<ArrayList<HomecareService>> call, Response<ArrayList<HomecareService>> response) {
                Log.i(TAG, response.raw().toString());
                ArrayList<HomecareService> services = response.body();
                if (response.code() != 200) {
                    Toast.makeText(getApplicationContext(), "Unauthorized Request!", Toast.LENGTH_LONG).show();
                } else {
                    if (services != null) {
                        homecareServiceListHomevisit.clear();
                        homecareServiceListHomestay.clear();
                        for (int i = 0; i < services.size(); i++) {
                            if (services.get(i).getServiceCategory().equals("homestay")) {
                                homecareServiceListHomestay.add(services.get(i));
                            } else {
                                homecareServiceListHomevisit.add(services.get(i));
                            }
                        }
                        adapterServiceHomevisit.notifyDataSetChanged();
                        adapterServiceHomestay.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<HomecareService>> call, Throwable t) {
                Log.i(TAG, t.getMessage() + " " + t.getCause());
                homecareSessionManager.logout();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.session_time_out), Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
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
