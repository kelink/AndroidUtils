package com.mingy.android.debug;

import android.os.StrictMode;

/**
 * ÑÏ¿ÁÄ£Ê½
 * 
 * */
public class StrictModeUtils {
    private StrictModeUtils( ){
        
    }
    
    public static void showStrictMode( ) {
        if ( Debug.DEBUG_MODE ) {
            StrictMode.setThreadPolicy( new StrictMode.ThreadPolicy.Builder( )
                .detectDiskReads( )
                .detectDiskWrites( )
                .detectNetwork( )
                .penaltyLog( )
                .build( ) );
            
            StrictMode.setVmPolicy( new StrictMode.VmPolicy.Builder( )
                .detectLeakedSqlLiteObjects( )
                .detectLeakedClosableObjects( )
                .penaltyLog( )
                .penaltyDeath( )
                .build( ) );
        }
    }
}
