package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import id.smartin.org.homecaretimedic.tools.TextFormatter;
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
    @BindView(R.id.downPayment)
    TextView downPayment;
    @BindView(R.id.totalApproxCash)
    TextView totalApproxCash;

    @BindView(R.id.summaryHeader)
    TextView summaryHeader;
    @BindView(R.id.serviceTitle)
    TextView serviceTitle;
    @BindView(R.id.serviceLocationTitle)
    TextView serviceLocationTitle;
    @BindView(R.id.serviceDateTitle)
    TextView serviceDateTitle;
    @BindView(R.id.serviceTimeTitle)
    TextView serviceTimeTitle;
    @BindView(R.id.serviceGPSPosTitle)
    TextView serviceGPSPosTitle;
    @BindView(R.id.serviceAddressTitle)
    TextView serviceAddressTitle;
    @BindView(R.id.thanks1)
    TextView thanks1;
    @BindView(R.id.thanks2)
    TextView thanks2;
    @BindView(R.id.thanks3)
    TextView thanks3;
    @BindView(R.id.thanks4)
    TextView thanks4;
    @BindView(R.id.thanks5)
    TextView thanks5;


    private HomecareTransactionAPIInterface homecareTransactionAPIInterface;
    private HomecareSessionManager homecareSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptance);
        ButterKnife.bind(this);
        createTitleBar();

        homecareSessionManager = new HomecareSessionManager(this, getApplicationContext());
        homecareTransactionAPIInterface = APIClient.getClientWithToken(homecareSessionManager, getApplicationContext()).create(HomecareTransactionAPIInterface.class);
        fillHomecareTransInfo();
        submitTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //gotoBillingMethodActivity();
                try {

                    sendTransaction();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
        setFonts();
    }

    public void fillHomecareTransInfo() {
        selectedLayanan.setText(SubmitInfo.selectedHomecareService.getServiceName());
        selectedLocation.setText(SubmitInfo.selectedServicePlace.getNameLocation());
        selectedGPSPos.setText("(" + SubmitInfo.selectedPlaceInfo.getLatitude() + "," + SubmitInfo.selectedPlaceInfo.getLongitude() + ")");
        selectedGPSLocInfo.setText(SubmitInfo.selectedPlaceInfo.getAdditionInfo());
        selectedDate.setText(SubmitInfo.selectedDateTime.getDate());
        selectedHour.setText(SubmitInfo.selectedDateTime.getTime());
        downPayment.setText(TextFormatter.doubleToRupiah(SubmitInfo.selectedHomecareService.getVisitCost()));
        totalApproxCash.setText(TextFormatter.doubleToRupiah(SubmitInfo.selectedHomecareService.getPriceRange()) + " s.d " + TextFormatter.doubleToRupiah(SubmitInfo.selectedHomecareService.getPriceRange() + 100000));
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
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void sendTransaction() throws ParseException {
        HomecareTransParam homecareTransParam = new HomecareTransParam();

        String selDate = SubmitInfo.selectedDateTime.getDate() + " " + SubmitInfo.selectedDateTime.getTime();
        String selDateFormat = "dd-MM-yyyy HH:mm";
        homecareTransParam.setDate(ConverterUtility.getTimeStamp(selDate, selDateFormat));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String currentDateandTime = sdf.format(new Date());
        Date date = sdf.parse(currentDateandTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, 1);
        homecareTransParam.setExpiredTransactionTime(calendar.getTimeInMillis());
        homecareTransParam.setPrepaidPrice(SubmitInfo.selectedHomecareService.getVisitCost());
        if (SubmitInfo.selectedHomecareService.getServiceCategory().equals("homevisit")) {
            homecareTransParam.setFixedPrice(SubmitInfo.selectedHomecareService.getPriceRange());
        } else {
            homecareTransParam.setFixedPrice(0.0);
        }
        if (SubmitInfo.selectedHomecareService.getServiceName().equals("Perawatan Luka")) {
            homecareTransParam.setFixedPrice(0.0);
        }
        homecareTransParam.setPredictionPrice(TextFormatter.doubleToRupiah(SubmitInfo.selectedHomecareService.getPriceRange()) + " s.d " + TextFormatter.doubleToRupiah(SubmitInfo.selectedHomecareService.getPriceRange() + 100000));
        homecareTransParam.setReceiptPath("");
        homecareTransParam.setLocationLatitude(SubmitInfo.selectedPlaceInfo.getLatitude());
        homecareTransParam.setLocationLongitude(SubmitInfo.selectedPlaceInfo.getLongitude());
        homecareTransParam.setTransactionDescription("Mobile Transaction");
        homecareTransParam.setAssessmentList(SubmitInfo.assessmentList);
        homecareTransParam.setHomecareTransactionStatus(new HomecareTransactionStatus((long) 8));
        homecareTransParam.setHomecarePatientId(SubmitInfo.registeredPatient.get(0));
        homecareTransParam.setPaymentMethod(new PaymentMethod((long) 1));
        homecareTransParam.setSelectedService(SubmitInfo.selectedHomecareService.getServiceName());
        homecareTransParam.setFullAddress(SubmitInfo.selectedPlaceInfo.getAdditionInfo());
        Call<ResponseBody> responseCall = homecareTransactionAPIInterface.insertNewTransaction(homecareTransParam);
        responseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 201) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.transaction_success), Toast.LENGTH_LONG).show();
                    gotoThanksPage();
                    finish();
                    SubmitInfo.clearAllData();
                    //gotoBillingMethodActivity();
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

    private void gotoBillingMethodActivity() {
        Intent intent = new Intent(this, BillingMethodActivity.class);
        startActivity(intent);
    }

    private void gotoThanksPage() {
        Intent intent = new Intent(this, ThanksPageActivity.class);
        startActivity(intent);
    }

    private void setFonts() {
        ArrayList<TextView> arrayList = new ArrayList<>();
        ArrayList<TextView> arrayListB = new ArrayList<>();
        arrayListB.add(summaryHeader);
        arrayListB.add(serviceTitle);
        arrayListB.add(serviceLocationTitle);
        arrayListB.add(serviceGPSPosTitle);
        arrayListB.add(serviceAddressTitle);
        arrayListB.add(serviceDateTitle);
        arrayListB.add(serviceTimeTitle);
        arrayList.add(selectedLayanan);
        arrayList.add(selectedLocation);
        arrayList.add(selectedGPSPos);
        arrayList.add(selectedGPSLocInfo);
        arrayList.add(selectedDate);
        arrayList.add(selectedHour);
        arrayList.add(submitTransaction);
        arrayList.add(downPayment);
        arrayList.add(totalApproxCash);
        arrayList.add(thanks1);
        arrayList.add(thanks2);
        arrayList.add(thanks3);
        arrayList.add(thanks4);
        arrayList.add(thanks5);
        ViewFaceUtility.applyFonts(arrayList, this, "fonts/Dosis-Regular.otf");
        ViewFaceUtility.applyFonts(arrayListB, this, "fonts/Dosis-Bold.otf");
    }

}
