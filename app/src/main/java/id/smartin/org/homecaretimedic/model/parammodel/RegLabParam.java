package id.smartin.org.homecaretimedic.model.parammodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import id.smartin.org.homecaretimedic.model.HomecareTransactionStatus;
import id.smartin.org.homecaretimedic.model.LabPackageItem;
import id.smartin.org.homecaretimedic.model.LabServices;

/**
 * Created by Hafid on 2/26/2018.
 */

public class RegLabParam {
    @SerializedName("date")
    private Long date;
    @SerializedName("totalPrice")
    private double totalPrice;
    @SerializedName("transactionDescription")
    private String transactionDescription;
    @SerializedName("locationLatitude")
    private double locationLatitude;
    @SerializedName("locationLongitude")
    private double locationLongitude;
    @SerializedName("employeeIdNumber")
    private String employeeIdNumber;
    @SerializedName("laboratorySelectedServiceTransactionCollection")
    private List<LabServices> laboratorySelectedServiceTransactionCollection;
    @SerializedName("laboratorySelectedPackageTransactionCollection")
    private List<LabPackageItem> laboratorySelectedPackageTransactionCollection;
    @SerializedName("idLaboratoryClinic")
    private Long idLaboratoryClinic;
    @SerializedName("idPatient")
    private Long idPatient;
    @SerializedName("transactionStatus")
    private Long transactionStatus;

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
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

    public String getEmployeeIdNumber() {
        return employeeIdNumber;
    }

    public void setEmployeeIdNumber(String employeeIdNumber) {
        this.employeeIdNumber = employeeIdNumber;
    }

    public List<LabServices> getLaboratorySelectedServiceTransactionCollection() {
        return laboratorySelectedServiceTransactionCollection;
    }

    public void setLaboratorySelectedServiceTransactionCollection(List<LabServices> laboratorySelectedServiceTransactionCollection) {
        this.laboratorySelectedServiceTransactionCollection = laboratorySelectedServiceTransactionCollection;
    }

    public List<LabPackageItem> getLaboratorySelectedPackageTransactionCollection() {
        return laboratorySelectedPackageTransactionCollection;
    }

    public void setLaboratorySelectedPackageTransactionCollection(List<LabPackageItem> laboratorySelectedPackageTransactionCollection) {
        this.laboratorySelectedPackageTransactionCollection = laboratorySelectedPackageTransactionCollection;
    }

    public Long getIdLaboratoryClinic() {
        return idLaboratoryClinic;
    }

    public void setIdLaboratoryClinic(Long idLaboratoryClinic) {
        this.idLaboratoryClinic = idLaboratoryClinic;
    }

    public Long getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(Long idPatient) {
        this.idPatient = idPatient;
    }

    public Long getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(Long transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}

/*
{

	"idLaboratoryClinic" : 1 ,
	"idPatient" : 60,
	"transactionStatus" : 1,
	"idServicePackage" : ""
}

 */