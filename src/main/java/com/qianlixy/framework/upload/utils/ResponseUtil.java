package com.qianlixy.framework.upload.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.bitwalker.useragentutils.UserAgent;

public class ResponseUtil {
	
	private static final String CHARACTER = "UTF-8";

	public static void responseText(HttpServletResponse response, int sc, String text) {
		response.setStatus(sc);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(text);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(null != writer) {
				writer.flush();
				writer.close();
			}
		}
	}
	
	public static void responseDownloadFile(HttpServletRequest request, HttpServletResponse response, File file, String filename)
			throws FileNotFoundException, IOException {
		response.reset();
		response.setCharacterEncoding(CHARACTER);
		if(null != filename) {
			if (UserAgent.parseUserAgentString(request.getHeader("User-Agent"))
					.getBrowser().toString().indexOf("IE") >= 0) {
				filename = URLEncoder.encode(filename, CHARACTER);
			} else {
				filename = new String(filename.getBytes(CHARACTER), "ISO-8859-1");
			}
			response.addHeader("Content-Disposition", "attachment; filename=" + filename);
		}
        response.addHeader("Content-Length", String.valueOf(file.length()));
		response.setContentType("application/octet-stream");
		outputFile(file, response.getOutputStream());
	}
	
	public static void responseFile(HttpServletResponse response, File file)
			throws FileNotFoundException, IOException {
		response.reset();
		outputFile(file, response.getOutputStream());
	}
	
	private static void outputFile(File file, OutputStream stream) throws FileNotFoundException {
		if(!file.exists()) {
			throw new FileNotFoundException();
		}
		OutputStream os = null;
		InputStream fis = null;
		try {
			fis = new BufferedInputStream(new FileInputStream(file));
			os = new BufferedOutputStream(stream);
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			os.write(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null != os) {
				try {
					os.flush();
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
