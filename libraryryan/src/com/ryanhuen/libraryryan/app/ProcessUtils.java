
package com.ryanhuen.libraryryan.app;

import java.io.FileInputStream;


import android.util.Log;

import com.ryanhuen.libraryryan.config.LibConfig;

/**
 * Created by ryanhuen
 */
public class ProcessUtils {
    public static final String TAG = ProcessUtils.class.getName();

    /**
     * get current process name
     *
     * @return
     */
    public static String getCurrentProcessName() {
        FileInputStream in = null;
        try {
            String fn = "/proc/self/cmdline";
            in = new FileInputStream(fn);
            byte[] buffer = new byte[256];
            int len = 0;
            int b;
            while ((b = in.read()) > 0 && len < buffer.length) {
                buffer[len++] = (byte) b;
            }
            if (len > 0) {
                String s = new String(buffer, 0, len, "UTF-8");
                return s;
            }
        } catch (Throwable e) {
            if (LibConfig.getInstance().isBASE_DEBUG()) {
                Log.e(TAG, e.getMessage(), e);
            }
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Throwable e) {
                    if (LibConfig.getInstance().isBASE_DEBUG()) {
                        Log.e(TAG, e.getMessage(), e);
                    }
                }
            }
        }
        return null;
    }
}
