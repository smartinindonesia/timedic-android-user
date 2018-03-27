package id.smartin.org.homecaretimedic.model.parammodel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hafid on 3/27/2018.
 */

public class CaregiverRateParam {
    @SerializedName("id")
    private Long id;
    @SerializedName("rate")
    private Double rate;
    @SerializedName("comment")
    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
