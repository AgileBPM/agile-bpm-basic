package com.dstz.base.core.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 * 压缩解压缩文件 工具类
 */
public class ZipUtil {

	/**
	 * 压缩文件 （压缩后 删除原有的文件）
	 *
	 * @param path
	 *            压缩文件\文件夹路径
	 */
	public static void zip(String path) {
		zip(path, true);
	}

	public static void main(String[] args) throws Exception {
		String path = "D:\\IT\\file";
		/*
		 * zip(path, false); //unZipFile("d:/test.zip", "d:");
		 * System.out.println("main");
		 */
		ZipFile zipFile = new ZipFile(new File("D:\\IT\\file.zip"));
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		ArrayList sourceFileList = new ArrayList<>();
		sourceFileList.add(new File("D:\\IT\\file\\Koala.jpg"));
		sourceFileList.add(new File("D:\\IT\\file\\Penguins.jpg"));
		zipFile.createZipFile(sourceFileList, parameters);
		System.out.println("main");
	}

	/**
	 * <pre>
	 * 把文件打包到zip文件中
	 * </pre>
	 * 
	 * @param fileNames
	 *            要压缩的文件列表
	 * @param zipName
	 *            打包的文件
	 * @return 返回压缩zip文件
	 */
	public static File zip(List<String> fileNames, String zipName) {
		try {
			ArrayList<File> sourceFileList = new ArrayList<>();
			for (String fileName : fileNames) {
				sourceFileList.add(new File(fileName));
			}
			return zip(sourceFileList,new ZipFile(new File(zipName)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
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
	 * 压缩文件
	 *
	 * @param path
	 *            压缩文件\文件夹路径
	 * @param isDelete
	 *            压缩后是否删除原文件\文件夹
	 */
	public static void zip(String path, Boolean isDelete) {
		ZipFile zipFile = null;
		try {
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			File file = new File(path);
			if (file.isDirectory()) {
				zipFile = new ZipFile(new File(path + ".zip"));
				zipFile.setFileNameCharset("utf-8");
				zipFile.addFolder(path, parameters);
			} else {
				zipFile = new ZipFile(new File(path.split(".")[0] + ".zip"));
				zipFile.setFileNameCharset("utf-8");
				zipFile.addFile(file, parameters);
			}

			if (isDelete)
				FileUtil.deleteDir(file);
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 压缩文件夹(加密)
	 *
	 * @param path
	 *            压缩文件\文件夹路径
	 * @param isDelete
	 *            压缩后是否删除原文件\文件夹
	 * @param password
	 *            加密密码
	 */
	public static void zipSetPass(String path, Boolean isDelete, String password) {
		ZipFile zipFile = null;
		try {
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			// 设置密码
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
			parameters.setPassword(password);
			File file = new File(path);
			if (file.isDirectory()) {
				zipFile = new ZipFile(new File(path + ".zip"));
				zipFile.setFileNameCharset("utf-8");
				zipFile.addFolder(path, parameters);
			} else {
				zipFile = new ZipFile(new File(path.split(".")[0] + ".zip"));
				zipFile.setFileNameCharset("utf-8");
				zipFile.addFile(file, parameters);
			}
			if (isDelete) {
				FileUtil.deleteDir(new File(path));
			}
		} catch (ZipException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 解压上传zip文件。
	 *
	 * @param multipartFile
	 * @param toPath
	 * @throws Exception
	 *             void
	 */
	public static void unZipFile(MultipartFile multipartFile, String toPath) throws Exception {
		// 转存文件
		String originalFilename = multipartFile.getOriginalFilename();
		String destPath = toPath + originalFilename;

		createFilePath(destPath, originalFilename);
		File file = new File(destPath);
		if (file.exists())
			file.delete();
		multipartFile.transferTo(file);
		ZipFile zipFile = new ZipFile(file);
		zipFile.setFileNameCharset("GBK"); // 设置文件名编码，在GBK系统中需要设置
		if (zipFile.isEncrypted())
			zipFile.setPassword("");
		if (!zipFile.isValidZipFile())
			throw new ZipException("压缩文件不合法,可能被损坏.");
		// 将文件抽出到解压目录(解压)
		zipFile.extractAll(toPath);
		// 删除解压文件
		FileUtil.deleteDir(file);
	}

	/**
	 * 解压压缩文件
	 *
	 * @param filePath
	 *            压缩文件路径
	 * @param toPath
	 *            解压到的文件夹路径
	 * @param password
	 *            密码 没有密码设置为""
	 */
	public static void unZip(String filePath, String toPath, String password) {
		try {
			unZipFile(new ZipFile(filePath), toPath, password);
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解压压缩文件
	 *
	 * @param zipFile
	 * @param toPath
	 * @param password
	 */
	public static void unZipFile(ZipFile zipFile, String toPath, String password) {
		try {
			if (zipFile.isEncrypted())
				zipFile.setPassword(password);
			List<?> fileHeaderList = zipFile.getFileHeaders();
			for (Object o : fileHeaderList) {
				FileHeader fileHeader = (FileHeader) o;
				zipFile.extractFile(fileHeader, toPath);
			}
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解压文件。
	 *
	 * @param filePath
	 *            zip文件路径
	 * @param toPath
	 *            目录 void
	 */
	public static void unZipFile(String filePath, String toPath) {
		unZip(filePath, toPath, "");
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
