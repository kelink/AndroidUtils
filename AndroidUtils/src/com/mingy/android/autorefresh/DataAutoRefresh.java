package com.mingy.android.autorefresh;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Timer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;

/**
 * 自动更新书架
 * 
 * */
public class DataAutoRefresh {
    public DataAutoRefresh( Context context, String[] supportSuffix ) throws NullPointerException{
        if( null == context || null == supportSuffix ){
            throw new NullPointerException( "传非空的参数进来！" );
        }
        
        mContext = context;
        mSupportSuffix = supportSuffix;
        
        initAutoRefreshBookShelf( );
    }
    
    public void setAutoRefreshListener( AutoRefreshListener autoRefreshListener ){
    	mAutoRefreshListener = autoRefreshListener;
    }
    
    /**
     * 不在本界面停止后台检索
     * 
     * */
    public void onPause( ){
        stopCheckFileTimer( );
    }
    
    /**
     * 返回界面恢复后台检索
     * 
     * */
    public void onResume( ){
        startCheckFileTimer( );
    }
    
    /**
     * 注销广播
     * 
     * */
    public void unregisterAutoRefreshBookShelf( ) throws NullPointerException{
        if( null == mBroadcastReceiver || null == mMediaStoreChangeObserver || null == mContext ){
            throw new NullPointerException( "没有初始化" );
        }
        mContext.unregisterReceiver( mBroadcastReceiver );
        mContext.getContentResolver( ).unregisterContentObserver( mMediaStoreChangeObserver );
        stopCheckFileTimer( );
    }
    
    /**
     * 得到变化的文件列表
     * 
     * */
    public void getChangedFileList( ){
        System.out.println( "toast ================= getChangedFileList " );
        startCheckFileTimer( );
    }
    
    private void initAutoRefreshBookShelf( ){
        startMediaFileListener( );
        observerMediaStoreChange( );
    }
    
    private void observerMediaStoreChange( ){
        if( null == mMediaStoreChangeObserver ){
            mMediaStoreChangeObserver = new MediaStoreChangeObserver( );
        }
        mContext.getContentResolver( ).registerContentObserver( MediaStore.Files.getContentUri("external"), false, mMediaStoreChangeObserver );
    }
    
