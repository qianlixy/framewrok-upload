package com.qianlixy.framework.upload.persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.qianlixy.framework.upload.FilePersistenceHandler;
import com.qianlixy.framework.upload.config.FileUploadConfig;

public class DefaultFilePersistenceHandler implements FilePersistenceHandler {
	
	@Autowired
	private FileUploadConfig config;
	
	@Override
	public String persistence(MultipartFile multipartFile) throws IOException {
		String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
		String path = getAbsolutePath() + new SimpleDateFormat("HHmmssSSS").format(new Date()).toString()
				+ (extension.length() == 0 ? "" : "." + extension);
		File file = new File(path);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			byte[] bytes = multipartFile.getBytes();
			fos.write(bytes);
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			if(null != fos) {
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					//ignore
				}
			}
		}
		String newPath = path.replace(config.getRootPath(), "");
		return newPath.substring(0, newPath.lastIndexOf(".")) + "?s=" + extension;
	}

	private String getAbsolutePath() {
		String directory = config.getRootPath() 
				+ "/"
				+ new SimpleDateFormat("yyyyMMdd").format(new Date()).toString() 
				+ "/";
		File file = new File(directory);
		if(!file.exists()) {
			synchronized (DefaultFilePersistenceHandler.class) {
				if(!file.exists()) {
					file.mkdirs();
				}
			}
		}
		return directory;
	}

}
