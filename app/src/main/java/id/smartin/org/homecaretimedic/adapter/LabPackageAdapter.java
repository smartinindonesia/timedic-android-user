package id.smartin.org.homecaretimedic.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.MapSelectorActivity;
import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.model.LabPackageItem;
import id.smartin.org.homecaretimedic.tools.ConverterUtility;
import id.smartin.org.homecaretimedic.tools.TextFormatter;

/**
 * Created by Hafid on 11/11/2017.
 */

public class LabPackageAdapter extends RecyclerView.Adapter<LabPackageAdapter.MyViewHolder> {
    public static String TAG = "[LabPackageAdapter]";

    private List<LabPackageItem> labPackageItems;
    private Context context;
    private Activity activity;

    public LabPackageAdapter(Activity activity, Context context, List<LabPackageItem> labPackageItems) {
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
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final LabPackageItem labPackageItem = labPackageItems.get(position);
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL) // because file name is always same
                .skipMemoryCache(true);
        Glide.with(context).load(labPackageItem.getUrl_icon())
                .apply(requestOptions)
                .thumbnail(0.5f)
                .into(holder.packageIcon);
        holder.packageName.setText(labPackageItem.getName());
        holder.packagePrice.setText(TextFormatter.doubleToRupiah(labPackageItem.getPrice()));
        holder.checkBox.setChecked(labPackageItem.isSelected());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                labPackageItems.get(position).setSelected(b);
                Log.i(TAG, "SET ITEM into " + b);
            }
        });
    }

    public List<LabPackageItem> getSelectedPackageItems() {
        List<LabPackageItem> labPackageItemsNew = new ArrayList<>();
        for (int i = 0; i < labPackageItems.size(); i++) {
            if (labPackageItems.get(i).isSelected()) {
                labPackageItemsNew.add(labPackageItems.get(i));
            }
        }
        return labPackageItemsNew;
    }

    @Override
    public int getItemCount() {
        return labPackageItems.size();
    }

    public LabPackageItem getItem(int position) {
        return labPackageItems.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.packageIcon)
        public ImageView packageIcon;
        @BindView(R.id.packageName)
        public TextView packageName;
        @BindView(R.id.packagePrice)
        public TextView packagePrice;
        @BindView(R.id.selectPackage)
        CheckBox checkBox;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
