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

package com.nesp.sdk.android.tencent.wechat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.nesp.sdk.android.graphics.BitmapUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXGameVideoFileObject;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import java.io.File;

import static com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req.WXSceneFavorite;
import static com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req.WXSceneSession;
import static com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req.WXSceneTimeline;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * @team NESP Technology
 * @time: Created 2020/3/20 14:57
 * @project FishMovie
 **/
public final class WXShareUtil {

    private WXShareUtil() {
        //no instance
    }

    public static void shareTextToSession(IWXAPI iwxapi, String text, String description) {
        shareText(iwxapi, text, description, SceneType.SESSION);
    }

    public static void shareTextToTimeline(IWXAPI iwxapi, String text, String description) {
        shareText(iwxapi, text, description, SceneType.TIMELINE);
    }

    public static void shareTextToFavorite(IWXAPI iwxapi, String text, String description) {
        shareText(iwxapi, text, description, SceneType.FAVORITE);
    }

    public static void shareText(IWXAPI iwxapi, String text, String description, @SceneType int sceneType) {
//        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID,false);
        //初始化一个 WXTextObject 对象，填写分享的文本内容
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

        //用 WXTextObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = description;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = msg;
        req.scene = getScene(sceneType);
        //调用api接口，发送数据到微信
        iwxapi.sendReq(req);
    }

    public static void shareImgToSession(IWXAPI iwxapi, Bitmap bmp, String description) {
        shareImg(iwxapi, bmp, description, SceneType.SESSION);
    }

    public static void shareImgToTimeline(IWXAPI iwxapi, Bitmap bmp, String description) {
        shareImg(iwxapi, bmp, description, SceneType.TIMELINE);
    }

    public static void shareImgToFavorite(IWXAPI iwxapi, Bitmap bmp, String description) {
        shareImg(iwxapi, bmp, description, SceneType.FAVORITE);
    }

    public static void shareImg(IWXAPI iwxapi, Bitmap bmp, String description, @SceneType int sceneType) {

        //初始化 WXImageObject 和 WXMediaMessage 对象
        WXImageObject imgObj = new WXImageObject(bmp);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        //设置缩略图
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
        bmp.recycle();
        msg.thumbData = BitmapUtil.bmpToByteArray(thumbBmp, true);

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = getScene(sceneType);
        //调用api接口，发送数据到微信
        iwxapi.sendReq(req);
    }

    public static void shareImgToSession(IWXAPI iwxapi, File imgFile, String description) {
        shareImg(iwxapi, imgFile, description, SceneType.SESSION);
    }

    public static void shareImgToTimeline(IWXAPI iwxapi, File imgFile, String description) {
        shareImg(iwxapi, imgFile, description, SceneType.TIMELINE);
    }

    public static void shareImgToFavorite(IWXAPI iwxapi, File imgFile, String description) {
        shareImg(iwxapi, imgFile, description, SceneType.FAVORITE);
    }

    public static void shareImg(IWXAPI iwxapi, File imgFile, String description, @SceneType int sceneType) {
        if (!imgFile.exists()) throw new RuntimeException("imgFile not exit");

        WXImageObject imgObj = new WXImageObject();
        imgObj.setImagePath(imgFile.getAbsolutePath());

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        Bitmap bmp = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
        bmp.recycle();
        msg.thumbData = BitmapUtil.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = getScene(sceneType);
        iwxapi.sendReq(req);
    }

    public static void shareMusicToSession(IWXAPI iwxapi, String musicUrl, String title, String description, Bitmap thumbBmp) {
        shareMusic(iwxapi, musicUrl, title, description, thumbBmp, SceneType.SESSION);
    }

    public static void shareMusicToTimeline(IWXAPI iwxapi, String musicUrl, String title, String description, Bitmap thumbBmp) {
        shareMusic(iwxapi, musicUrl, title, description, thumbBmp, SceneType.TIMELINE);
    }

    public static void shareMusicToFavorite(IWXAPI iwxapi, String musicUrl, String title, String description, Bitmap thumbBmp) {
        shareMusic(iwxapi, musicUrl, title, description, thumbBmp, SceneType.FAVORITE);
    }

