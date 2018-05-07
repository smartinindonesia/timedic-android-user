package id.smartin.org.homecaretimedic.model.parammodel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hafid on 3/20/2018.
 */

public class UserProfile {
    public static final String TAG = "[User]";

    @SerializedName("id")
    private Long id;
    @SerializedName("address")
    private String address;
    @SerializedName("dateBirth")
    private Long dateBirth;
    @SerializedName("email")
    private String email;
    @SerializedName("frontName")
    private String frontName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("middleName")
    private String middleName;
    @SerializedName("phoneNumber")
    private String phoneNumber;
    @SerializedName("latitude")
    private Float latitude;
    @SerializedName("longitude")
    private Float longitude;
    @SerializedName("gender")
    private String gender;
    @SerializedName("religion")
    private String religion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Long dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFrontName() {
        return frontName;
    }

    public void setFrontName(String frontName) {
        this.frontName = frontName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public boolean isValidPhone() {
        boolean isTrue = android.util.Patterns.PHONE.matcher(phoneNumber).matches();
        return isTrue;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }
}
