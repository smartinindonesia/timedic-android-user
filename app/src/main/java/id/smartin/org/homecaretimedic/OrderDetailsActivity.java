package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.model.Order;
import id.smartin.org.homecaretimedic.tools.ConverterUtility;
import id.smartin.org.homecaretimedic.tools.TextFormatter;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;

public class OrderDetailsActivity extends AppCompatActivity {
    public static String TAG = "[OrderDetailsActivity]";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.serviceName)
    TextView serviceName;
    @BindView(R.id.transactionDate)
    TextView transactionDate;
    @BindView(R.id.downPayment)
    TextView downPayment;
    @BindView(R.id.totalApproxCash)
    TextView totalApproxCash;
    @BindView(R.id.totalCash)
    TextView totalCash;
    @BindView(R.id.addressLoc)
    TextView addressLoc;
    @BindView(R.id.mapLocation)
    TextView mapLocation;
    @BindView(R.id.transactionStatus)
    TextView transactionStatus;

    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);
        createTitleBar();
        fillPage();
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

    public void fillPage() {
        order = (Order) getIntent().getSerializableExtra("order");
        serviceName.setText(order.getSelectedService());
        transactionDate.setText(ConverterUtility.getDateString(order.getDate()));
        downPayment.setText(TextFormatter.doubleToRupiah(order.getPrepaidPrice()));
        totalApproxCash.setText(TextFormatter.doubleToRupiah(order.getPredictionPrice()));
        totalCash.setText(TextFormatter.doubleToRupiah(order.getFixedPrice()));
        addressLoc.setText(order.getFullAddress());
        mapLocation.setText("(" + order.getLocationLatitude() + "," + order.getLocationLongitude() + ")");
        transactionStatus.setText(order.getHomecareTransactionStatus().getStatus());
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
}

