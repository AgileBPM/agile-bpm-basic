package com.dstz.base.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

    /**
     * 写入文件
     *
     * @param fileName 文件名称
     * @param content  文件内容
     */
    public static void writeFile(String fileName, String content) {
        writeFile(fileName, content, "utf-8");
    }

    /**
     * 指定字符集，写入文件。
     *
     * @param fileName
     * @param content
     * @param charset
     */
    public static void writeFile(String fileName, String content, String charset) {
        Writer out;
        try {
            createFolder(fileName, true);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), charset));
            out.write(content);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入文件
     *
     * @param fileName
     * @param is
     * @throws IOException
     */
    public static void writeFile(String fileName, InputStream is){
    	try {
    		FileOutputStream fos = new FileOutputStream(fileName);
            byte[] bs = new byte[512];
            int n = 0;
            while ((n = is.read(bs)) != -1) {
                fos.write(bs, 0, n);
            }
            is.close();
            fos.close();
    	}catch (Exception e) {
    		throw new RuntimeException(e);
		}
        
    }

    /**
     * 读取文件
     *
     * @param fileName 文件名称
     * @return
     */
    public static String readFile(String fileName) {
        try {
            File file = new File(fileName);
            String charset = getCharset(file);
            StringBuilder sb = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), charset));
            String str;
            while ((str = in.readLine()) != null) {
                sb.append(str + "\r\n");
            }
            in.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 通过类路径读取文件
     *
     * @param filePath 文件类路径
     * @return
     */
    public static String readByClassPath(String filePath) {
        return readByClassPath(filePath, null);
    }

    public static String readByClassPath(String filePath, ClassLoader classLoader) {
        InputStream is = null;
        try {
            URL url = null;
            if (classLoader != null) {
                url = classLoader.getResource("/" + filePath);
            } else {
                url = FileUtil.class.getResource("/" + filePath);
            }

            is = url.openStream();

            InputStreamReader isr = new InputStreamReader(is);

            BufferedReader br = new BufferedReader(isr);

            String s = null;
            String fileText = "";
            while ((s = br.readLine()) != null) {
                fileText = fileText + s + "\r\n";
            }

            return fileText;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param dir
     * @return
     */
    public static boolean isExistFile(String dir) {
        boolean isExist = false;
        File fileDir = new File(dir);
        if (fileDir.isDirectory()) {
            File[] files = fileDir.listFiles();
            if (files != null && files.length != 0) {
                isExist = true;
            }
        }
        return isExist;
    }

    /**
     * 获取文件的字符集
     *
     * @param file
     * @return
     */
    public static String getCharset(File file) {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1)
                return charset;
            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8";
                checked = true;
            }
            bis.reset();

            if (!checked) {
                // int loc = 0;
                while ((read = bis.read()) != -1) {
                    // loc++;
                    if (read >= 0xF0)
                        break;
                    // 单独出现BF以下的，也算是GBK
                    if (0x80 <= read && read <= 0xBF)
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF)// 双字节 (0xC0 - 0xDF)
                            // (0x80 -
                            // 0xBF),也可能在GB编码内
                            continue;
                        else
                            break;
                        // 也有可能出错，但是几率较小
                    } else if (0xE0 <= read && read <= 0xEF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }

            }
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return charset;
    }

    /**
     * 读取流到字节数组
     *
     * @param is
     * @return
     */
    public static byte[] readByte(InputStream is) {
        try {
            byte[] r = new byte[is.available()];
            is.read(r);
            return r;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取文件到字节数组
     *
     * @param fileName
     * @return
     */
    public static byte[] readByte(String fileName) {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            byte[] r = new byte[fis.available()];
            fis.read(r);
            fis.close();
            return r;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 写字节数组到文件
     *
     * @param fileName
     * @param b
     * @return
     */
    public static boolean writeByte(String fileName, byte[] b) {
        try {
            BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(fileName));
            fos.write(b);
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除目录
     *
     * @param dir
     * @return
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 序列化对象到文件
     *
     * @param obj
     * @param fileName
     */
    public static void serializeToFile(Object obj, String fileName) {
        try {
            ObjectOutput out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(obj);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从文件反序列化出对象
     *
     * @param fileName
     * @return
     */
    public static Object deserializeFromFile(String fileName) {
        try {
            File file = new File(fileName);
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            Object obj = in.readObject();
            in.close();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * stream 转为字符串
     *
     * @param input
     * @param charset
     * @return
     * @throws IOException
     */
    public static String inputStream2String(InputStream input, String charset) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(input, charset));
        StringBuilder buffer = new StringBuilder();
        String line = "";
        while ((line = in.readLine()) != null) {
            buffer.append(line + "\n");
        }
        return buffer.toString();

    }

    /**
     * 将stream按照utf-8编码转换为字符串。
     *
     * @param input
     * @return
     * @throws IOException
     */
    public static String inputStream2String(InputStream input) throws IOException {
        return inputStream2String(input, "utf-8");

    }

    /**
     * 根据目录取得文件列表
     *
     * @param path
     * @return
     */
    public static File[] getFiles(String path) {
        File file = new File(path);
        return file.listFiles();
    }

    /**
     * 根据文件路径创建文件夹,如果路径不存在则创建.
     *
     * @param path
     */
    public static void createFolderFile(String path) {
        createFolder(path, true);
    }

    /**
     * 创建文件夹
     *
     * @param path
     * @param isFile
     */
    public static void createFolder(String path, boolean isFile) {
        if (isFile) {
            path = path.substring(0, path.lastIndexOf(File.separator));
        }
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
    }

    /**
     * 复制文件来新路径上
     *
     * @param path    原路径
     * @param newPath 新路径
     */
    public static void renameFolder(String path, String newName) {
        File file = new File(path);
        if (file.exists())
            file.renameTo(new File(newName));
    }

    /**
     * 仅取得文件目录下的子目录。
     *
     * @param dir 目录
     * @return 子目录列表
     */
    public static ArrayList<File> getDiretoryOnly(File dir) {
        ArrayList<File> dirs = new ArrayList<File>();
        if (dir != null && dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles(new FileFilter() {

                @Override
                public boolean accept(File file) {
                    if (file.isDirectory())
                        return true;
                    return false;
                }
            });
            for (int i = 0; i < files.length; i++) {
                dirs.add(files[i]);
            }
        }
        return dirs;
    }

    /**
     * 列出子文件列表
     *
     * @param dir 指定目录
     * @return 子文件列表
     */
    public ArrayList<File> getFileOnly(File dir) {
        ArrayList<File> dirs = new ArrayList<File>();
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isFile())
                    return true;
                return false;
            }
        });
        for (int i = 0; i < files.length; i++) {
            dirs.add(files[i]);
        }
        return dirs;
    }

    /**
     * 删除文件
     *
     * @param path
     * @return
     */
    public static boolean deleteFile(String path) {
        File file = new File(path);
        return file.delete();
    }

    /**
     * 文件拷贝
     *
     * @param from
     * @param to
     * @return
     */
    public static boolean copyFile(String from, String to) {
        File fromFile = new File(from);
        File toFile = new File(to);
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(fromFile);
            fos = new FileOutputStream(toFile);
            int bytesRead;
            byte[] buf = new byte[4 * 1024]; // 4K buffer
            while ((bytesRead = fis.read(buf)) != -1) {
                fos.write(buf, 0, bytesRead);
            }

            fos.flush();
            fos.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    /**
     * 备份文件。 如果有备份文件，先删除。
     *
     * @param filePath
     */
    public static void backupFile(String filePath) {
        String backupName = filePath + ".bak";
        File file = new File(backupName);
        if (file.exists())
            file.delete();

        copyFile(filePath, backupName);

    }

    /**
     * 取得文件扩展名
     *
     * @return
     */
    public static String getFileExt(File file) {
        if (file.isFile()) {
            return getFileExt(file.getName());
        }
        return "";
    }

    /**
     * 根据文件名获取扩展名称。
     *
     * @param fileName
     * @return
     */
    public static String getFileExt(String fileName) {
        int pos = fileName.lastIndexOf(".");
        if (pos > -1) {
            return fileName.substring(pos + 1).toLowerCase();
        }
        return "";
    }

    /**
     * copy目录
     *
     * @param fromDir 源目录
     * @param toDir   目标目录
     * @throws IOException
     */
    public static void copyDir(String fromDir, String toDir) throws IOException {
        (new File(toDir)).mkdirs();
        File[] file = (new File(fromDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                String fromFile = file[i].getAbsolutePath();
                String toFile = toDir + "/" + file[i].getName();

                copyFile(fromFile, toFile);
            }
            if (file[i].isDirectory())
                copyDirectiory(fromDir + "/" + file[i].getName(), toDir + "/" + file[i].getName());
        }
    }

    /**
     * 递规调用目录拷贝。
     *
     * @param fromDir 源目录
     * @param toDir   目标目录
     * @throws IOException
     */
    private static void copyDirectiory(String fromDir, String toDir) throws IOException {
        (new File(toDir)).mkdirs();
        File[] file = (new File(fromDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                String fromName = file[i].getAbsolutePath();
                String toFile = toDir + "/" + file[i].getName();
                copyFile(fromName, toFile);
            }
            if (file[i].isDirectory())
                copyDirectiory(fromDir + "/" + file[i].getName(), toDir + "/" + file[i].getName());
        }
    }

    /**
     * 取得文件大小
     *
     * @return 返回文件大小
     * @throws IOException
     */
    public static String getFileSize(File file) throws IOException {
        if (file.isFile()) {
            FileInputStream fis = new FileInputStream(file);
            int size = fis.available();
            fis.close();
            return getSize((double) size);
        }
        return "";
    }

    /**
     * 根据字节大小获取带单位的大小。
     *
     * @param size
     * @return
     */
    public static String getSize(double size) {
        DecimalFormat df = new DecimalFormat("0.00");
        if (size > 1024 * 1024) {
            double ss = size / (1024 * 1024);
            return df.format(ss) + " M";
        } else if (size > 1024) {
            double ss = size / 1024;
            return df.format(ss) + " KB";
        } else {
            return size + " bytes";
        }
    }

    /**
     * 取得文件的相对父目录
     *
     * @param baseDir     基准目录
     * @param currentFile 当前文件
     * @return 相对基准目录路径
     */
    public static String getParentDir(String baseDir, String currentFile) {
        File f = new File(currentFile);
        String parentPath = f.getParent();
        String path = parentPath.replace(baseDir, "");
        return path.replace(File.separator, "/");
    }

    /**
     * 根据键在属性文件中获取数据。
     *
     * @param fileName 属性文件名称。
     * @param key      属性的键值。
     * @return
     */
    public static String readFromProperties(String fileName, String key) {
        String value = "";
        InputStream stream = null;
        try {
            stream = new BufferedInputStream(new FileInputStream(fileName));
            Properties prop = new Properties();
            prop.load(stream);
            value = prop.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    /**
     * 保存属性文件。
     *
     * @param fileName 文件名
     * @param key      键名
     * @param value    键值
     * @return 保存是否成功。
     */
    public static boolean saveProperties(String fileName, String key, String value) {
        StringBuilder sb = new StringBuilder();
        boolean isFound = false;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "utf-8"));
            String str;
            while ((str = in.readLine()) != null) {
                if (str.startsWith(key)) {
                    sb.append(key + "=" + value + "\r\n");
                    isFound = true;
                } else {
                    sb.append(str + "\r\n");
                }
            }
            // 添加新的键值。
            if (!isFound) {
                sb.append(key + "=" + value + "\r\n");
            }
            FileUtil.writeFile(fileName, sb.toString(), "utf-8");
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除属性key。
     *
     * @param fileName
     * @param key
     * @return
     */
    public static boolean delProperties(String fileName, String key) {
        StringBuilder sb = new StringBuilder();

        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "utf-8"));
            String str;
            while ((str = in.readLine()) != null) {
                if (!str.startsWith(key)) {
                    sb.append(str + "\r\n");
                }
            }
            FileUtil.writeFile(fileName, sb.toString(), "utf-8");
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取接口的所有实现类
     *
     * @param interfaceClass 接口类
     * @param samePackage    是否为同一包路径下
     * @return
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static List<Class<?>> getAllClassesByInterface(Class<?> interfaceClass, boolean samePackage) throws IOException, ClassNotFoundException, IllegalStateException {

        if (!interfaceClass.isInterface()) {
            throw new IllegalStateException("Class not a interface.");
        }

        ClassLoader $loader = interfaceClass.getClassLoader();
        /** 获取包名称 */
        String packageName = samePackage ? interfaceClass.getPackage().getName() : "/";
        return findClasses(interfaceClass, $loader, packageName);
    }

    /**
     * 获取实现接口的实现类文件
     *
     * @param interfaceClass
     * @param loader
     * @param packageName
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static List<Class<?>> findClasses(Class<?> interfaceClass, ClassLoader loader, String packageName) throws IOException, ClassNotFoundException {

        List<Class<?>> allClasses = new ArrayList<Class<?>>();
        /** 获取包路径 */
        String packagePath = packageName.replace(".", "/");
        if (!packagePath.equals("/")) {
            Enumeration<URL> resources = loader.getResources(packagePath);
            while (resources.hasMoreElements()) {
                URL $url = resources.nextElement();
                allClasses.addAll(findResources(interfaceClass, new File($url.getFile()), packageName));
            }
        } else {
            String path = loader.getResource("").getPath();
            allClasses.addAll(findResources(interfaceClass, new File(path), packageName));
        }
        return allClasses;
    }

    /**
     * 获取文件资源信息
     *
     * @param interfaceClass
     * @param directory
     * @param packageName
     * @return
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    private static List<Class<?>> findResources(Class<?> interfaceClass, File directory, String packageName) throws ClassNotFoundException {

        List<Class<?>> $results = new ArrayList<Class<?>>();
        if (!directory.exists())
            return Collections.EMPTY_LIST;
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                // 文件夹-->继续遍历
                if (!file.getName().contains(".")) {
                    if (!packageName.equals("/")) {
                        $results.addAll(findResources(interfaceClass, file, packageName + "." + file.getName()));
                    } else {
                        $results.addAll(findResources(interfaceClass, file, file.getName()));
                    }
                }
            } else if (file.getName().endsWith(".class")) {
                Class<?> clazz = null;
                if (!packageName.equals("/")) {
                    clazz = Class.forName(packageName + "." + file.getName().substring(0, file.getName().length() - 6));
                } else {
                    clazz = Class.forName(file.getName().substring(0, file.getName().length() - 6));
                }
                if (interfaceClass.isAssignableFrom(clazz) && !interfaceClass.equals(clazz)) {
                    $results.add(clazz);
                }
            }
        }
        return $results;
    }

    /**
     * 深度克隆对象。
     *
     * <pre>
     * 	将一个对象序列化一个内存数据组。
     *  在从数组中反序列化这个类。
     * </pre>
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static Object cloneObject(Object obj) throws Exception {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(obj);
        out.close();

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);

        Object rtn = in.readObject();
        in.close();
        return rtn;
    }

    /**
     * 获取classes路径。
     * @throws URISyntaxException
     */
    public static String getClassesPath() {
        String path = FileUtil.class.getClassLoader().getResource("").getPath();
        if ("\\".equals(File.separator)) {
            path = StringUtil.trimPrefix(path, "/");
        }
        path = path.replace("\\", "/");
        path = StringUtil.trimSuffix(path, "/");
        return path;
    }
    
    
    public static void main(String[] args) {
        System.out.println(FileUtil.getClassesPath());
    }

    /**
     * 获取应用程序根路径。
     *
     * @return 返回如下路径 E:/work/bpm/src/main/webapp
     * @throws Exception
     */
    public static String getWebRootPath() {
        String path = getClassesPath();
        path = path.substring(0, path.lastIndexOf("WEB-INF"));
        path = StringUtil.trimSuffix(path, "/");
        return path;
    }

    /**
     * 将输入流写入到输出流中。
     *
     * @param is
     * @param out
     * @throws IOException
     */
    public static void writeInput(InputStream is, OutputStream out) throws IOException {
        try {

            byte[] bs = new byte[1024];
            int n = 0;
            while ((n = is.read(bs)) != -1) {
                out.write(bs, 0, n);
            }
            out.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (is != null)
                is.close();
            if (out != null)
                out.close();
        }
    }
    
    /**
     * <pre>
     * inputstream转file
     * </pre>	
     * @param ins
     * @return
     */
	public static File inputstream2file(InputStream ins,File file) {
		try {
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
			return file;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
