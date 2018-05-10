package id.smartin.org.homecaretimedic;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class FirebaseChatReceiver extends Service {

    public FirebaseChatReceiver() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
