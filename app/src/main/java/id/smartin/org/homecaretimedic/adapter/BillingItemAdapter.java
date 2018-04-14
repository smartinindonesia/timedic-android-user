package id.smartin.org.homecaretimedic.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.model.BillingItem;
import id.smartin.org.homecaretimedic.tools.TextFormatter;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;

public class BillingItemAdapter extends RecyclerView.Adapter<BillingItemAdapter.MyViewHolder>{
    private List<BillingItem> billingItems;
    private Context context;
    private Activity activity;

    public BillingItemAdapter(Activity activity, Context context, List<BillingItem> billingItems) {
        this.billingItems = billingItems;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_billing, parent, false);
        return new BillingItemAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BillingItem billingItem = billingItems.get(position);
        holder.billingName.setText(billingItem.getItem());
        holder.billingPrice.setText(TextFormatter.doubleToRupiah(billingItem.getPrice()));
    }

    @Override
    public int getItemCount() {
        return billingItems.size();
    }

    public BillingItem getItem(int position){
        return billingItems.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.billingName)
        public TextView billingName;
        @BindView(R.id.billingPrice)
        public TextView billingPrice;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
            ViewFaceUtility.applyFont(billingName, activity, "fonts/Dosis-Regular.otf");
            ViewFaceUtility.applyFont(billingPrice, activity, "fonts/Dosis-Regular.otf");
        }
    }
}
