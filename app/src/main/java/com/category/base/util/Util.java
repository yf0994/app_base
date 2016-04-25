package com.category.base.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.category.base.constant.Constants;
import com.category.base.exception.DebugException;

import java.security.MessageDigest;
import java.util.regex.Pattern;

/**
 * This is a Util class.
 * Created by fengyin on 15-12-30.
 */
public class Util {
    /**
     * Judge emial is vaild.
     * @param email
     * @return true is valid or is invaild
     */
    public static boolean isValidEmail(String email){
        boolean isValidEmail = false;
        Pattern pattern = Pattern.compile("^[a-z0-9]+([._\\\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
        isValidEmail = pattern.matcher(email).matches();
        return isValidEmail;
    }


    /**
     * Judge number is phone number.
     * @param phone
     * @return
     */
    public static boolean isValidPhone(String phone){
        boolean isValidPhone = false;
        Pattern pattern = Pattern.compile("^((13[0-9])|(17[^4,\\D])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        isValidPhone = pattern.matcher(phone).matches();
        return isValidPhone;
    }

    /**
     * Generate md5 message by passowrd
     * @param password
     * @return
     */
    public static String getPasswordMD5(String password){
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

        try {
            byte[] btInput = password.getBytes();
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(btInput);
            byte[] md = digest.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    /**
     * Close Soft input method.
     * @param binder
     * @param context
     */
    public static void closeInputmethod(IBinder binder, Context context){
        InputMethodManager inputMethodManager = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isActive()){
            inputMethodManager.hideSoftInputFromWindow(binder, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * Get NavigationBar height.
     * @param activity
     * @return
     */
    public static int getNavigationBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height",
                "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }



}
