package id.pptik.org.homecaretimedic;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.pptik.org.homecaretimedic.adapter.HealthProductAdapter;
import id.pptik.org.homecaretimedic.customuicompt.RecyclerTouchListener;
import id.pptik.org.homecaretimedic.model.Produk;

public class HealthProductActivity extends AppCompatActivity {
    public static String TAG = "[HealthProductActivity]";

    private HealthProductAdapter healthProductAdapter;
    private List<Produk> produkList = new ArrayList<>();

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
        healthProductAdapter = new HealthProductAdapter(getApplicationContext(), produkList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(healthProductAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Produk produk = produkList.get(position);
                Toast.makeText(getApplicationContext(), produk.getNama_produk(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        healthProductAdapter.notifyDataSetChanged();
    }

    private void createHealthProduct(){
        Produk produk1 = new Produk("0","Kursi Roda", "https://www.medicalogy.com/blog/wp-content/uploads/2016/07/Kursi-Roda-1.jpg");
        Produk produk2 = new Produk("1","Glukometer","http://sehatista.com/wp-content/uploads/2016/10/alat-tes-gula-darah-Glukometer.jpg");
        produkList.add(produk1);
        produkList.add(produk2);
    }
}
