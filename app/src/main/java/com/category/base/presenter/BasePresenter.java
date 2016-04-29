package com.category.base.presenter;

import android.os.Bundle;

import com.category.base.BaseView;

/**
 * Created by fengyin on 16-4-11.
 */
public interface BasePresenter<T extends BaseView> {
    void onResume();

    void onDestroy();
}
