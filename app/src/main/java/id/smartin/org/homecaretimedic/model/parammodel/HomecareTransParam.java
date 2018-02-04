package id.smartin.org.homecaretimedic.model.parammodel;

import com.google.gson.annotations.SerializedName;

import id.smartin.org.homecaretimedic.model.HomecareTransactionStatus;
import id.smartin.org.homecaretimedic.model.PaymentMethod;

/**
 * Created by Hafid on 1/20/2018.
 */

public class HomecareTransParam {
    @SerializedName("id")
    private Long id;
    @SerializedName("date")
    private String date;
    @SerializedName("predictionPrice")
    private double predictionPrice;
    @SerializedName("prepaidPrice")
    private double prepaidPrice;
    @SerializedName("expiredTransactionTime")
    private Long expiredTransactionTime;
    @SerializedName("receiptPath")
    private String receiptPath;
    @SerializedName("locationLatitude")
    private double locationLatitude;
    @SerializedName("locationLongitude")
    private double locationLongitude;
    @SerializedName("transactionDescription")
    private String transactionDescription;
    @SerializedName("transactionStatusId")
    private HomecareTransactionStatus homecareTransactionStatus;
    @SerializedName("paymentMethodId")
    private PaymentMethod paymentMethod;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPredictionPrice() {
        return predictionPrice;
    }

    public void setPredictionPrice(double predictionPrice) {
        this.predictionPrice = predictionPrice;
    }

    public double getPrepaidPrice() {
        return prepaidPrice;
    }

    public void setPrepaidPrice(double prepaidPrice) {
        this.prepaidPrice = prepaidPrice;
    }

    public Long getExpiredTransactionTime() {
        return expiredTransactionTime;
    }

    public void setExpiredTransactionTime(Long expiredTransactionTime) {
        this.expiredTransactionTime = expiredTransactionTime;
    }

    public String getReceiptPath() {
        return receiptPath;
    }

    public void setReceiptPath(String receiptPath) {
        this.receiptPath = receiptPath;
    }

    public double getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public double getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(double locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public HomecareTransactionStatus getHomecareTransactionStatus() {
        return homecareTransactionStatus;
    }

    public void setHomecareTransactionStatus(HomecareTransactionStatus homecareTransactionStatus) {
        this.homecareTransactionStatus = homecareTransactionStatus;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
 * {
 "homecareTransactionCaregiverlistList": [],
 "homecareAssessmentRecordList" :
 [
 {
 "assessmentAnswer": "27 Tahun",
 "filePath": "c://pictures",
 "idAssessment": {"id": 1}
 },
 {
 "assessmentAnswer": "Islam",
 "filePath": "c://pictures",
 "idAssessment": {"id": 4}
 } , and so on .....
 ],
 "transactionStatusId": {"id" : 2}, // 1. Paid, 2. Unpaid, 3. Failed, 4. Cancelled. 5. Expire, 6. Finish, 7. Paid Down Payment
 "homecarePatientId": {"id": 9},
 "paymentMethodId": {"id": 1} // 1. Transfer Bank, 2 Virtual Account Bank, 3. Timedic Pay.
 }

 */
}
