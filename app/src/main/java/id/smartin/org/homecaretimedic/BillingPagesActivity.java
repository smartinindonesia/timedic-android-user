package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.model.PaymentMethod;
import id.smartin.org.homecaretimedic.model.PaymentMethodChild;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;

import static com.facebook.FacebookSdk.getApplicationContext;

public class BillingPagesActivity extends AppCompatActivity {
    public static final String TAG = "[BillingPagesActivity]";

    @BindView(R.id.cashTotal)
    TextView cashTotal;
    @BindView(R.id.paymentMethod)
    TextView paymentMethod;
    @BindView(R.id.billAccountTitle)
    TextView billAccountTitle;
    @BindView(R.id.billAccount)
    EditText billAccount;
    @BindView(R.id.billAccountUserTitle)
    TextView billAccountUserTitle;
    @BindView(R.id.billAccountUser)
    EditText billAccountUser;
    @BindView(R.id.notifSign)
    TextView notifSign;
    @BindView(R.id.paymentLogo)
    ImageView paymentLogo;

    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    PaymentMethodChild selectedMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_pages);
        ButterKnife.bind(this);
        createTitleBar();
        setView();
        setFonts();
    }

    private void setView(){
        selectedMethod = (PaymentMethodChild) getIntent().getSerializableExtra("PaymentMethod");
        paymentMethod.setText(selectedMethod.getMethodName());
        Glide.with(getApplicationContext()).load(selectedMethod.getLogoDrawable()).apply(new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE))
                .thumbnail(0.7f)
                .into(paymentLogo);
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
        arrayList.add(cashTotal);
        arrayList.add(paymentMethod);
        arrayList.add(billAccountTitle);
        arrayList.add(billAccount);
        arrayList.add(billAccountUserTitle);
        arrayList.add(billAccountUser);
        arrayList.add(notifSign);
        ViewFaceUtility.applyFonts(arrayList, this, "fonts/Dosis-Medium.otf");
    }

}
