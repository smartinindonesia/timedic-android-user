package id.smartin.org.homecaretimedic.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hafid on 4/12/2018.
 */

public class BankTransferMethodOpt {
    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String methodName;
    @SerializedName("logo")
    private Integer logoDrawable;
    @SerializedName("logoPath")
    private String logoPath;

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
