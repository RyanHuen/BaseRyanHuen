
package com.ryanhuen.libraryryan.device;

import java.lang.reflect.Method;

import com.ryanhuen.libraryryan.config.Config;

import android.util.Log;

/**
 * Created by ryanhuen
 */
public class SystemPropertyUtils {
    public static final String TAG = SystemPropertyUtils.class.getName();

    public static String getSystemProperty(String key) {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method m = clz.getMethod("get", String.class);
            String ret = (String) m.invoke(null, key);
            if (Config.BASE_DEBUG) {
                Log.d(TAG, "getting system property  :  " + key + "  ,  value  :  " + ret);
            }
            return ret;
        } catch (Exception e) {
            if (Config.BASE_DEBUG) {
                Log.d(TAG, "error getting system properties  :  " + key, e);
            }
        }
        return "";
    }

    public static String getSystemProperty(String key, String def) {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method m = clz.getMethod("get", String.class, String.class);
            String ret = (String) m.invoke(null, key, def);
            if (Config.BASE_DEBUG) {
                Log.d(TAG, "getting system property  :  " + key + " , value  :  " + ret);
            }
            return ret;
        } catch (Exception e) {
            if (Config.BASE_DEBUG) {
                Log.d(TAG, "error getting system property  :  " + key, e);
            }
        }
        return "";
    }
}
