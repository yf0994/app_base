package com.category.base.listener;

/**
 * Created by fengyin on 16-4-11.
 */
public interface RequestCallback<T> {
    void beforeRequest();

    void requestError(String msg);

    void requestSuccess(T t);

    void requestComplete();

    void connectNetworkFail(String msg);
}
