package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.adapter.BillingItemAdapter;
import id.smartin.org.homecaretimedic.adapter.CaregiverHistoryAdapter;
import id.smartin.org.homecaretimedic.adapter.PaymentMethodAdapter;
import id.smartin.org.homecaretimedic.model.BillingItem;
import id.smartin.org.homecaretimedic.model.PaymentMethod;
import id.smartin.org.homecaretimedic.model.submitmodel.SubmitInfo;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;

public class BillingMethodActivity extends AppCompatActivity {
    public static final String TAG = "[BillingMethodAct]";

    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.billingItems)
    RecyclerView billingItemsRV;
    @BindView(R.id.billingMethods)
    RecyclerView billingMethodsRV;

    private PaymentMethodAdapter paymentMethodAdapter;
    private List<PaymentMethod> paymentMethods = new ArrayList<>();

    private BillingItemAdapter billingItemAdapter;
    private List<BillingItem> billingItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_method);
        ButterKnife.bind(this);
        createTitleBar();

        DividerItemDecoration dividerItemDecorationBI = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        final RecyclerView.LayoutManager mLayoutManagerBI = new LinearLayoutManager(getApplicationContext());
        DividerItemDecoration dividerItemDecorationBM = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        final RecyclerView.LayoutManager mLayoutManagerBM = new LinearLayoutManager(getApplicationContext());

        billingItemAdapter = new BillingItemAdapter(this, this, billingItems);
        billingItemsRV.addItemDecoration(dividerItemDecorationBI);
        billingItemsRV.setLayoutManager(mLayoutManagerBI);
        billingItemsRV.setItemAnimator(new DefaultItemAnimator());
        billingItemsRV.setAdapter(billingItemAdapter);

        paymentMethodAdapter = new PaymentMethodAdapter(this, this, paymentMethods);
        billingMethodsRV.addItemDecoration(dividerItemDecorationBM);
        billingMethodsRV.setLayoutManager(mLayoutManagerBM);
        billingMethodsRV.setItemAnimator(new DefaultItemAnimator());
        billingMethodsRV.setAdapter(paymentMethodAdapter);
        getPaymentMethod();
        getBillingItems();
    }

    private void getBillingItems(){
        BillingItem a = new BillingItem();
        a.setItem(SubmitInfo.selectedHomecareService.getServiceName());
        a.setPrice(SubmitInfo.selectedHomecareService.getVisitCost());
        BillingItem b = new BillingItem();
        b.setItem("Perlengkapan");
        b.setPrice(SubmitInfo.getAssessmentPrice());
        billingItems.add(a);
        billingItems.add(b);
    }

    private void getPaymentMethod(){
        PaymentMethod a = new PaymentMethod();
        a.setDescription("Transfer Bank");
        a.setPaymentMethodName("TransferBank");
        paymentMethods.add(a);
        paymentMethodAdapter.notifyDataSetChanged();
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
}
