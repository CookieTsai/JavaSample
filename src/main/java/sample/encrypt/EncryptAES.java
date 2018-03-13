package sample.encrypt;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Arrays;

import static java.lang.System.out;

/**
 * Created by Cookie on 16/2/15.
 */
public class EncryptAES {

    private static final Key SECRET_KEY;

    static {
        SecretKeyFactory secretKeyFactory = null;
        Key secretKeySpec = null;
        try {
            byte[] key = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIFHbBKAx+CoPKuuKTVgOKgUd0BvERidMWskp8BA28hWYN2P8TqFVaLgorDBU85Gx+FhN1McBoT5Q0i5LUf26Q8CAwEAAQ==".getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            key = sha.digest(key);
            out.println(Base64.encodeBase64String(key));
            key = Arrays.copyOf(key, 16); // use only first 128 bit
            out.println(Base64.encodeBase64String(key));

            secretKeySpec = new SecretKeySpec(key, "AES");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SECRET_KEY = secretKeySpec;

    }

    public static byte[] encode(Key key, byte[] srcBytes) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(srcBytes);
    }

    public static byte[] decode(Key key, byte[] srcBytes) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(srcBytes);
    }

    public static String encodeBase64(Key key, byte[] srcBytes) throws Exception {
        return Base64.encodeBase64String(encode(key, srcBytes));
    }

    public static byte[] decodeBase64(Key key, String base64String) throws Exception {
        return decode(key, Base64.decodeBase64(base64String));
    }

    public static void main(String[] args) throws Exception {
        String msg = "這是測試加解密用的內容";
        out.println("明文:" + msg);

        out.println("公鑰加密，私鑰解密:");
        String base64Str = encodeBase64(SECRET_KEY, msg.getBytes("UTF-8"));
        out.println("\t加密後:" + base64Str);
        byte[] decBytes = decodeBase64(SECRET_KEY, base64Str);
        out.println("\t解密後:" + new String(decBytes));
    }
}
