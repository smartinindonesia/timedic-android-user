package id.pptik.org.homecaretimedic;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.pptik.org.homecaretimedic.adapter.ReminderAdapter;
import id.pptik.org.homecaretimedic.customuicompt.RecyclerTouchListener;
import id.pptik.org.homecaretimedic.model.Reminder;

public class MedicineReminderActivity extends AppCompatActivity {

    public static String TAG = "[MedicineReminderActivity]";

    ReminderAdapter reminderAdapter ;
    List<Reminder> reminderList = new ArrayList<>();

    @BindView(R.id.reminder_list)
    RecyclerView recyclerView;
    @BindView(R.id.addReminder)
    Button addReminderTime;
    @BindView(R.id.addReminderItem)
    Button addReminderItem;
    @BindView(R.id.time_reminder)
    EditText textReminder;
    @BindView(R.id.medicineType)
    EditText medicineType;
    @BindView(R.id.dosis)
    EditText dose;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_reminder);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        createReminderItem();
        addReminderTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MedicineReminderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        textReminder.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        addReminderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReminderItem();
            }
        });
        reminderAdapter = new ReminderAdapter(getApplicationContext(), reminderList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(reminderAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Reminder reminder = reminderList.get(position);
                Toast.makeText(getApplicationContext(), reminder.getJenisObat(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        reminderAdapter.notifyDataSetChanged();
    }

    public void createReminderItem(){
        Reminder reminder1 = new Reminder("Obat A", "1 tablet per minum", "06:00");
        Reminder reminder2 = new Reminder("Obat B", "1 tablet per minum", "06:00");
        reminderList.add(reminder1);
        reminderList.add(reminder2);
    }

    public void addReminderItem(){
        Reminder newReminder = new Reminder(medicineType.getText().toString(),dose.getText().toString(),textReminder.getText().toString());
        reminderList.add(newReminder);
        reminderAdapter.notifyDataSetChanged();
    }
}
