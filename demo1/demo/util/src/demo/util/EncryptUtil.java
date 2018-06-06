package demo.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 加密工具类
 */
public class EncryptUtil {
    /**
     * 信息解密
     */
    public static String decrypt(String source, String key) throws Exception {
        byte[] bytes = toHexBytes(source);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        bytes = cipher.doFinal(bytes);
        return java.net.URLDecoder.decode(new String(bytes, "UTF-8"), "UTF-8");
    }

    /**
     * 信息加密
     */
    public static String encrypt(String source, String key) throws Exception {
        source = java.net.URLEncoder.encode(source, "UTF-8");
        return toHexString(encryptByte(source, key)).toUpperCase();
    }

    /**
     * 信息解析字节
     */
    public static byte[] encryptByte(String source, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        return cipher.doFinal(source.getBytes("UTF-8"));
    }

    /**
     * 信息解析字节
     */
    public static byte[] toHexBytes(String source) {
        byte[] bytes = new byte[source.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            String s = source.substring(2 * i, 2 * i + 2);
            int v = Integer.parseInt(s, 16);
            bytes[i] = (byte) v;
        }
        return bytes;
    }

    /**
     * 字节解析信息
     */
    public static String toHexString(byte[] bytes) {
        StringBuffer source = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String s = Integer.toHexString(0xff & bytes[i]);
            if (s.length() < 2) {
                s = "0" + s;
            }
            source.append(s);
        }
        return source.toString();
    }
}