package com.hatcloud.genealogy.activity;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeff on 15/4/28.
 */
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 销毁所有活动，一般在用户点击退出的时候调用
     */
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
