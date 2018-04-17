package id.smartin.org.homecaretimedic.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.model.PaymentMethodChild;

public class PaymentMethodChildAdapter extends RecyclerView.Adapter<PaymentMethodChildAdapter.MyViewHolder>{
    private List<PaymentMethodChild> paymentMethodChildList;
    private Context context;
    private Activity activity;

    public PaymentMethodChildAdapter(Activity activity, Context context, List<PaymentMethodChild> paymentMethodChildList) {
        this.paymentMethodChildList = paymentMethodChildList;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_payment_method_child, parent, false);
        return new PaymentMethodChildAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PaymentMethodChild billingMethod = paymentMethodChildList.get(position);
        holder.paymentMethod.setText(billingMethod.getMethodName());
    }

    @Override
    public int getItemCount() {
        return paymentMethodChildList.size();
    }

    public PaymentMethodChild getItem(int position){
        return paymentMethodChildList.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.paymentMethod)
        public TextView paymentMethod;
        @BindView(R.id.paymentLogo)
        public ImageView paymentLogo;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
