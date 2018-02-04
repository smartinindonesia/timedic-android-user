package id.smartin.org.homecaretimedic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.model.Perawat;

/**
 * Created by Hafid on 9/23/2017.
 */

public class PerawatAdapter extends RecyclerView.Adapter<PerawatAdapter.MyViewHolder>{
    public static String TAG = "[PerawatAdapter]";

    private List<Perawat> perawatList;
    private Context context;


    public PerawatAdapter(Context context, List<Perawat> perawatList){
        this.context = context;
        this.perawatList = perawatList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.perawat_item, parent, false);
        return new PerawatAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Perawat perawat = perawatList.get(position);
        holder.nama.setText(perawat.getNamaPerawat());
        holder.kategori.setText(perawat.getKategoriPerawat());
        holder.agama.setText(perawat.getAlamat());
        holder.pendidikan.setText(perawat.getPendidikan());
        holder.skill.setText(perawat.getSkill());
        holder.btnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return perawatList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.namaPerawat)
        TextView nama;
        @BindView(R.id.kategoriPerawat)
        TextView kategori;
        @BindView(R.id.agamaPerawat)
        TextView agama;
        @BindView(R.id.skillPerawat)
        TextView skill;
        @BindView(R.id.pendidikanPerawat)
        TextView pendidikan;
        @BindView(R.id.btnPesan)
        Button btnPesan;
        @BindView(R.id.btnDetail)
        Button btnDetail;
        @BindView(R.id.profPic)
        ImageView profPic;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
