package com.category.base;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.category.base.util.CrashHandler;
import com.category.base.util.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by fengyin on 16-4-19.
 */
public class BaseApplication extends Application {

    private RequestQueue mRequestQueueInstance;

    private static BaseApplication sApplication;

    private AssetManager mAssetManager;
    private Resources mResources;
    private Resources.Theme mTheme;

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
    public AssetManager getAssets() {
        return mAssetManager == null ? super.getAssets() : mAssetManager;
    }

    @Override
    public Resources getResources() {
        return mResources == null ? super.getResources() : mResources;
    }

    @Override
    public Resources.Theme getTheme() {
        return mTheme == null ? super.getTheme() : mTheme;
    }

    public void loadResources(String dexPath){
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, dexPath);
            mAssetManager = assetManager;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        Resources superRes = super.getResources();
        superRes.getDisplayMetrics();
        superRes.getConfiguration();
        mResources = new Resources(mAssetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
        mTheme = mResources.newTheme();
        mTheme.setTo(super.getTheme());
    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        mRequestQueueInstance = Volley.newRequestQueue(getBaseContext());
        CrashHandler.getInstance().init(getBaseContext());
        Logger.getInstance().init(true);
    }

    public static Context getContext(){
        return sContext;
    }

    public RequestQueue getReuqestQueue(){
        return mRequestQueueInstance;
    }

}
