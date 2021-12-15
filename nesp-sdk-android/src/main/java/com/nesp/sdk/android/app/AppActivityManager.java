package com.nesp.sdk.android.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/8/9 14:00
 **/
public class AppActivityManager {
    private static final String TAG = "ActivityManager";

    private final Stack<Activity> mActivityStack;
    private Application mApplication;

    private volatile static AppActivityManager instance;
    private Application.ActivityLifecycleCallbacks mActivityLifecycleCallbacks;
    private final List<OnAppStateChangedListener> mOnAppStateChangedListeners = new ArrayList<>();

    private AppActivityManager() {
        mActivityStack = new Stack<>();
    }

    public static AppActivityManager getInstance() {
        if (instance == null) {
            synchronized (AppActivityManager.class) {
                if (instance == null) {
                    instance = new AppActivityManager();
                }
            }
        }
        return instance;
    }

    public void attachApplication(Application application) {
        this.mApplication = application;
        mActivityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull final Activity activity,
                                          @Nullable final Bundle savedInstanceState) {
                if (mActivityStack.isEmpty()) {
                    notifyAppStateChanged(AppState.ENTER);
                }
                mActivityStack.push(activity);
            }

            @Override
            public void onActivityStarted(@NonNull final Activity activity) {
            }

            @Override
            public void onActivityResumed(@NonNull final Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull final Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull final Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull final Activity activity, @NonNull final Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull final Activity activity) {
                if (!mActivityStack.isEmpty()) {
                    mActivityStack.remove(activity);
                } else {
                    notifyAppStateChanged(AppState.LEAVE);
                }
            }
        };
        application.registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
    }

    public void detachApplication() {
        mApplication.unregisterActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
    }

    public Stack<Activity> getActivityStack() {
        return mActivityStack;
    }

    public Activity getTopActivity() {
        if (mActivityStack.isEmpty()) return null;
        return mActivityStack.peek();
    }

    public Activity getRootActivity() {
        if (mActivityStack.isEmpty()) return null;
        return mActivityStack.get(0);
    }

    public void finishAllActivity() {
        if (mActivityStack.isEmpty()) {
            return;
        }
        for (final Activity activity : mActivityStack) {
            activity.finish();
        }
    }

    @Deprecated
    public static void startAppSettingDetailActivity(Context context) {
        Intent mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            mIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            mIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            mIntent.setAction(Intent.ACTION_VIEW);
            mIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
            mIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(mIntent);

    }

    public void addOnAppStateChangedListener(OnAppStateChangedListener onAppStateChangedListener) {
        if (mOnAppStateChangedListeners.contains(onAppStateChangedListener)) return;
        mOnAppStateChangedListeners.add(onAppStateChangedListener);
    }

    public void removeOnAppStateChangedListener(OnAppStateChangedListener onAppStateChangedListener) {
        mOnAppStateChangedListeners.remove(onAppStateChangedListener);
    }

    private void notifyAppStateChanged(AppState appState) {
        if (mOnAppStateChangedListeners.isEmpty()) return;
        for (final OnAppStateChangedListener onAppStateChangedListener : mOnAppStateChangedListeners) {
            onAppStateChangedListener.onChanged(appState);
        }
    }

    public void exit() {
        List<Activity> activityList = new ArrayList<>(mActivityStack);
        for (final Activity activity : activityList) {
            activity.finish();
        }
    }
}
