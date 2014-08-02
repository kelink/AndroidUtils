package com.app.common.baseactivity;

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
	 * 使用taskmap加入activity
	 * 
	 */
	public final void putActivity( Activity activity ) {
		mTaskMap.put( activity.toString( ), new SoftReference<Activity>( activity ) );
	}

	/**
	 * 使用taskmap加入activity
	 * 
	 */
	public final void removeActivity( Activity activity ) {
		mTaskMap.remove( activity.toString( ) );
	}

	/**
	 * 清除应用的task栈，如果程序正常运行这会使得程序完全退出
	 * 
	 */
	public final void exit( ) {
		for (Iterator<Entry<String, SoftReference<Activity>>> iterator = mTaskMap
				.entrySet().iterator(); iterator.hasNext();) {
			SoftReference<Activity> activityReference = iterator.next()
					.getValue();
			Activity activity = activityReference.get();
			if (activity != null) {
				activity.finish();
			}
		}
		mTaskMap.clear();
	}

	private static ActivityManager mActivityManager = null;
	// task map，用于记录activity栈，方便应用程序（这里为了不影响系统回收activity，所以用软引用）
	private final HashMap<String, SoftReference<Activity>> mTaskMap = new HashMap<String, SoftReference<Activity>>();
}
