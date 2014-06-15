package com.mingy.android.debug;




/**
 * 打印类名、行号、文件名
 * 
 * */
public class ClassUtils {
    public ClassUtils( int mask ){
        showClassInfo( mask );
    }
    
    private void showClassInfo( int mask ){
        if( Debug.DEBUG_MODE ){
            StringBuffer strBuffer = new StringBuffer( );
            StackTraceElement[ ] stackTrace = new Throwable( ).getStackTrace( );
            switch( mask ){
                case 0x01:{
                    strBuffer.append( "className = " ).append( stackTrace[ 1 ].getClassName( ) );
                }
                break;
                case 0x03:{
                    strBuffer.append( "lineNumber = " ).append( stackTrace[ 1 ].getLineNumber( ) );
                }
                break;
                case 0x07:{
                    strBuffer.append( "methodName = " ).append( stackTrace[ 1 ].getMethodName( ) );
                }
                break;
            
            }

            DebugUtils.println( strBuffer.toString( ) );
        }
    }
    
    public static final class Builder{
        private int mMask = 0;
        
        public Builder( ){
            mMask = 0;
        }
        
        public Builder showLineNumber( ){
            mMask |= 0x01;
            
            return this;
        }
        
        public Builder showMethodName( ){
            mMask |= 0x02;
            return this;
        }
        
        public Builder showFileName( ){
            mMask |= 0x04;
            return this;
        }
        
        public ClassUtils show( ){
            return new ClassUtils( mMask );
        }
    }
}
