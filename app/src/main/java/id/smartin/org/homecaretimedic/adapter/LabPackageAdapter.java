package id.smartin.org.homecaretimedic.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.MapSelectorActivity;
import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.model.LabPackageItem;

/**
 * Created by Hafid on 11/11/2017.
 */

public class LabPackageAdapter extends RecyclerView.Adapter<LabPackageAdapter.MyViewHolder> {

    private List<LabPackageItem> labPackageItems;
    private Context context;
    private Activity activity;

    public LabPackageAdapter(Activity activity, Context context, List<LabPackageItem> labPackageItems){
        this.context = context;
        this.labPackageItems = labPackageItems;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_package_lab, parent, false);
        return new LabPackageAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        LabPackageItem labPackageItem = labPackageItems.get(position);
        Glide.with(context).load(labPackageItem.getUrl_icon())
                .thumbnail(0.5f)
                .into(holder.packageIcon);
        holder.packageName.setText(labPackageItem.getName());
        holder.packagePrice.setText(labPackageItem.getPrice());
        holder.selectItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MapSelectorActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return labPackageItems.size();
    }

    public LabPackageItem getItem(int position){
        return  labPackageItems.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.selectItem)
        public Button selectItem;
        @BindView(R.id.packageIcon)
        public ImageView packageIcon;
        @BindView(R.id.packageName)
        public TextView packageName;
        @BindView(R.id.packagePrice)
        public TextView packagePrice;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
