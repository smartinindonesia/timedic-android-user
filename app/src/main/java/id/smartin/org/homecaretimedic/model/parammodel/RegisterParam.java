package id.smartin.org.homecaretimedic.model.parammodel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hafid on 1/1/2018.
 */

public class RegisterParam {

    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("firstname")
    private String firstname;
    @SerializedName("middlename")
    private String middlename;
    @SerializedName("lastname")
    private String lastname;
    @SerializedName("phone")
    private String phone;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isValidPhone() {
        boolean isTrue = android.util.Patterns.PHONE.matcher(phone).matches();
        return isTrue;
    }

    public boolean passwordValidator(String password){
        return this.password.equals(password);
    }

}
