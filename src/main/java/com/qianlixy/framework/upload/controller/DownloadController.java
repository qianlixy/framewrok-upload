package com.qianlixy.framework.upload.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qianlixy.framework.upload.config.FileUploadConfig;
import com.qianlixy.framework.upload.utils.ResponseUtil;

@Controller
public class DownloadController {
	
	@Autowired
	private FileUploadConfig config;

	@RequestMapping("/download/{path}/{filename}")
	public void download(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String path,	@PathVariable String filename,
			@RequestParam(value="n", required=false) String downloadFileName,
			@RequestParam(value="s", required=true) String suffix) {
		filename = filename + "." + suffix;
		if(null == downloadFileName) {
			downloadFileName = filename;
		} else {
			downloadFileName += ("." + suffix);
		}
		File file = new File(FilenameUtils.concat(config.getRootPath(), path + "/" + filename));
		try {
			ResponseUtil.responseDownloadFile(request, response, file, downloadFileName);
		} catch (FileNotFoundException e) {
			ResponseUtil.responseText(response, HttpServletResponse.SC_NOT_FOUND, "Not found file : " + filename);
		} catch (IOException e) {
			ResponseUtil.responseText(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@RequestMapping("/{path:\\d+}/{filename}")
	public void preview(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String path,	@PathVariable String filename,
			@RequestParam(value="s", required=true) String suffix) {
		filename = filename + "." + suffix;
		File file = new File(FilenameUtils.concat(config.getRootPath(), path + "/" + filename));
		try {
			ResponseUtil.responseFile(response, file);
		} catch (FileNotFoundException e) {
			ResponseUtil.responseText(response, HttpServletResponse.SC_NOT_FOUND, "Not found file : " + filename);
		} catch (IOException e) {
			ResponseUtil.responseText(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
