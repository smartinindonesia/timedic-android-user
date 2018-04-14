package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import id.smartin.org.homecaretimedic.config.Constants;
import id.smartin.org.homecaretimedic.model.HomecareService;
import id.smartin.org.homecaretimedic.model.ServicePlace;
import id.smartin.org.homecaretimedic.model.submitmodel.PlaceInfo;
import id.smartin.org.homecaretimedic.model.submitmodel.SubmitInfo;
import id.smartin.org.homecaretimedic.service.LocationService;
import id.smartin.org.homecaretimedic.tools.TextFormatter;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;

public class MapSelectorActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static String TAG = "[MapSelectorActivity]";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.keepCenter)
    FloatingActionButton fab;
    @BindView(R.id.btnGoToAssest)
    Button btnGoToAssest;
    @BindView(R.id.additionalInfo)
    EditText additionalInfo;
    @BindView(R.id.addressTitle)
    TextView addressTitle;
    EditText autocompletePlaces;

    private GoogleMap googleMap;

    private IntentFilter mIntentFilter;
    private Intent serviceIntent;
    private boolean isFirstSetMap = true;
    private boolean zoomToMyLoc = false;

    private HomecareService homecareService;
    private LatLng myLocation;
    private ServicePlace servicePlace;
    private Gson g = new Gson();
    private SweetAlertDialog progressDialog;
    private Marker currentLocationMarker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_selector);
        ButterKnife.bind(this);
        initilizeMap();
        createTitleBar();
        initProgressDialog();

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(Constants.actionPosition);

        serviceIntent = new Intent(this, LocationService.class);
        startService(serviceIntent);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomToMyLoc = true;
                showProgressDialog();
            }
        });

        PlaceAutocompleteFragment places = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompletePlaces = (EditText) places.getView().findViewById(R.id.place_autocomplete_search_input);
        autocompletePlaces.setTextSize(12.0f);
        autocompletePlaces.setHintTextColor(Color.WHITE);
        autocompletePlaces.setTextColor(Color.WHITE);
        autocompletePlaces.setBackgroundColor(getResources().getColor(R.color.colorPrimaryTransparent));

        places.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                myLocation = place.getLatLng();
                //animateMarker(currentLocationMarker,myLocation,false);
                currentLocationMarker.setPosition(myLocation);
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
                Toast.makeText(getApplicationContext(), place.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Status status) {

                Toast.makeText(getApplicationContext(), status.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        btnGoToAssest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapSelectorActivity.this, SelectCustomerActivity.class);
                PlaceInfo placeInfo = new PlaceInfo();
                placeInfo.setLatitude(currentLocationMarker.getPosition().latitude);
                placeInfo.setLongitude(currentLocationMarker.getPosition().longitude);
                placeInfo.setAdditionInfo(additionalInfo.getText().toString());
                SubmitInfo.selectedPlaceInfo = placeInfo;
                startActivity(intent);
                finish();
            }
        });

        //Intent intenExtras = getIntent();
        homecareService = SubmitInfo.selectedHomecareService;//g.fromJson(intenExtras.getStringExtra("jenis_layanan"), HomecareService.class);
        servicePlace = SubmitInfo.selectedServicePlace;//g.fromJson(intenExtras.getStringExtra("kota_layanan"), ServicePlace.class);
        setFonts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.actionPosition)) {
                if (zoomToMyLoc) {
                    myLocation = new LatLng(intent.getDoubleExtra("latitude", 0), intent.getDoubleExtra("longitude", 0));
                    animateMarker(currentLocationMarker, myLocation, false);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
                    zoomToMyLoc = false;
                    dissmissProgressDialog();
                }
            }
        }
    };


    public void initProgressDialog() {
        progressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDialog.setTitleText("Loading...");
        progressDialog.setContentText("Sedang menunggu update lokasi GPS!");
        progressDialog.setCanceledOnTouchOutside(true);
    }

    public void showProgressDialog() {
        initProgressDialog();
        progressDialog.show();
    }

    public void dissmissProgressDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
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

    private void initilizeMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(mReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        stopService(serviceIntent);
        super.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        myLocation = new LatLng(servicePlace.getLatitude(), servicePlace.getLongitude());
        currentLocationMarker = this.googleMap.addMarker(new MarkerOptions().draggable(true).position(myLocation).title("Set My Location"));
        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                myLocation = marker.getPosition();
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                myLocation = marker.getPosition();
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                myLocation = marker.getPosition();
            }
        });
        currentLocationMarker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_myloc));
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
        this.googleMap.animateCamera(CameraUpdateFactory.zoomTo(18), 2000, null);
    }

    public void animateMarker(final Marker marker, final LatLng toPosition,
                              final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = googleMap.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 500;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }

    private void setFonts() {
        ArrayList<TextView> arrayList = new ArrayList<>();
        arrayList.add(autocompletePlaces);
        arrayList.add(addressTitle);
        arrayList.add(additionalInfo);
        arrayList.add(btnGoToAssest);
        ViewFaceUtility.applyFonts(arrayList, this, "fonts/Dosis-Regular.otf");
    }
}
