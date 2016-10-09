
package com.ryanhuen.libraryryan.app;

import com.ryanhuen.libraryryan.Config;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by ryanhuen
 */
public class Version {
    public static final String TAG = Version.class.getName();

    /**
     * Get current application versionName
     *
     * @return Current application versionName
     */
    public static String getAppVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            if (Config.BASE_DEBUG) {
                Log.e(TAG, "Error getting app versionName", e);
            }
            return null;
        }
    }

    /**
     * Get current application versionCode
     * 
     * @param context
     * @return Current application versionCode
     */
    public static int getAppVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            if (Config.BASE_DEBUG) {
                Log.e(TAG, "Error getting app versionCode", e);
            }
            return 0;
        }
    }
}
