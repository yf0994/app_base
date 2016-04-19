package com.category.base.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.category.base.annotation.ContentView;
import com.category.base.annotation.DynamicHandler;
import com.category.base.annotation.EventBase;
import com.category.base.annotation.ViewInject;
import com.category.base.util.Util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * Author:fengyin
 * Date: 16-3-18    09:31
 * Email:594601408@qq.com
 * LastUpdateTime: 16-3-18
 * LastUpdateBy:594601408@qq.com
 */
public abstract class BaseInjectActivity extends AppCompatActivity {
    private int mContentViewId;
    private int mToolbarTitle;
    private int mToolbarId;
    protected Toolbar mToolbar;
    protected FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getClass().isAnnotationPresent(ContentView.class)){
            ContentView annotation = getClass().getAnnotation(ContentView.class);
            mContentViewId = annotation.value();
            mToolbarTitle = annotation.toolbarTitle();
            mToolbarId = annotation.toolbarId();
            setContentView(mContentViewId);
            if(mToolbarId != -1){
                mToolbar = (Toolbar)findViewById(mToolbarId);
            }
        }

        mFragmentManager = getSupportFragmentManager();

        autoInjectAllField();
        autoInjectEvent();
        initToolbar();
        registerListener();

    }

    protected abstract void registerListener();

    protected void initToolbar(){
        if(mToolbar != null && mToolbarTitle != -1){
            mToolbar.setTitle(mToolbarTitle);
        }
    }

    private void autoInjectAllField(){
        Field[] fields = this.getClass().getDeclaredFields();
        for(Field field : fields){
            if(field.isAnnotationPresent(ViewInject.class)){
                ViewInject inject = field.getAnnotation(ViewInject.class);
                int id = inject.value();
                if(id > 0){
                    field.setAccessible(true);
                    try {
                        field.set(this, this.findViewById(id));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void autoInjectEvent(){
        Class<? extends Activity> clazz = this.getClass();
        Method[] methods = clazz.getMethods();
        for(Method method : methods){
            Annotation[] annotations = method.getAnnotations();
            for(Annotation annotation : annotations){
                Class<? extends Annotation> annotationType = annotation.annotationType();
                EventBase eventBaseAnnotation = annotationType.getAnnotation(EventBase.class);
                if(eventBaseAnnotation != null){
                    String listenerSetter = eventBaseAnnotation.listenerSetter();
                    Class<?> listenerType = eventBaseAnnotation.listenerType();
                    String methodName = eventBaseAnnotation.methodName();

                    try {
                        Method aMethod = annotationType.getDeclaredMethod("value");
                        int [] viewIds = (int[])aMethod.invoke(annotation, null);

                        DynamicHandler handler = new DynamicHandler(this);
                        handler.addMethod(methodName, method);
                        Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(),
                                new Class<?>[]{listenerType}, handler);

                        for(int viewId : viewIds){
                            View view = this.findViewById(viewId);
                            Method setEventListenerMethod = view.getClass().getMethod(listenerSetter, listenerType);
                            setEventListenerMethod.invoke(view, listener);
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Show message in logcat in info level.
     * @param msg
     */
    public void Logi(String msg){
        Util.Logi(msg);
    }

    /**
     * Show message in logcat.
     * @param msg The showing message.
     * @param level @Constants.DEBUG_LEVEL_INFO,
     *              @Constants.DEBUG_LEVEL_ERROR,
     *              @Constants.DEBUG_LEVEL_WARNING,
     *              @Constants.DEBUG_LEVEL_VERBOSE,
     *              @Constants.DEBUG_LEVEL_DEBUG,
     */
    public void Log(String msg, int level){
        Util.Log(msg, level);
    }
}
