package com.qianlixy.framework.upload.utils;

import java.io.File;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5UtilTest {
	
	private static final Logger log = LoggerFactory.getLogger(MD5UtilTest.class);
	
	private Date start;
	
	@Before
	public void before() {
		start = new Date();
	}
	
	@After
	public void after() {
		log.info("耗费秒数 = {}s", (new Date().getTime() - start.getTime()) / 1000F);
	}

	@Test
	public void testMD5_30_34MB() {
		File file = new File("E:\\software\\VC6.0_Win7_XP85.rar");
		log.info(MD5Util.MD5(file));
	}
	
	@Test
	public void testMD5_187_41MB() {
		File file = new File("E:\\software\\jdk-8u91-windows-x64.exe");
		log.info(MD5Util.MD5(file));
	}
	
	@Test
	public void testMD5_871_77MB() {
		File file = new File("E:\\software\\cn_office_professional_plus_2013_x64_dvd_1134006.iso.emule.td");
		log.info(MD5Util.MD5(file));
	}
	
	@Test
	public void testMD5_3_88GB() {
		File file = new File("E:\\software\\cn_windows_10_enterprise_x64_dvd_6846957.iso");
		log.info(MD5Util.MD5(file));
	}
}
