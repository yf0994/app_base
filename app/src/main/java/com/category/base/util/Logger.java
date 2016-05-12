package com.category.base.util;

import android.text.TextUtils;
import android.util.Log;

import com.category.base.BaseApplication;
import com.category.base.constant.Constants;
import com.category.base.exception.DebugException;

/**
 * Created by fengyin on 16-4-21.
 */
public class Logger {

    private static Logger sLogger;

    private boolean mDebug = true;

    private Logger(){

    }

    public static Logger getInstance(){
        if(sLogger == null){
            synchronized (Logger.class){
                if(sLogger == null){
                    sLogger = new Logger();
                }
            }
        }
        return sLogger;
    }

    /**
     * Init Logger
     * @param isDebug true is in debug mode.
     */
    public void init(boolean isDebug){
        mDebug = isDebug;
    }

    /**
     * Show message in logcat in info level.
     * @param msg
     */
    public void Logi(String msg){
        if(mDebug){
            if(TextUtils.isEmpty(msg)){
                throw new NullPointerException("The message is null!");
            }
            Log.i(Constants.DEBUG_TAG, msg);
        }
    }

    /**
     * Show message in logcat by consume tag in info level.
     * @param tag consume tag.
     * @param msg
     */
    public void Logi(String tag, String msg){
        if(mDebug){
            if(TextUtils.isEmpty(msg)){
                throw new NullPointerException("The message is null!");
            }
            Log.i(tag == null ? Constants.DEBUG_TAG :tag, msg);
        }
    }


    /**
     * Show message in logcat.
     * @param msg The showing message.
     * @param level @Constants.DEBUG_LEVEL_INFO,
     *              @Constants.DEBUG_LEVEL_ERROR,
     *              @Constants.DEBUG_LEVEL_WARNING,
     *              @Constants.DEBUG_LEVEL_VERBOSE,
     *              @Constants.DEBUG_LEVEL_DEBUG,
     */
    public void Log(String msg, int level){
        if(mDebug){
            if(TextUtils.isEmpty(msg)){
                throw new NullPointerException("The message is null!");
            }
            if(level < Constants.DEBUG_LEVEL_DEBUG && level > Constants.DEBUG_LEVEL_ERROR){
                throw new DebugException("Out of debug level!");
            }
            switch(level){
                case Constants.DEBUG_LEVEL_DEBUG:
                    Log.d(Constants.DEBUG_TAG, msg);
                    return;
                case Constants.DEBUG_LEVEL_INFO:
                    Log.i(Constants.DEBUG_TAG, msg);
                    return;
                case Constants.DEBUG_LEVEL_WARNING:
                    Log.w(Constants.DEBUG_TAG, msg);
                    return;
                case Constants.DEBUG_LEVEL_ERROR:
                    Log.e(Constants.DEBUG_TAG, msg);
                    return;
                case Constants.DEBUG_LEVEL_VERBOSE:
                    Log.v(Constants.DEBUG_TAG, msg);
                    return;
            }

        }
    }

    /**
     * Show message in logcat by consume tag and msg.
     * @param tag The consume tag.
     * @param msg The showing message.
     * @param level @Constants.DEBUG_LEVEL_INFO,
     *              @Constants.DEBUG_LEVEL_ERROR,
     *              @Constants.DEBUG_LEVEL_WARNING,
     *              @Constants.DEBUG_LEVEL_VERBOSE,
     *              @Constants.DEBUG_LEVEL_DEBUG,
     */
    public void Log(String tag, String msg, int level){
        if(mDebug){
            if(TextUtils.isEmpty(msg)){
                throw new NullPointerException("The message is null!");
            }
            if(level < Constants.DEBUG_LEVEL_DEBUG && level > Constants.DEBUG_LEVEL_ERROR){
                throw new DebugException("Out of debug level!");
            }

            switch(level){
                case Constants.DEBUG_LEVEL_DEBUG:
                    Log.d(tag == null ? Constants.DEBUG_TAG : tag, msg);
                    return;
                case Constants.DEBUG_LEVEL_INFO:
                    Log.i(tag == null ? Constants.DEBUG_TAG : tag, msg);
                    return;
                case Constants.DEBUG_LEVEL_WARNING:
                    Log.w(tag == null ? Constants.DEBUG_TAG : tag, msg);
                    return;
                case Constants.DEBUG_LEVEL_ERROR:
                    Log.e(tag == null ? Constants.DEBUG_TAG : tag, msg);
                    return;
                case Constants.DEBUG_LEVEL_VERBOSE:
                    Log.v(tag == null ? Constants.DEBUG_TAG : tag, msg);
                    return;
            }

        }
    }

}
