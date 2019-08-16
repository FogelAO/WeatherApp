package com.example.weatherapp.base;

import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<V extends BaseView> {
    private WeakReference<V> viewRef;

    final void attachView(V view) {
        viewRef = new WeakReference<>(view);
        onAttach();
    }

    final void detachView() {
        onDetach();
        viewRef.clear();
    }

    protected void onAttach() {
    }

    protected void onDetach() {
    }

    @Nullable
    public final V getView() {
        return viewRef.get();
    }
}
