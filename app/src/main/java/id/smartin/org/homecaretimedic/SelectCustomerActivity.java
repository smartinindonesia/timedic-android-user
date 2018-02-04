package id.smartin.org.homecaretimedic;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.adapter.PatientAdapter;
import id.smartin.org.homecaretimedic.customuicompt.RecyclerTouchListener;
import id.smartin.org.homecaretimedic.manager.HomecareSessionManager;
import id.smartin.org.homecaretimedic.model.HomecareService;
import id.smartin.org.homecaretimedic.model.Patient;
import id.smartin.org.homecaretimedic.tools.restservice.APIClient;
import id.smartin.org.homecaretimedic.tools.restservice.HomecareServiceAPIInterface;
import id.smartin.org.homecaretimedic.tools.restservice.PatientAPIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectCustomerActivity extends AppCompatActivity {
    public static final String TAG = "[SelectCustomerAct]";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.customer_list)
    RecyclerView patient_list;
    @BindView(R.id.addNewCustomer)
    Button btnAddPatient;

    PatientAdapter patientAdapter;
    List<Patient> patientList = new ArrayList<>();

    private PatientAPIInterface patientAPIInterface;
    private HomecareSessionManager homecareSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_customer);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        createTitleBar();

        homecareSessionManager = new HomecareSessionManager(this, getApplicationContext());
        patientAPIInterface = APIClient.getClientWithToken(homecareSessionManager.getToken(), getApplicationContext()).create(PatientAPIInterface.class);

        patientAdapter = new PatientAdapter(this, patientList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        patient_list.setLayoutManager(mLayoutManager);

        patient_list.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), patient_list, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Patient patient = patientAdapter.getItem(position);
                Toast.makeText(getApplicationContext(), patient.getName(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        patient_list.setItemAnimator(new DefaultItemAnimator());
        patient_list.setAdapter(patientAdapter);

        btnAddPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectCustomerActivity.this, AddUserCustomerActivity.class);
                startActivity(intent);
            }
        });
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

    private void populatePatientList() {
        Call<ArrayList<Patient>> services = patientAPIInterface.getPatientByUserId(homecareSessionManager.getUserDetail().getId());
        services.enqueue(new Callback<ArrayList<Patient>>() {
            @Override
            public void onResponse(Call<ArrayList<Patient>> call, Response<ArrayList<Patient>> response) {
                Log.i(TAG, response.raw().toString());
                if (response.code() == 200) {
                    patientList.clear();
                    for (int i = 0; i < response.body().size(); i++) {
                        patientList.add(response.body().get(i));
                        Log.i(TAG, response.body().get(i).toString());
                    }
                    patientAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Patient>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        populatePatientList();
        patientAdapter.notifyDataSetChanged();
    }

}
