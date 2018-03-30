package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.manager.HomecareSessionManager;
import id.smartin.org.homecaretimedic.model.parammodel.RegLabParam;
import id.smartin.org.homecaretimedic.model.submitmodel.SubmitInfo;
import id.smartin.org.homecaretimedic.tools.ConverterUtility;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;
import id.smartin.org.homecaretimedic.tools.restservice.APIClient;
import id.smartin.org.homecaretimedic.tools.restservice.HomecareTransactionAPIInterface;
import id.smartin.org.homecaretimedic.tools.restservice.LaboratoryTransactionAPIInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LabAcceptanceActivity extends AppCompatActivity {

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

    private LaboratoryTransactionAPIInterface laboratoryTransactionAPIInterface;
    private HomecareSessionManager homecareSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_acceptance);
        ButterKnife.bind(this);

        createTitleBar();

        homecareSessionManager = new HomecareSessionManager(this, getApplicationContext());
        laboratoryTransactionAPIInterface = APIClient.getClientWithToken(homecareSessionManager, getApplicationContext()).create(LaboratoryTransactionAPIInterface.class);

        selectedLayanan.setText("Cek Laboratorium");
        selectedLocation.setText(SubmitInfo.selectedServicePlace.getNameLocation());
        selectedGPSPos.setText("(" + SubmitInfo.selectedPlaceInfo.getLatitude() + "," + SubmitInfo.selectedPlaceInfo.getLongitude() + ")");
        selectedGPSLocInfo.setText(SubmitInfo.selectedPlaceInfo.getAdditionInfo());
        selectedDate.setText(SubmitInfo.selectedDateTime.getDate());
        selectedHour.setText(SubmitInfo.selectedDateTime.getTime());

        submitTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNewTransaction();
            }
        });
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

    public void sendNewTransaction() {
        RegLabParam param = new RegLabParam();
        String selDate = SubmitInfo.selectedDateTime.getDate() + " " + SubmitInfo.selectedDateTime.getTime();
        String selDateFormat = "dd-MM-yyyy HH:mm";
        param.setDate(ConverterUtility.getTimeStamp(selDate, selDateFormat));
        param.setEmployeeIdNumber("");
        Double totalPrice = 0.0;
        for (int i = 0; i < SubmitInfo.selectedLabPackages.size(); i++) {
            totalPrice = totalPrice + SubmitInfo.selectedLabPackages.get(0).getPrice();
        }
        for (int i = 0; i < SubmitInfo.selectedLabServices.size(); i++) {
            totalPrice = totalPrice + SubmitInfo.selectedLabServices.get(0).getHargaLayanan();
        }
        param.setTotalPrice(totalPrice);
        param.setTransactionDescription("Mobile transaction");
        param.setLocationLatitude(SubmitInfo.selectedPlaceInfo.getLatitude());
        param.setLocationLatitude(SubmitInfo.selectedPlaceInfo.getLongitude());
        param.setLaboratorySelectedPackageTransactionCollection(SubmitInfo.selectedLabPackages);
        param.setLaboratorySelectedServiceTransactionCollection(SubmitInfo.selectedLabServices);
        param.setIdLaboratoryClinic((long) 1);
        param.setIdPatient((long) SubmitInfo.registeredPatient.get(0).getId());
        param.setTransactionStatus((long) 1);
        Call<ResponseBody> service = laboratoryTransactionAPIInterface.insertNewTransaction(param);
        service.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 201) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.transaction_success), Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.transaction_failed), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                call.cancel();
                homecareSessionManager.logout();
            }
        });
    }
}
