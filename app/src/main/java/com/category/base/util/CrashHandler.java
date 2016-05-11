package com.category.base.util;

import android.content.Context;
import android.os.*;
import android.os.Process;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fengyin on 16-5-11.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    private static final boolean DEBUG = true;

    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/Crash/log/";
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFFIX = ".trace";


    private Context mContext;

    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;

    private static CrashHandler sInstance;

    private CrashHandler(){

    }

    public static CrashHandler getInstance(){
        if(sInstance == null){
            synchronized (CrashHandler.class){
                if(sInstance == null){
                    sInstance = new CrashHandler();
                }
            }
        }

        return sInstance;
    }

    public void init(Context context){
        mContext = context.getApplicationContext();
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            dumpExceptionToSDCard(ex);
            uploadExceptionToServer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ex.printStackTrace();
        if(mDefaultCrashHandler != null){
            mDefaultCrashHandler.uncaughtException(thread, ex);
        } else {
            android.os.Process.killProcess(Process.myPid());
        }
    }

    private void dumpExceptionToSDCard(Throwable ex) throws IOException{
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return;
        }

        File dir = new File(PATH);
        if(!dir.exists()){
            dir.mkdirs();
        }

        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(current));
        File file = new File(PATH + FILE_NAME + time + FILE_NAME_SUFFIX);
        try{
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            pw.println(time);
            pw.println();
            ex.printStackTrace(pw);
            pw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void uploadExceptionToServer(){
        // TODO
    }
}
