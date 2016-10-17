
package com.ryanhuen.libraryryan.config;

import android.content.Context;

/**
 * Created by ryanhuenwork on 16-6-21.
 */
public class LibConfig {
    /**
     * 全局debug开关，使用builder进行控制
     */
    public final boolean BASE_DEBUG;
    /**
     * 全局上下文
     */
    private final Context mContext;

    private volatile static LibConfig sInstance;

    public Context getContext() {
        return mContext;
    }

    public boolean isBASE_DEBUG() {
        return BASE_DEBUG;
    }

    public static Builder init(Context context) {
        return new Builder(context);
    }

    public static LibConfig getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("LibraryRyan is not initialized.");
        }
        return sInstance;
    }

    private LibConfig(Builder builder) {
        mContext = builder.mContext;
        BASE_DEBUG = builder.mBaseDebug;
    }

    public static class Builder {
        private Context mContext;
        private boolean mBaseDebug;

        public Builder(Context context) {
            this.mContext = context.getApplicationContext();
        }

        public Builder setDebugConfig(boolean debug) {
            mBaseDebug = debug;
            return this;
        }

        public LibConfig build() {
            sInstance = new LibConfig(this);
            return sInstance;
        }
    }

}
