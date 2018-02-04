package id.smartin.org.homecaretimedic.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.model.Product;

/**
 * Created by Hafid on 8/23/2017.
 */

public class HealthProductAdapter extends RecyclerView.Adapter<HealthProductAdapter.MyViewHolder> {
    private static String TAG = "[HealthProductAdapter]";

    private List<Product> produkList;
    private Context context;
    private Activity activity;

    public HealthProductAdapter(Activity activity, Context context, List<Product> produkList) {
        this.produkList = produkList;
        this.context = context;
        this.activity = activity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.packageIcon)
        public ImageView packageIcon;
        @BindView(R.id.productName)
        public TextView productName;
        @BindView(R.id.packagePriceReal)
        public TextView packagePriceReal;
        @BindView(R.id.packagePriceDiscount)
        public TextView packagePriceDiscount;
        @BindView(R.id.selectItem)
        public Button selectItem;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_package_product, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Product produk = produkList.get(position);
        holder.productName.setText(produk.getNama_produk());
        holder.packagePriceDiscount.setText("discount price");
        holder.packagePriceReal.setText("real price");
        holder.selectItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        String uri = produk.getPic_url();
        Log.i(TAG, "Pic Uri : " + uri + " " + produk.getNama_produk());
        if (!uri.equals("")) {

            RequestOptions applyReq = new RequestOptions();
            applyReq.circleCrop();

            Glide.with(context).load(uri)
                    .thumbnail(0.5f)
                    .apply(applyReq)
                    .into(holder.packageIcon);
        }
    }

    @Override
    public int getItemCount() {
        return produkList.size();
    }

    public void setProdukList(List<Product> produkList) {
        this.produkList = produkList;
    }

    public Object getItem(int i) {
        return produkList.get(i);
    }
}
