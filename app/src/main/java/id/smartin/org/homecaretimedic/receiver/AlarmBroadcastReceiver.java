package id.smartin.org.homecaretimedic.receiver;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import id.smartin.org.homecaretimedic.AlarmActivity;
import id.smartin.org.homecaretimedic.config.Action;
import id.smartin.org.homecaretimedic.config.Constants;
import id.smartin.org.homecaretimedic.model.utilitymodel.AlarmModel;
import id.smartin.org.homecaretimedic.service.AlarmNotificationService;
import id.smartin.org.homecaretimedic.service.AlarmSoundService;

import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmModel alarmModel = (AlarmModel) intent.getSerializableExtra(Action.ACTION_ALARM_OBJECT_TRANSFER);
        Toast.makeText(context, "ALARM!! ALARM!!", Toast.LENGTH_SHORT).show();
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
        Intent a = new Intent(context, AlarmActivity.class);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        a.putExtra(Action.ACTION_ALARM_OBJECT_TRANSFER, alarmModel);
        context.startActivity(a);
        //Stop sound service to play sound for alarm
        //context.startService(new Intent(context, AlarmSoundService.class));

        //This will send a notification message and show notification in notification tray
        ComponentName comp = new ComponentName(context.getPackageName(),
                AlarmNotificationService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));

    }
}
