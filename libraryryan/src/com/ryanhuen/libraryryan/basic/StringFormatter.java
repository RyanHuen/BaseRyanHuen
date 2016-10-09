
package com.ryanhuen.libraryryan.basic;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;

/**
 * Created by ryanhuen
 */
public final class StringFormatter {
    public static final String TAG = StringFormatter.class.getName();

    /********************* HEX ********************/
    private static final char[] HEX_DIGITS_UPPER = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    public static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(HEX_DIGITS_UPPER[(b & 0xf0) >> 4]);
            sb.append(HEX_DIGITS_UPPER[b & 0x0f]);
        }
        return sb.toString();
    }

    /********************* Time Formatter *******************/

    public static String getFormatedTime(Context c, long time) {
        final Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");
        StringBuilder buffer = new StringBuilder();
        buffer.append(sdf.format(date));
        return buffer.toString();
    }

    public static String getFormatedDate(Context c, long time) {
        final Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        StringBuilder buffer = new StringBuilder();
        buffer.append(sdf.format(date));
        return buffer.toString();
    }

    public static String getFormatedTimeInDay(Context c, long time) {
        final Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        StringBuilder buffer = new StringBuilder();
        buffer.append(sdf.format(date));
        return buffer.toString();
    }

    /*********************** SIZE FORMATTER ***********************/

    public static String getHumanReadableSize(long bytes) {
        return getHumanReadableSize(bytes, 2, 1);
    }

    /**
     * 把字节长度转换为易读的格式
     * 
     * @param bytes 字节长度
     * @param maxFractionLength 小数点后最长保留的位数
     * @param minFractionLength 小数点后最短保留的位数
     * @return
     */
    public static String getHumanReadableSize(long bytes, int maxFractionLength,
            int minFractionLength) {
        NumberFormat sizeFormat = NumberFormat.getInstance();
        sizeFormat.setMaximumFractionDigits(maxFractionLength);
        sizeFormat.setMinimumFractionDigits(minFractionLength);

        if (bytes == 0) {
            return "0";
        } else if (bytes < 1024) {
            return bytes + "B";
        } else if (bytes < 1024 * 1024) {
            return sizeFormat.format(bytes / 1024d) + "KB";
        } else if (bytes < 1024 * 1024 * 1024) {
            return sizeFormat.format(bytes / (1024d * 1024d)) + "MB";
        } else {
            return sizeFormat.format(bytes / (1024d * 1024d * 1024d)) + "GB";
        }
    }
}
