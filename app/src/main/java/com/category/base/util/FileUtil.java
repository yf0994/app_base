package com.category.base.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by fengyin on 16-4-26.
 */
public class FileUtil {
    private static void close(Closeable closeable){
        if(closeable == null) return;
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean makeDirsIfNeeded(File dir){
        return dir.exists() || dir.mkdirs();
    }

    public static long copyFile(File source, File destination){
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try{
            fis = new FileInputStream(source);
            fos = new FileOutputStream(destination);
            return copyFile(fis, fos);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close(fos);
            close(fis);
        }
        return 0;
    }


    public static long copyFile(FileInputStream in, FileOutputStream out){
        FileChannel source = null;
        FileChannel destination = null;
        try{
            source = in.getChannel();
            destination = out.getChannel();
            return source.transferTo(0, source.size(), destination);
        }catch (Exception e){
        }
        return 0;
    }
}
