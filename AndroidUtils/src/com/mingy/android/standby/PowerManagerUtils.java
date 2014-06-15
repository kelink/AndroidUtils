package com.mingy.android.standby;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.PowerManager;
import android.text.TextUtils;

/**
 * 待机控制
 * 
 * */
public class PowerManagerUtils {
	public PowerManagerUtils( Context context ){
		mContext = context;
	}
	
	public PowerManagerUtils( Context context, String tag ){
		mContext = context;
		mTag = tag;
	}
	
	/**
     * 允许待机
     * 
     * */
    public void enableScreenOff( ){
    	if( null == mContext ){
    		return;
    	}
    	
        if( getWakeLockPermisson( mContext ) ){
            if( null != mWakeLock ){
                mWakeLock.release( );
                mWakeLock = null;
            }
        }
    }
	
	/**
     * 禁止待机
     * 
     * */
    @SuppressWarnings("deprecation")
    public void disableScreenOff( ){
    	if( null == mContext ){
    		return;
    	}
    	
        if( getWakeLockPermisson( mContext ) ){
            if( null == mPowerManager ){
                mPowerManager = ( PowerManager ) mContext.getSystemService( Context.POWER_SERVICE );
            }
            
            if( null != mWakeLock ){
                return;
            }
            
            mWakeLock = mPowerManager.newWakeLock( PowerManager.SCREEN_BRIGHT_WAKE_LOCK  | PowerManager.ON_AFTER_RELEASE, ( TextUtils.isEmpty( mTag ) )?"PowerManager":mTag );
            mWakeLock.setReferenceCounted( false );
            mWakeLock.acquire( );
        }
    }
    
    /**
     * 功能描述： 得到系统权限
     *  
     *  
     */
    private boolean getWakeLockPermisson( Context context ){
        try{
            PackageManager mPackageManager = context.getPackageManager( );
            PackageInfo mPackageInfo = mPackageManager.getPackageInfo( context.getPackageName( ), 0 );
            //得到自己的包名
            String mPkgName = mPackageInfo.packageName;
            PackageInfo mPkgInfo = mPackageManager.getPackageInfo( mPkgName, PackageManager.GET_PERMISSIONS );
            String mSharedPkgList[ ] = mPkgInfo.requestedPermissions;
            if( null == mSharedPkgList ){
                return false;
            }
            
            for(int mI = 0; mI < mSharedPkgList.length; mI++ ){
                String permName = mSharedPkgList[ mI ];
                if( permName.equals( "android.permission.WAKE_LOCK" ) ){
                    return true;
                }
            }
        }catch (NameNotFoundException e){
            e.printStackTrace( );
        }
        
        return false;
    }
    
    private String mTag = null;
    private Context mContext = null;
	private PowerManager mPowerManager = null;
    private PowerManager.WakeLock mWakeLock = null;
}
