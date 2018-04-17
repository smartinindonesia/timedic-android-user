package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import id.smartin.org.homecaretimedic.manager.HomecareSessionManager;
import id.smartin.org.homecaretimedic.model.Patient;
import id.smartin.org.homecaretimedic.model.parammodel.RegPatientParam;
import id.smartin.org.homecaretimedic.model.submitmodel.PickedDateTime;
import id.smartin.org.homecaretimedic.tools.ConverterUtility;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;
import id.smartin.org.homecaretimedic.tools.restservice.APIClient;
import id.smartin.org.homecaretimedic.tools.restservice.PatientAPIInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserCustomerActivity extends AppCompatActivity {
    public static final String TAG = "[AddUserCustomerAct]";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.religion)
    Spinner religionsSpinner;
    @BindView(R.id.genderSelector)
    Spinner genderSpinner;
    @BindView(R.id.patientsName)
    EditText patientsName;
    @BindView(R.id.heightMeasurement)
    EditText height;
    @BindView(R.id.weightMeasurement)
    EditText weight;
    @BindView(R.id.dateOfBirth)
    EditText dob;
    @BindView(R.id.placeOfBirth)
    EditText pob;
    @BindView(R.id.selectDOB)
    ImageButton selectDob;
    @BindView(R.id.addUser)
    Button addUser;

    @BindView(R.id.patientsNameTitle)
    TextView patientsNameTitle;
    @BindView(R.id.religionTitle)
    TextView religionTitle;
    @BindView(R.id.genderTitle)
    TextView genderTitle;
    @BindView(R.id.heightMeasurementTitle)
    TextView heightMeasurementTitle;
    @BindView(R.id.weightMeasurementTitle)
    TextView weightMeasurementTitle;
    @BindView(R.id.placeOfBirthTitle)
    TextView placeOfBirthTitle;
    @BindView(R.id.dateOfBirthTitle)
    TextView dateOfBirthTitle;



    ArrayAdapter<CharSequence> adapterGender, adapterReligion;

    private DatePickerDialog datePickerDialog;
    private PickedDateTime pickedDateTime;

    private PatientAPIInterface patientAPIInterface;
    private HomecareSessionManager homecareSessionManager;

    private SweetAlertDialog progressDialog;

    private Patient patientP;
    private boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_customer);
        ButterKnife.bind(this);
        createTitleBar();

        homecareSessionManager = new HomecareSessionManager(this, getApplicationContext());
        patientAPIInterface = APIClient.getClientWithToken(homecareSessionManager, getApplicationContext()).create(PatientAPIInterface.class);

        adapterReligion = ArrayAdapter.createFromResource(this, R.array.religion, android.R.layout.simple_spinner_item);
        adapterReligion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        religionsSpinner.setAdapter(adapterReligion);

        adapterGender = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapterGender);

        selectDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int day = mcurrentTime.get(Calendar.DAY_OF_MONTH) + 2;
                int month = mcurrentTime.get(Calendar.MONTH);
                int year = mcurrentTime.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(AddUserCustomerActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mcurrentTime.get(Calendar.YEAR), mcurrentTime.get(Calendar.MONTH), mcurrentTime.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setTitle("Pilih tanggal pelayanan");
                datePickerDialog.show();
                dob.setText(day + "-" + (month + 1) + "-" + year);
            }
        });
        openEditForm();
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editMode) {
                    editPatientData();
                } else {
                    sendNewPatientData();
                }
            }
        });
        setFonts();
    }

    private void editPatientData() {
        RegPatientParam patient = new RegPatientParam();
        if (height.getText().toString().equals("")) {
            height.setText("0");
        }
        if (weight.getText().toString().equals("")) {
            weight.setText("0");
        }
        patient.setHeight(Double.parseDouble(height.getText().toString()));
        patient.setWeight(Double.parseDouble(weight.getText().toString()));
        Long dobs = ConverterUtility.getTimeStamp(dob.getText().toString(), "dd-MM-yyyy");
        patient.setDateOfBirth(dobs);
        patient.setGender(genderSpinner.getSelectedItem().toString());
        patient.setIdAppUser(homecareSessionManager.getUserDetail().getId());
        patient.setName(patientsName.getText().toString());
        patient.setPlaceOfBirth(pob.getText().toString());
        showProgressDialog("Menyimpan data pasien");
        Call<ResponseBody> services = patientAPIInterface.editUser(patientP.getId(), patient);
        services.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    dissmissProgressDialog();
                    Toast.makeText(getApplicationContext(), "Berhasil mengubah data pasien!", Toast.LENGTH_LONG).show();
                    finish();
                } else if (response.code() == 201) {
                    dissmissProgressDialog();
                    Toast.makeText(getApplicationContext(), "Berhasil mengubah data pasien!", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    dissmissProgressDialog();
                    Log.i(TAG, response.raw().toString());
                    Log.i(TAG, "failure");
                    Toast.makeText(getApplicationContext(), "Gagal mengubah data pasien!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dissmissProgressDialog();
                Log.i(TAG, "FAILURE");
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "Gagal mengubah data pasien!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void sendNewPatientData() {
        RegPatientParam patient = new RegPatientParam();
        if (height.getText().toString().equals("")) {
            height.setText("0");
        }
        if (weight.getText().toString().equals("")) {
            weight.setText("0");
        }
        patient.setHeight(Double.parseDouble(height.getText().toString()));
        patient.setWeight(Double.parseDouble(weight.getText().toString()));
        patient.setDateOfBirth(ConverterUtility.getTimeStamp(dob.getText().toString(), "dd-MM-yyyy"));
        //patient.setDateOfBirth(dob.getText().toString());
        patient.setGender(genderSpinner.getSelectedItem().toString());
        patient.setIdAppUser(homecareSessionManager.getUserDetail().getId());
        patient.setName(patientsName.getText().toString());
        patient.setPlaceOfBirth(pob.getText().toString());
        showProgressDialog("Menyimpan data pasien");
        Call<Patient> services = patientAPIInterface.insertNewPatient(patient);
        services.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if (response.code() == 200) {
                    dissmissProgressDialog();
                    Toast.makeText(getApplicationContext(), "Berhasil menyimpan data pasien baru!", Toast.LENGTH_LONG).show();
                    finish();
                } else if (response.code() == 201) {
                    dissmissProgressDialog();
                    Toast.makeText(getApplicationContext(), "Berhasil menyimpan data pasien baru!", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    dissmissProgressDialog();
                    Log.i(TAG, response.raw().toString());
                    Log.i(TAG, "failure");
                    Toast.makeText(getApplicationContext(), "Gagal menyimpan data pasien baru!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                dissmissProgressDialog();
                Log.i(TAG, "FAILURE");
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "Gagal menyimpan data pasien baru!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void openEditForm() {
        patientP = (Patient) getIntent().getSerializableExtra("patient_data");
        if (patientP != null) {
            religionsSpinner.setSelection(adapterReligion.getPosition(patientP.getReligion()));
            genderSpinner.setSelection(adapterGender.getPosition(patientP.getGender()));
            patientsName.setText(patientP.getName());
            height.setText(String.valueOf(patientP.getHeight()));
            weight.setText(String.valueOf(patientP.getWeight()));
            dob.setText(ConverterUtility.getDateString(patientP.getDateOfBirth()));
            pob.setText(patientP.getPlaceOfBirth());
            addUser.setHint("Edit Pasien");
            editMode = true;
        }
    }

    public void initProgressDialog(String message) {
        progressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDialog.setTitleText("Loading...");
        progressDialog.setContentText(message);
        progressDialog.setCanceledOnTouchOutside(true);
    }

    public void showProgressDialog(String message) {
        initProgressDialog(message);
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

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private void setFonts(){
        ArrayList<TextView> arrayList = new ArrayList<>();
        arrayList.add(patientsNameTitle);
        arrayList.add(religionTitle);
        arrayList.add(genderTitle);
        arrayList.add(heightMeasurementTitle);
        arrayList.add(weightMeasurementTitle);
        arrayList.add(placeOfBirthTitle);
        arrayList.add(dateOfBirthTitle);

        arrayList.add(patientsName);
        arrayList.add(height);
        arrayList.add(weight);
        arrayList.add(dob);
        arrayList.add(pob);
        arrayList.add(addUser);

        ViewFaceUtility.applyFonts(arrayList, this, "fonts/Dosis-Medium.otf");
    }
}
