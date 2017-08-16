package com.qianlixy.framework.upload.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qianlixy.framework.upload.vo.UploadResult;

@RestController("defaultController")
@RequestMapping("/")
public class DefaultController {

	@RequestMapping("upload")
	public UploadResult upload() {
		return null;
	}
	
	@RequestMapping("multi-upload")
	public List<UploadResult> multiUpload() {
		return null;
	}
	
}
