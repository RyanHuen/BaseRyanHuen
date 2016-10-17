
package com.ryanhuen.libraryryan.device;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.regex.Pattern;


import android.text.TextUtils;
import android.util.Log;

import com.ryanhuen.libraryryan.config.LibConfig;

/**
 * Created by ryanhuen
 */
public class CpuInfoUtils {
    public static final String TAG = CpuInfoUtils.class.getName();

    /**
     * 获取CPU名称
     * 
     * @return
     */
    public static String getCpuName() {
        BufferedReader br = null;
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            br = new BufferedReader(fr);

            String modelName = null;
            String processorName = null;
            String line;
            while ((line = br.readLine()) != null) {
                String[] segs = line.split(":");
                if (segs != null && segs.length >= 2) {
                    String key = segs[0].trim();
                    if ("Processor".equalsIgnoreCase(key) && processorName == null
                            && segs[1].trim().length() > 2) {
                        processorName = segs[1].trim();
                    } else if ("model name".equalsIgnoreCase(key)) {
                        modelName = segs[1].trim();
                    }
                }
            }

            return TextUtils.isEmpty(modelName) ? processorName : modelName;
        } catch (Exception e) {
            if (LibConfig.getInstance().isBASE_DEBUG()) {
                Log.e(TAG, "error getting cpu name", e);
            }
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    if (LibConfig.getInstance().isBASE_DEBUG()) {
                        Log.d(TAG, "", e);
                    }
                }
            }
        }
        return "";
    }

    /**
     * 获取CPU核心数
     * 
     * @return
     */
    public static String getNumCores() {
        try {
            File dir = new File("/sys/devices/system/cpu/");
            File[] files = dir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    if (Pattern.matches("cpu[0-9]", filename)) {
                        return true;
                    }
                    return false;
                }
            });
            return String.valueOf(files.length);
        } catch (Exception e) {
            if (LibConfig.getInstance().isBASE_DEBUG()) {
                Log.d(TAG, "error getting cpu core numbers", e);
            }
        }
        return "";
    }

    /**
     * 获取CPU最大频率
     * 
     * @return
     */
    public static String getCpuMaxFreq() {
        File file = new File("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq");
        if (file.exists() && file.isFile()) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine().trim()) != null) {
                    long freqInMHz = Long.valueOf(line) / 1000;
                    if (freqInMHz >= 1024) {
                        float digit = (float) freqInMHz / 1000;
                        return (float) (Math.round(digit * 100)) / 100 + "GHz";
                    } else {
                        return freqInMHz + "MHz";
                    }
                }
            } catch (Exception e) {
                if (LibConfig.getInstance().isBASE_DEBUG()) {
                    Log.d(TAG, "error getting cpu max frequency", e);
                }
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        if (LibConfig.getInstance().isBASE_DEBUG()) {
                            Log.d(TAG, "", e);
                        }
                    }
                }
            }
        }
        return "";
    }
}
