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

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.SpannableString;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.nesp.sdk.android.R;
import com.nesp.sdk.android.device.ScreenUtil;

/**
 * Author: <a href="mailto:jinzhaolu@numob.com">Jack Email:jinzhaolu@numob.com</a>
 * Time: Created 2020/8/7 11:14 AM
 * Description:
 **/

public class AppBasicActivityWrapperImpl extends AppBasicActivityWrapper implements LifecycleEventObserver {

    private static final String TAG = "AppBasicActivityWrapperImpl";

    private final Build build;

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

    private AppBasicActivityWrapperImpl(Build build) {
        this.build = build;
    }

    public static class Build {

        private final AppCompatActivity targetActivity;

        public Build(AppCompatActivity targetActivity) {
            this.targetActivity = targetActivity;
        }

        public AppCompatActivity getTargetActivity() {
            return targetActivity;
        }

        public AppBasicActivityWrapperImpl build() {
            return new AppBasicActivityWrapperImpl(this);
        }

    }

    @Override
    public void wrap() {
        if (build == null || build.getTargetActivity() == null) return;

        AppCompatActivity targetActivity = build.getTargetActivity();
        targetActivity.getLifecycle().addObserver(this);
    }

    @Override
    public void onTargetActivityCreate(LifecycleOwner source) {
        if (build == null || build.getTargetActivity() == null) return;

        AppCompatActivity targetActivity = build.getTargetActivity();
        targetActivity.getWindow().setStatusBarColor(Color.WHITE);
        ActionBar supportActionBar = targetActivity.getSupportActionBar();
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
//                    ivScan.setImageDrawable(targetActivity.getDrawable(R.drawable.scan));
                } else {
                    ivScan.setVisibility(View.GONE);
                    ivScan.setImageDrawable(targetActivity.getDrawable(R.drawable.ic_search_close));
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

    @Override
    public void onTargetActivityStart(LifecycleOwner source) {

    }

    @Override
    public void onTargetActivityResume(LifecycleOwner source) {

    }

    @Override
    public void onTargetActivityPause(LifecycleOwner source) {

    }

    @Override
    public void onTargetActivityStop(LifecycleOwner source) {

    }

    @Override
    public void onTargetActivityDestroy(LifecycleOwner source) {

    }

    @Override
    public void onTargetActivityAny(LifecycleOwner source) {

    }

    @Override
    public void setShowBack(Boolean isShow) {
        if (build == null || build.getTargetActivity() == null) return;
        AppCompatActivity targetActivity = build.getTargetActivity();

        ActionBar supportActionBar = targetActivity.getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(isShow);
            supportActionBar.setDisplayShowHomeEnabled(isShow);
        }
    }

    @Override
    public void setQuery(String queryText, boolean submit) {
        if (searchView != null) {
            searchView.setQuery(queryText, submit);
        }
    }

