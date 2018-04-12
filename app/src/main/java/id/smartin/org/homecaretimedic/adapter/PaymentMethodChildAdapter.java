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
import id.smartin.org.homecaretimedic.model.BankTransferMethodOpt;

public class PaymentMethodChildAdapter extends RecyclerView.Adapter<PaymentMethodChildAdapter.MyViewHolder>{
    private List<BankTransferMethodOpt> bankTransferMethodOptList;
    private Context context;
    private Activity activity;

    public PaymentMethodChildAdapter(Activity activity, Context context, List<BankTransferMethodOpt> bankTransferMethodOptList) {
        this.bankTransferMethodOptList = bankTransferMethodOptList;
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
        BankTransferMethodOpt billingMethod = bankTransferMethodOptList.get(position);
        holder.paymentMethod.setText(billingMethod.getMethodName());
    }

    @Override
    public int getItemCount() {
        return bankTransferMethodOptList.size();
    }

    public BankTransferMethodOpt getItem(int position){
        return bankTransferMethodOptList.get(position);
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
