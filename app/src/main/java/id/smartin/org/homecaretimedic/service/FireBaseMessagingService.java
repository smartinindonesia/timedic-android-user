package id.smartin.org.homecaretimedic.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.manager.HomecareSessionManager;

public class FireBaseMessagingService extends FirebaseMessagingService {
    public static final String TAG = "[FireBaseMessaging]";
    public HomecareSessionManager homecareSessionManager;

    public FireBaseMessagingService() {

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        homecareSessionManager = new HomecareSessionManager(getApplicationContext());
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        if (homecareSessionManager.getSetting().getActive()) {
            sendNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle());
        }
        /**
         NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
         .setContentTitle(remoteMessage.getNotification().getTitle())
         .setContentText(remoteMessage.getNotification().getBody())
         .setAutoCancel(true);
         */
    }

    private void sendNotification(String msg, String title) {
        /**
         Intent intent = new Intent(this, NewTransactionsHistActivity.class);
         intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
         PendingIntent pendingIntent = PendingIntent.getActivity(this, new Random().nextInt(100) , intent,
         PendingIntent.FLAG_ONE_SHOT);
         */
        long when = System.currentTimeMillis();
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(this);
        mNotifyBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000, 1000});
        boolean lollipop = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        if (lollipop) {

            mNotifyBuilder = new NotificationCompat.Builder(this)
                    .setContentTitle(title)
                    .setStyle(
                            new NotificationCompat.BigTextStyle()
                                    .bigText(msg))
                    .setContentText(msg)
                    .setColor(Color.TRANSPARENT)
                    .setLargeIcon(
                            BitmapFactory.decodeResource(
                                    getResources(),
                                    R.drawable.timedic_splash_logo))
                    .setSmallIcon(R.drawable.timedic_splash_logo)
                    .setWhen(when).setAutoCancel(true)
                    .setSound(defaultSoundUri);
            //.setContentIntent(pendingIntent);

        } else {

            mNotifyBuilder = new NotificationCompat.Builder(this)
                    .setStyle(
                            new NotificationCompat.BigTextStyle()
                                    .bigText(msg))
                    .setContentTitle(title)
                    .setContentText(msg)
                    .setSmallIcon(R.drawable.timedic_splash_logo)
                    .setWhen(when).setAutoCancel(true)
                    .setSound(defaultSoundUri);
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(new Random().nextInt(100) /* ID of notification */, mNotifyBuilder.build());
    }
}
