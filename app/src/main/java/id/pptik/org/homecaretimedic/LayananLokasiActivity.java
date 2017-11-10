package id.pptik.org.homecaretimedic;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import id.pptik.org.homecaretimedic.config.Constants;
import id.pptik.org.homecaretimedic.manager.HomecareSessionManager;
import id.pptik.org.homecaretimedic.model.Layanan;
import id.pptik.org.homecaretimedic.model.ServicePlace;
import id.pptik.org.homecaretimedic.model.submitmodel.SubmitInfo;
import id.pptik.org.homecaretimedic.tools.HomecareRestClient;

public class LayananLokasiActivity extends AppCompatActivity {
    public static String TAG = "[LayananLokasiActivity]";

    @BindView(R.id.btnGotoMapSelector)
    Button btnGoSubmit;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pilihLayanan)
    Spinner pilihLayanan;
    @BindView(R.id.pilihKota)
    Spinner pilihKota;

    private List<Layanan> layananList;
    private List<ServicePlace> servicePlaceList;
    private ArrayAdapter<Layanan> adapterLayanan;
    private ArrayAdapter<ServicePlace> adapterServicePlace;
    private HomecareSessionManager homecareSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layanan_lokasi);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        createTitleBar();
        homecareSessionManager = new HomecareSessionManager(this, getApplicationContext());
        layananList = new ArrayList<Layanan>();
        servicePlaceList = new ArrayList<ServicePlace>();
        adapterLayanan = new ArrayAdapter<Layanan>(this, android.R.layout.simple_spinner_item, layananList);
        adapterServicePlace = new ArrayAdapter<ServicePlace>(this, android.R.layout.simple_spinner_item, servicePlaceList);
        pilihLayanan.setAdapter(adapterLayanan);
        pilihKota.setAdapter(adapterServicePlace);
        btnGoSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson g = new Gson();
                Layanan l = (Layanan) pilihLayanan.getSelectedItem();
                ServicePlace s = (ServicePlace) pilihKota.getSelectedItem();
                SubmitInfo.selectedLayanan = l;
                SubmitInfo.selectedServicePlace = s;
                Intent mapSelector = new Intent(LayananLokasiActivity.this, MapSelectorActivity.class);
                //mapSelector.putExtra("jenis_layanan",g.toJson(l,Layanan.class));
                //mapSelector.putExtra("kota_layanan",g.toJson(s, ServicePlace.class));
                startActivity(mapSelector);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateLayananDummy();
        populateServicePlaceDummy();
        //populateLayanan();
    }

    public void createTitleBar() {
        ActionBar mActionbar = getSupportActionBar();
        mActionbar.setDisplayHomeAsUpEnabled(false);
        mActionbar.setDefaultDisplayHomeAsUpEnabled(false);
        mActionbar.setDisplayShowTitleEnabled(false);
        mActionbar.setDisplayShowHomeEnabled(false);
        mActionbar.setDisplayShowCustomEnabled(true);
        View view = getLayoutInflater().inflate(R.layout.action_bar_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        mActionbar.setCustomView(view, params);
    }

    public void populateLayanan(){
        layananList.clear();
        String token = homecareSessionManager.getUserDetail().getSession();
        Log.i(TAG, "TOKEN "+token);
        HomecareRestClient.get(Constants.ROUTE_LAYANANLIST, token ,null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Gson g = new Gson();
                for (int i = 0; i < response.length() ; i++){
                    try {
                        Layanan layanan = g.fromJson(response.get(i).toString(), Layanan.class);
                        layananList.add(layanan);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapterLayanan.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getApplicationContext(),"Invalid token ", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void populateLayananDummy(){
        layananList.clear();
        Layanan layanan = new Layanan();
        Layanan layanan1 = new Layanan();
        layanan.setId(1);
        layanan.setKodeLayanan("TM1");
        layanan.setNamaLayanan("24Jam");
        layanan.setTipeLayanan("HomeStay");
        layananList.add(layanan);
        layanan1.setId(2);
        layanan1.setKodeLayanan("TM4");
        layanan1.setNamaLayanan("Perawatan Luka");
        layanan1.setTipeLayanan("HomeVisit");
        layananList.add(layanan1);
        adapterLayanan.notifyDataSetChanged();
    }

    public void populateServicePlaceDummy(){
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