    @Override
    public void setQueryHint(String queryHint) {
        if (build == null || build.getTargetActivity() == null) return;
        AppCompatActivity targetActivity = build.getTargetActivity();

        if (searchView != null) searchView.setQueryHint(queryHint);

        if (tvSearchHint == null) {
            try {
                tvSearchHint = targetActivity.findViewById(R.id.tvSearchHint);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (tvSearchHint != null) {
            setUnderLineEnable(false);
            tvSearchHint.setText(queryHint);
        }
    }

    @Override
    public void setOnSearchContentClickListener(View.OnClickListener onClickListener) {
        if (build == null || build.getTargetActivity() == null) return;
        AppCompatActivity targetActivity = build.getTargetActivity();

        if (llSearchContent == null) {
            try {
                llSearchContent = targetActivity.findViewById(R.id.ll_search_content);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (llSearchContent != null) {
            llSearchContent.setOnClickListener(onClickListener);
        }
    }

    @Override
    public void OnSearchScanClick(View view) {

    }

    @Override
    public void OnClearSearchTextClick(View view) {
        if (canClearSearchText) {
            if (searchView != null) {
                searchView.setQuery("", false);
            }
        }
    }

    @Override
    public void OnSearchCancelClick(View view) {
        if (build == null || build.getTargetActivity() == null) return;
        AppCompatActivity targetActivity = build.getTargetActivity();
        targetActivity.finish();
    }

    @Override
    public void showLeftAction() {
        if (tvLeftAction == null) return;
        tvLeftAction.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLeftAction() {
        if (tvLeftAction == null) return;
        tvLeftAction.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showLastTitle(String text) {
        showLeftAction(text, true, null);
    }

    @Override
    public void showLeftAction(String text) {
        showLeftAction(text, null);
    }

    @Override
    public void showLeftAction(String text, View.OnClickListener onClickListener) {
        showLeftAction(text, false, onClickListener);
    }

    @Override
    public void showLeftAction(String text, Boolean isLastTitle, View.OnClickListener onClickListener) {
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
    public void setTitle(CharSequence title) {
        if (title != null) {
            setTitle(title.toString());
        }
    }

    @Override
    public void setTitle(String title) {

        if (build == null || build.getTargetActivity() == null) return;
        AppCompatActivity targetActivity = build.getTargetActivity();

        if (tvTitle == null) return;
        ActionBar supportActionBar = targetActivity.getSupportActionBar();
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

                int screenWidth = ScreenUtil.getScreenWidth(targetActivity);
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

    @Override
    public void setSubTitle(CharSequence charSequence) {
        if (build == null || build.getTargetActivity() == null) return;
        if (charSequence != null) {
            setSubTitle(charSequence.toString());
        }
    }

    @Override
    public void setSubTitle(String subTitle) {
        if (build == null || build.getTargetActivity() == null) return;
        if (tvSubTitle == null) return;
        if (subTitle != null && !subTitle.equals("")) {
            tvSubTitle.setVisibility(View.VISIBLE);
            tvSubTitle.setText(subTitle);
            tvSubTitle.post(() -> {
                int measuredWidth = tvSubTitle.getMeasuredWidth();
                int screenWidth = ScreenUtil.getScreenWidth(build.getTargetActivity());
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

    public void setUnderLineEnable(Boolean enable) {
        if (build == null || build.getTargetActivity() == null) return;
        AppCompatActivity targetActivity = build.getTargetActivity();

        ActionBar supportActionBar = targetActivity.getSupportActionBar();
        View searchUnderLine = targetActivity.findViewById(R.id.vSearchLine);
        if (supportActionBar != null) {
            if (enable) {
                if (searchUnderLine != null) {
                    searchUnderLine.setVisibility(View.VISIBLE);
                }
                supportActionBar.setBackgroundDrawable(targetActivity.getDrawable(R.drawable.action_bar_bg));
            } else {
                if (searchUnderLine != null) {
                    searchUnderLine.setVisibility(View.GONE);
                }
                supportActionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            }
        }
    }

    @Override
    public void setDisplayHomeAsUpEnabled(Boolean enable) {
        if (build == null || build.getTargetActivity() == null) return;
        ActionBar supportActionBar = build.getTargetActivity().getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(enable);
            supportActionBar.setDisplayShowHomeEnabled(enable);
        }
    }

    @Override
    public void refreshTitle(String title) {
        if (tvTitle == null || title == null) {
            return;
        }
        tvTitle.setText(title);
    }

    @Override
    public Boolean getSearchViewEnable() {
        return false;
    }

    @Override
    public View getCustomActionBarView() {
        if (build == null || build.getTargetActivity() == null) return null;
        return build.getTargetActivity().getLayoutInflater().inflate(getCustomActionBarViewResId(), null);
    }

    @Override
    public View getCustomActionBarSearchView() {
        if (build == null || build.getTargetActivity() == null) return null;
        return build.getTargetActivity().getLayoutInflater().inflate(getCustomActionBarSearchViewResId(), null);
    }

    @Override
    public int getCustomActionBarViewResId() {
        return R.layout.action_title;
    }

    @Override
    public int getCustomActionBarSearchViewResId() {
        return R.layout.action_search;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (build == null || build.getTargetActivity() == null) return false;
        if (scanEnable()) {
            canClearSearchText = newText.length() != 0;
//            ivScan.setImageDrawable(build.getTargetActivity().getDrawable(canClearSearchText ? R.drawable.ic_search_close : R.drawable.scan));
        } else {
            canClearSearchText = true;
            ivScan.setVisibility(newText.length() == 0 ? View.GONE : View.VISIBLE);
        }

        return true;
    }

    @Override
    public boolean scanEnable() {
        return false;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (build == null || build.getTargetActivity() == null) return false;
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            for (int i = 0; i < menu.size(); i++) {
                MenuItem item = menu.getItem(i);
                setRightMenuTitleEnable(item);
            }
        }
        return build.getTargetActivity().onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (build == null || build.getTargetActivity() == null) return false;

        if (item.getItemId() == android.R.id.home) {
            onBack();
            onHomeUpClick();
            build.getTargetActivity().finish();
        }
//        else if (item.getItemId() == R.id.menu_save) {
//            onSave();
//        }
        return build.getTargetActivity().onOptionsItemSelected(item);
    }

    @Override
    public void onSave() {
        if (build == null || build.getTargetActivity() == null) return;
        build.getTargetActivity().finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (build == null || build.getTargetActivity() == null) return false;

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBack();
        }
        return build.getTargetActivity().onKeyDown(keyCode, event);
    }

    @Override
    public void onHomeUpClick() {

    }

    @Override
    public void onBack() {

    }

    @Override
    public void setRightMenuTitleEnable(MenuItem menuItem) {
        if (build == null || build.getTargetActivity() == null) return;
        if (menuItem != null) {
            SpannableString spanString = new SpannableString(menuItem.getTitle().toString());
//            spanString.setSpan(new ForegroundColorSpan(build.getTargetActivity().getResources().getColor(R.color.colorIOSBlue)), 0, spanString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            menuItem.setTitle(spanString);
        }
    }

    @Override
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_CREATE) {
            onTargetActivityCreate(source);
        } else if (event == Lifecycle.Event.ON_START) {
            onTargetActivityStart(source);
        } else if (event == Lifecycle.Event.ON_RESUME) {
            onTargetActivityResume(source);
        } else if (event == Lifecycle.Event.ON_PAUSE) {
            onTargetActivityPause(source);
        } else if (event == Lifecycle.Event.ON_STOP) {
            onTargetActivityStop(source);
        } else if (event == Lifecycle.Event.ON_DESTROY) {
            onTargetActivityDestroy(source);
        } else if (event == Lifecycle.Event.ON_ANY) {
            onTargetActivityAny(source);
        }
    }


}