    public static void shareMusic(IWXAPI iwxapi, String musicUrl, String title, String description, Bitmap thumbBmp, @SceneType int sceneType) {
//初始化一个WXMusicObject，填写url
        WXMusicObject music = new WXMusicObject();
        music.musicUrl = musicUrl;

//用 WXMusicObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = music;
        msg.title = title;
        msg.description = description;
        //设置音乐缩略图
        msg.thumbData = BitmapUtil.bmpToByteArray(thumbBmp, true);

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("music");
        req.message = msg;
        req.scene = getScene(sceneType);
        //调用api接口，发送数据到微信
        iwxapi.sendReq(req);
    }

    public static void shareMusicByMusicLowBandUrlToSession(IWXAPI iwxapi, String musicLowBandUrl, String title, String description, Bitmap thumbBmp) {
        shareMusicByMusicLowBandUrl(iwxapi, musicLowBandUrl, title, description, thumbBmp, SceneType.SESSION);
    }

    public static void shareMusicByMusicLowBandUrlToTimeline(IWXAPI iwxapi, String musicLowBandUrl, String title, String description, Bitmap thumbBmp) {
        shareMusicByMusicLowBandUrl(iwxapi, musicLowBandUrl, title, description, thumbBmp, SceneType.TIMELINE);
    }

    public static void shareMusicByMusicLowBandUrlToFavorite(IWXAPI iwxapi, String musicLowBandUrl, String title, String description, Bitmap thumbBmp) {
        shareMusicByMusicLowBandUrl(iwxapi, musicLowBandUrl, title, description, thumbBmp, SceneType.FAVORITE);
    }

