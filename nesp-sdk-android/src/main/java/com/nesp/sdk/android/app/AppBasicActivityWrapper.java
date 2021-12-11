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

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LifecycleOwner;

/**
 * Author: <a href="mailto:jinzhaolu@numob.com">Jack Email:jinzhaolu@numob.com</a>
 * Time: Created 2020/8/7 11:27 AM
 * Description:
 **/
public abstract class AppBasicActivityWrapper implements SearchView.OnQueryTextListener {

    public abstract void wrap();

    public abstract void onTargetActivityCreate(LifecycleOwner source);

    public abstract void onTargetActivityStart(LifecycleOwner source);

    public abstract void onTargetActivityResume(LifecycleOwner source);

    public abstract void onTargetActivityPause(LifecycleOwner source);

    public abstract void onTargetActivityStop(LifecycleOwner source);

    public abstract void onTargetActivityDestroy(LifecycleOwner source);

    public abstract void onTargetActivityAny(LifecycleOwner source);

    public abstract void setShowBack(Boolean isShow);

    public abstract void setQuery(String queryText, boolean submit);

    public abstract void setQueryHint(String queryHint);

    public abstract void setOnSearchContentClickListener(View.OnClickListener onClickListener);

    public abstract void OnSearchScanClick(View view);

    public abstract void OnClearSearchTextClick(View view);

    public abstract void OnSearchCancelClick(View view);

    public abstract void showLeftAction();

    public abstract void hideLeftAction();

    public abstract void showLastTitle(String text);

    public abstract void showLeftAction(String text);

    public abstract void showLeftAction(String text, View.OnClickListener onClickListener);

    public abstract void showLeftAction(String text, Boolean isLastTitle, View.OnClickListener onClickListener);

    public abstract void setTitle(CharSequence title);

    public abstract void setTitle(String title);

    public abstract void setSubTitle(CharSequence charSequence);

    public abstract void setSubTitle(String subTitle);

    public abstract void setUnderLineEnable(Boolean enable);

    public abstract void setDisplayHomeAsUpEnabled(Boolean enable);

    public abstract void refreshTitle(String title);

    public abstract Boolean getSearchViewEnable();

    public abstract View getCustomActionBarView();

    public abstract View getCustomActionBarSearchView();

    public abstract int getCustomActionBarViewResId();

    public abstract int getCustomActionBarSearchViewResId();

    public abstract boolean onQueryTextSubmit(String query);

    public abstract boolean onQueryTextChange(String newText);

    public abstract boolean scanEnable();

    public abstract boolean onPrepareOptionsMenu(final Menu menu);

    public abstract boolean onOptionsItemSelected(@NonNull final MenuItem item);

    public abstract void onSave();

    public abstract boolean onKeyDown(final int keyCode, final KeyEvent event);

    public abstract void onHomeUpClick();

    public abstract void onBack();

    public abstract void setRightMenuTitleEnable(MenuItem menuItem);

}
