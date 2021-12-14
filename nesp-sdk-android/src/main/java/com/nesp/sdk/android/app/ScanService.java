package com.nesp.sdk.android.app;

import android.view.KeyEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/8/4 10:02
 * <p>
 * 扫码服务，接收扫码枪数据
 **/
public final class ScanService {
    private static final String TAG = "ScanService";


    private volatile static ScanService singleton;

    public static ScanService getSingleton() {
        if (singleton == null) {
            synchronized (ScanService.class) {
                if (singleton == null) {
                    singleton = new ScanService();
                }
            }
        }
        return singleton;
    }

    private ScanService() {
    }

    private final List<OnScanListener> mOnScanListeners = new ArrayList<>();

    public ScanService addOnScanListener(OnScanListener onScanListener) {
        this.mOnScanListeners.add(onScanListener);
        return this;
    }

    public ScanService removeOnScanListener(OnScanListener onScanListener) {
        this.mOnScanListeners.remove(onScanListener);
        return this;
    }

    private final StringBuilder scanText = new StringBuilder();

    public boolean dispatchKeyEvent(final KeyEvent event) {
        if (event.getDeviceId() == -1) return false;

        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() != KeyEvent.KEYCODE_ENTER
        ) {
            if (scanText.length() == 0 && event.getKeyCode() == KeyEvent.KEYCODE_SPACE) {
                return false;
            }
            if (event.getKeyCode() >= KeyEvent.KEYCODE_0 && event.getKeyCode() <= KeyEvent.KEYCODE_9) {
                scanText.append(event.getDisplayLabel());
            }
        }

        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            notifyScanned(scanText.toString());
            scanText.delete(0, scanText.length());
        }

        return true;
    }

    private void notifyScanned(String scanText) {
        for (final OnScanListener onScanListener : mOnScanListeners) {
            onScanListener.onScan(scanText);
        }
    }


    public interface OnScanListener {
        void onScan(String scanText);
    }


}
