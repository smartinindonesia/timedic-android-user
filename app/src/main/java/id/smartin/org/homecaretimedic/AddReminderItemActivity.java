package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    private AlarmModel alarmModel = new AlarmModel();
    private List<AlarmModel.AlarmTime> alarmTimes = new ArrayList<>();
    private DBHelperAlarmModel dbHelperAlarmModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder_item);
        ButterKnife.bind(this);
        createTitleBar();
        setFonts();
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

    private void setFonts(){
        ArrayList<TextView> arrayList = new ArrayList<>();
        arrayList.add(medicineName);
        ArrayList<TextView> arrayListB = new ArrayList<>();
        arrayListB.add(medicineNameTitle);
        ViewFaceUtility.applyFonts(arrayList, this, "fonts/Dosis-Medium.otf");
        ViewFaceUtility.applyFonts(arrayListB, this, "fonts/Dosis-Bold.otf");
    }
}
