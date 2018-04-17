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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.adapter.BillingItemAdapter;
import id.smartin.org.homecaretimedic.adapter.ExpandablePaymentMethodAdpt;
import id.smartin.org.homecaretimedic.model.PaymentMethodChild;
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
    ExpandableListView billingMethodsRV;
    @BindView(R.id.totalCashLabel)
    TextView totalCashLabel;
    @BindView(R.id.paymentMethodLabel)
    TextView paymentMethodLabel;

    ExpandablePaymentMethodAdpt listAdapter;
    List<PaymentMethod> listDataHeader = new ArrayList<>();
    HashMap<PaymentMethod, List<PaymentMethodChild>> listDataChild = new HashMap<PaymentMethod, List<PaymentMethodChild>>();

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

        billingItemAdapter = new BillingItemAdapter(this, this, billingItems);
        //billingItemsRV.addItemDecoration(dividerItemDecorationBI);
        billingItemsRV.setLayoutManager(mLayoutManagerBI);
        billingItemsRV.setItemAnimator(new DefaultItemAnimator());
        billingItemsRV.setAdapter(billingItemAdapter);

        getBillingItems();
        getPaymentMethod();

        listAdapter = new ExpandablePaymentMethodAdpt(this, this, listDataHeader, listDataChild);
        billingMethodsRV.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
        billingMethodsRV.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });
        // Listview Group expanded listener
        billingMethodsRV.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });
        billingMethodsRV.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });
        billingMethodsRV.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                setListViewHeight(parent, groupPosition);
                return false;
            }
        });
        billingMethodsRV.setDividerHeight(0);
        setFonts();
    }

    private void getBillingItems() {
        BillingItem a = new BillingItem();
        a.setItem(SubmitInfo.selectedHomecareService.getServiceName());
        a.setPrice(SubmitInfo.selectedHomecareService.getVisitCost());
        BillingItem b = new BillingItem();
        b.setItem("Perlengkapan");
        b.setPrice(SubmitInfo.getAssessmentPrice());
        billingItems.add(a);
        billingItems.add(b);
    }

    private void getPaymentMethod() {
        // Adding child data
        listDataHeader.add(new PaymentMethod((long) 1, "Transfer Bank (Cek Otomatis)"));

        // Adding child data
        List<PaymentMethodChild> bankTransfer = new ArrayList<PaymentMethodChild>();
        bankTransfer.add(new PaymentMethodChild((long) 1, "BCA", R.drawable.bank_bca));
        bankTransfer.add(new PaymentMethodChild((long) 2, "Mandiri", R.drawable.bank_mandiri));
        bankTransfer.add(new PaymentMethodChild((long) 2, "BNI", R.drawable.bank_bni));

        listDataChild.put(listDataHeader.get(0), bankTransfer); // Header, Child data
        //listAdapter.notifyDataSetChanged();
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

    private void setListViewHeight(ExpandableListView listView, int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private void setFonts() {
        ArrayList<TextView> tvs = new ArrayList<>();
        tvs.add(totalCashLabel);
        tvs.add(paymentMethodLabel);
        ViewFaceUtility.applyFonts(tvs, this, "fonts/Dosis-Bold.otf");
    }
}
