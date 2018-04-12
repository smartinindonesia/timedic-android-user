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
import id.smartin.org.homecaretimedic.model.PaymentMethod;

public class PaymentMethodAdapter extends RecyclerView.Adapter<PaymentMethodAdapter.MyViewHolder>{
    private List<PaymentMethod> paymentMethodList;
    private Context context;
    private Activity activity;

    public PaymentMethodAdapter(Activity activity, Context context, List<PaymentMethod> paymentMethodList) {
        this.paymentMethodList = paymentMethodList;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_payment_methods, parent, false);
        return new PaymentMethodAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PaymentMethod paymentMethod = paymentMethodList.get(position);
        holder.paymentMethod.setText(paymentMethod.getPaymentMethodName());
    }

    @Override
    public int getItemCount() {
        return paymentMethodList.size();
    }

    public PaymentMethod getItem(int position){
        return paymentMethodList.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.paymentMethod)
        public TextView paymentMethod;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
