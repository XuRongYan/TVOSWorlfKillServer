package com.rongyan.tvosworlfkillserver.base;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * Activity堆栈的管理类
 * Created by XRY on 2016/8/31.
 */
public class BaseAppManager {
    private static final String TAG = BaseAppManager.class.getSimpleName();

    private static BaseAppManager instance = null;

    private static List<Activity> mActivities = new LinkedList<>();

    private BaseAppManager() {

    }

    public static BaseAppManager getInstance() {
        if (instance == null) {
            synchronized (BaseAppManager.class) {
                if (instance == null) {
                    instance = new BaseAppManager();
                }
            }
        }
        return instance;
    }

    public int size() {
        return mActivities.size();
    }

    public synchronized Activity getTopActivity() {
        return size() > 0 ? mActivities.get(size() - 1) : null;
    }

    public synchronized void addActivity(Activity activity) {
        mActivities.add(activity);
    }

    public synchronized void removeActivity(Activity activity) {
        if (mActivities.contains(activity)) {
            mActivities.remove(activity);
        }
    }

    public synchronized void clearAll() {
        for (int i = mActivities.size() - 1; i > -1; i--) {
            Activity aty = mActivities.get(i);
            removeActivity(aty);
            aty.finish();
            i = mActivities.size();
        }
    }

    /**
     * 将栈顶的activity去掉只留最后一个activity
     */
    public synchronized void clearTop() {
        for (int i = mActivities.size() - 2; i > -1; i--) {
            Activity aty = mActivities.get(i);
            removeActivity(aty);
            aty.finish();
            i = mActivities.size() - 1;
        }
    }


}

