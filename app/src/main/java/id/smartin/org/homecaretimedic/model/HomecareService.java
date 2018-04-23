package id.smartin.org.homecaretimedic.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hafid on 9/24/2017.
 */

public class HomecareService {

    @SerializedName("id")
    private Integer id;
    @SerializedName("serviceCaterogry")
    private String serviceCategory;
    @SerializedName("serviceCode")
    private String serviceCode;
    @SerializedName("serviceName")
    private String serviceName;
    @SerializedName("serviceUrlIcon")
    private String serviceUrlIcon;
    @SerializedName("visitCost")
    private Double visitCost;
    @SerializedName("priceRange")
    private Double priceRange;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(String serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceUrlIcon() {
        return serviceUrlIcon;
    }

    public void setServiceUrlIcon(String serviceUrlIcon) {
        this.serviceUrlIcon = serviceUrlIcon;
    }

    public Double getVisitCost() {
        return visitCost;
    }

    public void setVisitCost(Double visitCost) {
        this.visitCost = visitCost;
    }

    public Double getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(Double priceRange) {
        this.priceRange = priceRange;
    }
}
