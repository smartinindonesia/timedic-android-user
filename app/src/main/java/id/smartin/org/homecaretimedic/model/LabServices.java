package id.smartin.org.homecaretimedic.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hafid on 11/11/2017.
 */

public class LabServices {
    @SerializedName("id")
    private long id;
    @SerializedName("serviceName")
    private String namaLayanan;
    @SerializedName("serviceCode")
    private String serviceCode;
    @SerializedName("uriServiceIcon")
    private String uriServiceIcon;
    @SerializedName("price")
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

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getUriServiceIcon() {
        return uriServiceIcon;
    }

    public void setUriServiceIcon(String uriServiceIcon) {
        this.uriServiceIcon = uriServiceIcon;
    }

    @Override
    public String toString() {
        return namaLayanan;
    }
}
