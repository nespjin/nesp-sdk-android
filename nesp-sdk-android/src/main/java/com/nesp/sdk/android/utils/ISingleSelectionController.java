package com.nesp.sdk.android.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/9/3 18:22
 **/
public interface ISingleSelectionController<T> {

    boolean isSelect(T item);

    void select(T item);

    void clearSelect();

    @Nullable
    T selectedItem();

    @NonNull
    String itemId(T item);

}
