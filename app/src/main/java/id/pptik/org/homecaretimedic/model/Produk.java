package id.pptik.org.homecaretimedic.model;

/**
 * Created by Hafid on 8/23/2017.
 */

public class Produk {
    private String id;
    private String nama_produk;
    private String pic_url;

    public Produk() {
    }

    public Produk(String id, String nama_produk, String pic_url) {
        this.id = id;
        this.nama_produk = nama_produk;
        this.pic_url = pic_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }
}

