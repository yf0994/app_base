package com.category.base.presenter;

import android.os.Bundle;

import com.category.base.BaseView;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by fengyin on 16-4-11.
 */
public abstract class BasePresenterImpl<T extends BaseView> implements BasePresenter{
    protected Reference<T> mView;

    public BasePresenterImpl(T view){
        mView = new WeakReference<T>(view);
    }

    protected T getView(){
        return mView.get();
    }

    public boolean isViewAttached(){
        return mView != null && mView.get() != null;
    }

    private void detachView(){
        if(mView != null){
            mView.clear();
            mView = null;
        }
    }

    @Override
    public void onDestroy() {
        detachView();
    }
}
