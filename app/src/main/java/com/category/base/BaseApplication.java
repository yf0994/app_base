package com.category.base;

import android.app.Application;
import android.content.Context;

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

    private static Context sContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        mRequestQueueInstance = Volley.newRequestQueue(getBaseContext());
    }

    public static Context getContext(){
        return sContext;
    }

    public RequestQueue getReuqestQueue(){
        return mRequestQueueInstance;
    }

}
