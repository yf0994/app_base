package com.category.base.net;

/**
 * Created by fengyin on 16-4-11.
 */
public interface IReponseListener<T> {
    void onSuccess(T t);

    void onFail(String msg);

    void beforeRequest();

    void afterRequest();
}
