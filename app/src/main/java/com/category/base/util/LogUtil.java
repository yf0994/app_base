package com.category.base.util;

import android.text.TextUtils;
import android.util.Log;

import com.category.base.constant.Constants;
import com.category.base.exception.DebugException;

/**
 * Created by fengyin on 16-4-21.
 */
public class LogUtil {
    /**
     * Show message in logcat in info level.
     * @param msg
     */
    public static void Logi(String msg){
        if(Constants.DEBUG){
            if(TextUtils.isEmpty(msg)){
                throw new NullPointerException("The message is null!");
            }
            Log.i(Constants.DEBUG_TAG, msg);
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
    public static void Log(String msg, int level){
        if(Constants.DEBUG){
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
}
