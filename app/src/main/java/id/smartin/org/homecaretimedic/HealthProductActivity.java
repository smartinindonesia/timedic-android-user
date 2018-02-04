package id.smartin.org.homecaretimedic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.adapter.HealthProductAdapter;
import id.smartin.org.homecaretimedic.customuicompt.GridSpacingItemDecoration;
import id.smartin.org.homecaretimedic.model.Product;

public class HealthProductActivity extends AppCompatActivity {
    public static String TAG = "[HealthProductActivity]";

    private HealthProductAdapter healthProductAdapter;
    private List<Product> produkList = new ArrayList<>();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.product_list)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_product);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        createHealthProduct();

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, GridSpacingItemDecoration.dpToPx(10), true, 0));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        healthProductAdapter = new HealthProductAdapter(this, getApplicationContext(), produkList);
        recyclerView.setAdapter(healthProductAdapter);
        healthProductAdapter.notifyDataSetChanged();
    }

    private void createHealthProduct(){
        Product produk1 = new Product("0","Kursi Roda", "https://www.medicalogy.com/blog/wp-content/uploads/2016/07/Kursi-Roda-1.jpg");
        Product produk2 = new Product("1","Glukometer","http://sehatista.com/wp-content/uploads/2016/10/alat-tes-gula-darah-Glukometer.jpg");
        produkList.add(produk1);
        produkList.add(produk2);
    }
}
