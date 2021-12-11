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

package com.nesp.sdk.android.webkit;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.Toast;

import com.nesp.sdk.android.R;

/**
 * @Team: NESP Technology
 * @Author: 靳兆鲁
 * Email: 1756404649@qq.com
 * @Time: Created 18-11-12 上午10:27
 * @Project Assistant
 **/
public class WebView extends android.webkit.WebView {

    private static final String TAG = "WebView";

    public WebView(Context context) {
        super(context);
    }

    public WebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private OnScrollChangeListener onScrollChangeListener;

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollChangeListener != null) {
            onScrollChangeListener.onScrollChanged(l, t, oldl, oldt);

            // webview的高度
            float webcontent = getContentHeight() * getScale();

            // 当前webview的高度
            float webnow = getHeight() + getScrollY();

            if (Math.abs(webcontent - webnow) < 1) {
                //处于底端
                onScrollChangeListener.onPageBottom(l, t, oldl, oldt);
            }

            if (getScrollY() == 0) {
                //处于顶端
                onScrollChangeListener.onPageTop(l, t, oldl, oldt);
            }

            if (t > oldt) onScrollChangeListener.onScrollUp();

            if (t < oldt) onScrollChangeListener.onScrollDown();
        }

    }

    public void openNightMode() {
        loadUrl(getNightModeCssJs());
    }

    private static String getNightModeCssJs() {
        String nightModeCssJs = "javascript:style=document.createElement('style');style.setAttribute('type','text/css');styleElem=document.getElementsByTagName('head')[0].appendChild(style);styleElem.innerHTML='';child=document.createTextNode('body,body * {background:#2e2e2e!important;color:RGB(200,200,200) !important;}');styleElem.appendChild(child); ";
        return nightModeCssJs;
    }

//    protected void filtElement(Context context) {
//        loadUrl(getClearAdDivJs(context));
//    }

    public void openByBrowser(Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(getUrl()));
        context.startActivity(intent);
    }
    public void setOnScrollChangeListener(OnScrollChangeListener listener) {
        this.onScrollChangeListener = listener;
    }

    public interface OnScrollChangeListener {

        void onPageBottom(int l, int t, int oldl, int oldt);

        void onPageTop(int l, int t, int oldl, int oldt);

        void onScrollChanged(int l, int t, int oldl, int oldt);

        void onScrollUp();

        void onScrollDown();

    }

}
