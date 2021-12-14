package com.nesp.sdk.android.app;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.nesp.sdk.android.R;

import java.util.Timer;


public class TipDialog extends Dialog {

    public static int SIZE = 280;

    private boolean mCancelable = true;
    private boolean mIsEnabledBgColor = false;

    private TextView mTvMessage;
    private ImageView mIvStatus;
    private ProgressBar mProgressBarCircleIndicator;


    public static final long DURATION_SHORT = 1500;
    public static final long DURATION_LONG = 3000;

    private OnShowListener mOnShowListener;
    private final Handler mMainHandler = new Handler(Looper.getMainLooper());
    private long mDuration = 1500;
    private Type mType = Type.NONE;
    private String mMessage = "";
    private Timer mStatusIconRotateTimer;

    public TipDialog(@NonNull Context context, String msg) {
        this(context, R.style.NespTipDialog, true, true, msg);
    }

    public TipDialog(@NonNull Context context, int themeResId, boolean cancelable,
                     boolean isEnabledBgColor, String msg) {
        super(context, themeResId);
        mMessage = msg;
        mCancelable = cancelable;
        mIsEnabledBgColor = isEnabledBgColor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        super.setOnShowListener(dialog -> {
            if (mOnShowListener != null) {
                mOnShowListener.onShow(dialog);
            }
            if (mType == Type.LOADING) return;
            mMainHandler.postDelayed(this::dismiss, mDuration);
        });
    }

    @Override
    public void setOnShowListener(@Nullable final OnShowListener listener) {
        this.mOnShowListener = listener;
    }


    /**
     * 初始化布局
     */
    private void initView() {
        setContentView(R.layout.nesp_sdk_dialog_tip);

        // 设置窗口大小
//        WindowManager windowManager = getWindow().getWindowManager();
//        int screenWidth = windowManager.getDefaultDisplay().getWidth();
//        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        WindowManager.LayoutParams attributes = getWindow().getAttributes();

        // 设置窗口背景透明度
        if (mIsEnabledBgColor) {
            attributes.alpha = 0.8f;
        } else {
            attributes.alpha = 1.0f;
        }
//        attributes.width = screenWidth / 5 * 3;
//        attributes.height = screenHeight / 5 * 3;
        attributes.width = SIZE;
        attributes.height = SIZE;
        getWindow().setAttributes(attributes);
        getWindow().setGravity(Gravity.CENTER);
        setCancelable(mCancelable);
        setCanceledOnTouchOutside(false);

        mTvMessage = findViewById(R.id.tv_message);
        mIvStatus = findViewById(R.id.iv_status);
        mProgressBarCircleIndicator = findViewById(R.id.pb_circle_indicator);

        mTvMessage.setText(mMessage);
        invalidateStatusImageViewAndCircleIndicator();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        setCancelable(mCancelable);
//        setCanceledOnTouchOutside(mCancelable);
    }

    private void tryInvalidateStatusImageViewAndCircleIndicator() {
        try {
            invalidateStatusImageViewAndCircleIndicator();
        } catch (Exception ignored) {

        }
    }

    private void invalidateStatusImageViewAndCircleIndicator() {
        if (mIvStatus == null) {
            return;
        }
        int drawableId = R.drawable.ic_nesp_sdk_tip_success;
        mProgressBarCircleIndicator.setVisibility(View.GONE);
        mIvStatus.setVisibility(View.VISIBLE);
        switch (mType) {
            case NONE:
                break;
            case ERROR:
                drawableId = R.drawable.ic_nesp_sdk_tip_error;
                break;
            case SUCCESS:
                drawableId = R.drawable.ic_nesp_sdk_tip_success;
                break;
            case WARNING:
                drawableId = R.drawable.ic_nesp_sdk_tip_warning;
                break;
            case LOADING:
//                drawableId = R.drawable.ic_tip_loading;
                mProgressBarCircleIndicator.setVisibility(View.VISIBLE);
                mIvStatus.setVisibility(View.GONE);
                break;
        }
        mIvStatus.setImageDrawable(ContextCompat.getDrawable(getContext(), drawableId));

       /* if (mType == Type.LOADING) {
            final FloatRef rotateAngle = new FloatRef(0F);
            mStatusIconRotateTimer = new Timer();
            mStatusIconRotateTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (rotateAngle.getValue() >= 360F) {
                        rotateAngle.setValue(0F);
                    } else {
                        rotateAngle.setValue(rotateAngle.getValue() + 5);
                    }


                    mIvStatus.post(() -> {
                        mIvStatus.setRotation(rotateAngle.getValue());
                    });
                }
            }, 0, 20);
        }*/
    }

    public TipDialog setMessage(String message) {
        this.mMessage = message;
        mTvMessage.setText(message);
        return this;
    }

    public TipDialog setDuration(final long duration) {
        mDuration = duration;
        return this;
    }

    public TipDialog setType(final Type type) {
        mType = type;
        tryInvalidateStatusImageViewAndCircleIndicator();
        return this;
    }

    public void showShort() {
        setDuration(DURATION_SHORT);
        show();
    }

    public void showLong() {
        setDuration(DURATION_LONG);
        show();
    }

    public static TipDialog showTip(Context context, String message) {
        return showTip(context, Type.NONE, message, DURATION_SHORT);
    }

    public static TipDialog showTip(Context context, String message, long duration) {
        return showTip(context, Type.NONE, message, duration);
    }

    public static TipDialog showTip(Context context, Type type, String message) {
        return showTip(context, type, message, DURATION_SHORT);
    }

    public static TipDialog showTip(Context context, Type type, String message, long duration) {
        TipDialog tipDialog = new TipDialog(context, message);
        tipDialog.setDuration(duration);
        tipDialog.setCancelable(true);
        tipDialog.setCanceledOnTouchOutside(true);
        tipDialog.show();
        tipDialog.setType(type);
        return tipDialog;
    }

    public static void dismiss(final TipDialog dialog) {
        if (dialog == null) return;
        dialog.dismiss();
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 屏蔽返回键
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public enum Type {
        NONE, WARNING, ERROR, LOADING, SUCCESS
    }

    @Override
    protected void onStop() {
        if (mStatusIconRotateTimer != null) {
            mStatusIconRotateTimer.cancel();
        }
        super.onStop();
    }
}
