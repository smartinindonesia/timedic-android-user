package id.smartin.org.homecaretimedic.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.config.Action;
import id.smartin.org.homecaretimedic.model.LayananLab;

/**
 * Created by Hafid on 11/11/2017.
 */

public class LayananLabAdapter extends RecyclerView.Adapter<LayananLabAdapter.MyViewHolder> implements Filterable {

    private List<LayananLab> layananLabList;
    private Context context;

    public LayananLabAdapter(Context context, List<LayananLab> layananLabs) {
        this.context = context;
        this.layananLabList = layananLabs;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layanan_lab_selected_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        LayananLab layananLab = layananLabList.get(position);
        holder.servicename.setText(layananLab.getNamaLayanan());
        holder.servicePrice.setText("" + layananLab.getHargaLayanan());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layananLabList.remove(position);
                //notifyItemRemoved(position);
                notifyDataSetChanged();
                Intent in = new Intent();
                in.setAction(Action.BROADCAST_DELETE_LAYANAN_EVENT);
                in.putExtra("number_of_item", layananLabList.size());
                in.putExtra("total_price", sumOfPrice());
                context.sendBroadcast(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return layananLabList.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.serviceName)
        public TextView servicename;
        @BindView(R.id.servicePrice)
        public TextView servicePrice;
        @BindView(R.id.deleteButton)
        public ImageButton delete;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public double sumOfPrice() {
        double priceSum = 0;
        for (int i = 0; i < layananLabList.size(); i++) {
            priceSum = priceSum + layananLabList.get(i).getHargaLayanan();
        }
        return priceSum;
    }

}