package com.nesp.sdk.android.utils;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/9/3 18:24
 **/
public abstract class SingleSelectionController<T> implements ISingleSelectionController<T> {
    private static final String TAG = "SingleSelectionController";

    private T selectItem;
    private OnSelectionListener<T> onSelectListener;
    private RecyclerView.Adapter<?> mAdapter;

    public SingleSelectionController(final T selectItem) {
        this.selectItem = selectItem;
    }

    @Override
    public boolean isSelect(final T item) {
        return itemId(selectItem).equals(itemId(item));
    }

    @Override
    public void select(final T item) {
        selectItem = item;
        if (onSelectListener != null) onSelectListener.onSelect(item);
        if (mAdapter != null) mAdapter.notifyDataSetChanged();
    }

    @Override
    public void clearSelect() {
        selectItem = null;
    }

    @Override
    @Nullable
    public T selectedItem() {
        return selectItem;
    }

    public SingleSelectionController<T> bindAdapter(final RecyclerView.Adapter<?> adapter) {
        mAdapter = adapter;
        return this;
    }

    public SingleSelectionController<T> setOnSelectListener(final OnSelectionListener<T> onSelectListener) {
        this.onSelectListener = onSelectListener;
        return this;
    }

}
