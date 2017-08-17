package com.qianlixy.framework.upload.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.qianlixy.framework.upload.FilePersistenceHandler;
import com.qianlixy.framework.upload.config.FileUploadConfig;

@Component
public class FilePersistenceFactory {

	@Autowired
	private FileUploadConfig config;

	@Bean
	public FilePersistenceHandler newHandler() throws Exception {
		if(null == config.getHandler()) {
			return new DefaultFilePersistenceHandler();
		}
		FilePersistenceHandler newInstance = null;
		try {
			newInstance = (FilePersistenceHandler) Class.forName(config.getHandler()).newInstance();
		} catch (ClassNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception("Cannot instance file persistence handler", e);
		}
		return newInstance;
	}

}
