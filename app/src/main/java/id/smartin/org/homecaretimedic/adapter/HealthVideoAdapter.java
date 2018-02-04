package id.smartin.org.homecaretimedic.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.api.services.youtube.model.Video;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.R;

/**
 * Created by Hafid on 8/23/2017.
 */

public class HealthVideoAdapter extends RecyclerView.Adapter<HealthVideoAdapter.MyViewHolder>{
    public static String TAG = "[HealthVideoAdapter]";

    private List<Video> videoList;
    private Activity activity;

    public HealthVideoAdapter(Activity activity, List<Video> videos) {
        this.videoList = videos;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.health_video_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Video video = videoList.get(position);
        holder.vidItem.setText(video.getSnippet().getTitle());
        RequestOptions applyReq = new RequestOptions();
        applyReq.fitCenter();
        Glide.with(activity).load(video.getSnippet().getThumbnails().getMedium().getUrl())
                .thumbnail(0.5f)
                .apply(applyReq)
                .into(holder.playItem);
        holder.channelName.setText(video.getSnippet().getChannelTitle());
        holder.viewCount.setText(video.getStatistics().getViewCount().toString());
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.playItem)
        public ImageView playItem;
        @BindView(R.id.videoItem)
        public TextView vidItem;
        @BindView(R.id.channelLogo)
        public ImageView channelLogo;
        @BindView(R.id.channelName)
        public TextView channelName;
        @BindView(R.id.viewCount)
        public TextView viewCount;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
