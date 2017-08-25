package com.qianlixy.framework.upload.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qianlixy.framework.upload.FilePersistenceHandler;
import com.qianlixy.framework.upload.exception.LimitSizeException;
import com.qianlixy.framework.upload.utils.FileSizeUtil;
import com.qianlixy.framework.upload.vo.UploadResult;

@RestController("defaultController")
public class UploadController {
	
	private static final String PARAM_FILE_LIMIT_SIZE = "limitSize";
	
	@Autowired
	private FilePersistenceHandler fileHandler;
	
	private static final Logger log = LoggerFactory.getLogger(UploadController.class);

	private UploadResult uploadFile(MultipartFile file, String limitSize) {
		if(null != limitSize) {
			long limit = FileSizeUtil.toSize(limitSize);
			if(limit > 0 && limit < file.getSize()) {
				throw new LimitSizeException("The file size is greater than " + FileSizeUtil.prettySize(limit));
			}
		}
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
	
	@RequestMapping(value="upload",method=RequestMethod.POST)
	public String multiUpload(MultipartHttpServletRequest request, HttpServletResponse response) {
		String limitSize = request.getParameter(PARAM_FILE_LIMIT_SIZE);
		Map<String, MultipartFile> fileMap = request.getFileMap();
		List<UploadResult> results = new ArrayList<>();
		for(String param : fileMap.keySet()) {
			MultipartFile file = fileMap.get(param);
			if(!file.isEmpty()) {
				try {
					results.add(uploadFile(file, limitSize));
				} catch (LimitSizeException e) {
					results.add(new UploadResult(false, e.getMessage()));
				} catch (Exception e) {
					results.add(new UploadResult(false, "Unknown reason"));
				}
			}
		}
		
		if(results.size() == 1){
			return JSONObject.toJSONString(results.get(0));
		}
		return JSONArray.toJSONString(results);
	}
	
}
