package com.hatcloud.genealogy.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by Jeff on 15/4/28.
 * 为了全局获取Context，我自定义了一个Application
 * 只需调用Application.getContext()即可获取到Context
 */
public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
