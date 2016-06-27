package com.category.base.net;

import com.category.base.BaseApplication;
import com.category.base.listener.IReponseListener;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by fengyin on 16-4-20.
 */
public class RequestManager {
    private static RequestManager sRequestManager;
    private OkHttpClient mOkHttpClient;

    private RequestManager(){
        Cache cache = new Cache(new File(BaseApplication.getContext().getCacheDir(), "HttpCache"), 1024 * 1024 * 16);
        mOkHttpClient = new OkHttpClient.Builder().
                connectTimeout(10, TimeUnit.SECONDS).
                writeTimeout(10, TimeUnit.SECONDS).
                readTimeout(30, TimeUnit.SECONDS).
                retryOnConnectionFailure(true).cache(cache).
                cookieJar(new CookieJar() {
                    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<HttpUrl, List<Cookie>>();
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url, cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url);
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                }).
                build();
    }

    public static RequestManager getInstance(){
        if(sRequestManager == null){
            synchronized (RequestManager.class){
                if(sRequestManager == null){
                    sRequestManager = new RequestManager();
                }
            }
        }
        return sRequestManager;
    }

    public <T> void  getResponseByGetMethod(String url, final IReponseListener<T> listener, final Class<T> clazz, Map<String, String> params){
        StringBuffer sb = new StringBuffer();
        sb.append(url);
        if(params != null){
            Set<Map.Entry<String, String>> set =  params.entrySet();
            Iterator<Map.Entry<String, String>> iterator = set.iterator();
            sb.append("?");
            while(iterator.hasNext()){
                Map.Entry<String, String> entry = iterator.next();
                sb.append(entry.getKey()).
                        append("=").append(entry.getValue()).append("&");
            }
        }

        Request request = new Request.Builder().url(sb.toString()).build();
        Call call = mOkHttpClient.newCall(request);
        listener.beforeRequest();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.afterRequest();
                listener.onFail(e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                listener.afterRequest();
                Gson gson = new Gson();
                T t = gson.fromJson(result, clazz);
                listener.onSuccess(t);
            }
        });
    }

    public <T> void getResponseByPostMethod(String url, final IReponseListener<T> listener,final Class<T> clazz, Map<String, String> params){
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        listener.beforeRequest();
        if(params != null){
            Set<Map.Entry<String, String>> set =  params.entrySet();
            Iterator<Map.Entry<String, String>> iterator = set.iterator();
            while(iterator.hasNext()){
                Map.Entry<String, String> entry = iterator.next();
                formBodyBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        FormBody formBody = formBodyBuilder.build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFail(e.getLocalizedMessage());
                listener.afterRequest();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                listener.afterRequest();
                String result = response.body().string();
                Gson gson = new Gson();
                T t = gson.fromJson(result, clazz);
                listener.onSuccess(t);
            }
        });
    }

    public <T> void uploadFileByPostMethod(String url, final IReponseListener<T> listener,
                                      final Class<T> clazz, Map<String, File> fileParams ,Map<String, String> stringParams){
        Request request = buildMutlipartFormRequest(url, fileParams, stringParams);
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFail(e.getLocalizedMessage());
                listener.afterRequest();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                listener.afterRequest();
                String result = response.body().string();
                Gson gson = new Gson();
                T t = gson.fromJson(result, clazz);
                listener.onSuccess(t);
            }
        });
    }


    private Request buildMutlipartFormRequest(String url, Map<String, File> fileParams, Map<String, String> stringParams){
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if(stringParams != null && stringParams.size() > 0){
            for (Map.Entry<String, String> entry : stringParams.entrySet()) {
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
                        RequestBody.create(null, entry.getValue()));
            }
        }


        if(fileParams != null && fileParams.size() > 0){
            RequestBody fileBody = null;
            for(Map.Entry<String, File> entry : fileParams.entrySet()){
                File file = entry.getValue();
                fileBody = RequestBody.create(MediaType.parse(getMimeType(file.getName())), file);
                builder.addPart(Headers.of("Content-Disposition",
                                "form-data; name=\"" + entry.getKey() + "\"; filename=\"" + file.getName() + "\""),
                        fileBody);
            }
        }

        RequestBody requestBody = builder.build();
        return new Request.Builder().url(url).post(requestBody).build();
    }

    private String getMimeType(String path){
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        return contentTypeFor == null ? "application/octet-stream" : contentTypeFor;
    }
}
