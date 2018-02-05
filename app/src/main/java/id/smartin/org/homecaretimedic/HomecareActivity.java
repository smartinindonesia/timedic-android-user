package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.config.UIConstants;
import id.smartin.org.homecaretimedic.model.submitmodel.SubmitInfo;

public class HomecareActivity extends AppCompatActivity {
    public static String TAG = "[HomecareActivity]";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btnPerawat)
    ImageButton btnPerawat;
    @BindView(R.id.btnFisioterapis)
    ImageButton btnFisioterapis;
    @BindView(R.id.btnCeklab)
    ImageButton btnCeklab;
    @BindView(R.id.btnBidan)
    ImageButton btnBidan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homecare);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        createTitleBar();
        btnPerawat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitInfo.serviceAvailable = UIConstants.HOMECARE_SERVICE;
                Intent intent = new Intent(getApplicationContext(), LayananLokasiActivity.class);
                startActivity(intent);
            }
        });
        btnCeklab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitInfo.serviceAvailable = UIConstants.CHECKLAB_SERVICE;
                Intent intent = new Intent(getApplicationContext(), CekLabOptionActivity.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("RestrictedApi")
    public void createTitleBar() {
        ActionBar mActionbar = getSupportActionBar();
        mActionbar.setDisplayHomeAsUpEnabled(false);
        mActionbar.setDefaultDisplayHomeAsUpEnabled(false);
        mActionbar.setDisplayShowTitleEnabled(false);
        mActionbar.setDisplayShowHomeEnabled(false);
        mActionbar.setDisplayShowCustomEnabled(true);
        View view = getLayoutInflater().inflate(R.layout.action_bar_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        mActionbar.setCustomView(view, params);
    }
}
