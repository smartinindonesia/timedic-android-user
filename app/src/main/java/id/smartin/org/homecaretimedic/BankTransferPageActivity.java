package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.manager.HomecareSessionManager;
import id.smartin.org.homecaretimedic.tools.PaymentUtility;
import id.smartin.org.homecaretimedic.tools.TextFormatter;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;

public class BankTransferPageActivity extends AppCompatActivity {
    public static final String TAG = "[BankTransferPageAct]";

    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.expirityDateTitle)
    TextView expirityDateTitle;
    @BindView(R.id.expirityDate)
    TextView expirityDate;
    @BindView(R.id.accountBillReceiverTitle)
    TextView accountBillReceiverTitle;
    @BindView(R.id.paymentLogo)
    ImageView paymentLogo;
    @BindView(R.id.paymentAccountBillReceiver)
    TextView paymentAccountBillReceiver;
    @BindView(R.id.accountBillReceiver)
    TextView accountBillReceiver;
    @BindView(R.id.accountBillReceiverCopy)
    TextView accountBillReceiverCopy;
    @BindView(R.id.totalPaidPrice)
    TextView totalPaidPrice;
    @BindView(R.id.paidPrice)
    TextView paidPrice;
    @BindView(R.id.paidPriceNotif)
    TextView paidPriceNotif;
    @BindView(R.id.paidPriceCopy)
    TextView paidPriceCopy;

    private Double price;
    private String accountReceiver;

    private HomecareSessionManager homecareSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_transfer_page);
        ButterKnife.bind(this);
        createTitleBar();
        homecareSessionManager = new HomecareSessionManager(this, this);
        fillTheForm();
        accountBillReceiverCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyToClipboard(accountReceiver);
            }
        });
        paidPriceCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyToClipboard(String.valueOf(price));
            }
        });
        setFonts();
    }

    private void fillTheForm() {
        accountReceiver = "Yufia";
        price = 100000.0;
        price = price + PaymentUtility.getThreeDigitPhoneNumber(homecareSessionManager.getUserDetail().getPhoneNumber());
        accountBillReceiver.setText("a/n " + accountReceiver);
        paidPrice.setText(TextFormatter.doubleToRupiah(price));
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        onBackPressed();
        return true;
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

    private void setFonts() {
        ArrayList<TextView> arrayList = new ArrayList<>();
        arrayList.add(expirityDate);
        arrayList.add(expirityDateTitle);
        arrayList.add(accountBillReceiverTitle);
        arrayList.add(paymentAccountBillReceiver);
        arrayList.add(accountBillReceiver);
        arrayList.add(accountBillReceiverCopy);
        arrayList.add(totalPaidPrice);
        arrayList.add(paidPrice);
        arrayList.add(paidPriceNotif);
        arrayList.add(paidPriceCopy);
        ViewFaceUtility.applyFonts(arrayList, this, "fonts/Dosis-Medium.otf");
        ViewFaceUtility.applyFont(expirityDateTitle, this, "fonts/Dosis-ExtraBold.otf");
    }

    private void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("text", text);
        clipboard.setPrimaryClip(clip);
        Snackbar.make(mainLayout, "Telah dicopy ke clipboard", Snackbar.LENGTH_LONG).show();
    }
}