    /**
     * 监听USB的状态，更新书架书本信息
     * 
     * */
    private void startMediaFileListener( ){
        if( null != mBroadcastReceiver ){
            return;
        }
        
        IntentFilter intentFilter = new IntentFilter( );
        intentFilter.addAction( Intent.ACTION_MEDIA_SCANNER_FINISHED );
        intentFilter.addAction( Intent.ACTION_MEDIA_MOUNTED );
        intentFilter.addAction( Intent.ACTION_MEDIA_EJECT );
        intentFilter.addDataScheme( "file" );
          
        mBroadcastReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context,Intent intent){
                String action = intent.getAction( );
                if( Intent.ACTION_MEDIA_SCANNER_FINISHED.equals( action ) ){
                    System.out.println( "toast ================= ACTION_MEDIA_SCANNER_FINISHED " );
                    mTimerWorking = false;
                    startCheckFileTimer( );
                }else if( action.equals( Intent.ACTION_MEDIA_MOUNTED ) ){
                    System.out.println( "toast ================= ACTION_MEDIA_MOUNTED or ACTION_MEDIA_EJECT " );
                    mTimerWorking = true;
                    if( null != mAutoRefreshListener ){
                    	mAutoRefreshListener.onBookScan( );
                    }
                }else if( action.equals( Intent.ACTION_MEDIA_EJECT ) ){
                	if( null != mAutoRefreshListener ){
                		mAutoRefreshListener.onBookScan( );
                	}
                }
            }
        };
        mContext.registerReceiver( mBroadcastReceiver, intentFilter );//注册监听函数
    }
    
    /**
     * 媒体数据库变更观察类
     * 
     * */
    class MediaStoreChangeObserver extends ContentObserver{
        public MediaStoreChangeObserver( ) {
            super( new Handler( ) );
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            startCheckFileTimer( );
        }
    }
    
    private void startCheckFileTimer( ){
        if( mTimerWorking ){
            return;
        }
        
        mCheckFileTimer = new Timer( );
        mCheckFileTimer.schedule( new CheckFileChangeTimerTask( ), 5000 );
        mTimerWorking = true;
    }
    
    private void stopCheckFileTimer( ){
        if( null != mCheckFileTimer ){
            mCheckFileTimer.cancel( );
            mCheckFileTimer = null;
            
            mTimerWorking = false;
        }
    }
    
    /**
     * 得到新增的文件列表
     * 
     * */
    public ArrayList<String> getChangedFileList( Context context, String[] searchFileSuffix, ArrayList<String> existFileList ){
        ArrayList<String> changedFileList = null;
        if( null == context || null == searchFileSuffix ){
            return changedFileList;
        }
        
        ArrayList<String> supportFileList = getSupportFileList( context, searchFileSuffix );
        changedFileList = getDifferentFileList( supportFileList, existFileList );
        if( null == changedFileList || changedFileList.size( ) == 0 ){
            changedFileList = null;
        }
        
        return changedFileList;
    }
    
    /**
     * 获取新增的文件列表
     * 
     * */
    private ArrayList<String> getDifferentFileList( ArrayList<String> newFileList, ArrayList<String> existFileList ){
        ArrayList<String> differentFileList = null;
        if( null == newFileList || newFileList.size( ) == 0 ){
            return differentFileList;
        }
        
        differentFileList = new ArrayList<String>( );
        boolean isExist = false;
        if( null == existFileList ){
            // 如果已存在文件为空，那肯定是全部加进来啦。
            for( String newFilePath : newFileList ){
                differentFileList.add( newFilePath );
            }
        }else{
            for( String newFilePath : newFileList ){
                isExist = false;
                for( String existFilePath : existFileList ){
                    if( existFilePath.equals( newFilePath ) ){
                        isExist = true;
                        break;
                    }
                }
                
                if( !isExist ){
                    differentFileList.add( newFilePath );
                }
            }
        }
        
        return differentFileList;
    }
    
    /**
     * 从媒体库中获取指定后缀的文件列表
     * 
     * */
    public ArrayList<String> getSupportFileList( Context context, String[] searchFileSuffix ) {
        ArrayList<String> searchFileList = null;
        if( null == context || null == searchFileSuffix || searchFileSuffix.length == 0 ){
            return null;
        }
        
        String searchPath = "";
        int length = searchFileSuffix.length;
        for( int index = 0; index < length; index++ ){
            searchPath += ( MediaStore.Files.FileColumns.DATA + " LIKE '%" + searchFileSuffix[ index ] + "' " );
            if( ( index + 1 ) < length ){
                searchPath += "or ";
            }
        }
        
        searchFileList = new ArrayList<String>();
        Uri uri = MediaStore.Files.getContentUri("external");
        Cursor cursor = context.getContentResolver().query(
                uri, new String[] { MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.SIZE, MediaStore.Files.FileColumns._ID },
                searchPath, null, null);
        
        String filepath = null;
        if (cursor == null) {
            System.out.println("Cursor 获取失败!");
        } else {
            if (cursor.moveToFirst()) {
                do {
                    filepath = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
                    try {
                        searchFileList.add(new String(filepath.getBytes("UTF-8")));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                } while (cursor.moveToNext());
            }

            if (!cursor.isClosed()) {
                cursor.close();
            }
        }

        return searchFileList;
    }
    
    /**
     * 得到媒体库更新的文件
     * 
     * */
    class GetMediaStoreDataTask extends AsyncTask< Void , Void , Void>{
        @Override
        protected Void doInBackground(Void... arg0) {
        	if( null == mAutoRefreshListener ){
        		return null;
        	}
        	
            ArrayList<String> changedFileList = getChangedFileList( mContext, mSupportSuffix, mAutoRefreshListener.onGetBookPathList( ) );
            if( null != changedFileList && changedFileList.size( ) > 0 ){
                mAutoRefreshListener.onBookRefresh( changedFileList );
            }
            mTimerWorking = false;
            
            return null;
        }
    }
    
    class CheckFileChangeTimerTask extends java.util.TimerTask{
        @Override
        public void run() {
            new GetMediaStoreDataTask( ).execute( );
        }
    }
    
    /**
     * 书架自动刷新接口
     * 
     * */
    public interface AutoRefreshListener{
        public ArrayList<String> onGetBookPathList( ); // 得到书架书本列表
        public void onBookRefresh( ArrayList<String> bookInfoList );// 刷新书架
        public void onBookScan( );//全盘扫描书架
    }
    
    private boolean mTimerWorking = false;
    
    private Context mContext = null;
    private Timer mCheckFileTimer = null;
    private BroadcastReceiver mBroadcastReceiver = null;
    private MediaStoreChangeObserver mMediaStoreChangeObserver = null;
    private AutoRefreshListener mAutoRefreshListener = null;
    private String[] mSupportSuffix = null;
}
