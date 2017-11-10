package id.pptik.org.homecaretimedic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import id.pptik.org.homecaretimedic.R;
import id.pptik.org.homecaretimedic.model.HealthVideo;

/**
 * Created by Hafid on 8/23/2017.
 */

public class HealthVideoAdapter extends RecyclerView.Adapter<HealthVideoAdapter.MyViewHolder>{
    public static String TAG = "[HealthVideoAdapter]";

    private List<HealthVideo> videoList;
    private Context context;

    public HealthVideoAdapter(Context context, List<HealthVideo> videos) {
        this.videoList = videos;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.health_video_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        HealthVideo video = videoList.get(position);
        holder.vidItem.setText(video.getJudulVideo());
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageButton playItem;
        public TextView vidItem;

        public MyViewHolder(View view) {
            super(view);
            playItem = (ImageButton) view.findViewById(R.id.playItem);
            vidItem = (TextView) view.findViewById(R.id.videoItem);
        }
    }
}
