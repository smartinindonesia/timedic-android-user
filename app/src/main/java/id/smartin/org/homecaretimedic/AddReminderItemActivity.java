package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.media.Image;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.adapter.MedicineTypeSpinner;
import id.smartin.org.homecaretimedic.config.VarConst;
import id.smartin.org.homecaretimedic.model.MedicineType;
import id.smartin.org.homecaretimedic.model.utilitymodel.AlarmModel;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;
import id.smartin.org.homecaretimedic.tools.sqlitehelper.DBHelperAlarmModel;

public class AddReminderItemActivity extends AppCompatActivity {
    public static final String TAG = "[AddReminderItemAct]";

    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.medicineName)
    EditText medicineName;
    @BindView(R.id.medicineNameTitle)
    TextView medicineNameTitle;

    @BindView(R.id.doseTitle)
    TextView doseTitle;
    @BindView(R.id.interval)
    EditText interval;
    @BindView(R.id.times)
    EditText times;
    @BindView(R.id.unitMeasure)
    Spinner unitMeasure;
    @BindView(R.id.perText)
    TextView perText;
    @BindView(R.id.numOfDays)
    EditText numOfDays;
    @BindView(R.id.dayText)
    TextView dayText;

    @BindView(R.id.scheduleTitle)
    TextView scheduleTitle;
    @BindView(R.id.datePick)
    EditText datePick;
    @BindView(R.id.datePickInputLayout)
    TextInputLayout datePickInputLayout;
    @BindView(R.id.btnChooseDate)
    ImageButton btnChooseDate;

    @BindView(R.id.alarmStatusTitle)
    TextView alarmStatusTitle;
    @BindView(R.id.onStatus)
    RadioButton onStatus;
    @BindView(R.id.offStatus)
    RadioButton offStatus;

    @BindView(R.id.btnSetAlarm)
    Button btnSetAlarm;

    private MedicineTypeSpinner medicineTypeSpinner;
    private DatePickerDialog datePickerDialog;

    private AlarmModel alarmModel = new AlarmModel();
    private List<AlarmModel.AlarmTime> alarmTimes = new ArrayList<>();
    private DBHelperAlarmModel dbHelperAlarmModel;
    private String dateSet;
    private boolean editmode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder_item);
        ButterKnife.bind(this);
        createTitleBar();

        medicineTypeSpinner = new MedicineTypeSpinner(this, this, VarConst.getMedType());
        unitMeasure.setAdapter(medicineTypeSpinner);

        btnChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int day = mcurrentTime.get(Calendar.DAY_OF_MONTH) + 2;
                final int month = mcurrentTime.get(Calendar.MONTH);
                int year = mcurrentTime.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(AddReminderItemActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        datePick.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        dateSet = year + "-" + month + "-" + dayOfMonth + " 00:00:00";
                    }
                }, mcurrentTime.get(Calendar.YEAR), mcurrentTime.get(Calendar.MONTH), mcurrentTime.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setTitle("Pilih tanggal pelayanan");
                datePickerDialog.show();
                datePick.setText(day + "-" + (month + 1) + "-" + year);
            }
        });

        btnSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editmode){
                    onClickNewForm();
                } else {
                    onClickEditForm();
                }
            }
        });
        setFonts();
    }

    private void openEditForm() {

    }

    private void onClickNewForm() {
        alarmModel = new AlarmModel();
        alarmModel.setMedicineName(medicineName.getText().toString());
        alarmModel.setNumOfMedicine(Integer.parseInt(interval.getText().toString()));
        alarmModel.setIntervalTime(Integer.parseInt(times.getText().toString()));
        alarmModel.setMedicineShape(((MedicineType) unitMeasure.getAdapter().getItem(unitMeasure.getSelectedItemPosition())).getMedicineType());
        alarmModel.setIntervalDay(Integer.parseInt(numOfDays.getText().toString()));
        alarmModel.setStartingDate(dateSet);
        if (onStatus.isChecked()) {
            alarmModel.setActive();
        }
        if (offStatus.isChecked()) {
            alarmModel.setInactive();
        }
        dbHelperAlarmModel = new DBHelperAlarmModel(this, alarmModel);
        long num = dbHelperAlarmModel.insertAlarm(alarmModel);
        if (num > 0) {
            Toast.makeText(this, "Pengaturan alarm telah berhasil disimpan!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Snackbar.make(mainLayout, "Pengaturan alarm gagal disimpan!", Snackbar.LENGTH_LONG).show();
        }
    }

    private void onClickEditForm() {

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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setFonts() {
        ArrayList<TextView> arrayList = new ArrayList<>();
        arrayList.add(medicineName);
        arrayList.add(interval);
        arrayList.add(times);
        arrayList.add(perText);
        arrayList.add(numOfDays);
        arrayList.add(dayText);
        arrayList.add(datePick);
        arrayList.add(btnSetAlarm);
        arrayList.add(onStatus);
        arrayList.add(offStatus);
        ArrayList<TextView> arrayListB = new ArrayList<>();
        arrayListB.add(medicineNameTitle);
        arrayListB.add(doseTitle);
        arrayListB.add(scheduleTitle);
        arrayListB.add(alarmStatusTitle);
        ViewFaceUtility.applyFonts(arrayList, this, "fonts/Dosis-Medium.otf");
        ViewFaceUtility.applyFonts(arrayListB, this, "fonts/Dosis-Bold.otf");
        ViewFaceUtility.applyTextInputLayout(datePickInputLayout, this, "fonts/Dosis-Medium.otf");
    }
}
