package com.mingy.android.crash;

import android.app.Application;

/**
 * 崩溃的Application，其它Application可以继承这个Application实现捕获Android的异常信息
 * 
 * */
public class CrashApplication extends Application {
	@Override
	public void onCreate( ) {
		super.onCreate( );
		
		initCrashException( );
	}
	
	/**
	 * 初始化异常捕获类
	 * 
	 * */
	private void initCrashException( ){
		if( mCrashException ){
			CrashHandler crashHandler = CrashHandler.getInstance( );  
	        crashHandler.init( this ); 
		}
	}
	
	public static final boolean mCrashException = true;
}
