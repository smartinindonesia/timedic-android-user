package id.smartin.org.homecaretimedic.adapter;

import android.app.Activity;
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
import id.smartin.org.homecaretimedic.model.LabServices;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;

/**
 * Created by Hafid on 11/11/2017.
 */

public class LayananLabAdapter extends RecyclerView.Adapter<LayananLabAdapter.MyViewHolder> implements Filterable {

    private List<LabServices> labServicesList;
    private Context context;
    private Activity activity;

    public LayananLabAdapter(Activity activity, Context context, List<LabServices> labServices) {
        this.context = context;
        this.labServicesList = labServices;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layanan_lab_selected_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        LabServices labServices = labServicesList.get(position);
        holder.servicename.setText(labServices.getNamaLayanan());
        holder.servicePrice.setText("" + labServices.getHargaLayanan());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labServicesList.remove(position);
                //notifyItemRemoved(position);
                notifyDataSetChanged();
                Intent in = new Intent();
                in.setAction(Action.BROADCAST_DELETE_LAYANAN_EVENT);
                in.putExtra("number_of_item", labServicesList.size());
                in.putExtra("total_price", sumOfPrice());
                context.sendBroadcast(in);
            }
        });
        ViewFaceUtility.applyFont(holder.servicename, activity, "fonts/Dosis-Bold.otf");
    }

    @Override
    public int getItemCount() {
        return labServicesList.size();
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
        for (int i = 0; i < labServicesList.size(); i++) {
            priceSum = priceSum + labServicesList.get(i).getHargaLayanan();
        }
        return priceSum;
    }

    public List<LabServices> getAllSelectedServices(){
        return labServicesList;
    }

}