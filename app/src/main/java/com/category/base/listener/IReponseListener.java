package com.category.base.listener;

import com.category.base.net.Result;

/**
 * Created by fengyin on 16-4-11.
 */
public interface IReponseListener<T> {
    void onSuccess(Result<T> t);

    void onFail(String errMsg);

    void beforeRequest();

    void afterRequest();

    void connectNetworkFail(String msg);
}
