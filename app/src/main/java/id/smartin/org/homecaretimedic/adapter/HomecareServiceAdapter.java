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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.MapSelectorActivity;
import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.model.HomecareService;
import id.smartin.org.homecaretimedic.model.submitmodel.SubmitInfo;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;

/**
 * Created by Hafid on 12/8/2017.
 */

public class HomecareServiceAdapter extends RecyclerView.Adapter<HomecareServiceAdapter.MyViewHolder> {

    private List<HomecareService> homecareServiceList;
    private Context context;
    private Activity activity;

    public HomecareServiceAdapter(Activity activity, Context context, List<HomecareService> homecareServiceList) {
        this.context = context;
        this.homecareServiceList = homecareServiceList;
        this.activity = activity;
    }

    @Override
    public HomecareServiceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_homecare_service, parent, false);
        return new HomecareServiceAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HomecareServiceAdapter.MyViewHolder holder, int position) {
        final HomecareService homecareService = homecareServiceList.get(position);
        Glide.with(context).load(homecareService.getServiceUrlIcon()).apply(new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL))
                .thumbnail(0.5f)
                .into(holder.serviceIcon);
        holder.serviceName.setText(homecareService.getServiceName());
        holder.selectItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitInfo.selectedHomecareService = homecareService;
                Intent intent = new Intent(context, MapSelectorActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return homecareServiceList.size();
    }

    public HomecareService getItem(int position) {
        return homecareServiceList.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.selectItem)
        public Button selectItem;
        @BindView(R.id.serviceIcon)
        public ImageView serviceIcon;
        @BindView(R.id.serviceName)
        public TextView serviceName;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            ViewFaceUtility.applyFont(selectItem, activity, "fonts/Dosis-Regular.otf");
            ViewFaceUtility.applyFont(serviceName, activity, "fonts/Dosis-Regular.otf");
        }
    }
}
