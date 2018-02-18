package id.smartin.org.homecaretimedic.tools;


import id.smartin.org.homecaretimedic.config.Constants;

import static id.smartin.org.homecaretimedic.config.Constants.APP_KEY;
import static id.smartin.org.homecaretimedic.config.Constants.ITERATION;
import static id.smartin.org.homecaretimedic.config.Constants.IV;
import static id.smartin.org.homecaretimedic.config.Constants.KEY_SIZE;
import static id.smartin.org.homecaretimedic.config.Constants.SALT;

public class AesUtil {

   
    public static String Encrypt(String PLAIN_TEXT) {
        AesUtilHelper util = new AesUtilHelper(KEY_SIZE, ITERATION);
        String encrypt = util.encrypt(SALT, IV, Constants.APP_KEY, PLAIN_TEXT);
     //   System.out.println(encrypt);
        return encrypt;
    }
    
   
    public static String  Decrypt(String CIPHER_TEXT) {
        AesUtilHelper util = new AesUtilHelper(KEY_SIZE, ITERATION);
        String decrypt = util.decrypt(SALT, IV, APP_KEY, CIPHER_TEXT);
     //   System.out.println(decrypt);
        return decrypt;
    }

}
