package com.mingy.android.audio;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.mingy.android.file.FileUtils;

/**
 * 媒体库扫描
 * 
 * */
public class MediaFileScanUtils {
    
    /**
     * 得到媒体库中存在的指定后缀的文件列表
     * 
     * @param context
     * @param searchFileSuffix 需要搜索的文件后缀数组，比如new String[]{"avi","mp4","wma"};
     * 
     * */
    public static ArrayList<String> getExistFileList( Context context, String[] searchFileSuffix ){
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
                uri, new String[] { MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.SIZE },
                searchPath, null, null);

        if (cursor == null) {
            System.out.println("Cursor 获取失败!");
        } else {
            if (cursor.moveToFirst()) {
                do {
                    String filepath = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));

                    if ( FileUtils.isFileExist(filepath) ) {
                        try {
                            searchFileList.add(new String(filepath.getBytes("UTF-8")));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }

                } while (cursor.moveToNext());
            }

            if (!cursor.isClosed()) {
                cursor.close();
            }
        }

        return searchFileList;
    }
}
