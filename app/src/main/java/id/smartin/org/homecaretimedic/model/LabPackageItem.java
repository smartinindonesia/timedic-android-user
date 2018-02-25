package id.smartin.org.homecaretimedic.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hafid on 11/11/2017.
 */

public class LabPackageItem {
    @SerializedName("id")
    private int id;
    @SerializedName("packageName")
    private String name;
    @SerializedName("packageCode")
    private String packageCode;
    @SerializedName("packageDescription")
    private String packageDescription;
    @SerializedName("price")
    private String price;
    @SerializedName("uriPackageIcon")
    private String url_icon;
    private boolean selected;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl_icon() {
        return url_icon;
    }

    public void setUrl_icon(String url_icon) {
        this.url_icon = url_icon;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return name;
    }
}
