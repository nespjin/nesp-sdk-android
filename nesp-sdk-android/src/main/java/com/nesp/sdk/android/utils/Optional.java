package com.nesp.sdk.android.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nesp.sdk.android.utils.function.Function;

import java.util.Objects;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/7/27 12:37
 * Optional API level 24，N
 **/
public final class Optional<T> {

    public static final Optional<?> EMPTY = new Optional<>();

    @Nullable
    private final T value;

    public static <T> Optional<T> of(@NonNull T value) {
        Objects.requireNonNull(value);
        return new Optional<>(value);
    }

    public static <T> Optional<T> ofNullable(@Nullable T value) {
        return new Optional<>(value);
    }

    public Optional<T> ifNonNull(Function<T, T> function) {
        if (isPresent()) {
            return Optional.ofNullable(function.apply(value));
        }
        return empty();
    }

    public <R> Optional<R> map(@NonNull Mapper<R, T> mapper) {
        if (!isPresent()) {
            return empty();
        } else {
            return ofNullable(mapper.map(value));
        }
    }

    public T orElse(T other) {
        return value == null ? other : value;
    }

    @NonNull
    public T get() {
        Objects.requireNonNull(value);
        return value;
    }

    @Nullable
    public T getOrNull() {
        return value;
    }

    public String orString(String other) {
        return value == null ? other : String.valueOf(value);
    }

    public String orEmptyString() {
        return value == null ? "" : String.valueOf(value);
    }

    public static <T> Optional<T> empty() {
        @SuppressWarnings("unchecked")
        Optional<T> t = (Optional<T>) EMPTY;
        return t;
    }


    public boolean isPresent() {
        return value != null;
    }

    private Optional() {
        this(null);
    }

    private Optional(@Nullable T value) {
        this.value = value;
    }


}
