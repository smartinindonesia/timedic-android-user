package id.smartin.org.homecaretimedic;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.config.Constants;
import id.smartin.org.homecaretimedic.config.RequestCode;
import id.smartin.org.homecaretimedic.model.HealthVideo;

public class VideoPlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    public static final String TAG = "[VideoPlayerActivity]";

    @BindView(R.id.youtube_view)
    YouTubePlayerView youTubePlayerView;

    private YouTubePlayer youTubePlayer;
    private HealthVideo healthVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        ButterKnife.bind(this);
        Intent extra = getIntent();
        healthVideo = (HealthVideo) extra.getSerializableExtra("video_obj");
        youTubePlayerView.initialize(Constants.YOUTUBE_API_KEY, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestCode.REQUEST_RECOVERY_DIALOG) {
            youTubePlayerView.initialize(Constants.YOUTUBE_API_KEY, this);
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        this.youTubePlayer = youTubePlayer;
        if (!b) {
            this.youTubePlayer.cueVideo(healthVideo.getId());
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RequestCode.REQUEST_RECOVERY_DIALOG).show();
        } else {
            String errorMessage = String.format(
                    "There was an error initializing the YouTubePlayer",
                    youTubeInitializationResult.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }
}
