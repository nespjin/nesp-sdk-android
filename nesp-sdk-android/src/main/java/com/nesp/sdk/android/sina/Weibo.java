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

package com.nesp.sdk.android.sina;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/**
 * @Team : NESP Technology
 * @Author : nesp
 * Email : 1756404649@qq.com
 * @Time : 18-4-3 上午12:11
 */
public class Weibo {

    /**
     * 打开微博用户
     * @param context context
     * @param uidValue 用户ID 打开网页微博,鼠标放在头像下面的关注或粉丝时就能在右下方看见UID
     *                 NESPTechnology UID=3619635672
     */
    public static void openWeiboUser(Context context, String uidValue) {
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("com.sina.weibo", "com.sina.weibo.page.ProfileInfoActivity");
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(componentName);
        intent.putExtra("uid", uidValue);
        context.startActivity(intent);
    }
}