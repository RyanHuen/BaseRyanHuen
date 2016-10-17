
package com.ryanhuen.libraryryan.device;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;

/**
 * Created by ryanhuen
 */
public class MemoryInfoUtils {
    public static final String TAG = MemoryInfoUtils.class.getName();

    /**
     * 获取系统内存总大小，单位是B 支持16以上Android版本
     */
    public static long getTotalMemSize(Context context) {
        MemoryInfo memoryInfo = new MemoryInfo();
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.totalMem;
    }
}
