package com.app.common.baseactivity;

import android.app.Activity;
import android.os.Bundle;

/**
 * 窗口管理器，便于一次性退出所有Activity
 * 
 * 使用说明：
 * 
 * 1、所有activity继承BaseActivity,
 * 2、在需要一次性退出app的地方调用exit()方法
 * 3、在子activity的onCreate方法中，优先调用setConentView，然后调用super，然后分别实现findViews,getData,showConent方法，eg:
 * @Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_fancycoverflow_layout);
		super.onCreate(savedInstanceState);
	}
 * */
public abstract class BaseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init( );
		mManager.putActivity( this );
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mManager.removeActivity( this );
	}
	
	private void init( ){
		findViews( );
		getData( );
		showContent( );
	}

	public void exit() {
		mManager.exit();
	}
	
	public abstract void findViews( );
	public abstract void getData( );
	public abstract void showContent( );

	private ActivityManager mManager = ActivityManager.getActivityManager(this);
}
