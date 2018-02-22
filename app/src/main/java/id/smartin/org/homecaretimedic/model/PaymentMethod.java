package id.smartin.org.homecaretimedic.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hafid on 1/20/2018.
 */

public class PaymentMethod {
    @SerializedName("id")
    private Long id;
    private String paymentMethodName;

    public PaymentMethod(Long id) {
        this.id = id;
    }

    public PaymentMethod(Long id, String paymentMethodName) {
        this.id = id;
        this.paymentMethodName = paymentMethodName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }
}
