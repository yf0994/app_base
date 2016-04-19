package com.category.base.net;

/**
 * Created by fengyin on 16-4-11.
 */
public interface RequestCallback<T> {
    void beforeRequest();

    void requestError(String msg);

    void requestSuccess(T t);

    void requestComplete();
}
