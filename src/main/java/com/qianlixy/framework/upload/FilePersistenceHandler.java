package com.qianlixy.framework.upload;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FilePersistenceHandler {

	String persistence(MultipartFile file) throws IOException;

}
