package com.qianlixy.framework.upload.intercepter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.qianlixy.framework.upload.utils.ResponseUtil;

public class ValidateIntercepter implements HandlerInterceptor {
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//ignore
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mv)
			throws Exception {
		//ignore
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		MultipartHttpServletRequest multipartReq = (MultipartHttpServletRequest) request;
		return validateRequiredParam(multipartReq, response);
	}

	private boolean validateRequiredParam(MultipartHttpServletRequest multipartReq, HttpServletResponse response)
			throws IOException {
		if(null != multipartReq.getFileMap() && multipartReq.getFileMap().size() > 0) {
			Map<String, MultipartFile> fileMap = multipartReq.getFileMap();
			for(String param : fileMap.keySet()) {
				if(!fileMap.get(param).isEmpty()) return true;
			}
			ResponseUtil.responseText(response, HttpServletResponse.SC_BAD_REQUEST, "The file is required");
			return false;
		}
		return false;
	}

}
