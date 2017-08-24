package com.qianlixy.framework.upload.vo;

import com.qianlixy.framework.upload.utils.FileSizeUtil;

public class UploadResult {
	
	public UploadResult() {}
	
	public UploadResult(Boolean result, String message) {
		this.result = result;
		this.message = message;
	}

	private Boolean result;
	private String message;
	private String originName;
	private String imagePath;
	private Long fileSize;
	private String prettySize;

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getPrettySize() {
		if (null != prettySize) {
			return prettySize;
		}
		if(null == fileSize) {
			return null;
		}
		return FileSizeUtil.prettySize(fileSize);
	}

	public void setPrettySize(String prettySize) {
		this.prettySize = prettySize;
	}

}
