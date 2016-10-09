
package com.ryanhuen.libraryryan.device;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import com.ryanhuen.libraryryan.config.Config;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by ryanhuen
 */
public class DeviceInfoUtils {

    public static final String TAG = DeviceInfoUtils.class.getName();

    public static final String DEFAULT_IMEI = "";

    public static final String DEVICE_ID_FILENAME = "DEVICE_ID"; // 3.8.0之前的版本

    public static final String DEVICE_ID_FILENAME_NEW = "DEV"; // 3.8.0及其之后的版本

    public static final String DEVICE_ID_FILENAME_NEW_V2 = "DEVV2"; // 5.0.9及其之后的版本

    public static final String ANDROID_ID_FILENAME = "ANDROID_ID"; // 保存手机的android_id，用来校验DEVICE_ID是否需要重新获取

    public static String getImei(Context ctx) {
        if (ctx != null) {
            TelephonyManager tm = (TelephonyManager) ctx
                    .getSystemService(Context.TELEPHONY_SERVICE);
            try {
                if (tm != null && tm.getDeviceId() != null) {
                    return tm.getDeviceId();
                }
            } catch (Exception e) {
                if (Config.BASE_DEBUG) {
                    Log.e(TAG, "error getting device imei", e);
                }
            }
        }
        return DEFAULT_IMEI;
    }

    /**
     * 获取Android设备SerialNumber
     *
     * @return "" if no result
     */
    public static synchronized String getDeviceSerialNumber() {
        String serial = "";
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "ro.serialno");
            if (serial == null) {
                serial = "";
            }
        } catch (Exception e) {
            if (Config.BASE_DEBUG) {
                Log.e(TAG, "error getting device serial number", e);
            }
        }

        return serial;
    }

    /**
     * 获取CPU序列号
     *
     * @return CPU序列号(16位) 读取失败为null
     */
    public static String getCPUSerial() {
        String line = "";
        String cpuAddress = null;
        try {
            // 读取CPU信息
            Process pp = Runtime.getRuntime().exec("cat /proc/cpuinfo");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            // 查找CPU序列号
            for (int i = 1; i < 100; i++) {
                line = input.readLine();
                if (line != null) {
                    // 查找到序列号所在行
                    line = line.toLowerCase();
                    int p1 = line.indexOf("serial");
                    int p2 = line.indexOf(":");
                    if (p1 > -1 && p2 > 0) {
                        // 提取序列号
                        cpuAddress = line.substring(p2 + 1);
                        // 去空格
                        cpuAddress = cpuAddress.trim();
                        break;
                    }
                } else {
                    // 文件结尾
                    break;
                }
            }
        } catch (IOException ex) {
            if (Config.BASE_DEBUG) {
                Log.e(TAG, "error getting cpu serial code ", ex);
            }
        }
        if (Config.BASE_DEBUG) {
            Log.d(TAG, "cpuAddress=" + cpuAddress);
        }
        return cpuAddress;
    }

    /**
     * 获取ANDROID_ID号
     */
    public static String getAndroidId(Context context) {
        String ai = System.getString(context.getContentResolver(), Secure.ANDROID_ID);
        return ai == null ? "" : ai;
    }

    /**
     * 获取无线网卡的MAC地址(需要权限)
     */
    public static String getWiFiMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    /**
     * 获取网卡Mac地址
     * 
     * @return
     */
    public static String getEthernetMacAddress() {
        String mac = "";
        try {
            Enumeration<NetworkInterface> localEnumeration = NetworkInterface
                    .getNetworkInterfaces();
            while (localEnumeration.hasMoreElements()) {
                NetworkInterface localNetworkInterface = (NetworkInterface) localEnumeration
                        .nextElement();
                String interfaceName = localNetworkInterface.getDisplayName();

                if (interfaceName == null) {
                    continue;
                }

                if (interfaceName.equals("eth0")) {
                    mac = bytesToMacAddress(localNetworkInterface.getHardwareAddress());
                    if (mac != null && mac.startsWith("0:")) {
                        mac = "0" + mac;
                    }
                    break;
                }
            }
        } catch (SocketException e) {
            Log.e(TAG, "error getting ethernet mac address", e);
        }
        return mac;
    }

    private static String bytesToMacAddress(byte[] mac) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            byte b = mac[i];
            int value = 0;
            if (b >= 0 && b <= 16) {
                value = b;
                sb.append("0" + Integer.toHexString(value));
            } else if (b > 16) {
                value = b;
                sb.append(Integer.toHexString(value));
            } else {
                value = 256 + b;
                sb.append(Integer.toHexString(value));
            }
            if (i != mac.length - 1) {
                sb.append(":");
            }
        }
        return sb.toString();
    }

}
