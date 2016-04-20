package com.category.base.net;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.*;
import okhttp3.Request;

/**
 * Created by fengyin on 16-4-20.
 */
public class RequestManager {
    private static RequestManager sRequestManager;
    private OkHttpClient mOkHttpClient;

    private Gson mGson;

    private RequestManager(){
        mOkHttpClient = new OkHttpClient.Builder().
                connectTimeout(30, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
        mGson = new Gson();
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

    public void getResponseByGetMethod(String url, final IReponseListener listener, Params ... params){
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
                listener.afterRequest();
                listener.onSuccess(response.body().toString());
            }
        });
    }

    public void getResponseByPostMethod(String url, final IReponseListener listener, Params ... params){
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
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                listener.onSuccess(response.body().toString());
            }
        });
    }
}
