package id.smartin.org.homecaretimedic.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.model.Reminder;
import id.smartin.org.homecaretimedic.model.utilitymodel.AlarmModel;
import id.smartin.org.homecaretimedic.tools.ConverterUtility;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;

/**
 * Created by Hafid on 8/23/2017.
 */

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.MyViewHolder> {
    public static String TAG = "[ReminderAdapter]";

    private List<AlarmModel> reminderList;
    private Activity activity;
    private Context context;

    public ReminderAdapter(Context context, Activity activity, List<AlarmModel> reminderList) {
        this.reminderList = reminderList;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_medicine_reminder_list, parent, false);
        return new ReminderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AlarmModel remind = reminderList.get(position);
        holder.medicineName.setText(remind.getMedicineName());
        holder.dose.setText(remind.getNumOfMedicine() + "x" + remind.getIntervalTime() + " " + remind.getMedicineShape() + "/" + remind.getIntervalDay() + " hari");
        holder.treatmentDate.setText("");
        holder.status.setText(remind.getStatus().getStatus());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.medicineName)
        public TextView medicineName;
        @BindView(R.id.dose)
        public TextView dose;
        @BindView(R.id.startingDate)
        public TextView treatmentDate;
        @BindView(R.id.status)
        public TextView status;
        @BindView(R.id.btnEdit)
        public Button edit;
        @BindView(R.id.btnDelete)
        public Button delete;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            ViewFaceUtility.applyFont(medicineName, activity, "fonts/Dosis-Bold.otf");
            ArrayList<TextView> arrayList = new ArrayList<>();
            arrayList.add(dose);
            arrayList.add(treatmentDate);
            arrayList.add(status);
            arrayList.add(edit);
            arrayList.add(delete);
            ViewFaceUtility.applyFonts(arrayList, activity, "fonts/Dosis-Medium.otf");
        }

    }
}

