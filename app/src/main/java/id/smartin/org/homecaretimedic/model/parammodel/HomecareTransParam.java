package id.smartin.org.homecaretimedic.model.parammodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import id.smartin.org.homecaretimedic.model.Assessment;
import id.smartin.org.homecaretimedic.model.HomecareTransactionStatus;
import id.smartin.org.homecaretimedic.model.Patient;
import id.smartin.org.homecaretimedic.model.PaymentMethod;

/**
 * Created by Hafid on 1/20/2018.
 */

public class HomecareTransParam {
    @SerializedName("id")
    private Long id;
    @SerializedName("date")
    private Long date;
    @SerializedName("fixedPrice")
    private double fixedPrice;
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
    @SerializedName("homecareAssessmentRecordList")
    private List<AssessmentAnswerParam> assessmentList;
    @SerializedName("transactionStatusId")
    private HomecareTransactionStatus homecareTransactionStatus;
    @SerializedName("paymentMethodId")
    private PaymentMethod paymentMethod;
    @SerializedName("homecarePatientId")
    private Patient homecarePatientId;
    @SerializedName("fullAddress")
    private String fullAddress;
    @SerializedName("selectedService")
    private String selectedService;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
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

    public double getFixedPrice() {
        return fixedPrice;
    }

    public void setFixedPrice(double fixedPrice) {
        this.fixedPrice = fixedPrice;
    }

    public List<AssessmentAnswerParam> getAssessmentList() {
        return assessmentList;
    }

    public void setAssessmentList(List<AssessmentAnswerParam> assessmentList) {
        this.assessmentList = assessmentList;
    }

    public Patient getHomecarePatientId() {
        return homecarePatientId;
    }

    public void setHomecarePatientId(Patient homecarePatientId) {
        this.homecarePatientId = homecarePatientId;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getSelectedService() {
        return selectedService;
    }

    public void setSelectedService(String selectedService) {
        this.selectedService = selectedService;
    }
}
