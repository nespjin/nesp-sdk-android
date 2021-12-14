package com.nesp.sdk.android.app;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

import java.util.Objects;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/8/24 9:30
 **/
public class ScreenAdjuster {
    private static final String TAG = "ScreenAdjuster";


    private volatile static ScreenAdjuster instance;
    private float mNonCompactDensity;
    private float mNonCompactScaledDensity;
    private float mTargetDensity;
    private float mTargetScaledDensity;
    private int mTargetDensityDpi;
    private Application mApplication;
    private float mDesignWidth;

    private ScreenAdjuster(Application application, final float designWidth) {
        mApplication = application;
        mDesignWidth = designWidth;
    }

    public static ScreenAdjuster getInstance() {
        Objects.requireNonNull(instance);
        return instance;
    }

    public static ScreenAdjuster createInstanceAndInitialize(Application application,
                                                             float designWidth) {
        if (instance == null) {
            synchronized (ScreenAdjuster.class) {
                if (instance == null) {
                    instance = new ScreenAdjuster(application, designWidth);
                }
            }
        }
        return instance;
    }


    public void adjust(Activity activity) {
        final DisplayMetrics appDisplayMetrics = mApplication.getResources().getDisplayMetrics();
        if (mNonCompactDensity == 0) {
            mNonCompactDensity = appDisplayMetrics.scaledDensity;
            mApplication.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(@NonNull final Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        mNonCompactScaledDensity =
                                mApplication.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
        mTargetDensity = appDisplayMetrics.widthPixels / mDesignWidth;
        mTargetScaledDensity = mTargetDensity * (mNonCompactScaledDensity / mNonCompactDensity);
        mTargetDensityDpi = (int) (160 * mTargetDensity);

        appDisplayMetrics.density = mTargetDensity;
        appDisplayMetrics.scaledDensity = mTargetScaledDensity;
        appDisplayMetrics.densityDpi = mTargetDensityDpi;
        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = mTargetDensity;
        activityDisplayMetrics.scaledDensity = mTargetScaledDensity;
        activityDisplayMetrics.densityDpi = mTargetDensityDpi;
    }

}
