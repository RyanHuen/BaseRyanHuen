
package com.ryanhuen.libraryryan.ui;

import com.ryanhuen.libraryryan.config.LibConfig;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by ryanhuen
 */
public class ImageUtils {
    public static final String TAG = ImageUtils.class.getName();

    public static BitmapDrawable safelyDecodeBitmapDrawableFromResId(Resources res, int id) {
        return safelyDecodeBitmapDrawableFromResId(res, id, true, true);
    }

    /**
     * 从资源中decode一张图片资源，允许应用降低质量以及不针对高dpi设备拉伸内存中bitmap尺寸的优化，并且会捕捉内存不足的异常
     *
     * @param lowQuality 采取16bits的降低质量的内存优化
     * @param nonScale 采取不针对高dpi设备拉伸内存中bitmap尺寸的优化
     */
    public static BitmapDrawable safelyDecodeBitmapDrawableFromResId(Resources res, int id,
            boolean lowQuality, boolean nonScale) {
        return new BitmapDrawable(res, safelyDecodeBitmapFromResId(res, id, lowQuality, nonScale));
    }

    public static Bitmap safelyDecodeBitmapFromResId(Resources res, int id) {
        return safelyDecodeBitmapFromResId(res, id, true, true);
    }

    /**
     * 从资源中decode一张图片资源，允许应用降低质量以及不针对高dpi设备拉伸内存中bitmap尺寸的优化，并且会捕捉内存不足的异常
     *
     * @param lowQuality 采取16bits的降低质量的内存优化
     * @param nonScale 采取不针对高dpi设备拉伸内存中bitmap尺寸的优化
     */
    public static Bitmap safelyDecodeBitmapFromResId(Resources res, int id, boolean lowQuality,
            boolean nonScale) {
        Bitmap bm = null;
        if (id > 0) {
            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inScaled = !nonScale
                    || res.getDisplayMetrics().densityDpi <= DisplayMetrics.DENSITY_HIGH;
            options.inPreferredConfig = Config.RGB_565;

            try {
                bm = BitmapFactory.decodeResource(res, id, options);
            } catch (OutOfMemoryError e) {
                if (LibConfig.getInstance().isBASE_DEBUG()) {
                    Log.d("decodeResource", "decode resource failed: out of memory");
                }
            }
        }
        return bm;
    }

}
