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
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.manager.HomecareSessionManager;
import id.smartin.org.homecaretimedic.model.HomecareTransactionStatus;
import id.smartin.org.homecaretimedic.model.PaymentMethod;
import id.smartin.org.homecaretimedic.model.parammodel.HomecareTransParam;
import id.smartin.org.homecaretimedic.model.responsemodel.LoginResponse;
import id.smartin.org.homecaretimedic.model.submitmodel.SubmitInfo;
import id.smartin.org.homecaretimedic.tools.ConverterUtility;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;
import id.smartin.org.homecaretimedic.tools.restservice.APIClient;
import id.smartin.org.homecaretimedic.tools.restservice.HomecareTransactionAPIInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private HomecareTransactionAPIInterface homecareTransactionAPIInterface;
    private HomecareSessionManager homecareSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptance);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        createTitleBar();

        homecareSessionManager = new HomecareSessionManager(this, getApplicationContext());
        homecareTransactionAPIInterface = APIClient.getClientWithToken(homecareSessionManager, getApplicationContext()).create(HomecareTransactionAPIInterface.class);

        selectedLayanan.setText(SubmitInfo.selectedHomecareService.getServiceName());
        selectedLocation.setText(SubmitInfo.selectedServicePlace.getNameLocation());
        selectedGPSPos.setText("(" + SubmitInfo.selectedPlaceInfo.getLatitude() + "," + SubmitInfo.selectedPlaceInfo.getLongitude() + ")");
        selectedGPSLocInfo.setText(SubmitInfo.selectedPlaceInfo.getAdditionInfo());
        selectedDate.setText(SubmitInfo.selectedDateTime.getDate());
        selectedHour.setText(SubmitInfo.selectedDateTime.getTime());

        submitTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sendTransaction();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
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

    public void sendTransaction() throws ParseException {
        HomecareTransParam homecareTransParam = new HomecareTransParam();

        String selDate = SubmitInfo.selectedDateTime.getDate() + " " + SubmitInfo.selectedDateTime.getTime();
        String selDateFormat = "dd-MM-yyyy HH:mm";
        homecareTransParam.setDate(ConverterUtility.getTimeStamp(selDate, selDateFormat));
        homecareTransParam.setFixedPrice(0);
        homecareTransParam.setPrepaidPrice(0);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String currentDateandTime = sdf.format(new Date());
        Date date = sdf.parse(currentDateandTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, 1);
        homecareTransParam.setExpiredTransactionTime(calendar.getTimeInMillis());

        homecareTransParam.setReceiptPath("");
        homecareTransParam.setLocationLatitude(SubmitInfo.selectedPlaceInfo.getLatitude());
        homecareTransParam.setLocationLongitude(SubmitInfo.selectedPlaceInfo.getLongitude());
        homecareTransParam.setTransactionDescription("Mobile Transaction");
        homecareTransParam.setAssessmentList(SubmitInfo.assessmentList);
        homecareTransParam.setHomecareTransactionStatus(new HomecareTransactionStatus((long) 2));
        homecareTransParam.setHomecarePatientId(SubmitInfo.registeredPatient.get(0));
        homecareTransParam.setPaymentMethod(new PaymentMethod((long) 1));
        Call<ResponseBody> responseCall = homecareTransactionAPIInterface.insertNewTransaction(homecareTransParam);
        responseCall.enqueue(new Callback<ResponseBody>() {
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
