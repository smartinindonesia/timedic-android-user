package id.smartin.org.homecaretimedic.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.OrderDetailsActivity;
import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.model.Order;
import id.smartin.org.homecaretimedic.tools.ConverterUtility;

/**
 * Created by Hafid on 11/25/2017.
 */

public class HistoryOrderAdapter extends RecyclerView.Adapter<HistoryOrderAdapter.MyViewHolder> {
    public static final String TAG = "[ActiveOrdAdapter]";

    private List<Order> orderList;
    private Context context;
    private Activity activity;

    public HistoryOrderAdapter(Activity activity, Context context, List<Order> orders) {
        this.orderList = orders;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public HistoryOrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_order, parent, false);
        return new HistoryOrderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HistoryOrderAdapter.MyViewHolder holder, int position) {
        final Order order = orderList.get(position);
        holder.serviceName.setText(order.getSelectedService());
        holder.orderDescription.setText(order.getTransactionDescription());
        holder.patientsName.setText(order.getHomecarePatientId().getName());
        holder.transactionDate.setText(ConverterUtility.getDateStringCustomPattern(order.getDate(), "dd-MM-yyyy HH:mm"));
        holder.transactionStatus.setText(order.getHomecareTransactionStatus().getStatus());
        holder.gotoDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, OrderDetailsActivity.class);
                intent.putExtra("order", order);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.serviceName)
        public TextView serviceName;
        @BindView(R.id.transactionDate)
        public TextView transactionDate;
        @BindView(R.id.orderDescripton)
        public TextView orderDescription;
        @BindView(R.id.patientsName)
        public TextView patientsName;
        @BindView(R.id.transactionStatus)
        public TextView transactionStatus;
        @BindView(R.id.orderDetails)
        public ImageButton gotoDetails;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
