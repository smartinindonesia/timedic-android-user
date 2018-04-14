package id.smartin.org.homecaretimedic.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Hafid on 4/12/2018.
 */

public class BankTransferMethodOpt implements Serializable {
    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String methodName;
    @SerializedName("logo")
    private Integer logoDrawable;
    @SerializedName("logoPath")
    private String logoPath;

    public BankTransferMethodOpt(Long id, String methodName) {
        this.id = id;
        this.methodName = methodName;
    }

    public BankTransferMethodOpt(Long id, String methodName, Integer logoDrawable) {
        this.id = id;
        this.methodName = methodName;
        this.logoDrawable = logoDrawable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Integer getLogoDrawable() {
        return logoDrawable;
    }

    public void setLogoDrawable(Integer logoDrawable) {
        this.logoDrawable = logoDrawable;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }
}
