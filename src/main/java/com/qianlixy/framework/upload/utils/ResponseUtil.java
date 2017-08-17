package com.qianlixy.framework.upload.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class ResponseUtil {

	public static void reponseText(HttpServletResponse response, int sc, String text) throws IOException {
		response.setStatus(sc);
		PrintWriter writer = response.getWriter();
		writer.write(text);
		writer.flush();
		writer.close();
	}
	
}
