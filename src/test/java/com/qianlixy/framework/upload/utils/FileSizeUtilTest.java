package com.qianlixy.framework.upload.utils;

import org.junit.Test;

public class FileSizeUtilTest {

	@Test
	public void test() {
//		System.out.println(FileSizeUtil.prettySize(FileSizeUtil.toSize("11")));
//		System.out.println(FileSizeUtil.prettySize(FileSizeUtil.toSize("11B")));
//		System.out.println(FileSizeUtil.prettySize(FileSizeUtil.toSize("11KB")));
//		System.out.println(FileSizeUtil.prettySize(FileSizeUtil.toSize("11MB")));
//		System.out.println(FileSizeUtil.prettySize(FileSizeUtil.toSize("11GB")));
//		System.out.println(FileSizeUtil.prettySize(FileSizeUtil.toSize("11TB")));
//		System.out.println(FileSizeUtil.prettySize(FileSizeUtil.toSize("11K")));
//		System.out.println(FileSizeUtil.prettySize(FileSizeUtil.toSize("11M")));
//		System.out.println(FileSizeUtil.prettySize(FileSizeUtil.toSize("11G")));
//		System.out.println(FileSizeUtil.prettySize(FileSizeUtil.toSize("11T")));
		System.out.println(FileSizeUtil.prettySize(FileSizeUtil.toSize("11k")));
		System.out.println(FileSizeUtil.prettySize(FileSizeUtil.toSize("11m")));
		System.out.println(FileSizeUtil.prettySize(FileSizeUtil.toSize("11g")));
		System.out.println(FileSizeUtil.prettySize(FileSizeUtil.toSize("11t")));
	}
	
}
