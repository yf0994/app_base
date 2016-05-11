package com.category.base.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    public static void deleteDir(File dir){
        deleteFile(dir);
    }

    public static void deleteFile(File file){
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File f : files){
                deleteFile(f);
            }
        }
        file.delete();
    }

    public static void writeToFile(File file, byte [] data){
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(fos);
        }
    }

    public static byte[] readFromFile(File file){
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        try {
            fis = new FileInputStream(file);
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int length = 0;
            while((length = fis.read(buffer)) != -1){
                baos.write(buffer, 0, length);
            }

            return baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(fis);
            close(baos);
        }
        return null;
    }
}
