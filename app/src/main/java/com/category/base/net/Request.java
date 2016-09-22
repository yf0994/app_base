package com.category.base.net;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.category.base.BaseApplication;
import com.category.base.listener.IReponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * Author:fengyin
 * Date: 16-3-9    11:34
 * Email:helloyf521@gmail.com
 * LastUpdateTime: 16-3-9
 * LastUpdateBy:helloyf521@gmail.com
 */
public class Request {
    private RequestQueue mQueue;
    private int mMethod = com.android.volley.Request.Method.POST;
    private Map<String, String> mParams;

    public Request(Context context) {
        mQueue = Volley.newRequestQueue(context);
    }

    /**
     * Set request method.
     *
     * @param method link @ com.android.volley.Request.Method.POST
     * @ om.android.volley.Request.Method.GET
     */
    public void setMethod(int method) {
        mMethod = method;
    }

    /**
     * Set request params. If you need't parmas, you can do nothing.
     *
     * @param params
     */
    public void setParams(Map<String, String> params) {
        mParams = params;
    }

    /**
     * Get JsonObject by url and params, and get reponse by listener.
     * If don't have params, set params null.
     *
     * @param url
     * @param listener The response listener.
     */
    public void getJsonResponse(String url, final IReponseListener listener) {
        StringRequest request = null;
        listener.beforeRequest();
        if (mMethod == com.android.volley.Request.Method.POST) {
            request = new StringRequest(com.android.volley.Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String msg) {
                    try {
                        listener.onSuccess(new JSONObject(msg));
                        listener.afterRequest();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    listener.onFail(new RequestError(volleyError));
                    listener.afterRequest();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return mParams;
                }
            };
        } else {
            StringBuffer sb = new StringBuffer();
            sb.append(url);
            if (mParams != null) {
                sb.append("?");
                Iterator uee = mParams.entrySet().iterator();

                while (uee.hasNext()) {
                    Map.Entry entry = (Map.Entry) uee.next();
                    sb.append(URLEncoder.encode((String) entry.getKey()));
                    sb.append('=');
                    sb.append(URLEncoder.encode((String) entry.getValue()));
                    sb.append('&');
                }
            }
            request = new StringRequest(com.android.volley.Request.Method.GET, sb.toString(), new Response.Listener<String>() {
                @Override
                public void onResponse(String msg) {
                    try {
                        listener.onSuccess(new JSONObject(msg));
                        listener.afterRequest();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    listener.onFail(new RequestError(volleyError));
                    listener.afterRequest();
                }
            }) {
            };
        }
        BaseApplication.getInstance().getReuqestQueue().add(request);
//        mQueue.add(request);
    }

    private String getParams(Map<String, String> parms) {
        StringBuffer sb = new StringBuffer();
        sb.append("?");
        Iterator uee = mParams.entrySet().iterator();

        while (uee.hasNext()) {
            Map.Entry entry = (Map.Entry) uee.next();
            sb.append(URLEncoder.encode((String) entry.getKey()));
            sb.append('=');
            sb.append(URLEncoder.encode((String) entry.getValue()));
            sb.append('&');
        }
        return sb.toString();
    }

    public void getReponseByGetMethod(String url, final IReponseListener listener) {
        StringRequest request = null;
        listener.beforeRequest();
        StringBuffer sb = new StringBuffer();
        sb.append(url);
        if (mParams != null) {
            sb.append(getParams(mParams));
        }
        request = new StringRequest(com.android.volley.Request.Method.GET, sb.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String msg) {
                try {
                    listener.onSuccess(new JSONObject(msg));
                    listener.afterRequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                listener.onFail(new RequestError(volleyError));
                listener.afterRequest();
            }
        }) {
        };
        BaseApplication.getInstance().getReuqestQueue().add(request);
    }

    public void getReponseByPostMethod(String url, final IReponseListener listener) {
        listener.beforeRequest();
        StringRequest request =
                request = new StringRequest(com.android.volley.Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String msg) {
                        try {
                            listener.onSuccess(new JSONObject(msg));
                            listener.afterRequest();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        listener.onFail(new RequestError(volleyError));
                        listener.afterRequest();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        return mParams;
                    }
                };
        BaseApplication.getInstance().getReuqestQueue().add(request);
    }
}