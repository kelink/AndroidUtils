package com.mingy.android.usb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * SD卡插拔监听
 * 
 * */
public class SDCardBroadCastUtils extends BroadcastReceiver {
    public SDCardBroadCastUtils(Context context) {
        mContext = context;
        registerReceiver( );
    }
    
    public void unRegisterReceiver() {
        mContext.unregisterReceiver(this);
    }
    
    private void registerReceiver( ) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_MEDIA_MOUNTED);
        filter.addAction(Intent.ACTION_MEDIA_CHECKING);
        filter.addAction(Intent.ACTION_MEDIA_EJECT);
        filter.addAction(Intent.ACTION_MEDIA_REMOVED);
        // 必须要有此行，否则无法收到广播
        filter.addDataScheme("file");
        
        mContext.registerReceiver(this, filter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction( );
        if( Intent.ACTION_MEDIA_MOUNTED.equals( action ) ){
            
        }else if( Intent.ACTION_MEDIA_CHECKING.equals( action ) ){
            
        }else if( Intent.ACTION_MEDIA_EJECT.equals( action ) ){
            
        }else if( Intent.ACTION_MEDIA_REMOVED.equals( action ) ){
            
        }
    }
    
    private Context mContext = null;
}
