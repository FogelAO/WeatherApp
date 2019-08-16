package com.example.weatherapp.base;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity<V extends BaseView, P extends BasePresenter<V>> extends AppCompatActivity {
    private P presenter;

    @SuppressWarnings("unchecked")
    protected void attachPresenter() {
        presenter = (P) getLastNonConfigurationInstance();
        if (presenter == null)
            presenter = providePresenter();

        presenter.attachView((V) this);
    }

    @Override
    protected void onDestroy() {
        if (presenter != null)
            presenter.detachView();
        super.onDestroy();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    @NonNull
    protected abstract P providePresenter();

    protected final P getPresenter() {
        return presenter;
    }
}
