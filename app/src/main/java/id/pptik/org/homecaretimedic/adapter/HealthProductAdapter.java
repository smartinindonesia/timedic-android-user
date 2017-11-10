package id.pptik.org.homecaretimedic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import id.pptik.org.homecaretimedic.R;
import id.pptik.org.homecaretimedic.model.Produk;

/**
 * Created by Hafid on 8/23/2017.
 */

public class HealthProductAdapter extends RecyclerView.Adapter<HealthProductAdapter.MyViewHolder> {
    private static String TAG = "[HealthProductAdapter]";

    private List<Produk> produkList;
    private Context context;

    public HealthProductAdapter(Context context, List<Produk> produkList) {
        this.produkList = produkList;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nama;
        public ImageView iconPic;

        public MyViewHolder(View view) {
            super(view);
            iconPic = (ImageView) view.findViewById(R.id.iconProduk);
            nama = (TextView) view.findViewById(R.id.itemName);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.health_product_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Produk produk = produkList.get(position);
        holder.nama.setText(produk.getNama_produk());
        String uri = produk.getPic_url();
        Log.i(TAG, "Pic Uri : " + uri + " " + produk.getNama_produk());
        if (!uri.equals("")) {

            RequestOptions applyReq = new RequestOptions();
            applyReq.circleCrop();

            Glide.with(context).load(uri)
                    .thumbnail(0.5f)
                    .apply(applyReq)
                    .into(holder.iconPic);
        }
    }

    @Override
    public int getItemCount() {
        return produkList.size();
    }

    public void setProdukList(List<Produk> produkList) {
        this.produkList = produkList;
    }

    public Object getItem(int i) {
        return produkList.get(i);
    }
}
