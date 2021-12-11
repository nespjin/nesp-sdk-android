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

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.nesp.sdk.android.R;
import com.nesp.sdk.android.device.ScreenUtil;


/**
 * Author: <a href="mailto:jinzhaolu@numob.com">靳兆鲁 Email:jinzhaolu@numob.com</a>
 * Time: Created 2020/5/20 4:42 PM
 * Description:
 **/
public class AppBasicActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = "AppBaseActivity";

    private TextView tvTitle;
    private TextView tvSubTitle;

    /**
     * TextView which on left of ActionBar
     */
    private TextView tvLeftAction;
    /**
     * TextView for Search placeholder
     */
    private TextView tvSearchHint;

    /**
     * Search Activity entry
     */
    private LinearLayout llSearchContent;

    /**
     * SearchView for SearchActivity
     */
    protected SearchView searchView;
    /**
     * TextView for cancel search
     */
    private TextView tvSearchCancel;
    private ImageView ivScan;
    private Boolean canClearSearchText = false;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.WHITE);
        initializeView();
    }

    private void initializeView() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            setUnderLineEnable(true);
            supportActionBar.setElevation(0);
            supportActionBar.setDisplayShowTitleEnabled(false);
            supportActionBar.setDisplayShowHomeEnabled(false);
            supportActionBar.setDisplayShowCustomEnabled(true);

            setShowBack(true);

            View customView = getSearchViewEnable() ? getCustomActionBarSearchView() : getCustomActionBarView();

            try {
                tvTitle = customView.findViewById(android.R.id.title);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                tvSubTitle = customView.findViewById(R.id.sub_title);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                tvLeftAction = customView.findViewById(R.id.tv_left_action);
            } catch (Exception e) {
                e.printStackTrace();
            }

            setTitle("");
            setSubTitle("");

            try {
                searchView = customView.findViewById(R.id.searchView);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (searchView != null) {
                searchView.setOnQueryTextListener(this);
                searchView.requestFocus();
                try {
                    SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
                    searchAutoComplete.setTextSize(16);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            try {
                tvSearchCancel = customView.findViewById(R.id.tvCancel);
            } catch (Exception e) {
                e.printStackTrace();
            }


            if (tvSearchCancel != null) {
                tvSearchCancel.setOnClickListener(this::OnSearchCancelClick);
            }

            try {
                ivScan = customView.findViewById(R.id.iv_scan);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (ivScan != null) {

                if (scanEnable()) {
                    ivScan.setVisibility(View.VISIBLE);
//                    ivScan.setImageDrawable(getDrawable(R.drawable.scan));
                } else {
                    ivScan.setVisibility(View.GONE);
                    ivScan.setImageDrawable(getDrawable(R.drawable.ic_search_close));
                }

                ivScan.setOnClickListener(v -> {
                    if (canClearSearchText) {
                        OnClearSearchTextClick(v);
                    } else {
                        OnSearchScanClick(v);
                    }
                });
            }


            supportActionBar.setCustomView(customView, new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            ((Toolbar) customView.getParent()).setContentInsetsAbsolute(0, 0);
        }
    }

    /**
     * @param isShow if true to show BackButton
     */
    protected void setShowBack(Boolean isShow) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(isShow);
            supportActionBar.setDisplayShowHomeEnabled(isShow);
        }
    }

    protected void setQuery(String queryText, boolean submit) {
        if (searchView != null) {
            searchView.setQuery(queryText, submit);
        }
    }

    /**
     * Set query for SearchView on SearchActivity or TextView on SearchActivity entry
     *
     * @param queryHint placeholder
     */
    protected void setQueryHint(String queryHint) {
        if (searchView != null) searchView.setQueryHint(queryHint);

        if (tvSearchHint==null){
            try {
                tvSearchHint = findViewById(R.id.tvSearchHint);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (tvSearchHint != null) {
            setUnderLineEnable(false);
            tvSearchHint.setText(queryHint);
        }
    }

    /**
     * Search Activity entry
     *
     * @param onClickListener onClickListener
     */
    protected void setOnSearchContentClickListener(View.OnClickListener onClickListener) {
        if (llSearchContent == null) {
            try {
                llSearchContent = findViewById(R.id.ll_search_content);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (llSearchContent != null) {
            llSearchContent.setOnClickListener(onClickListener);
        }
    }

    /**
     * Call when click scan ImageView which in SearchView
     *
     * @param view view
     */
    protected void OnSearchScanClick(View view) {
    }

    protected void OnClearSearchTextClick(View view) {
        if (canClearSearchText) {
            if (searchView != null) {
                searchView.setQuery("", false);
            }
        }
    }

    protected void OnSearchCancelClick(View view) {
        finish();
    }

    protected void showLeftAction() {
        if (tvLeftAction == null) return;
        tvLeftAction.setVisibility(View.VISIBLE);
    }

    protected void hideLeftAction() {
        if (tvLeftAction == null) return;
        tvLeftAction.setVisibility(View.INVISIBLE);
    }

    protected void showLastTitle(String text) {
        showLeftAction(text, true, null);
    }

    protected void showLeftAction(String text) {
        showLeftAction(text, null);
    }

    protected void showLeftAction(String text, View.OnClickListener onClickListener) {
        showLeftAction(text, false, onClickListener);
    }

    protected void showLeftAction(String text, Boolean isLastTitle, View.OnClickListener onClickListener) {
        if (tvLeftAction == null) return;

        if (text == null || text.equals("")) {
            tvLeftAction.setVisibility(View.GONE);
            return;
        }

        if (isLastTitle) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tvLeftAction.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, 0);
            tvLeftAction.setLayoutParams(layoutParams);
        }

        tvLeftAction.setVisibility(View.VISIBLE);
        tvLeftAction.setText(text);
        tvLeftAction.setOnClickListener(onClickListener);
    }

    @Override
    public void setTitle(final CharSequence title) {
        super.setTitle(title);
        if (title != null) {
            setTitle(title.toString());
        }
    }

    protected void setUnderLineEnable(Boolean enable) {
        ActionBar supportActionBar = getSupportActionBar();
        View searchUnderLine = findViewById(R.id.vSearchLine);
        if (supportActionBar != null) {
            if (enable) {
                if (searchUnderLine != null) {
                    searchUnderLine.setVisibility(View.VISIBLE);
                }
                supportActionBar.setBackgroundDrawable(getDrawable(R.drawable.action_bar_bg));
            } else {
                if (searchUnderLine != null) {
                    searchUnderLine.setVisibility(View.GONE);
                }
                supportActionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            }
        }
    }

    protected void refreshTitle(String title) {
        if (tvTitle == null || title == null) {
            return;
        }
        tvTitle.setText(title);

    }

    protected void setTitle(String title) {
        if (tvTitle == null) return;
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null && title != null) {
            supportActionBar.setTitle(title);
            tvTitle.setText(title);
            tvTitle.post(() -> {
                int measuredWidth = tvTitle.getMeasuredWidth();
//                if (measuredWidth == 0 && titleMeasureWidth != 0) {
//                    measuredWidth = titleMeasureWidth;
//                } else if (measuredWidth != 0) {
//                    titleMeasureWidth = measuredWidth;
//                }

                int screenWidth = ScreenUtil.getScreenWidth(AppBasicActivity.this);
                int[] location = new int[2];
                tvTitle.getLocationOnScreen(location);
                int shouldOffset = (screenWidth - measuredWidth) / 2;

                int realOffset = shouldOffset - location[0];
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tvTitle.getLayoutParams();
                layoutParams.setMargins(realOffset, 0, 0, 0);

                tvTitle.setLayoutParams(layoutParams);
//                tvTitle.layout(shouldOffset,tvTitle.getTop(), tvTitle.getRight(), tvTitle.getBottom());

            });
        }
    }

    protected Boolean getSearchViewEnable() {
        return false;
    }

    protected View getCustomActionBarView() {
        return getLayoutInflater().inflate(getCustomActionBarViewResId(), null);
    }

    protected View getCustomActionBarSearchView() {
        return getLayoutInflater().inflate(getCustomActionBarSearchViewResId(), null);
    }

    protected int getCustomActionBarViewResId() {
        return R.layout.action_title;
    }

    protected int getCustomActionBarSearchViewResId() {
        return R.layout.action_search;
    }

    protected void setSubTitle(CharSequence charSequence) {
        if (charSequence != null) {
            setSubTitle(charSequence.toString());
        }
    }


    protected void setSubTitle(String subTitle) {
        if (tvSubTitle == null) return;
        if (subTitle != null && !subTitle.equals("")) {
            tvSubTitle.setVisibility(View.VISIBLE);
            tvSubTitle.setText(subTitle);
            tvSubTitle.post(() -> {
                int measuredWidth = tvSubTitle.getMeasuredWidth();
                int screenWidth = ScreenUtil.getScreenWidth(AppBasicActivity.this);
                int[] location = new int[2];
                tvSubTitle.getLocationOnScreen(location);
                int shouldOffset = (screenWidth - measuredWidth) / 2;
                int realOffset = shouldOffset - location[0];
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tvSubTitle.getLayoutParams();
                layoutParams.setMargins(realOffset, 0, 0, 0);
                tvSubTitle.setLayoutParams(layoutParams);
            });
        } else {
            tvSubTitle.setVisibility(View.GONE);
        }
    }

    protected void setDisplayHomeAsUpEnabled(Boolean enable) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(enable);
            supportActionBar.setDisplayShowHomeEnabled(enable);
        }
    }

    @Override
    public boolean onQueryTextSubmit(final String query) {
        return false;
    }

    @CallSuper
    @Override
    public boolean onQueryTextChange(final String newText) {
        if (scanEnable()) {
            canClearSearchText = newText.length() != 0;
//            ivScan.setImageDrawable(getDrawable(canClearSearchText ? R.drawable.ic_search_close : R.drawable.scan));
        } else {
            canClearSearchText = true;
            ivScan.setVisibility(newText.length() == 0 ? View.GONE : View.VISIBLE);
        }

        return true;
    }


    protected Boolean scanEnable() {
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            for (int i = 0; i < menu.size(); i++) {
                MenuItem item = menu.getItem(i);
                setRightMenuTitleEnable(item);
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBack();
            onHomeUpClick();
            finish();
        }
//        else if (item.getItemId() == R.id.menu_save) {
//            onSave();
//        }
        return super.onOptionsItemSelected(item);
    }

    protected void onSave() {
        finish();
    }


    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBack();
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void onHomeUpClick() {

    }

    protected void onBack() {

    }


    private void setRightMenuTitleEnable(MenuItem menuItem) {
        if (menuItem != null) {
            SpannableString spanString = new SpannableString(menuItem.getTitle().toString());
            spanString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorIosBlue)), 0, spanString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            menuItem.setTitle(spanString);
        }
    }

    protected void sendLocalBroadcastReceiverSync(Intent intent) {
        LocalBroadcastManager.getInstance(this).sendBroadcastSync(intent);
    }

    protected void sendLocalBroadcastReceiver(Intent intent) {
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    protected void registerLocalBroadcastReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
    }

    protected void unregisterLocalBroadcastReceiver(BroadcastReceiver broadcastReceiver) {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }


}
