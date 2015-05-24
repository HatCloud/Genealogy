package com.hatcloud.genealogy.activity;

import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;

import com.hatcloud.genealogy.util.LogUtil;

/**
 * Created by Jeff on 15/4/28.
 * 这是本项目中所有Activity的基类
 * 通过该类能在调试过程中知晓现在在那哪一个活动
 */
public class BaseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //打印现在所处的活动的类名
        LogUtil.d("BaseActivity", getClass().getSimpleName());

        //使所有继承此类的活动在创建的时候就加入ActivityCollector中，方便统一管理
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //在活动自己销毁的时候，从ActivityCollector中移除
        ActivityCollector.removeActivity(this);
    }

}
