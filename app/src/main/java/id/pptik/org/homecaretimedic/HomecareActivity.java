package id.pptik.org.homecaretimedic;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomecareActivity extends AppCompatActivity {
    public static String TAG = "[HomecareActivity]";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btnPerawat)
    Button btnPerawat;

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
                Intent intent = new Intent(getApplicationContext(), LayananLokasiActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

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
