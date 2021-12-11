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

package com.nesp.sdk.android.tencent.qq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.IntDef;

import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzonePublish;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

/**
 * @Team : NESP Technology
 * @Author : nesp
 * Email : 1756404649@qq.com
 * @Time : 18-4-2 下午10:25
 */
@Deprecated
public final class QQ {

    private QQ() {
        //no instance
    }

    public static Tencent getTencent(Context context, String qqAppId) {
        return Tencent.createInstance(qqAppId, context);
    }

    public static void openQQFriend(Context context, String qqFriendNumber) {
        context.startActivity(
                new Intent(Intent.ACTION_VIEW,
                        Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + qqFriendNumber + "&version=1")));
    }

    /****************
     *相关接口地址: https://qun.qq.com
     *
     * 发起添加群流程。群号：NESP Technology官方群(147362343) 的 key 为： -MRme_Pcns9c6WSLP4O3_Jz3ld6-KVph
     * 调用 joinQQGroup(-MRme_Pcns9c6WSLP4O3_Jz3ld6-KVph) 即可发起手Q客户端申请加群 NESP Technology官方群(147362343)
     *
     *
     * 发起添加群流程。群号：NESP第三方ROM(450561293) 的 key 为： srsiaFQ-kaCC4M0Xz6EoATIqLz20cV_b
     * 调用 joinQQGroup(srsiaFQ-kaCC4M0Xz6EoATIqLz20cV_b) 即可发起手Q客户端申请加群 NESP第三方ROM(450561293)
     *
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     ******************/
    public static boolean joinQQGroup(Context context, String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            return false;
        }
    }

    public static boolean joinQQGroup(Context context, @QQGroupType int qqGroupType) {
        String key = getQQGroupKey(qqGroupType);
        return !key.isEmpty() && joinQQGroup(context, key);
    }

    public static String getQQGroupKey(@QQGroupType int qqGroupType) {
        String key = "";
        switch (qqGroupType) {
            case QQGroupType.NESP_TECHNOLOGY:
                key = "EHNtLa_rlbTcnS65WPEIMepFTEw3avJY";
                break;
            case QQGroupType.FISH_MOVIE:
                key = "2f0EtjKXhFxtky6nah-LFGb0hVzAOGbA";
                break;
        }
        return key;
    }

    public static void shareToQQ(Activity activity, Tencent tencent, String title, String summary, String targetUrl, String imageUrl, String additionContent, IUiListener iUiListener) {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);// 标题
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);// 摘要
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, targetUrl);// 内容地址
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);// 网络图片地址 params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "应用名称");// 应用名称
        params.putString(QQShare.SHARE_TO_QQ_EXT_INT, additionContent);
        // 分享操作要在主线程中完成
        tencent.shareToQQ(activity, params, iUiListener);
    }

    /**
     * 分享纯图片到QQ
     *
     * @param imgUrl 图片url
     */
    public static void shareImgToQQ(Activity activity, Tencent tencent, String imgUrl, IUiListener iUiListener) {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);// 设置分享类型为纯图片分享
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, imgUrl);// 需要分享的本地图片URL
        // 分享操作要在主线程中完成
        tencent.shareToQQ(activity, params, iUiListener);
    }

    public static void shareToQQZone(Activity activity, Tencent tencent, String title, String summary, String targetUrl, ArrayList<String> imgUrlList, IUiListener iUiListener) {
        Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);// 标题
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, summary);// 摘要
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, targetUrl);// 内容地址
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgUrlList);// 图片地址
        // 分享操作要在主线程中完成
        tencent.shareToQzone(activity, params, iUiListener);
    }

    /**
     * 发表到QQ空间
     *
     * @param imgUrlList 图片地址集合--上传的只能是本地图片！
     */
    public static void publishToQzone(Activity activity, Tencent tencent, String content,
                                      ArrayList<String> imgUrlList, IUiListener iUiListener) {
        // 分享类型
        Bundle params = new Bundle();
        params.putInt(QzonePublish.PUBLISH_TO_QZONE_KEY_TYPE, QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD);
        params.putString(QzonePublish.PUBLISH_TO_QZONE_SUMMARY, content);
        params.putStringArrayList(QzonePublish.PUBLISH_TO_QZONE_IMAGE_URL, imgUrlList);// 图片地址ArrayList
        // 分享操作要在主线程中完成
        tencent.publishToQzone(activity, params, iUiListener);
    }

    @IntDef({QQGroupType.NESP_TECHNOLOGY, QQGroupType.FISH_MOVIE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface QQGroupType {
        int NESP_TECHNOLOGY = 0;
        int FISH_MOVIE = 1;
    }
}