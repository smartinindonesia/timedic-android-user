package id.smartin.org.homecaretimedic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;

public class ProductDetailActivity extends AppCompatActivity {
    public static final String TAG = "[ProductDetailActivity]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
    }
}
