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

package com.nesp.sdk.android.widget.loadingtipdialog;

/**
 * Created by Luo_xiasuhuei321@163.com on 2016/11/12.
 * desc:用于设置全局的loading样式
 */
@Deprecated
public class StyleManager {
    public StyleManager() {
    }

    public StyleManager(boolean open, int repeatTime, LoadingTipDialog.Speed speed,
                        int contentSize, int textSize, long showTime, boolean interceptBack,
                        String loadText, String successText, String failedText) {
        this.openAnim = open;
        this.repeatTime = repeatTime;
        this.speed = speed;
        this.contentSize = contentSize;
        this.textSize = textSize;
        this.showTime = showTime;
        this.interceptBack = interceptBack;
        this.loadText = loadText;
        this.successText = successText;
        this.failedText = failedText;

    }

    public StyleManager(boolean open, int repeatTime, LoadingTipDialog.Speed speed,
                        int contentSize, int textSize, long showTime, boolean interceptBack,
                        String loadText, String successText, String failedText, int loadStyle) {
        this.openAnim = open;
        this.repeatTime = repeatTime;
        this.speed = speed;
        this.contentSize = contentSize;
        this.textSize = textSize;
        this.showTime = showTime;
        this.interceptBack = interceptBack;
        this.loadText = loadText;
        this.successText = successText;
        this.failedText = failedText;
        this.loadStyle = loadStyle;
    }

    /**
     * 是否开启绘制
     */
    private boolean openAnim = true;

    /**
     * 重绘次数
     */
    private int repeatTime;

    private LoadingTipDialog.Speed speed = LoadingTipDialog.Speed.SPEED_TWO;

    /**
     * 反馈的尺寸，单位px
     */
    private int contentSize = -1;

    /**
     * 文字的尺寸，单位px
     */
    private int textSize = -1;

    /**
     * loading的反馈展示的时间，单位ms
     */
    private long showTime = -1;

    private boolean interceptBack = true;

    private String loadText = "加载中...";

    private String successText = "加载成功";

    private String failedText = "加载失败";

    private int loadStyle = LoadingTipDialog.STYLE_RING;

    public StyleManager setLoadStyle(int loadStyle) {
        this.loadStyle = loadStyle;
        return this;
    }

    public int getLoadStyle() {
        return loadStyle;
    }

    public boolean isOpenAnim() {
        return openAnim;
    }

    public String getFailedText() {
        return failedText;
    }

    public String getSuccessText() {
        return successText;
    }

    public String getLoadText() {
        return loadText;
    }

    public boolean isInterceptBack() {
        return interceptBack;
    }

    public long getShowTime() {
        return showTime;
    }

    public int getTextSize() {
        return textSize;
    }

    public int getContentSize() {
        return contentSize;
    }

    public LoadingTipDialog.Speed getSpeed() {
        return speed;
    }

    public int getRepeatTime() {
        return repeatTime;
    }

    /**
     * 是否开启动态绘制
     *
     * @param openAnim true开启，false关闭
     * @return this
     */
    public StyleManager Anim(boolean openAnim) {
        this.openAnim = openAnim;
        return this;
    }

    /**
     * 重复次数
     *
     * @param times 次数
     * @return this
     */
    public StyleManager repeatTime(int times) {
        this.repeatTime = times;
        return this;
    }

    public StyleManager speed(LoadingTipDialog.Speed s) {
        this.speed = s;
        return this;
    }

    /**
     * 设置loading的大小
     *
     * @param size 尺寸，单位px
     * @return this
     */
    public StyleManager contentSize(int size) {
        this.contentSize = size;
        return this;
    }

    /**
     * 设置loading 文字的大小
     *
     * @param size 尺寸，单位px
     * @return this
     */
    public StyleManager textSize(int size) {
        this.textSize = size;
        return this;
    }

    /**
     * 设置展示的事件，如果开启绘制则从绘制完毕开始计算
     *
     * @param showTime 事件
     * @return this
     */
    public StyleManager showTime(long showTime) {
        this.showTime = showTime;
        return this;
    }

    /**
     * 设置是否拦截back，默认拦截
     *
     * @param interceptBack true拦截，false不拦截
     * @return this
     */
    public StyleManager intercept(boolean interceptBack) {
        this.interceptBack = interceptBack;
        return this;
    }

    /**
     * 设置loading时的文字
     *
     * @param text 文字
     * @return this
     */
    public StyleManager loadText(String text) {
        this.loadText = text;
        return this;
    }

    /**
     * 设置success时的文字
     *
     * @param text 文字
     * @return this
     */
    public StyleManager successText(String text) {
        this.successText = text;
        return this;
    }

    /**
     * 设置failed时的文字
     *
     * @param text 文字
     * @return this
     */
    public StyleManager failedText(String text) {
        this.failedText = text;
        return this;
    }

}
