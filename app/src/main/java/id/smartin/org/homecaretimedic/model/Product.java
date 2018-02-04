package id.smartin.org.homecaretimedic.model;

/**
 * Created by Hafid on 8/23/2017.
 */

public class Product {
    private String id;
    private String nama_produk;
    private String pic_url;
    private int quantity;
    private String description;
    private double price;
    private double discount;

    public Product() {
    }

    public Product(String id, String nama_produk, String pic_url) {
        this.id = id;
        this.nama_produk = nama_produk;
        this.pic_url = pic_url;
    }

    public Product(String id, String nama_produk, String pic_url, int quantity, String description) {
        this.id = id;
        this.nama_produk = nama_produk;
        this.pic_url = pic_url;
        this.quantity = quantity;
        this.description = description;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

