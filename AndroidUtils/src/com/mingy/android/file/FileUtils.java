package com.mingy.android.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import com.mingy.android.text.StringUtils;

/**
 * 文件操作类
 * 
 * */
public class FileUtils {

    public final static String FILE_EXTENSION_SEPARATOR = ".";

    private FileUtils( ){
        
    }
    
    /**
     * 得到SD卡的路径
     * 
     * */
    public static String getSDPATH( ){
        return Environment.getExternalFlashStorageDirectory( ).toString( ) + "/";
    }

    /**
     * 得到隐藏盘路径
     * 
     * */
    public static String getFlashAPath( ){
        return Environment.getInternalStorageDirectory( ).toString( ) + "/";
    }

    /**
     * 得到内部存储设备的路径
     * 
     * */
    public static String getFlashBPath( ){
        return Environment.getExternalStorageDirectory( ).toString( ) + "/";
    }
    
    /**
     * 判断文件是否存在
     * 
     * @param filePath 文件全路径
     * */
    public static boolean isFileExist(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return false;
        }

        File file = new File(filePath);
        
        return (file.exists() && file.isFile());
    }

    /**
     * 判断文件夹是否存在
     * 
     * @param directoryPath 文件夹全路径
     * */
    public static boolean isFolderExist(String directoryPath) {
        if (StringUtils.isBlank(directoryPath)) {
            return false;
        }

        File dire = new File(directoryPath);
        return (dire.exists() && dire.isDirectory());
    }
    
    /**
     *  判断磁盘空间是否足够
     *  
     *  @param filePath 文件路径
     *  @param sizeMb 多少M（单位：M）
     * */
    public static boolean isSpaceEnough( String filePath, int sizeMb ){
        boolean hasSpace = false;
        if( TextUtils.isEmpty( filePath ) || sizeMb <= 0 ){
            return hasSpace;
        }
        StatFs statFs = new StatFs( filePath );

        if( null != statFs ){
            long blockSize = statFs.getBlockSize( );
            long blocks = statFs.getAvailableBlocks( );
            double availableSpare = ( blocks * blockSize * 1.0 ) / ( 1024 * 1024 );

            if( availableSpare > sizeMb ){
                hasSpace = true;
            }
        }

        return hasSpace;
    }
    
    /**
     * 得到不带后缀的文件名
     * 
     * @param filePath 文件全路径
     * @return 不带后缀的文件名
     * */
    public static String getFileNameWithoutExtension(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (filePosi == -1) {
            return (extenPosi == -1 ? filePath : filePath.substring(0, extenPosi));
        }
        if (extenPosi == -1) {
            return filePath.substring(filePosi + 1);
        }
        
        return (filePosi < extenPosi ? filePath.substring(filePosi + 1, extenPosi) : filePath.substring(filePosi + 1));
    }

    /**
     * 得到文件名
     * 
     * @param filePath 文件全路径
     * @return 文件名
     * */
    public static String getFileName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        
        return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
    }

    /**
     * 得到文件夹的名称
     * 
     * @param filePath 文件夹全路径
     * @return 文件夹名称
     * */
    public static String getFolderName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        
        return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
    }

    /**
     * 得到文件后缀
     * 
     * @param filePath 文件全路径
     * @return 文件后缀，不带点
     * */
    public static String getFileExtension(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return filePath;
        }

        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (extenPosi == -1) {
            return "";
        }
        return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
    }
    
    /**
     * 创建文件夹
     * 
     * @param filePath 文件夹路径
     * @return 是否创建成功
     * */
    public static boolean makeDirs(String filePath) {
        String folderName = getFolderName(filePath);
        if (TextUtils.isEmpty(folderName)) {
            return false;
        }

        File folder = new File(folderName);
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }

    /**
     * 删除文件
     * 
     * @param filePath 文件全路径
     * @return 是否删除成功
     * */
    public static boolean deleteFile(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return true;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }
        for (File f : file.listFiles()) {
            if (f.isFile()) {
                f.delete();
            } else if (f.isDirectory()) {
                deleteFile(f.getAbsolutePath());
            }
        }
        
        return file.delete();
    }

    /**
     * 得到文件大小，单位B
     * 
     * @param filePath 文件全路径
     * @return 文件大小
     * */
    public static long getFileSize(String filePath) {
        if (StringUtils.isBlank( filePath )) {
            return -1;
        }

        File file = new File( filePath );
        return (file.exists() && file.isFile() ? file.length() : -1);
    }
    
    /**
     * 读文件
     * 
     * @param filePath 文件全路径
     * @param charsetName 编码格式
     * @return 文件不存在返回null，存在则返回文件内容
     */
    public static StringBuilder readFile(String filePath, String charsetName) {
        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder("");
        if (file == null || !file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!fileContent.toString().equals("")) {
                    fileContent.append("\r\n");
                }
                fileContent.append(line);
            }
            reader.close();
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * 写文件
     * 
     * @param filePath 文件全路径
     * @param content 写的内容
     * @param append 是否为append模式
     * @return 如果内容为空则返回false，否则为true
     * */
    public static boolean writeFile(String filePath, String content, boolean append) {
        if (TextUtils.isEmpty(content)) {
            return false;
        }

        FileWriter fileWriter = null;
        try {
            makeDirs(filePath);
            fileWriter = new FileWriter(filePath, append);
            fileWriter.write(content);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * 写文件
     * 
     * @param filePath 文件全路径
     * @param content 写的内容
     * @return true 写成功，false 写失败
     */
    public static boolean writeFile(String filePath, String content) {
        return writeFile(filePath, content, false);
    }
}
