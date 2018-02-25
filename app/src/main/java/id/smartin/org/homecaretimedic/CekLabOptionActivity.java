package id.smartin.org.homecaretimedic;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.adapter.LabPackageAdapter;
import id.smartin.org.homecaretimedic.adapter.LayananLabAdapter;
import id.smartin.org.homecaretimedic.adapter.LayananLabAutoCompleteAdapter;
import id.smartin.org.homecaretimedic.config.Action;
import id.smartin.org.homecaretimedic.config.RequestCode;
import id.smartin.org.homecaretimedic.customuicompt.GridSpacingItemDecoration;
import id.smartin.org.homecaretimedic.model.LabPackageItem;
import id.smartin.org.homecaretimedic.model.LayananLab;
import id.smartin.org.homecaretimedic.tools.TextFormatter;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class CekLabOptionActivity extends AppCompatActivity {
    public static final String TAG = "[CekLabOptionActivity]";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.autoSearchLabService)
    AutoCompleteTextView autoSearchLabService;
    @BindView(R.id.card_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.addeditem_recycler_view)
    RecyclerView addedItemView;
    @BindView(R.id.selectItemLayananLayout)
    LinearLayout selectItemLayananLayout;
    @BindView(R.id.rujukanLayout)
    LinearLayout rujukanLayout;
    @BindView(R.id.addedItemLayout)
    RelativeLayout addedItemLayout;
    @BindView(R.id.packageLayout)
    RelativeLayout packageLayout;
    @BindView(R.id.totalPrice)
    TextView selectedTotalPrice;
    @BindView(R.id.btnRujukanDokter)
    Button btnRujukanDokter;
    @BindView(R.id.nextButton)
    Button btnGotoMap;
    @BindView(R.id.selectItem)
    Button selectItem;

    private List<LabPackageItem> labPackageItemList = new ArrayList<LabPackageItem>();
    private LabPackageAdapter labPackageAdapter;

    private ArrayList<LayananLab> layananLabs = new ArrayList<>();
    private LayananLabAutoCompleteAdapter adapterLayanan;

    private List<LayananLab> selectedLayananLabs = new ArrayList<>();
    private LayananLabAdapter selectedLabAdapter;

    BroadcastReceiver refresher = new MyReceiver();
    Intent nextIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_lab_option);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        createTitleBar();
        selectedLabAdapter = new LayananLabAdapter(getApplicationContext(), selectedLayananLabs);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        addedItemView.setLayoutManager(mLayoutManager);
        addedItemView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        addedItemView.setItemAnimator(new DefaultItemAnimator());
        addedItemView.setAdapter(selectedLabAdapter);

        adapterLayanan = new LayananLabAutoCompleteAdapter(getApplicationContext(), R.layout.layanan_lab_item, layananLabs);
        autoSearchLabService.setAdapter(adapterLayanan);
        autoSearchLabService.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LayananLab l = (LayananLab) parent.getAdapter().getItem(position);
                if (!selectedLayananLabs.contains(l)) selectedLayananLabs.add(l);
                selectedLabAdapter.notifyDataSetChanged();
                if (selectedLabAdapter.getItemCount() > 0) {
                    setLayananLabsAvailable();
                    selectedTotalPrice.setText("Harga total : " + TextFormatter.doubleToRupiah(selectedLabAdapter.sumOfPrice()));
                } else {
                    setLayananLabsDisable();
                }
            }
        });
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, GridSpacingItemDecoration.dpToPx(10), true, 0));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        labPackageAdapter = new LabPackageAdapter(this, getApplicationContext(), labPackageItemList);
        recyclerView.setAdapter(labPackageAdapter);

        btnRujukanDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
            }
        });

        btnGotoMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(nextIntent);
                finish();
            }
        });

        nextIntent = new Intent(CekLabOptionActivity.this, MapSelectorActivity.class);
        prepareLayanan();
        prepareLabPackageItems();

        selectItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(nextIntent);
                finish();
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

    private void openImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih");
        builder.setItems(RequestCode.CAMERA_ACTION_ITEM, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (RequestCode.CAMERA_ACTION_ITEM[i].equals(RequestCode.CAMERA_ACTION_ITEM[0])) {
                    int permission = ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.CAMERA);
                    if (permission == PackageManager.PERMISSION_GRANTED) {
                        EasyImage.openCamera(CekLabOptionActivity.this, RequestCode.REQUEST_CODE_CAMERA);
                    } else {
                        Toast.makeText(getApplicationContext(), "Penggunaan kamera belum diizinkan oleh pengguna", Toast.LENGTH_LONG).show();
                    }
                } else if (RequestCode.CAMERA_ACTION_ITEM[i].equals(RequestCode.CAMERA_ACTION_ITEM[1])) {
                    EasyImage.openGallery(CekLabOptionActivity.this, RequestCode.REQUEST_CODE_GALLERY);
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                switch (type) {
                    case RequestCode.REQUEST_CODE_CAMERA:
                        imageFile.getAbsolutePath();
                        startActivity(nextIntent);
                        finish();
                        break;
                    case RequestCode.REQUEST_CODE_GALLERY:
                        imageFile.getAbsolutePath();
                        startActivity(nextIntent);
                        finish();
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Action.BROADCAST_DELETE_LAYANAN_EVENT);
        registerReceiver(refresher, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(refresher);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(refresher);
        } catch (IllegalArgumentException err) {
            Log.i(TAG, "Broadcast receiver unregistered already...");
        }
        super.onDestroy();
    }

    private void prepareLabPackageItems() {
        Log.i(TAG, "Preparing list");
        labPackageItemList.clear();
        LabPackageItem a = new LabPackageItem();
        a.setId(0);
        a.setName("Complete Check Up");
        a.setPrice("Rp 20.000");
        a.setUrl_icon("https://tinypng.com/web/output/9ucnfrgjbhjqn412u4m8kd38mrb40z41/Icon_CompleteCheckUp.png");
        labPackageItemList.add(a);
        LabPackageItem b = new LabPackageItem();
        b.setId(1);
        b.setName("Diabetic Screening");
        b.setPrice("Rp 20.000");
        b.setUrl_icon("https://tinypng.com/web/output/jwkad86g1x9wpvunkrbu2f1uztzum73x/Icon_DiabeticScreening.png");
        labPackageItemList.add(b);
        labPackageAdapter.notifyDataSetChanged();
    }

    public void prepareLayanan() {
        layananLabs.clear();
        LayananLab layananLab = new LayananLab();
        layananLab.setId(1);
        layananLab.setHargaLayanan(5000);
        layananLab.setNamaLayanan("HomecareService 1");
        LayananLab layananLab1 = new LayananLab();
        layananLab1.setId(2);
        layananLab1.setHargaLayanan(5000);
        layananLab1.setNamaLayanan("HomecareService 2");
        layananLabs.add(layananLab);
        layananLabs.add(layananLab1);
        adapterLayanan.notifyDataSetChanged();
    }

    public void setLayananLabsAvailable() {
        addedItemLayout.setVisibility(View.VISIBLE);
        packageLayout.setVisibility(View.GONE);
        rujukanLayout.setVisibility(View.GONE);
    }

    public void setLayananLabsDisable() {
        addedItemLayout.setVisibility(View.GONE);
        packageLayout.setVisibility(View.VISIBLE);
        rujukanLayout.setVisibility(View.VISIBLE);
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Action.BROADCAST_DELETE_LAYANAN_EVENT)) {
                if (intent.getIntExtra("number_of_item", 0) == 0) {
                    setLayananLabsDisable();
                }
                selectedTotalPrice.setText("Harga total : " + TextFormatter.doubleToRupiah(selectedLabAdapter.sumOfPrice()));
            }
        }
    }
}
