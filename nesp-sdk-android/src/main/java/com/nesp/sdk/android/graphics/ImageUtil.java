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

package com.nesp.sdk.android.graphics;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;

import com.nesp.sdk.android.utils.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import id.zelory.compressor.Compressor;

/**
 * @Team: NESP Technology
 * @Author: 靳兆鲁
 * Email: 1756404649@qq.com
 * @Time: Created 18-6-4 下午5:00
 * @Project Assistant
 **/
@Deprecated
public final class ImageUtil {

    private ImageUtil() {
        //no instance
    }

    private static final String TAG = "ImageUtils";

    public static Bitmap uriToBitmap(Uri uri, Activity activity) {
        return BitmapFactory.decodeFile(uriToImageFile(uri, activity).getAbsolutePath());
    }

    /**
     * uri 转 ImageFile
     *
     * @param uri
     * @return
     */
    public static File uriToImageFile(Uri uri, Activity activity) {

        if (uri == null) {
            return null;
        }

        File file;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = activity.managedQuery(uri, proj, null,
                null, null);
        int actual_image_column_index = actualimagecursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor
                .getString(actual_image_column_index);
        file = new File(img_path);
        return file;
    }

    public static Bitmap compressImageFile(Context context, Uri uri, Activity activity) throws IOException {
        return compressImageFile(context, uriToImageFile(uri, activity), 200, 200, 50);
    }

    public static Bitmap compressImageFile(Context context, File imageFile) throws IOException {
        return compressImageFile(context, imageFile, 90, 90, 40);
    }

    public static Bitmap compressImageFile(Context context, File imageFile, int maxWidth, int maxHeight, int quality) throws IOException {
        return new Compressor(context)
                .setMaxWidth(maxWidth)
                .setMaxHeight(maxHeight)
                .setQuality(quality)
                .setCompressFormat(Bitmap.CompressFormat.WEBP)
                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
                .compressToBitmap(imageFile);
    }

    public static Bitmap compressImage(Bitmap bitmap) {
        //8kb
        return compressImage(bitmap, 8 * 1024);
    }

    public static Bitmap compressImage(Bitmap image, int maxLength) {

        int options = maxLength;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.WEBP, 40, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        while (baos.toByteArray().length / 1024 > options) { //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= maxLength / 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        return BitmapFactory.decodeStream(isBm, null, null);
    }

    /**
     * 用省内存的方法加载图片
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * 从View保存图像的方法：
     *
     * @param view
     * @param context
     */
    public static String saveBitmapFromView(View view, Context context) {
        return saveBitmapFromView(view, context, null);
    }

    public static String saveBitmapFromView(View view) {
        return saveBitmapFromView(view, view.getContext(), null);
    }

    /**
     * 从View保存图像的方法：
     *
     * @param view
     * @param context
     */
    public static String saveBitmapFromView(View view, Context context, OnSaveBitmapListener onSaveBitmapListener) {
        int w = view.getWidth();
        int h = view.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        view.layout(0, 0, w, h);
        view.draw(c);
        // 缩小图片
        Matrix matrix = new Matrix();
        matrix.postScale(0.5f, 0.5f); //长和宽放大缩小的比例
        bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //文件路径
        return saveBitmap(bmp, format.format(new Date(System.currentTimeMillis())) + ".JPEG", context, onSaveBitmapListener);
//        return bmp;
    }

    /**
     * 从View保存图像的方法：
     *
     * @param view
     */
    public static String saveBitmapFromViewToDir(View view, String filePath, OnSaveBitmapListener onSaveBitmapListener) {
        int w = view.getWidth();
        int h = view.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        view.layout(0, 0, w, h);
        view.draw(c);
        // 缩小图片
        Matrix matrix = new Matrix();
        matrix.postScale(0.5f, 0.5f); //长和宽放大缩小的比例
        bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        //文件路径
        return saveBitmapToDir(bmp, filePath, onSaveBitmapListener);
//        return bmp;
    }

    public static String saveBitmap(Bitmap bitmap, Context context, OnSaveBitmapListener onSaveBitmapListener) {
        return saveBitmap(bitmap, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())) + ".JPEG",
                context, onSaveBitmapListener);
    }

    /**
     * 保存文件，文件名为当前日期
     */
    public static String saveBitmap(Bitmap bitmap, String bitName, Context context, OnSaveBitmapListener onSaveBitmapListener) {
        String fileName;
        File file;
        if (Build.BRAND.equals("Xiaomi")) { // 小米手机
            fileName = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/" + bitName;
        } else {  // Meizu 、Oppo
            fileName = Environment.getExternalStorageDirectory().getPath() + "/DCIM/" + bitName;
        }
        file = new File(fileName);

        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            // 格式为 JPEG，照相机拍出的图片为JPEG格式的，PNG格式的不能显示在相册中
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)) {
                out.flush();
                out.close();
                bitmap.recycle();
                // 插入图库
                MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), bitName, null);
                if (onSaveBitmapListener != null) {
                    onSaveBitmapListener.onResult(true);
                }
            } else {
                if (onSaveBitmapListener != null) {
                    onSaveBitmapListener.onResult(false);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            if (onSaveBitmapListener != null) {
                onSaveBitmapListener.onResult(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (onSaveBitmapListener != null) {
                onSaveBitmapListener.onResult(false);
            }
        }
        // 发送广播，通知刷新图库的显示
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + fileName)));
        //文件路径

        return file.getAbsolutePath();
    }

    /**
     * 保存文件，文件名为当前日期
     */
    public static String saveBitmapToDir(Bitmap bitmap, String filePath, OnSaveBitmapListener onSaveBitmapListener) {
        File file = new File(filePath);

        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;

        try {
            out = new FileOutputStream(file);
            // 格式为 JPEG，照相机拍出的图片为JPEG格式的，PNG格式的不能显示在相册中
            if (bitmap.compress(Bitmap.CompressFormat.WEBP, 100, out)) {
                out.flush();
                out.close();
                bitmap.recycle();
                if (onSaveBitmapListener != null) {
                    onSaveBitmapListener.onResult(true);
                }
            } else {
                if (onSaveBitmapListener != null) {
                    onSaveBitmapListener.onResult(false);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            if (onSaveBitmapListener != null) {
                onSaveBitmapListener.onResult(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (onSaveBitmapListener != null) {
                onSaveBitmapListener.onResult(false);
            }
        }
        //文件路径
        return file.getAbsolutePath();
    }

    public static Bitmap webData2bitmap(String data) {
        Log.e(TAG, "ImageUtils.webData2bitmap: " + data);
        byte[] imageBytes = Base64.decode(data.split(",")[1], Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

    public static Bitmap url2bitmap(String url) {
        Bitmap bm;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;
            int length = http.getContentLength();
            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
            if (bm != null) {
                return bm;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public interface OnSaveBitmapListener {
        void onResult(boolean isSuccess);
    }
}
