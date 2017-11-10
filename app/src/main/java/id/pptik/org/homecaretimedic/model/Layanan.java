package id.pptik.org.homecaretimedic.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hafid on 9/24/2017.
 */

public class Layanan {
    @SerializedName("id")
    private int id;
    @SerializedName("serviceCode")
    private String kodeLayanan;
    @SerializedName("serviceName")
    private String namaLayanan;
    @SerializedName("serviceType")
    private String tipeLayanan;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKodeLayanan() {
        return kodeLayanan;
    }

    public void setKodeLayanan(String kodeLayanan) {
        this.kodeLayanan = kodeLayanan;
    }

    public String getNamaLayanan() {
        return namaLayanan;
    }

    public void setNamaLayanan(String namaLayanan) {
        this.namaLayanan = namaLayanan;
    }

    public String getTipeLayanan() {
        return tipeLayanan;
    }

    public void setTipeLayanan(String tipeLayanan) {
        this.tipeLayanan = tipeLayanan;
    }

    @Override
    public String toString() {
        return namaLayanan;
    }
}
