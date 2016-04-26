package com.category.base.net;

import com.category.base.listener.IReponseListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by fengyin on 16-4-20.
 */
public class RequestManager {
    private static RequestManager sRequestManager;
    private OkHttpClient mOkHttpClient;

    private RequestManager(){
        mOkHttpClient = new OkHttpClient.Builder().
                connectTimeout(10, TimeUnit.SECONDS).
                writeTimeout(10, TimeUnit.SECONDS).
                readTimeout(30, TimeUnit.SECONDS).
                cookieJar(new CookieJar() {
                    private final HashMap<HttpUrl, List<Cookie>> mCookieStore = new HashMap<HttpUrl, List<Cookie>>();
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        mCookieStore.put(url, cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        return mCookieStore.get(url);
                    }
                }).
                retryOnConnectionFailure(true).
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

    public <T> void  getResponseByGetMethod(String url, final IReponseListener<T> listener, final Class<T> clazz, Params ... params){
        StringBuffer sb = new StringBuffer();
        sb.append(url);
        if(params != null){
            sb.append("?");
            for(int i = 0; i < params.length; i++){
                sb.append(params[i].getKey())
                        .append("=")
                        .append(params[i].getValue()).append("&");
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

    public <T> void getResponseByPostMethod(String url, final IReponseListener<T> listener,final Class<T> clazz, Params... params){
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        listener.beforeRequest();
        for(int i = 0; i < params.length; i++){
            formBodyBuilder.add(params[i].getKey(), params[i].getValue());
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
}
