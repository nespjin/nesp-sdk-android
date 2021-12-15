/*
 *
 *   Copyright (c) 2020  NESP Technology Corporation. All rights reserved.
 *
 *   This program is not free software; you can't redistribute it and/or modify it
 *   without the permit of team manager.
 *
 *   Unless required by applicable law or agreed to in writing.
 *
 *   If you have any questions or if you find a bug,
 *   please contact the author by email or ask for Issues.
 *
 *   Author:JinZhaolu <1756404649@qq.com>
 */

package com.nesp.sdk.android.app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.nesp.sdk.android.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;


public class CrashCatcherActivity extends AppCompatActivity {

    private static final String TAG = "CrashCatcherActivity";

    public static final String CRASH_MODEL = "crash_model";
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private CrashCatcher.CrashInfo crashInfo;

    private View root;
    private Context context = CrashCatcherActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crashcatcher);
        setTitle("崩溃信息展示");

        crashInfo = getIntent().getParcelableExtra(CRASH_MODEL);
        if (crashInfo == null) {
            return;
        }
        Log.e(TAG, Log.getStackTraceString(crashInfo.getEx()));

        root = findViewById(R.id.root);
        TextView tvPackageName = findViewById(R.id.nesp_sdk_crash_catcher_activity_tv_packageName);
        TextView textMessage = findViewById(R.id.nesp_sdk_crash_catcher_activity_textMessage);
        TextView tvClassName = findViewById(R.id.nesp_sdk_crash_catcher_activity_tv_className);
        TextView tvMethodName = findViewById(R.id.nesp_sdk_crash_catcher_activity_tv_methodName);
        TextView tvLineNumber = findViewById(R.id.nesp_sdk_crash_catcher_activity_tv_lineNumber);
        TextView tvExceptionType = findViewById(R.id.nesp_sdk_crash_catcher_activity_tv_exceptionType);
        TextView tvFullException = findViewById(R.id.nesp_sdk_crash_catcher_activity_tv_fullException);
        TextView tvTime = findViewById(R.id.nesp_sdk_crash_catcher_activity_tv_time);
        TextView tvModel = findViewById(R.id.nesp_sdk_crash_catcher_activity_tv_model);
        TextView tvBrand = findViewById(R.id.nesp_sdk_crash_catcher_activity_tv_brand);
        TextView tvSysApiLevel = findViewById(R.id.nesp_sdk_crash_catcher_activity_tv_sys_api_level);
        TextView tvSysVersion = findViewById(R.id.nesp_sdk_crash_catcher_activity_tv_sys_version);
        TextView tvAppName = root.findViewById(R.id.nesp_sdk_crash_catcher_activity_tv_app_name);
        TextView tvAppVersion = root.findViewById(R.id.nesp_sdk_crash_catcher_activity_tv_app_version);

        tvPackageName.setText(crashInfo.getClassName());
        textMessage.setText(crashInfo.getExceptionMsg());
        tvClassName.setText(crashInfo.getFileName());
        tvMethodName.setText(crashInfo.getMethodName());
        tvLineNumber.setText(String.valueOf(crashInfo.getLineNumber()));
        tvExceptionType.setText(crashInfo.getExceptionType());
        tvFullException.setText(crashInfo.getFullException());
        tvTime.setText(df.format(crashInfo.getTime()));

        tvModel.setText(crashInfo.getDevice().getModel());
        tvBrand.setText(crashInfo.getDevice().getBrand());
        tvSysApiLevel.setText(crashInfo.getDevice().getSysApiLevel());
        tvSysVersion.setText(crashInfo.getDevice().getSysVersion());
        tvAppName.setText(ApplicationUtil.name(context));
        tvAppVersion.setText(ApplicationUtil.versionName(context));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.crashcatcher_more, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_copy_text) {
            String crashText = getShareText(crashInfo);
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            if (cm != null) {
                ClipData mClipData = ClipData.newPlainText("crash", crashText);
                cm.setPrimaryClip(mClipData);
                showToast("拷贝成功");
            }
        } else if (id == R.id.menu_share_text) {
            String crashText = getShareText(crashInfo);
            shareText(crashText);
        } else if (id == R.id.menu_share_image) {
            if (ContextCompat.checkSelfPermission(CrashCatcherActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
                    ContextCompat.checkSelfPermission(CrashCatcherActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
            } else {
                shareImage();
            }
        }
        return true;
    }

    private String getShareText(CrashCatcher.CrashInfo model) {
        StringBuilder builder = new StringBuilder();

        builder.append("崩溃信息:")
                .append("\n")
                .append(model.getExceptionMsg())
                .append("\n");
        builder.append("\n");//空一行，好看点，(#^.^#)

        builder.append("类名:")
                .append(model.getFileName()).append("\n");
        builder.append("\n");//空一行，好看点，(#^.^#)

        builder.append("方法:").append(model.getMethodName()).append("\n");
        builder.append("\n");//空一行，好看点，(#^.^#)

        builder.append("行数:").append(model.getLineNumber()).append("\n");
        builder.append("\n");//空一行，好看点，(#^.^#)

        builder.append("类型:").append(model.getExceptionType()).append("\n");
        builder.append("\n");//空一行，好看点，(#^.^#)

        builder.append("时间:").append(df.format(model.getTime())).append("\n");
        builder.append("\n");//空一行，好看点，(#^.^#)

        builder.append("设备名称:").append(model.getDevice().getModel()).append("\n");
        builder.append("\n");//空一行，好看点，(#^.^#)

        builder.append("设备厂商:").append(model.getDevice().getBrand()).append("\n");
        builder.append("\n");//空一行，好看点，(#^.^#)

        builder.append("系统版本:").append(model.getDevice().getSysVersion()).append("\n");
        builder.append("\n");

        builder.append("系统API等级:").append(model.getDevice().getSysApiLevel()).append("\n");
        builder.append("\n");

        builder.append("软件名:").append(ApplicationUtil.name(context)).append("\n");
        builder.append("\n");

        builder.append("软件版本:").append(ApplicationUtil.versionName(context)).append("\n");
        builder.append("\n");

        builder.append("全部信息:")
                .append("\n")
                .append(model.getFullException()).append("\n");

        return builder.toString();
    }

    private void shareText(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "崩溃信息：");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, "分享到"));
    }

    private static final int REQUEST_CODE = 110;

    private void requestPermission(String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        //判断请求码，确定当前申请的权限
        if (requestCode == REQUEST_CODE) {
            //判断权限是否申请通过
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //授权成功
                shareImage();
            } else {
                //授权失败
                showToast("请授予SD卡权限才能分享图片");
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public Bitmap getBitmapByView(ScrollView view) {
        if (view == null) return null;
        int height = 0;
        for (int i = 0; i < view.getChildCount(); i++) {
            height += view.getChildAt(i).getHeight();
        }
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawRGB(255, 255, 255);
        view.draw(canvas);
        return bitmap;
    }

    private File BitmapToFile(Bitmap bitmap) {
        if (bitmap == null) return null;
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .getAbsolutePath();
        File imageFile = new File(path, "spiderMan-" + df.format(crashInfo.getTime()) + ".jpg");
        try {
            FileOutputStream out = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            bitmap.recycle();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }

    private void shareImage() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            showToast("未插入sd卡");
            return;
        }
        File file = BitmapToFile(getBitmapByView(findViewById(R.id.scrollView)));
        if (file == null || !file.exists()) {
            showToast("图片文件不存在");
            return;
        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(),
                    "com.nesp.sdk.FileProvider", file);
            intent.putExtra(Intent.EXTRA_STREAM, contentUri);
        } else {
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(intent, "分享图片"));
    }

    private void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

}
