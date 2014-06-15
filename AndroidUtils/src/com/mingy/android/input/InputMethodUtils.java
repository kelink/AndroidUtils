package com.mingy.android.input;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 输入法管理器
 * 
 * */
public class InputMethodUtils {
    public InputMethodUtils( Context context ){
        mContext = context;
        mInputMethodManager = ( InputMethodManager )mContext.getSystemService( Context.INPUT_METHOD_SERVICE );
    }
    
    /**
     * 显示输入法
     * 
     * @param view
     * 
     * */
    public void showInputMethod( View view ){
        if( null != view ){
            mInputMethodManager.showSoftInput( view, InputMethodManager.SHOW_FORCED );
            mIsInputMethodShow = true;
        }
    }
    
    /**
     * 显示输入法
     * 
     * @param view
     * 
     * */
    public void hideInputMethod( View view ){
        if( null != view ){
            mInputMethodManager.hideSoftInputFromWindow( view.getWindowToken( ), 0 );
            mIsInputMethodShow = false;
        }
    }
    
    /**
     * 切换输入法状态
     * 
     * 
     * @param view
     * */
    public void toggle( View view ){
        mIsInputMethodShow = !mIsInputMethodShow;
        if( mIsInputMethodShow ){
            showInputMethod( view );
        }else{
            hideInputMethod( view );
        }
    }
    
    private boolean mIsInputMethodShow = false;
    private InputMethodManager mInputMethodManager = null;
    private Context mContext = null;
}
