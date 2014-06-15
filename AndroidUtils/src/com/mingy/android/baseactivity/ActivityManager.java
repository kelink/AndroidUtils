package com.mingy.android.baseactivity;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Context;

public class ActivityManager {
    private ActivityManager( Context context ) {
        // disable other class new object
    }
    
    public static ActivityManager getActivityManager( Context context ) {
        if (mActivityManager == null) {
            mActivityManager = new ActivityManager( context );
        }
        
        return mActivityManager;
    }

    /**
     * 往应用task map加入activity
     */
    public final void putActivity( Activity activity ) {
        mActivityTaskMap.put( activity.toString( ), new SoftReference<Activity>( activity ) );
    }

    /**
     * 往应用task map加入activity
     */
    public final void removeActivity( Activity activity ) {
        mActivityTaskMap.remove( activity.toString( ) );
    }

    /**
     * 清除应用的task栈，如果程序正常运行这会导致应用退回到桌面
     */
    public final void exit( ) {
        for (Iterator<Entry<String, SoftReference<Activity>>> iterator = mActivityTaskMap.entrySet().iterator(); iterator.hasNext();) {
            SoftReference<Activity> activityReference = iterator.next().getValue();
            Activity activity = activityReference.get();
            if (activity != null) {
                activity.finish( );
            }
        }
        
        mActivityTaskMap.clear( );
    }

    private static ActivityManager mActivityManager = null;
    private final HashMap<String, SoftReference<Activity>> mActivityTaskMap = new HashMap<String, SoftReference<Activity>>();
}
