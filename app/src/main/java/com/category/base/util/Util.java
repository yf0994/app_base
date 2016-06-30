package com.category.base.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.category.base.constant.Constants;
import com.category.base.exception.DebugException;

import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.concurrent.ThreadFactory;
import java.util.regex.Pattern;

import okio.OkBuffer;
import okio.Source;

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





    public static void checkOffsetAndCount(long arrayLength, long offset, long count) {
        if ((offset | count) < 0 || offset > arrayLength || arrayLength - offset < count) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }


    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Deletes the contents of {@code dir}. Throws an IOException if any file
     * could not be deleted, or if {@code dir} is not a readable directory.
     */
    public static void deleteContents(File dir) throws IOException {
        File[] files = dir.listFiles();
        if (files == null) {
            throw new IOException("not a readable directory: " + dir);
        }
        for (File file : files) {
            if (file.isDirectory()) {
                deleteContents(file);
            }
            if (!file.delete()) {
                throw new IOException("failed to delete file: " + file);
            }
        }
    }

    /**
     * Fills 'dst' with bytes from 'in', throwing EOFException if insufficient bytes are available.
     */
    public static void readFully(InputStream in, byte[] dst) throws IOException {
        readFully(in, dst, 0, dst.length);
    }

    /**
     * Reads exactly 'byteCount' bytes from 'in' (into 'dst' at offset 'offset'), and throws
     * EOFException if insufficient bytes are available.
     *
     * Used to implement {@link java.io.DataInputStream#readFully(byte[], int, int)}.
     */
    public static void readFully(InputStream in, byte[] dst, int offset, int byteCount)
            throws IOException {
        if (byteCount == 0) {
            return;
        }
        if (in == null) {
            throw new NullPointerException("in == null");
        }
        if (dst == null) {
            throw new NullPointerException("dst == null");
        }
        checkOffsetAndCount(dst.length, offset, byteCount);
        while (byteCount > 0) {
            int bytesRead = in.read(dst, offset, byteCount);
            if (bytesRead < 0) {
                throw new EOFException();
            }
            offset += bytesRead;
            byteCount -= bytesRead;
        }
    }

    /** Returns the remainder of 'source' as a buffer, closing it when done. */
    public static OkBuffer readFully(Source source) throws IOException {
        OkBuffer result = new OkBuffer();
        while (source.read(result, 2048) != -1) {
        }
        source.close();
        return result;
    }


    public static ThreadFactory threadFactory(final String name, final boolean daemon) {
        return new ThreadFactory() {
            @Override public Thread newThread(Runnable runnable) {
                Thread result = new Thread(runnable, name);
                result.setDaemon(daemon);
                return result;
            }
        };
    }

    public static File getDiskCacheDir(Context context, String uniqueName){
        File tempDir;
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                ||!Environment.isExternalStorageRemovable()){
            tempDir = context.getExternalCacheDir();
        } else {
            tempDir = context.getCacheDir();
        }
        return new File(tempDir, uniqueName);
    }

    public static String hashKey(String key){
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

        try {
            byte[] btInput = key.getBytes();
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
     * Scales one side of a rectangle to fit aspect ratio.
     *
     * @param maxPrimary Maximum size of the primary dimension (i.e. width for
     *        max width), or zero to maintain aspect ratio with secondary
     *        dimension
     * @param maxSecondary Maximum size of the secondary dimension, or zero to
     *        maintain aspect ratio with primary dimension
     * @param actualPrimary Actual size of the primary dimension
     * @param actualSecondary Actual size of the secondary dimension
     */
    private static int getResizedDimension(int maxPrimary, int maxSecondary, int actualPrimary,
                                           int actualSecondary) {
        // If no dominant value at all, just return the actual.
        if (maxPrimary == 0 && maxSecondary == 0) {
            return actualPrimary;
        }

        // If primary is unspecified, scale primary to match secondary's scaling ratio.
        if (maxPrimary == 0) {
            double ratio = (double) maxSecondary / (double) actualSecondary;
            return (int) (actualPrimary * ratio);
        }

        if (maxSecondary == 0) {
            return maxPrimary;
        }

        double ratio = (double) actualSecondary / (double) actualPrimary;
        int resized = maxPrimary;
        if (resized * ratio > maxSecondary) {
            resized = (int) (maxSecondary / ratio);
        }
        return resized;
    }

    /**
     * Returns the largest power-of-two divisor for use in downscaling a bitmap
     * that will not result in the scaling past the desired dimensions.
     *
     * @param actualWidth Actual width of the bitmap
     * @param actualHeight Actual height of the bitmap
     * @param desiredWidth Desired width of the bitmap
     * @param desiredHeight Desired height of the bitmap
     */
    // Visible for testing.
    static int findBestSampleSize(
            int actualWidth, int actualHeight, int desiredWidth, int desiredHeight) {
        double wr = (double) actualWidth / desiredWidth;
        double hr = (double) actualHeight / desiredHeight;
        double ratio = Math.min(wr, hr);
        float n = 1.0f;
        while ((n * 2) <= ratio) {
            n *= 2;
        }

        return (int) n;
    }

    public static Bitmap doParse(byte[] data, int maxWidth, int maxHeight){
        Bitmap bitmap = null;
        BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
        if(maxWidth == 0 && maxHeight == 0){
            decodeOptions.inPreferredConfig = Bitmap.Config.RGB_565;
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, decodeOptions);
        } else {
            decodeOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(data, 0, data.length, decodeOptions);
            int actualWidth = decodeOptions.outWidth;
            int actualHeight = decodeOptions.outHeight;

            int desiredWidth = getResizedDimension(maxWidth, maxHeight, actualWidth, actualHeight);
            int desireHeight = getResizedDimension(maxHeight, maxWidth, actualHeight, actualWidth);
            decodeOptions.inJustDecodeBounds = false;

            decodeOptions.inSampleSize = findBestSampleSize(actualWidth, actualHeight, desiredWidth, desireHeight);
            Bitmap tempBitmap =
                    BitmapFactory.decodeByteArray(data, 0, data.length, decodeOptions);

            if(tempBitmap != null && (tempBitmap.getWidth() > desiredWidth
                    || tempBitmap.getHeight() > desireHeight)){
                bitmap = Bitmap.createScaledBitmap(tempBitmap, desiredWidth, desireHeight, true);
                tempBitmap.recycle();
            } else {
                bitmap = tempBitmap;
            }
        }

        return bitmap;
    }

    public static int getAppVersion(Context context){
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
