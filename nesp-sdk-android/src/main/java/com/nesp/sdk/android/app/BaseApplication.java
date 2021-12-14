package com.nesp.sdk.android.app;

import android.app.Application;
import android.content.Intent;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/8/27 16:01
 **/
public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        AppActivityManager.getInstance().attachApplication(this);
    }

    public void restart() {
        Intent intent =   getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("REBOOT", "reboot");
        AppActivityManager.getInstance().getTopActivity().startActivity(intent);
        AppActivityManager.getInstance().exit();
    }
}
