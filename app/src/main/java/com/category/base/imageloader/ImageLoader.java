//<<<<<<< HEAD
////package com.category.base.imageloader;
////
////import android.content.Context;
////import android.graphics.Bitmap;
////import android.graphics.BitmapFactory;
////import android.util.LruCache;
////import android.widget.ImageView;
////
////import com.category.base.net.RequestManager;
////import com.category.base.util.Util;
////
////import java.io.IOException;
////import java.io.OutputStream;
////import java.lang.ref.WeakReference;
////
/////**
//// * Created by fengyin on 16-6-30.
//// */
////public class ImageLoader {
////
////    //This is use to memory cache.
////    private LruCache<String, WeakReference<Bitmap>> mCache;
////
////    private DiskLruCache mDiskLruCache;
////
////    private static ImageLoader sInstance;
////
////    private ImageLoader(Context context, long maxSize) throws IOException {
////        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
////        int cacheSize = maxMemory / 8;
////        mCache = new LruCache<String, WeakReference<Bitmap>>(cacheSize);
////        mDiskLruCache = DiskLruCache.open(Util.getDiskCacheDir(context, "images"), Util.getAppVersion(context), 1, maxSize);
////    }
////
////    public static ImageLoader getInstance(Context context){
////        if(sInstance == null){
////            synchronized (ImageLoader.class){
////                if(sInstance == null){
////                    try {
////                        sInstance = new ImageLoader(context, 10 * 1024 * 1024);
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
////                }
////            }
////        }
////        return sInstance;
////    }
////
////
////    public void loadImage(final String url, final ImageView view, final int defaultImageResId, final int errorImageResId, final int maxWidth, final int maxHeight) throws IOException {
////        final String hashUrl = Util.hashKey(url);
////        WeakReference<Bitmap> reference = mCache.get(hashUrl);
////        if(reference != null){
////            Bitmap bitmap = reference.get();
////            if(bitmap != null){
////                view.setImageBitmap(bitmap);
////                return;
////            }
////            mCache.remove(url);
////        }
////
////        final DiskLruCache.Editor editor = mDiskLruCache.edit(hashUrl);
////        if(editor != null){
////            Bitmap bitmap = BitmapFactory.decodeStream(editor.newInputStream(0));
////            mCache.put(hashUrl, new WeakReference<Bitmap>(bitmap));
////            return;
////        }
////
////        RequestManager.getInstance().loadImage(url, new RequestManager.ImageListener() {
////            @Override
////            public void onSuccess(byte[] data) {
////                Bitmap bitmap = Util.doParse(data, maxWidth, maxHeight);
////                if (bitmap == null) {
////                    view.setImageResource(defaultImageResId);
////                } else {
////                    view.setImageBitmap(bitmap);
////                    mCache.put(hashUrl, new WeakReference<Bitmap>(bitmap));
////                    try {
////                        DiskLruCache.Editor et = mDiskLruCache.edit(hashUrl);
////                        OutputStream os = editor.newOutputStream(0);
////                        os.write(data);
////                        os.close();
////                        et.commit();
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
////                }
////            }
////
////            @Override
////            public void onError() {
////                view.setImageResource(errorImageResId);
////            }
////        });
////    }
////}
//=======
//package com.category.base.imageloader;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.util.LruCache;
//import android.widget.ImageView;
//
//import com.category.base.net.RequestManager;
//import com.category.base.util.Util;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.lang.ref.WeakReference;
//
///**
// * Created by fengyin on 16-6-30.
// */
//public class ImageLoader {
//
//    //This is use to memory cache.
//    private LruCache<String, WeakReference<Bitmap>> mCache;
//
//    private DiskLruCache mDiskLruCache;
//
//    private static ImageLoader sInstance;
//
//    private ImageLoader(Context context, long maxSize) throws IOException {
//        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
//        int cacheSize = maxMemory / 8;
//        mCache = new LruCache<String, WeakReference<Bitmap>>(cacheSize);
//        mDiskLruCache = DiskLruCache.open(Util.getDiskCacheDir(context, "images"), Util.getAppVersion(context), 1, maxSize);
//    }
//
//    public static ImageLoader getInstance(Context context) {
//        if (sInstance == null) {
//            synchronized (ImageLoader.class) {
//                if (sInstance == null) {
//                    try {
//                        sInstance = new ImageLoader(context, 10 * 1024 * 1024);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//        return sInstance;
//    }
//
//    public void loadImage(final String url, final ImageView view, final int defaultImageResId, final int errorImageResId, final int maxWidth, final int maxHeight) throws IOException {
//        final String hashUrl = Util.hashKey(url);
//        WeakReference<Bitmap> reference = mCache.get(hashUrl);
//        if (reference != null) {
//            Bitmap bitmap = reference.get();
//            if (bitmap != null) {
//                view.setImageBitmap(bitmap);
//                return;
//            }
//            mCache.remove(url);
//        }
//
//        final DiskLruCache.Editor editor = mDiskLruCache.edit(hashUrl);
//        if (editor != null) {
//            Bitmap bitmap = BitmapFactory.decodeStream(editor.newInputStream(0));
//            mCache.put(hashUrl, new WeakReference<Bitmap>(bitmap));
//            return;
//        }
//
//        RequestManager.getInstance().loadImage(url, new RequestManager.ImageListener() {
//            @Override
//            public void onSuccess(byte[] data) {
//                Bitmap bitmap = Util.doParse(data, maxWidth, maxHeight);
//                if (bitmap == null) {
//                    view.setImageResource(defaultImageResId);
//                } else {
//                    view.setImageBitmap(bitmap);
//                    mCache.put(hashUrl, new WeakReference<Bitmap>(bitmap));
//                    try {
//                        DiskLruCache.Editor et = mDiskLruCache.edit(hashUrl);
//                        OutputStream os = editor.newOutputStream(0);
//                        os.write(data);
//                        os.close();
//                        et.commit();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onError() {
//                view.setImageResource(errorImageResId);
//            }
//        });
//    }
//}
//>>>>>>> 630e131c84ba3578b37ba415528eaf7f227d3425
