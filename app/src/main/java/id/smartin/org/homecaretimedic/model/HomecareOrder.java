package id.smartin.org.homecaretimedic.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Hafid on 2/23/2018.
 */

public class HomecareOrder implements Serializable{
    @SerializedName("id")
    private Long id;
    @SerializedName("date")
    private Long date;
    @SerializedName("fixedPrice")
    private Double fixedPrice;
    @SerializedName("predictionPrice")
    private Double predictionPrice;
    @SerializedName("prepaidPrice")
    private Double prepaidPrice;
    @SerializedName("expiredTransactionTime")
    private Long expiredTransactionTime;
    @SerializedName("receiptPath")
    private String receiptPath;
    @SerializedName("locationLatitude")
    private Double locationLatitude;
    @SerializedName("locationLongitude")
    private Double locationLongitude;
    @SerializedName("transactionDescription")
    private String transactionDescription;
    @SerializedName("homecareTransactionCaregiverlistList")
    private ArrayList<CaregiverOrder> caregiverArrayList;
    @SerializedName("homecareAssessmentRecordList")
    private ArrayList<Assessment> homecareAssessmentRecordList;
    @SerializedName("transactionStatusId")
    private HomecareTransactionStatus homecareTransactionStatus;
    @SerializedName("homecarePatientId")
    private Patient homecarePatientId;
    @SerializedName("paymentMethodId")
    private PaymentMethod paymentMethodId;
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

    public Double getFixedPrice() {
        return fixedPrice;
    }

    public void setFixedPrice(Double fixedPrice) {
        this.fixedPrice = fixedPrice;
    }

    public Double getPredictionPrice() {
        return predictionPrice;
    }

    public void setPredictionPrice(Double predictionPrice) {
        this.predictionPrice = predictionPrice;
    }

    public Double getPrepaidPrice() {
        return prepaidPrice;
    }

    public void setPrepaidPrice(Double prepaidPrice) {
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

    public Double getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(Double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public Double getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(Double locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public ArrayList<Assessment> getHomecareAssessmentRecordList() {
        return homecareAssessmentRecordList;
    }

    public void setHomecareAssessmentRecordList(ArrayList<Assessment> homecareAssessmentRecordList) {
        this.homecareAssessmentRecordList = homecareAssessmentRecordList;
    }

    public HomecareTransactionStatus getHomecareTransactionStatus() {
        return homecareTransactionStatus;
    }

    public void setHomecareTransactionStatus(HomecareTransactionStatus homecareTransactionStatus) {
        this.homecareTransactionStatus = homecareTransactionStatus;
    }

    public Patient getHomecarePatientId() {
        return homecarePatientId;
    }

    public void setHomecarePatientId(Patient homecarePatientId) {
        this.homecarePatientId = homecarePatientId;
    }

    public PaymentMethod getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(PaymentMethod paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
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

    public ArrayList<CaregiverOrder> getCaregiverArrayList() {
        return caregiverArrayList;
    }

    public void setCaregiverArrayList(ArrayList<CaregiverOrder> caregiverArrayList) {
        this.caregiverArrayList = caregiverArrayList;
    }
}
