package com.oriental.manage.core.fileUtils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * 将文件打包成ZIP压缩文件
 */
public class FileDownloadUtils {
	private static Logger LOG = LoggerFactory.getLogger(FileDownloadUtils.class);

	/**
	 * 提供页面文件下载 
	 */
	public static void responseFileToWeb(HttpServletResponse response, File file){
		if (file != null) {
			ServletOutputStream outputStream = null;
			try {
				response.reset();
				response.setContentType("application/x-msdownload");
				response.setHeader("Content-Disposition", "attachment; filename=" +
								URLEncoder.encode(file.getName(), "UTF-8"));
				outputStream = response.getOutputStream();  
				outputStream.write(org.apache.commons.io.FileUtils.readFileToByteArray(file));
			} catch (UnsupportedEncodingException e) {
                LOG.error("异常：{}",e);
			} catch (IOException e) {
				LOG.error("异常：{}",e);
			} finally {
				try {
					if(outputStream != null){
						outputStream.flush();  
						IOUtils.closeQuietly(outputStream);
					}
					if(file.exists()){
						file.delete();
					}
				} catch (IOException e) {
					LOG.error("异常：{}",e);
				}
			}
		}
	}
	/**
	 * 提供页面文件下载
	 * 
	 */
	public static boolean downloadFileToWeb(File file, String path,
											HttpServletResponse response, HttpServletRequest request) {
		boolean result = false;
		if(null==file){
			return result;
		}else if(!file.exists() && !file.isDirectory()){
			return result;
		}
		OutputStream out = null;
		BufferedInputStream bin = null;
		try {
			response.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");
			response.addHeader("Content-Disposition", "attachment; filename="
					+ java.net.URLEncoder.encode(file.getName(), "UTF-8"));
			response.setContentType("application/octet-stream");
			out = response.getOutputStream();
			bin = new BufferedInputStream(new FileInputStream(file.getPath()));
			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = bin.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			result = true;
		} catch (UnsupportedEncodingException e) {
			LOG.error("异常：{}",e);
		} catch (FileNotFoundException e) {
			LOG.error("异常：{}",e);
		} catch (IOException e) {
			LOG.error("异常：{}",e);
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(bin);
			if(file.exists()){
				file.delete();
			}
		}
		return result;
	}
}
