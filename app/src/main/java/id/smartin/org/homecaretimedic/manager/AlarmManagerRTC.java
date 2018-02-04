package id.smartin.org.homecaretimedic.manager;

import android.content.Context;

import java.util.ArrayList;

import id.smartin.org.homecaretimedic.model.Reminder;

/**
 * Created by Hafid on 9/10/2017.
 */

public class AlarmManagerRTC {
    public static String TAG = "[RTCAlarm]";

    private ArrayList<Reminder> reminderArrayList;
    private Context context;

    public AlarmManagerRTC(Context appContext, ArrayList<Reminder> reminders){
        this.reminderArrayList = reminders;
        this.context = appContext;
    }
}
