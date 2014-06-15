package com.mingy.android.baseactivity;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Activity»ùÀà
 * 
 * */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityManager.putActivity( this );
    }
    
    @Override
    protected void onPause() {
        super.onPause( );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityManager.removeActivity( this );
    }

    public void exit() {
        mActivityManager.exit( );
    }
    
    public void startActivityEx(Activity activity, Class<?> cls, BasicNameValuePair...name){
        Intent intent=new Intent();
        intent.setClass(activity,cls);
        for(int i=0;i<name.length;i++){
            intent.putExtra(name[i].getName(), name[i].getValue());
        }
        activity.startActivity(intent);
    }
    
    public void startActivity( Activity activity, Class<?> cls, Bundle bundle ){
        Intent intent=new Intent();
        intent.setClass(activity,cls);
        if( null != bundle ){
            intent.putExtras( bundle );
        }
        
        activity.startActivity(intent);
    }
    
    public void startActivityEx( Activity activity, Class<?> cls ){
        startActivity( activity, cls, null );
    }
    
    public void startActivityForResultEx( Activity activity, Class<?> cls, Bundle bundle, int requestCode ){
        Intent intent=new Intent();
        intent.setClass(activity,cls);
        if( null != bundle ){
            intent.putExtras( bundle );
        }
        
        activity.startActivityForResult(intent, requestCode);
    }
    
    public void startActivityForResultEx( Activity activity, Class<?> cls, int requestCode ){
        startActivityForResultEx( activity, cls, null, requestCode );
    }
    
    private ActivityManager mActivityManager = ActivityManager.getActivityManager(this);
}
