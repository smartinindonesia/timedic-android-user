package id.pptik.org.homecaretimedic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.pptik.org.homecaretimedic.R;
import id.pptik.org.homecaretimedic.model.Reminder;

/**
 * Created by Hafid on 8/23/2017.
 */

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.MyViewHolder>{
    public static String TAG = "[ReminderAdapter]";

    private List<Reminder> reminderList;
    private Context context;

    public ReminderAdapter(Context context, List<Reminder> reminderList){
        this.reminderList = reminderList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicine_reminder_item, parent, false);
        return new ReminderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Reminder remind = reminderList.get(position);
        holder.jenisObat.setText(remind.getJenisObat());
        holder.remindMe.setText(remind.getTimeReminder());
        holder.dosis.setText(remind.getDosis());
    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView jenisObat;
        public TextView dosis;
        public TextView remindMe;

        public MyViewHolder(View view) {
            super(view);
            jenisObat = (TextView) view.findViewById(R.id.jenisObat);
            dosis = (TextView) view.findViewById(R.id.dosis);
            remindMe = (TextView) view.findViewById(R.id.remindMe);
        }
    }
}

