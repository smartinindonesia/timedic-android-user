package id.smartin.org.homecaretimedic.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hafid on 4/12/2018.
 */

public class TransferPaymentMethod {
    @SerializedName("id")
    private Long id;
    @SerializedName("bankName")
    private String bankName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
