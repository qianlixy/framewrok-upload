package com.qianlixy.framework.upload.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.qianlixy.framework.upload.FilePersistenceHandler;
import com.qianlixy.framework.upload.vo.UploadResult;

@RestController("defaultController")
@RequestMapping("/")
public class DefaultController {
	
	@Autowired
	private FilePersistenceHandler fileHandler;
	
	private static final Logger log = LoggerFactory.getLogger(DefaultController.class);

	private UploadResult upload(MultipartFile file) {
		UploadResult ur = new UploadResult();
		try {
			String path = fileHandler.persistence(file);
			ur.setResult(true);
			ur.setMessage("File upload successed");
			ur.setImagePath(path);
		} catch (Exception e) {
			log.error("File upload fail", e);
			ur.setResult(false);
			ur.setMessage(e.getMessage());
		}
		ur.setFileSize(file.getSize());
		ur.setOriginName(FilenameUtils.getBaseName(file.getOriginalFilename()));
		return ur;
	}
	
	@RequestMapping("upload")
	public List<UploadResult> multiUpload(MultipartHttpServletRequest request) {
		Map<String, MultipartFile> fileMap = request.getFileMap();
		List<UploadResult> results = new ArrayList<>();
		for(String param : fileMap.keySet()) {
			MultipartFile multipartFile = fileMap.get(param);
			results.add(upload(multipartFile));
		}
		return results;
	}
	
}
