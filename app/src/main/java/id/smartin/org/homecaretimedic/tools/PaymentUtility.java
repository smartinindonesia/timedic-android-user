package id.smartin.org.homecaretimedic.tools;

/**
 * Created by Hafid on 4/17/2018.
 */

public class PaymentUtility {

    public static Double getThreeDigitPhoneNumber(String phoneNumber) {
        String sub = phoneNumber.substring(phoneNumber.toCharArray().length - 4, phoneNumber.toCharArray().length - 1);
        Double value = Double.parseDouble(sub);
        return value;
    }
}
