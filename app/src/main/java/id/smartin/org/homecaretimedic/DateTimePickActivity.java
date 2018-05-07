package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
//import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.config.UIConstants;
import id.smartin.org.homecaretimedic.customuicompt.ButtonModel;
import id.smartin.org.homecaretimedic.model.submitmodel.PickedDateTime;
import id.smartin.org.homecaretimedic.model.submitmodel.SubmitInfo;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;

public class DateTimePickActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.notifSign)
    TextView notifSign;
    @BindView(R.id.btnChooseDate)
    ImageButton btnChooseDate;
    @BindView(R.id.gotoNext)
    Button btnGoToNext;
    @BindView(R.id.datePick)
    EditText tglPelayanan;
    @BindView(R.id.timeSelectionHeader)
    TextView timeSelectionHeader;
    @BindView(R.id.btnLayout)
    LinearLayout layoutVertical;
    @BindView(R.id.datePickInputLayout)
    TextInputLayout datePickInputLayout;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;


    //private DatePickerDialog datePickerDialog;
    private PickedDateTime pickedDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time_pick);
        ButterKnife.bind(this);
        createTitleBar();

        createBtnLayout();

        btnChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int day = mcurrentTime.get(Calendar.DAY_OF_MONTH) + 2;
                int month = mcurrentTime.get(Calendar.MONTH);
                int year = mcurrentTime.get(Calendar.YEAR);
                Calendar next2Week = Calendar.getInstance();
                next2Week.add(Calendar.DATE, 14);
                Calendar minDate = Calendar.getInstance();
                minDate.add(Calendar.DATE, 2);

                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(DateTimePickActivity.this, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                dpd.setMinDate(minDate);
                dpd.setMaxDate(next2Week);
                dpd.show(getFragmentManager(), "Datepickerdialog");
                //dpd.setDisabledDays(getSundayList(minDate, next2Week));

                tglPelayanan.setText(day + "-" + (month + 1) + "-" + year);
            }
        });

        btnGoToNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitInfo.selectedDateTime = pickedDateTime;
                Boolean go1 = pickedDateTime.getDate() == null || pickedDateTime.getTime() == null;
                if (!go1) {
                    Boolean go2 = pickedDateTime.getTime().equals("") || pickedDateTime.getTime().isEmpty() || pickedDateTime.getDate().isEmpty() || pickedDateTime.getDate().equals("");
                    if (!go2) {
                        if (SubmitInfo.serviceAvailable.equals(UIConstants.HOMECARE_SERVICE)) {
                            Intent intent = new Intent(DateTimePickActivity.this, AcceptanceActivity.class);
                            startActivity(intent);
                        } else if (SubmitInfo.serviceAvailable.equals(UIConstants.CHECKLAB_SERVICE)) {
                            Intent intent = new Intent(DateTimePickActivity.this, LabAcceptanceActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        Snackbar.make(mainLayout, "Mohon lengkapi tanggal dan waktu!", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(mainLayout, "Mohon lengkapi tanggal dan waktu!", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        pickedDateTime = new PickedDateTime();
        setFonts();
    }

    private Calendar[] getSundayList(Calendar cAux, Calendar gc){
        List<Calendar> dayslist= new LinkedList<Calendar>();
        Calendar[] daysArray;
        while ( cAux.getTimeInMillis() <= gc.getTimeInMillis()) {
            if (cAux.get(Calendar.DAY_OF_WEEK) == 1) {
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(cAux.getTimeInMillis());
                dayslist.add(c);
            }
            cAux.setTimeInMillis(cAux.getTimeInMillis() + (24*60*60*1000));
        }
        daysArray = new Calendar[dayslist.size()];
        for (int i = 0; i<daysArray.length;i++)
        {
            daysArray[i]=dayslist.get(i);
        }
        return daysArray;
    }

    private void disableButton(ButtonModel<String>[] buttons, int start, int end){
        for (int j = start; j < end; j++) {
            buttons[j].button.setText(j + ":00");
            buttons[j].button.setEnabled(false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                buttons[j].button.setBackground(getResources().getDrawable(R.drawable.red_button_background));
            } else {
                buttons[j].button.setBackgroundDrawable(getResources().getDrawable(R.drawable.red_button_background));
            }
        }
    }

    private void hideButton(ButtonModel<String>[] buttons, int start, int end){
        for (int j = start; j < end; j++) {
            buttons[j].button.setVisibility(View.GONE);
        }
    }

    public void createBtnLayout() {
        int row_count = 6;
        LinearLayout rowLayout = null;
        final ButtonModel<String>[] buttons = new ButtonModel[24];

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1);

        for (int i = 0; i < buttons.length; i++) {
            if (i % row_count == 0) {
                rowLayout = new LinearLayout(this);
                rowLayout.setWeightSum(row_count);
                layoutVertical.addView(rowLayout, param);
            }

            buttons[i] = new ButtonModel<String>();
            buttons[i].button = new ToggleButton(this);
            buttons[i].button.setText(i + ":00");
            buttons[i].button.setTextSize(8.0f);
            buttons[i].button.setHeight(20);

            buttons[i].button.setTextColor(getResources().getColorStateList(R.color.sign_in_selector_fg));
            //ViewFaceUtility.applyFont(buttons[i].button, this, "fonts/Dosis-Medium.otf");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                buttons[i].button.setBackground(getResources().getDrawable(R.drawable.green_button_bg));
            } else {
                buttons[i].button.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_button_bg));
            }

            LinearLayout blayout = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            blayout.setLayoutParams(params);
            blayout.setPadding(2, 2, 2, 2);
            blayout.addView(buttons[i].button);
            rowLayout.addView(blayout, param);
        }

        hideButton(buttons, 0, 6);
        hideButton(buttons, 18, 24);
        disableButton(buttons, 0, 8);
        disableButton(buttons, 17, 24);

        for (int i = 0; i < buttons.length; i++) {
            final int finalI = i;
            buttons[finalI].button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < buttons.length; j++) {
                        buttons[j].button.setText(j + ":00");
                        buttons[j].setOn(false);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            buttons[j].button.setBackground(getResources().getDrawable(R.drawable.green_button_bg));
                        } else {
                            buttons[j].button.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_button_bg));
                        }
                    }
                    buttons[finalI].button.setText("On");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        buttons[finalI].button.setBackground(getResources().getDrawable(R.drawable.red_button_background));
                    } else {
                        buttons[finalI].button.setBackgroundDrawable(getResources().getDrawable(R.drawable.red_button_background));
                    }
                    buttons[finalI].setOn(true);
                    pickedDateTime.setTime("" + finalI + ":00");
                    disableButton(buttons, 0, 8);
                    disableButton(buttons, 17, 24);
                }
            });
        }
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

    private void setFonts() {
        ArrayList<TextView> arrayList = new ArrayList<>();
        arrayList.add(timeSelectionHeader);
        arrayList.add(btnGoToNext);
        arrayList.add(tglPelayanan);
        arrayList.add(timeSelectionHeader);
        arrayList.add(notifSign);
        ViewFaceUtility.applyFonts(arrayList, this, "fonts/Dosis-Medium.otf");
        ViewFaceUtility.applyTextInputLayout(datePickInputLayout, this, "fonts/Dosis-Medium.otf");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        tglPelayanan.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
        pickedDateTime.setDate(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
    }
}
