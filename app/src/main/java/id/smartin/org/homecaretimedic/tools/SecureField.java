package id.smartin.org.homecaretimedic.tools;

import android.util.Log;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Hafid on 2/16/2018.
 */

public class SecureField {
    public static String Encrypt(String source, String key) {

        byte[] raw = key.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        byte[] encrypted = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            encrypted = cipher.doFinal(source.getBytes("utf-8"));
        } catch (Exception localException) {
            localException.printStackTrace();
            Log.e("SECURITY", "Gagal");
        }
        return new String(Base64.encodeBase64(encrypted));
    }

    public static String Decrypt(String source, String key) {
        byte[] raw = key.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        String decrypted = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(2, skeySpec);
            decrypted = new String(cipher.doFinal(Base64.decodeBase64(source)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return decrypted;
    }
}
