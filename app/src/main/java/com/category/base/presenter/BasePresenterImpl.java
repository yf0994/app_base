package com.category.base.presenter;

import android.os.Bundle;

import com.category.base.BaseView;

/**
 * Created by fengyin on 16-4-11.
 */
public abstract class BasePresenterImpl<T extends BaseView> implements BasePresenter{
    protected T mView;

    public BasePresenterImpl(T view){
        mView = view;
    }

}
