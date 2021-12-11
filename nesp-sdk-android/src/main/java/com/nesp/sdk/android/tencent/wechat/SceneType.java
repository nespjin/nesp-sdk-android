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

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * @team NESP Technology
 * @time: Created 2020/3/20 14:17
 * @project FishMovie
 **/
@IntDef({SceneType.SESSION, SceneType.TIMELINE, SceneType.FAVORITE})
@Retention(RetentionPolicy.SOURCE)
public @interface SceneType {
    //会话
    int SESSION = 0;
    //朋友圈
    int TIMELINE = 1;
    //收藏
    int FAVORITE = 2;
}
