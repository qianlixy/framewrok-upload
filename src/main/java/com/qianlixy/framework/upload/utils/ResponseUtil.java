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
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

public class ResponseUtil {
	
	private static final String CHARACTER = "UTF-8";

	public static void responseText(HttpServletResponse response, int sc, String text) throws IOException {
		response.setStatus(sc);
		PrintWriter writer = response.getWriter();
		writer.write(text);
		writer.flush();
		writer.close();
	}
	
	public static void responseFile(HttpServletResponse response, File file, String filename) throws FileNotFoundException {
		if(!file.exists()) {
			throw new FileNotFoundException();
		}
		response.reset();
		response.setCharacterEncoding(CHARACTER);
		try {
			response.addHeader("Content-Disposition", "attachment; filename=" + new String(filename.getBytes(CHARACTER), "ISO8859-1"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
        response.addHeader("Content-Length", String.valueOf(file.length()));
//		response.setContentType("application/octet-stream");
		OutputStream os = null;
		InputStream fis = null;
		try {
			fis = new BufferedInputStream(new FileInputStream(file));
			os = new BufferedOutputStream(response.getOutputStream());
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
