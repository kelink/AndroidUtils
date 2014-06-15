package com.mingy.android.view;

/**
 * 按钮相关操作
 * 
 * */
public class BtnClickUtils {
    private BtnClickUtils( ){
        
    }
    
    /**
     * 检测按钮是否快速点击
     * 
     * */
    public static boolean isFastClick( ){
        long currentClickTime = System.currentTimeMillis( );
        long timeDistance = currentClickTime - mLastClickTime;
        
        if( timeDistance > 0 && timeDistance < 1000 ){
            return true;
        }
        
        mLastClickTime = currentClickTime;
        
        return false;
    }
    
    private static long mLastClickTime = 0;
}
