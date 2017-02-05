package lab.aikibo.util;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by tamami on 05/02/17.
 */
public class Encrypt {

    private static final String ALGORITHM = "md5";
    private static final String DIGEST_STRING = "HG58YZ3CR9";
    private static final String CHARSET_UTF_8 = "utf-8";
    private static final String SECRET_KEY_ALGORITHM = "DESede";
    private static final String TRANSFORMATION_PADDING = "DESede/CBC/PKCS5Padding";

    public static String getEncrypted(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException,
            NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException {
        final MessageDigest md = MessageDigest.getInstance(ALGORITHM);
        final byte[] digestOfPassword = md.digest(DIGEST_STRING.getBytes(CHARSET_UTF_8));
        final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
        for(int i=0, j = 16; i < 8;) {
            keyBytes[j++] = keyBytes[i++];
        }

        final SecretKey key = new SecretKeySpec(keyBytes, SECRET_KEY_ALGORITHM);
        final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
        final Cipher cipher = Cipher.getInstance(TRANSFORMATION_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);

        final byte[] plainTextBytes = text.getBytes(CHARSET_UTF_8);
        final byte[] cipherText = cipher.doFinal(plainTextBytes);

        return new String(cipherText);
    }

}