    public static void shareMusicByMusicLowBandUrl(IWXAPI iwxapi, String musicLowBandUrl, String title, String description, Bitmap thumbBmp, @SceneType int sceneType) {
        WXMusicObject music = new WXMusicObject();
        music.musicLowBandUrl = musicLowBandUrl;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = music;
        msg.title = title;
        msg.description = description;

        msg.thumbData = BitmapUtil.bmpToByteArray(Bitmap.createScaledBitmap(thumbBmp, 150, 150, true), true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("music");
        req.message = msg;
        req.scene = getScene(sceneType);
        iwxapi.sendReq(req);
    }

    public static void shareVideoToSession(IWXAPI iwxapi, String videoUrl, String title, String description, Bitmap thumbBmp) {
        shareVideo(iwxapi, videoUrl, title, description, thumbBmp, SceneType.SESSION);
    }

    public static void shareVideoToTimeline(IWXAPI iwxapi, String videoUrl, String title, String description, Bitmap thumbBmp) {
        shareVideo(iwxapi, videoUrl, title, description, thumbBmp, SceneType.TIMELINE);
    }

    public static void shareVideoToFavorite(IWXAPI iwxapi, String videoUrl, String title, String description, Bitmap thumbBmp) {
        shareVideo(iwxapi, videoUrl, title, description, thumbBmp, SceneType.FAVORITE);
    }

    public static void shareVideo(IWXAPI iwxapi, String videoUrl, String title, String description, Bitmap thumbBmp, @SceneType int sceneType) {
        //初始化一个WXVideoObject，填写url
        WXVideoObject video = new WXVideoObject();
        video.videoUrl = videoUrl;

        //用 WXVideoObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage(video);
        msg.title = title;
        msg.description = description;
        msg.thumbData = BitmapUtil.bmpToByteArray(thumbBmp, true);

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("video");
        req.message = msg;
        req.scene = getScene(sceneType);

        //调用api接口，发送数据到微信
        iwxapi.sendReq(req);
    }

    public static void shareVideoByVideoLowBandUrlToSession(IWXAPI iwxapi, String videoLowBandUrl, String title, String description, Bitmap thumbBmp) {
        shareVideoByVideoLowBandUrl(iwxapi, videoLowBandUrl, title, description, thumbBmp, SceneType.SESSION);
    }

    public static void shareVideoByVideoLowBandUrlToTimeline(IWXAPI iwxapi, String videoLowBandUrl, String title, String description, Bitmap thumbBmp) {
        shareVideoByVideoLowBandUrl(iwxapi, videoLowBandUrl, title, description, thumbBmp, SceneType.TIMELINE);
    }

    public static void shareVideoByVideoLowBandUrlToFavorite(IWXAPI iwxapi, String videoLowBandUrl, String title, String description, Bitmap thumbBmp) {
        shareVideoByVideoLowBandUrl(iwxapi, videoLowBandUrl, title, description, thumbBmp, SceneType.FAVORITE);
    }

    public static void shareVideoByVideoLowBandUrl(IWXAPI iwxapi, String videoLowBandUrl, String title, String description, Bitmap thumbBmp, @SceneType int sceneType) {
        WXVideoObject video = new WXVideoObject();
        video.videoLowBandUrl = videoLowBandUrl;

        WXMediaMessage msg = new WXMediaMessage(video);

        msg.mediaTagName = "mediaTagName";
        msg.messageAction = "MESSAGE_ACTION_SNS_VIDEO#gameseq=1491995805&GameSvrEntity=87929&RelaySvrEntity=2668626528&playersnum=10";

        msg.title = title;
        msg.description = description;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("video");
        req.message = msg;
        req.scene = getScene(sceneType);
        iwxapi.sendReq(req);
    }

    public static void shareLocalVideoToSession(IWXAPI iwxapi, String path, String title, String description, Bitmap thumbBmp) {
        shareLocalVideo(iwxapi, path, title, description, thumbBmp, SceneType.SESSION);
    }

    public static void shareLocalVideoToTimeline(IWXAPI iwxapi, String path, String title, String description, Bitmap thumbBmp) {
        shareLocalVideo(iwxapi, path, title, description, thumbBmp, SceneType.SESSION);
    }

    public static void shareLocalVideoToFavorite(IWXAPI iwxapi, String path, String title, String description, Bitmap thumbBmp) {
        shareLocalVideo(iwxapi, path, title, description, thumbBmp, SceneType.SESSION);
    }

    public static void shareLocalVideo(IWXAPI iwxapi, String path, String title, String description, Bitmap thumbBmp, @SceneType int sceneType) {
        final WXGameVideoFileObject gameVideoFileObject = new WXGameVideoFileObject();
        gameVideoFileObject.filePath = path;

        final WXMediaMessage msg = new WXMediaMessage();
        msg.setThumbImage(BitmapUtil.extractThumbNail(path, 150, 150, true));
        msg.title = title;
        msg.description = description;
        msg.mediaObject = gameVideoFileObject;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("appdata");
        req.message = msg;
        req.scene = getScene(sceneType);
        iwxapi.sendReq(req);
    }

    public static void shareWebPageToSession(IWXAPI iwxapi, String url, String title, String description, Bitmap thumbBmp) {
        shareWebPage(iwxapi, url, title, description, thumbBmp, SceneType.SESSION);
    }

    public static void shareWebPageToTimeline(IWXAPI iwxapi, String url, String title, String description, Bitmap thumbBmp) {
        shareWebPage(iwxapi, url, title, description, thumbBmp, SceneType.TIMELINE);
    }

    public static void shareWebPageToFavorite(IWXAPI iwxapi, String url, String title, String description, Bitmap thumbBmp) {
        shareWebPage(iwxapi, url, title, description, thumbBmp, SceneType.FAVORITE);
    }

    public static void shareWebPage(IWXAPI iwxapi, String url, String title, String description, Bitmap thumbBmp, @SceneType int sceneType) {
        //初始化一个WXWebpageObject，填写url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;

//用 WXWebpageObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;
        msg.thumbData = BitmapUtil.bmpToByteArray(thumbBmp, true);

//构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = getScene(sceneType);

//调用api接口，发送数据到微信
        iwxapi.sendReq(req);
    }

    public static void shareWXMiniProgram(IWXAPI iwxapi, String webpageUrl, int miniprogramType, String userName, String path, String title, String description, Bitmap thumbBmp) {
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.webpageUrl = webpageUrl; // 兼容低版本的网页链接
//        miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
        miniProgramObj.miniprogramType = miniprogramType;// 正式版:0，测试版:1，体验版:2
        miniProgramObj.userName = userName;     // 小程序原始id
        miniProgramObj.path = path;            //小程序页面路径；对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"
        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = title;                    // 小程序消息title
        msg.description = description;               // 小程序消息desc
        msg.thumbData = BitmapUtil.bmpToByteArray(thumbBmp, true);                      // 小程序消息封面图片，小于128k

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("miniProgram");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前只支持会话
        iwxapi.sendReq(req);
    }

    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private static int getScene(@SceneType final int sceneType) {
        switch (sceneType) {
            case SceneType.TIMELINE:
                return WXSceneTimeline;
            case SceneType.FAVORITE:
                return WXSceneFavorite;
            case SceneType.SESSION:
            default:
                return WXSceneSession;
        }
    }


}
