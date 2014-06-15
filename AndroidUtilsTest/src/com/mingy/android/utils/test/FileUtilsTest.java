package com.mingy.android.utils.test;

import junit.framework.TestCase;

import com.mingy.android.file.FileUtils;

public class FileUtilsTest extends TestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testgetSDPATH( ){
        assertEquals("/mnt/external_sd/", FileUtils.getSDPATH( ) );
    }
    
    public void testgetFlashAPath( ){
        assertEquals("/mnt/flasha/", FileUtils.getFlashAPath( ) );
    }
    
    public void testgetFlashBPath( ){
        assertEquals("/mnt/sdcard/", FileUtils.getFlashBPath( ) );
    }
    
    public void testisFileExist( ){
        
    }
    
    public void testwriteFile( ){
        assertTrue( FileUtils.writeFile(FileUtils.getFlashBPath( )+"testwriteFile.txt", "This ia an android junit test case,test method is testwriteFile"));
    }
    
    public void testgetFileNameWithoutExtension( ){
        assertEquals("hello", FileUtils.getFileNameWithoutExtension( "hello.txt" ) );
        assertEquals("hello", FileUtils.getFileNameWithoutExtension( "hello" ) );
        assertEquals("hello", FileUtils.getFileNameWithoutExtension( "hello." ) );
        assertEquals("", FileUtils.getFileNameWithoutExtension( "" ) );
        assertEquals(null, FileUtils.getFileNameWithoutExtension( null ) );
        assertEquals("1", FileUtils.getFileNameWithoutExtension( FileUtils.getSDPATH( ) + "1.txt" ) );
    }
    
    public void testgetFileName( ){
        assertEquals("hello.txt", FileUtils.getFileName( "hello.txt" ) );
        assertEquals("hello", FileUtils.getFileName( "hello" ) );
        assertEquals("hello.", FileUtils.getFileName( "hello." ) );
        assertEquals("", FileUtils.getFileName( "" ) );
        assertEquals(null, FileUtils.getFileName( null ) );
        assertEquals("1.txt", FileUtils.getFileName( FileUtils.getSDPATH( ) + "1.txt" ) );
        assertEquals("1.txt", FileUtils.getFileName( "/1.txt" ) );
    }
}
