package id.smartin.org.homecaretimedic.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hafid on 11/11/2017.
 */

public class LayananLab {
    @SerializedName("id")
    private long id;
    @SerializedName("nama")
    private String namaLayanan;
    @SerializedName("harga")
    private double hargaLayanan;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNamaLayanan() {
        return namaLayanan;
    }

    public void setNamaLayanan(String namaLayanan) {
        this.namaLayanan = namaLayanan;
    }

    public double getHargaLayanan() {
        return hargaLayanan;
    }

    public void setHargaLayanan(double hargaLayanan) {
        this.hargaLayanan = hargaLayanan;
    }

    @Override
    public String toString() {
        return namaLayanan;
    }
}
