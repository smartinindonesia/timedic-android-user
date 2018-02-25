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
import id.smartin.org.homecaretimedic.model.Order;
import id.smartin.org.homecaretimedic.tools.ConverterUtility;

/**
 * Created by Hafid on 11/25/2017.
 */

public class ActiveOrderAdapter extends RecyclerView.Adapter<ActiveOrderAdapter.MyViewHolder>{
    public static final String TAG = "[ActiveOrdAdapter]";

    private List<Order> orderList;
    private Context context;
    private Activity activity;

    public ActiveOrderAdapter(Activity activity, Context context, List<Order> orders) {
        this.orderList = orders;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_active_order, parent, false);
        return new ActiveOrderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.orderName.setText(order.getTransactionDescription());
        holder.patientsName.setText(order.getHomecarePatientId().getName());
        holder.transactionDate.setText(ConverterUtility.getDateStringCustomPattern(order.getDate(),"dd-MM-yyyy HH:mm"));
        holder.transactionStatus.setText(order.getHomecareTransactionStatus().getStatus());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.transactionDate)
        public TextView transactionDate;
        @BindView(R.id.orderName)
        public TextView orderName;
        @BindView(R.id.patientsName)
        public TextView patientsName;
        @BindView(R.id.transactionStatus)
        public TextView transactionStatus;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
