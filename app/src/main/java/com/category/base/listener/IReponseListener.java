package com.category.base.listener;

import com.category.base.net.RequestError;

/**
 * Created by fengyin on 16-4-11.
 */
public interface IReponseListener<T> {
    void onSuccess(T t);

    void onFail(RequestError requestError);

    void beforeRequest();

    void afterRequest();

    void connectNetworkFail(String msg);
}
