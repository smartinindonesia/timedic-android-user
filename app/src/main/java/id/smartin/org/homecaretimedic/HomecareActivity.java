package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.config.UIConstants;
import id.smartin.org.homecaretimedic.model.submitmodel.SubmitInfo;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;

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
        setSupportActionBar(toolbar);
        ViewFaceUtility.changeToolbarFont(toolbar, this,"fonts/Dosis-Bold.otf", R.color.theme_black);
        ActionBar mActionbar = getSupportActionBar();
        mActionbar.setDisplayHomeAsUpEnabled(true);
        mActionbar.setDefaultDisplayHomeAsUpEnabled(true);
        mActionbar.setDisplayShowHomeEnabled(true);
        mActionbar.setDisplayShowTitleEnabled(true);
        mActionbar.setDisplayShowCustomEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        onBackPressed();
        return true;
    }

}
