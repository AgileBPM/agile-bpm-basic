package com.dstz.base.core.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 * 压缩解压缩文件 工具类
 * @deprecated
 */
public class ZipUtil {

	/**
	 * <pre>
	 * 把文件打包到zip文件中
	 * </pre>	
	 * @param sourceFileList
	 * @param zipName
	 * @return
	 */
	public static File zip(ArrayList<File> sourceFileList, ZipFile zipFile) {
		try {
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			zipFile.createZipFile(sourceFileList, parameters);
			return zipFile.getFile();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
   /**
     * Extracts a zip file specified by the zipFilePath to a directory specified
     * by destDirectory (will be created if does not exists)
     * 
     * @param zipFilePath
     * @param destDirectory
     * @throws IOException
     */
    public static void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }
    
    /**
     * Extracts a zip entry (file entry)
     * 
     * @param zipIn
     * @param filePath
     * @throws IOException
     */
    private static final int BUFFER_SIZE = 4096;
    public static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    } 
 

	 
	/**
	 * 创建文件目录
	 *
	 * @param tempPath
	 * @param fileName
	 *            文件名称
	 * @return 文件的完整目录
	 */
	public static String createFilePath(String tempPath, String fileName) {
		File file = new File(tempPath);
		// 文件夹不存在创建
		if (!file.exists())
			file.mkdirs();
		return file.getPath() + File.separator + fileName;
	}
}
