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
	
	private static SimpleDateFormat directoryFormater = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat fileNameFormater = new SimpleDateFormat("HHmmssSSS");

	@Autowired
	private FileUploadConfig config;
	
	@Override
	public String persistence(MultipartFile multipartFile) throws IOException {
		String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
		String path = getAbsolutePath() + fileNameFormater.format(new Date()).toString()
				+ (extension.length() == 0 ? "" : "." + extension);
		File file = new File(path);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			fos.write(multipartFile.getBytes());
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
		return path.replace(config.getRootPath(), "");
	}

	private String getAbsolutePath() {
		String directory = config.getRootPath() 
				+ "/"
				+ directoryFormater.format(new Date()).toString() 
				+ "/";
		File file = new File(directory);
		if(!file.exists()) {
			file.mkdirs();
		}
		return directory;
	}

}
