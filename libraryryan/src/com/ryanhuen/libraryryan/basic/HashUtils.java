
package com.ryanhuen.libraryryan.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.text.TextUtils;

/**
 * Created by ryanhuen
 */
public class HashUtils {
    public static final String TAG = HashUtils.class.getName();

    private static String bytesToHexString(byte[] src) {
        char[] res = new char[src.length * 2];
        final char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        };
        for (int i = 0, j = 0; i < src.length; i++) {
            res[j++] = hexDigits[src[i] >>> 4 & 0x0f];
            res[j++] = hexDigits[src[i] & 0x0f];
        }

        return new String(res);
    }

    /**
     * 获取MD5加密
     * 
     * @param input
     * @return
     */
    public static String getMD5(String input) {
        if (TextUtils.isEmpty(input)) {
            return null;
        }

        return getMD5(input.getBytes());
    }

    public static String getMD5(byte[] input) {
        if (input == null) {
            return null;
        }

        String value = null;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (final NoSuchAlgorithmException e) {
        }
        if (md != null) {
            md.update(input);
            value = bytesToHexString(md.digest());
        }

        return value;
    }

    public static String getMD5(File file) {
        if (file == null || !file.exists()) {
            return null;
        }

        String value = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);

            MessageDigest digester = MessageDigest.getInstance("MD5");
            byte[] bytes = new byte[4096];
            int byteCount;
            while ((byteCount = in.read(bytes)) > 0) {
                digester.update(bytes, 0, byteCount);
            }
            value = bytesToHexString(digester.digest());
        } catch (Exception e) {
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return value;
    }

}
