
package com.ryanhuen.libraryryan.resources;

import com.ryanhuen.libraryryan.config.LibConfig;

import android.content.Context;

public class ResourceHelper {

    /**
     * 全局Application的上下文Context,需要首先在全局Application中init整个lib工程
     * 
     * @see LibConfig#init(Context)
     */
    private static final Context mContext = LibConfig
            .getInstance().getContext();

    public static int getIntegerById(int id) {
        return mContext.getResources().getInteger(id);
    }

    public static int getColorById(int id) {
        return mContext.getResources().getColor(id);
    }

    public static int getDimensById(int id) {
        return mContext.getResources().getDimensionPixelSize(id);
    }

    public static String getStringById(int id) {
        return mContext.getResources().getString(id);
    }

    public static String getStringByIdWithFormat(int id, Object format) {
        return mContext.getResources().getString(id, format);

    }

    public static String getStringByVar(String var) {
        try {
            return getStringById(mContext.getResources().getIdentifier(var,
                    "string", mContext.getPackageName()));
        } catch (final Exception e) {
            e.printStackTrace();
            return "unknown";
        }
    }

    public static int getDrawableIdByVar(String var) throws Exception {
        final int id = mContext.getResources().getIdentifier(var, "drawable",
                mContext.getPackageName());
        if (0 == id) {
            throw new Exception("Illegal drawable id");
        }
        return id;
    }
}
