package com.mingy.android.debug;

import android.text.TextUtils;
import android.util.Log;

public class DebugUtils {
    private DebugUtils( ){
        
    }
    
    public static void print( String infoStr ){
        if( Debug.DEBUG_MODE && !TextUtils.isEmpty( infoStr ) ){
            System.out.print( infoStr );
        }
    }
    
    public static void println( String infoStr ){
        if( Debug.DEBUG_MODE && !TextUtils.isEmpty( infoStr ) ){
            System.out.println( infoStr );
        }
    }
    
    public static void V( String tag, String logVInfoStr ){
        if( Debug.DEBUG_MODE && !TextUtils.isEmpty( tag ) && !TextUtils.isEmpty( logVInfoStr ) ){
            Log.v(tag, logVInfoStr);
        }
    }
    
    public static void D( String tag, String logDInfoStr ){
        if( Debug.DEBUG_MODE && !TextUtils.isEmpty( tag ) && !TextUtils.isEmpty( logDInfoStr ) ){
            Log.v(tag, logDInfoStr);
        }
    }
    
    public static void I( String tag, String logIInfoStr ){
        if( Debug.DEBUG_MODE && !TextUtils.isEmpty( tag ) && !TextUtils.isEmpty( logIInfoStr ) ){
            Log.v(tag, logIInfoStr);
        }
    }
    
    public static void W( String tag, String logWInfoStr ){
        if( Debug.DEBUG_MODE && !TextUtils.isEmpty( tag ) && !TextUtils.isEmpty( logWInfoStr ) ){
            Log.v(tag, logWInfoStr);
        }
    }
    
    public static void E( String tag, String logEInfoStr ){
        if( Debug.DEBUG_MODE && !TextUtils.isEmpty( tag ) && !TextUtils.isEmpty( logEInfoStr ) ){
            Log.v(tag, logEInfoStr);
        }
    }
}
