package com.category.base;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by fengyin on 16-4-19.
 */
public class BaseApplication extends Application {

    private RequestQueue mRequestQueueInstance;

    private static BaseApplication sApplication;

    public static BaseApplication getApplicationBase(){
        synchronized (BaseApplication.class) {
            if (sApplication == null) {
                sApplication = new BaseApplication();
            }
        }
        return sApplication;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mRequestQueueInstance = Volley.newRequestQueue(getBaseContext());
    }

    public RequestQueue getReuqestQueue(){
        return mRequestQueueInstance;
    }
}
