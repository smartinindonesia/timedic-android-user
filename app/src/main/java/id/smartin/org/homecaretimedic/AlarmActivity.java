package id.smartin.org.homecaretimedic;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DigitalClock;
import android.widget.TextView;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.config.Action;
import id.smartin.org.homecaretimedic.model.utilitymodel.AlarmModel;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;

public class AlarmActivity extends AppCompatActivity {

    @BindView(R.id.swipe_btn)
    SwipeButton swipeButton;
    @BindView(R.id.digitalClock)
    DigitalClock digitalClock;
    @BindView(R.id.notifText)
    TextView notifText;

    private MediaPlayer mediaPlayer;
    private AlarmModel remind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);
        mediaPlayer = MediaPlayer.create(this, R.raw.apple_ring);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);//set looping true to run it infinitely
        swipeButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                if (active) {
                    finish();
                }
            }
        });
        remind = (AlarmModel) getIntent().getSerializableExtra(Action.ACTION_ALARM_OBJECT_TRANSFER);
        notifText.setText(remind.getMedicineName()+" "+remind.getNumOfMedicine() + "x" + remind.getIntervalTime() + " " + remind.getMedicineShape() + "/" + remind.getIntervalDay() + " hari");
        setFonts();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //On destory stop and release the media player
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
        }
    }

    private void setFonts() {
        ArrayList<TextView> arrayList = new ArrayList<>();
        arrayList.add(notifText);
        arrayList.add(digitalClock);
        ViewFaceUtility.applyFonts(arrayList, this, "fonts/Dosis-ExtraBold.otf");
    }
}
