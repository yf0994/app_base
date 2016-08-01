package com.category.base;

/**
 * Author:fengyin
 * Date: 16-3-18    09:31
 * Email:594601408@qq.com
 * LastUpdateTime: 16-3-18
 * LastUpdateBy:594601408@qq.com
 */
public interface BaseView {

    void toast(String msg);

    void showProgress();

    void hideProgress();

    void showProgress(String msg);

    void toastNetworkError(String msg);

}
