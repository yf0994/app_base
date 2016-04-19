package com.category.base.annotation;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by fengyin on 16-4-13.
 */
public class DynamicHandler implements InvocationHandler {
    private WeakReference<Object> mHandlerRef;
    private HashMap<String, Method> mMethodMap = new HashMap<String, Method>();

    public DynamicHandler(Object obj){
        mHandlerRef = new WeakReference<Object>(obj);
    }

    public void addMethod(String name, Method method){
        mMethodMap.put(name, method);
    }

    public Object getHandler(){
        return mHandlerRef.get();
    }

    public void setHandler(Object obj){
        mHandlerRef = new WeakReference<Object>(obj);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object obj = mHandlerRef.get();
        if(obj != null){
            String methodName = method.getName();
            method = mMethodMap.get(methodName);
            if(method != null){
                return method.invoke(obj, args);
            }
        }

        return null;
    }
}
