package com.qianlixy.framework.upload.utils;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.qianlixy.framework.upload.exception.InvalidFileSizeException;

public class FileSizeUtil {
	
	public static final long FILE_SIZE_B = 1L;
	public static final long FILE_SIZE_KB = 1024L;
	public static final long FILE_SIZE_MB = 1048576L;
	public static final long FILE_SIZE_GB = 1073741824L;
	public static final long FILE_SIZE_TB = 1099511627776L;
	
	public static final String FILE_SUFFIX_B = "B";
	public static final String FILE_SUFFIX_KB = "KB";
	public static final String FILE_SUFFIX_MB = "MB";
	public static final String FILE_SUFFIX_GB = "GB";
	public static final String FILE_SUFFIX_TB = "TB";
	
	private static Map<String, Long> fileSizeMap;
	
	static {
		fileSizeMap = new HashMap<>();
		fileSizeMap.put(FILE_SUFFIX_B, FILE_SIZE_B);
		fileSizeMap.put(FILE_SUFFIX_KB, FILE_SIZE_KB);
		fileSizeMap.put(FILE_SUFFIX_MB, FILE_SIZE_MB);
		fileSizeMap.put(FILE_SUFFIX_GB, FILE_SIZE_GB);
		fileSizeMap.put(FILE_SUFFIX_TB, FILE_SIZE_TB);
	}

	public static String prettySize(long fileSize) {
		DecimalFormat df = new DecimalFormat("#.##");
		if (fileSize < FILE_SIZE_KB) {
			return df.format((double) fileSize) + FILE_SUFFIX_B;
		} else if (fileSize < FILE_SIZE_MB) {
			return df.format((double) fileSize / FILE_SIZE_KB) + FILE_SUFFIX_KB;
		} else if (fileSize < FILE_SIZE_GB) {
			return df.format((double) fileSize / FILE_SIZE_MB) + FILE_SUFFIX_MB;
		} else if (fileSize < FILE_SIZE_TB) {
			return df.format((double) fileSize / FILE_SIZE_GB) + FILE_SUFFIX_GB;
		} else {
			return df.format((double) fileSize / FILE_SIZE_TB) + FILE_SUFFIX_TB;
		}
	}
	
	public static long toSize(String sizeStr) {
		if(null == sizeStr || "".equals(sizeStr)) return 0L;
		Matcher matcher = Pattern.compile("^[\\d]+([KMGTB]{0,2})$", Pattern.CASE_INSENSITIVE)
				.matcher(sizeStr);
		if(!matcher.find()) {
			throw new InvalidFileSizeException("Invalid size of file : '" + sizeStr + "'");
		}
		String suffix = matcher.group(1).toUpperCase();
		Long cardinal = fileSizeMap.get(suffix.contains(FILE_SUFFIX_B) ? suffix : suffix + FILE_SUFFIX_B);
		if(null == cardinal) {
			throw new InvalidFileSizeException("Invalid size of file : '" + sizeStr + "'");
		}
		return Long.valueOf(sizeStr.toUpperCase().replace(suffix, "")) * cardinal;
	}
	
}
