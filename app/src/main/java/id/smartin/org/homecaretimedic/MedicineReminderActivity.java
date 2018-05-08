package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
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
import id.smartin.org.homecaretimedic.adapter.ReminderAdapter;
import id.smartin.org.homecaretimedic.customuicompt.RecyclerTouchListener;
import id.smartin.org.homecaretimedic.model.Reminder;
import id.smartin.org.homecaretimedic.model.utilitymodel.AlarmModel;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;
import id.smartin.org.homecaretimedic.tools.sqlitehelper.DBHelperAlarmModel;

public class MedicineReminderActivity extends AppCompatActivity {

    public static String TAG = "[MedicineReminderActivity]";

    ReminderAdapter reminderAdapter;
    List<AlarmModel> reminderList = new ArrayList<>();

    @BindView(R.id.reminder_list)
    RecyclerView recyclerView;
    @BindView(R.id.addReminderItem)
    Button addReminderItem;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private DBHelperAlarmModel dbHelperAlarmModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_reminder);
        ButterKnife.bind(this);
        createTitleBar();
        dbHelperAlarmModel = new DBHelperAlarmModel(this, new AlarmModel());
        addReminderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicineReminderActivity.this, AddReminderItemActivity.class);
                intent.putExtra("iseditmode", false);
                startActivity(intent);
            }
        });
        reminderAdapter = new ReminderAdapter(getApplicationContext(), this, reminderList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(reminderAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                AlarmModel reminder = reminderList.get(position);
                Toast.makeText(getApplicationContext(), reminder.getMedicineName(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        reminderAdapter.notifyDataSetChanged();

        setFonts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetRecycler();
    }

    @SuppressLint("RestrictedApi")
    public void createTitleBar() {
        setSupportActionBar(toolbar);
        ViewFaceUtility.changeToolbarFont(toolbar, this, "fonts/BalooBhaina-Regular.ttf", R.color.theme_black);
        ActionBar mActionbar = getSupportActionBar();
        mActionbar.setDisplayHomeAsUpEnabled(true);
        mActionbar.setDefaultDisplayHomeAsUpEnabled(true);
        mActionbar.setDisplayShowHomeEnabled(true);
        mActionbar.setDisplayShowTitleEnabled(true);
        mActionbar.setDisplayShowCustomEnabled(true);
    }

    public void resetRecycler() {
        reminderList.clear();
        List<AlarmModel> temp = dbHelperAlarmModel.getAllAlarm();
        for (int i = 0; i < temp.size(); i++) {
            reminderList.add(temp.get(i));
        }
        reminderAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setFonts() {
        ViewFaceUtility.applyFont(addReminderItem, this, "fonts/Dosis-Medium.otf");
    }
}
