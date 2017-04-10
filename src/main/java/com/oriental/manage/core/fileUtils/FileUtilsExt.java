package com.oriental.manage.core.fileUtils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件工具类
 * @author hj
 * 2013-3-28 下午12:23:25
 */
public class FileUtilsExt extends FileUtils {
	private static Logger LOG = LoggerFactory.getLogger(FileUtilsExt.class);

	/**
	 * 文件后面添加指定的字符，用于文件压缩 （如：a.txt  需要得到a_1.txt）
	 * @param fileName 如 a.txt
	 * @param appendString 如  1
	 * @param splitFlag 如 "_"
	 * @return 如 a_1.txt
	 */
	public static String getFileNameByAppendString(String fileName, String appendString, String splitFlag){
		String result;
		if(!fileName.contains(".")){
			result = fileName;
		}else{
			int index = fileName.lastIndexOf('.');
			result = fileName.substring(0, index) + splitFlag + appendString + fileName.substring(index, fileName.length());
		}
		return result;
	}

	/**
	 * 把接受的全部文件打成压缩包
	 * @param files 文件名称 或 文件 集合
	 * @param targetFileName 目标压缩包名
	 */
	public static <T> void zipFile(List<T> files, String targetFileName) {
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		try {
			fos = new FileOutputStream(new File(targetFileName), false);
			zos = new ZipOutputStream(fos);
			for (T t : files) {
                File file;
                if(t instanceof String){
                    String absolutePath = (String) t;
                    file = new File(absolutePath);
                }else if(t instanceof File){
                    file = (File) t;
                }else{
                    throw new RuntimeException("FileUtilsExt.zipFile()方法, 参数List中只能存放String或File类型!");
                }
				zipFile(file, zos);
			}
			zos.flush();
		} catch (FileNotFoundException e) {
			LOG.error("异常",e);
		} catch (IOException e) {
			LOG.error("异常",e);
		} finally {
			IOUtils.closeQuietly(zos);
			IOUtils.closeQuietly(fos);
		}
	}

    /**
     * 把接受的文件打成压缩包
     * @param file 文件
     * @param targetFileName 目标压缩包名
     */
    public static void zipFile(File file, String targetFileName) {
        List<File> files = new ArrayList<File>();
        files.add(file);
        zipFile(files, targetFileName);
    }

	/**  
	 * 根据输入的文件与输出流对文件进行打包
	 * @param inputFile
     * @param outputStream
	 */
	public static void zipFile(File inputFile, ZipOutputStream outputStream) {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		try {
			if (inputFile.exists()) {
				if (inputFile.isFile()) {
					fis = new FileInputStream(inputFile);
					bis = new BufferedInputStream(fis, 512);
					ZipEntry entry = (ZipEntry) new ZipEntry(inputFile.getName());
                    outputStream.putNextEntry((ZipEntry) entry);
					// 向压缩文件中输出数据   
					int nNumber;
					byte[] buffer = new byte[512];
					while ((nNumber = bis.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, nNumber);
					}
				} else {
					File[] files = inputFile.listFiles();
					for (int i = 0; i < files.length; i++) {
						zipFile(files[i], outputStream);
					}
				}
			}
		} catch (Exception e) {
			LOG.error("异常",e);
		} finally {
			IOUtils.closeQuietly(bis);
			IOUtils.closeQuietly(fis);
		}
	}
	
	/**
	 * 判断是否已存在
	 * @param filename 文件绝对路径
	 * @return 是否存在
	 */
	public static boolean exists(String filename) {
        return new File(filename).exists();
    }

	/**
	 * 多级目录创建
	 * create File xiabin add 2011-08-21 18:30:10
	 * @param path
	 */
	public static void writeFile(String path){
		path = path.replace("\\", "/");
		if(!path.subSequence(path.length() -1, path.length()).equals("/")){
			path+="/";
		}
		String[] newpath = path.substring(0,path.lastIndexOf('/')).split("/");
        StringBuffer buf = new StringBuffer();
		for (int i = 0; i < newpath.length; i++) {
            buf.append(newpath[i]+"/");
			File file = new File(buf.toString());
			if(!file.exists()){
				file.mkdir();
			}
		}
	}
}
