package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.media.Image;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.adapter.MedicineTypeSpinner;
import id.smartin.org.homecaretimedic.config.VarConst;
import id.smartin.org.homecaretimedic.customuicompt.InputFilterMinMax;
import id.smartin.org.homecaretimedic.model.MedicineType;
import id.smartin.org.homecaretimedic.model.utilitymodel.AlarmModel;
import id.smartin.org.homecaretimedic.tools.ConverterUtility;
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
    @BindView(R.id.time1)
    Button time1;
    @BindView(R.id.time2)
    Button time2;
    @BindView(R.id.time3)
    Button time3;
    @BindView(R.id.time4)
    Button time4;
    @BindView(R.id.time5)
    Button time5;

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


    ArrayList<Button> timeButtons = new ArrayList<>();

    private MedicineTypeSpinner medicineTypeSpinner;
    private DatePickerDialog datePickerDialog;

    private AlarmModel alarmModel = new AlarmModel();
    private AlarmModel.AlarmTime[] alarmTimes = new AlarmModel.AlarmTime[5];
    private DBHelperAlarmModel dbHelperAlarmModel;
    private String dateSet;
    private boolean editmode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder_item);
        ButterKnife.bind(this);
        createTitleBar();

        editmode = getIntent().getBooleanExtra("iseditmode", false);

        medicineTypeSpinner = new MedicineTypeSpinner(this, this, VarConst.getMedType());
        unitMeasure.setAdapter(medicineTypeSpinner);

        time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTimeButtonClick(time1, 0);
            }
        });
        time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTimeButtonClick(time2, 1);
            }
        });
        time3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTimeButtonClick(time3, 2);
            }
        });
        time4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTimeButtonClick(time4, 3);
            }
        });
        time5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTimeButtonClick(time5, 4);
            }
        });

        timeButtons.clear();
        timeButtons.add(time1);
        timeButtons.add(time2);
        timeButtons.add(time3);
        timeButtons.add(time4);
        timeButtons.add(time5);

        btnChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int day = mcurrentTime.get(Calendar.DAY_OF_MONTH) + 2;
                final int month = mcurrentTime.get(Calendar.MONTH);
                int year = mcurrentTime.get(Calendar.YEAR);
                String dateRaw = day + "-" + (month + 1) + "-" + year;
                final String inputPattern = "yyyy-MM-dd";
                final String outputPattern2 = "dd-MMM-yyyy";
                String resultPattern = ConverterUtility.convertDate(dateRaw, inputPattern, outputPattern2);
                datePickerDialog = new DatePickerDialog(AddReminderItemActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String indate = year + "-" + (month + 1) + "-" + dayOfMonth;
                        String resultPattern = ConverterUtility.convertDate(indate, inputPattern, outputPattern2);
                        datePick.setText(resultPattern);
                        dateSet = year + "-" + (month + 1) + "-" + dayOfMonth + " 00:00:00";
                    }
                }, mcurrentTime.get(Calendar.YEAR), mcurrentTime.get(Calendar.MONTH), mcurrentTime.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setTitle("Pilih tanggal pelayanan");
                datePickerDialog.show();
                datePick.setText(resultPattern);
            }
        });

        btnSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editmode) {
                    onClickNewForm();
                } else {
                    onClickEditForm();
                }
            }
        });

        times.setFilters(new InputFilter[]{new InputFilterMinMax("1", "5")});
        times.addTextChangedListener(new TextWatcher() {
            @TargetApi(Build.VERSION_CODES.M)
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    Integer switchInt = Integer.parseInt(s.toString());
                    switch (switchInt) {
                        case 0:
                            dissmissTimeButton();
                            break;
                        case 1:
                            time1.setVisibility(View.VISIBLE);
                            time2.setVisibility(View.GONE);
                            time3.setVisibility(View.GONE);
                            time4.setVisibility(View.GONE);
                            time5.setVisibility(View.GONE);
                            break;
                        case 2:
                            time1.setVisibility(View.VISIBLE);
                            time2.setVisibility(View.VISIBLE);
                            time3.setVisibility(View.GONE);
                            time4.setVisibility(View.GONE);
                            time5.setVisibility(View.GONE);
                            break;
                        case 3:
                            time1.setVisibility(View.VISIBLE);
                            time2.setVisibility(View.VISIBLE);
                            time3.setVisibility(View.VISIBLE);
                            time4.setVisibility(View.GONE);
                            time5.setVisibility(View.GONE);
                            break;
                        case 4:
                            time1.setVisibility(View.VISIBLE);
                            time2.setVisibility(View.VISIBLE);
                            time3.setVisibility(View.VISIBLE);
                            time4.setVisibility(View.VISIBLE);
                            time5.setVisibility(View.GONE);
                            break;
                        case 5:
                            time1.setVisibility(View.VISIBLE);
                            time2.setVisibility(View.VISIBLE);
                            time3.setVisibility(View.VISIBLE);
                            time4.setVisibility(View.VISIBLE);
                            time5.setVisibility(View.VISIBLE);
                            break;
                        default:
                            dissmissTimeButton();
                            break;

                    }
                } else {
                    dissmissTimeButton();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });
        dissmissTimeButton();
        if (editmode) {
            openEditForm();
        }

        setFonts();
    }

    private void dissmissTimeButton() {
        time1.setVisibility(View.GONE);
        time2.setVisibility(View.GONE);
        time3.setVisibility(View.GONE);
        time4.setVisibility(View.GONE);
        time5.setVisibility(View.GONE);
    }

    private void openEditForm() {
        alarmModel = (AlarmModel) getIntent().getSerializableExtra("alarm_object");
        medicineName.setText(alarmModel.getMedicineName().toString());
        interval.setText(alarmModel.getNumOfMedicine().toString());
        times.setText(alarmModel.getIntervalTime().toString());
        unitMeasure.setSelection(VarConst.getMedicineTypeIndex(alarmModel.getMedicineShape()));
        numOfDays.setText(alarmModel.getIntervalDay().toString());
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd-MM-yyyy";
        String outputPattern2 = "dd-MMM-yyyy";
        for (int i = 0; i < alarmModel.getIntervalTime() ; i++){
            AlarmModel.AlarmTime alarmTime = alarmModel.getTime().get(i);
            timeButtons.get(i).setHint(alarmTime.getTime());
        }
        dateSet = ConverterUtility.convertDate(alarmModel.getStartingDate(), inputPattern, outputPattern);
        datePick.setText(ConverterUtility.convertDate(alarmModel.getStartingDate(), inputPattern, outputPattern2));
        if (alarmModel.getStatus().getId() == 1) {
            onStatus.setChecked(true);
        } else if (alarmModel.getStatus().getId() == 2) {
            offStatus.setChecked(true);
        }
    }

    private void onTimeButtonClick(final Button timebtn, final int index) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AddReminderItemActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                timebtn.setHint(selectedHour + ":" + selectedMinute + ":00");
                alarmTimes[index] = new AlarmModel.AlarmTime();
                alarmTimes[index].setTime(selectedHour + ":" + selectedMinute + ":00");
                alarmTimes[index].setAlarmId(alarmModel.getId());
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Pilih waktu");
        mTimePicker.show();
    }

    private void onClickNewForm() {
        if (makeSureFormNotNull()) {
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
            if (makeSureFilledTime()) {
                long row_alm_id = dbHelperAlarmModel.insertAlarm(alarmModel);
                for (int i = 0; i < Integer.parseInt(times.getText().toString()); i++) {
                    dbHelperAlarmModel.getTimeListTable().insertTime(row_alm_id, alarmTimes[i]);
                }
                if (row_alm_id > 0) {
                    Toast.makeText(this, "Pengaturan alarm telah berhasil disimpan!", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Snackbar.make(mainLayout, "Pengaturan alarm gagal disimpan!", Snackbar.LENGTH_LONG).show();
                }
            } else {
                Snackbar.make(mainLayout, "Terdapat pengaturan waktu alarm yang masih kosong!", Snackbar.LENGTH_LONG).show();
            }
        } else {
            Snackbar.make(mainLayout, "Mohon isi form dengan benar!", Snackbar.LENGTH_LONG).show();
        }
    }

    private void onClickEditForm() {
        if (makeSureFormNotNull()) {
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
            /**
             * if no changes in alarm time just update, vice versa clear related alarm time then recreate again
             */
            if (alarmModel.getTime().size() == Integer.parseInt(times.getText().toString())) {
                int num = dbHelperAlarmModel.updateAlarm(alarmModel);
                if (num > 0) {
                    Toast.makeText(this, "Pengaturan alarm telah berhasil disimpan!", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Snackbar.make(mainLayout, "Pengaturan alarm gagal disimpan!", Snackbar.LENGTH_LONG).show();
                }
            } else {
                dbHelperAlarmModel.getTimeListTable().deleteAll();
                alarmModel.getTime().clear();
                int num = dbHelperAlarmModel.updateAlarm(alarmModel);
                if (makeSureFilledTime()) {
                    for (int i = 0; i < Integer.parseInt(times.getText().toString()); i++) {
                        dbHelperAlarmModel.getTimeListTable().insertTime(alarmTimes[i]);
                    }
                    if (num > 0) {
                        Toast.makeText(this, "Pengaturan alarm telah berhasil disimpan!", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Snackbar.make(mainLayout, "Pengaturan alarm gagal disimpan!", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(mainLayout, "Terdapat pengaturan waktu alarm yang masih kosong!", Snackbar.LENGTH_LONG).show();
                }
            }
        } else {
            Snackbar.make(mainLayout, "Mohon isi form dengan benar!", Snackbar.LENGTH_LONG).show();
        }
    }

    private boolean makeSureFilledTime() {
        int size = Integer.parseInt(times.getText().toString());
        for (int i = 0; i < size; i++) {
            if (alarmTimes[i] == null) {
                return false;
            }
        }
        return true;
    }

    private boolean makeSureFormNotNull() {
        if (medicineName.getText().toString().trim().equals("") || medicineName.getText().toString().isEmpty() || medicineName.getText().toString() == null) {
            return false;
        }
        if (interval.getText().toString().trim().equals("") || interval.getText().toString().isEmpty() || interval.getText().toString() == null) {
            return false;
        }
        if (times.getText().toString().trim().equals("") || times.getText().toString().isEmpty() || times.getText().toString() == null) {
            return false;
        }
        if (numOfDays.getText().toString().trim().equals("") || numOfDays.getText().toString().isEmpty() || numOfDays.getText().toString() == null) {
            return false;
        }
        if (dateSet.trim().equals("") || dateSet.isEmpty() || dateSet == null) {
            return false;
        }
        if (!onStatus.isChecked() && !offStatus.isChecked()) {
            return false;
        }
        return true;
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
        arrayList.add(time1);
        arrayList.add(time2);
        arrayList.add(time3);
        arrayList.add(time4);
        arrayList.add(time5);
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
