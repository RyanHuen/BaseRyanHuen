
package com.ryanhuen.libraryryan.config;

import android.content.Context;

/**
 * Created by ryanhuenwork on 16-6-21.
 */
public class LibConfig {
    /**
     * 全局上下文
     */
    private final Context mContext;

    private volatile static LibConfig sInstance;

    public Context getContext() {
        return mContext;
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
    }

    public static class Builder {
        private Context mContext;

        public Builder(Context context) {
            this.mContext = context.getApplicationContext();
        }

        public LibConfig build() {
            sInstance = new LibConfig(this);
            return sInstance;
        }
    }

}
