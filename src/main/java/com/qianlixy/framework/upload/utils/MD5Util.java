package com.qianlixy.framework.upload.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

	public static String MD5(File file) {
        FileInputStream fis = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length = -1;
            while ((length = fis.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }
            BigInteger bigInt = new BigInteger(1, md.digest());
            return bigInt.toString(16); 
        } catch (IOException ex) {
        	ex.printStackTrace();
        } catch (NoSuchAlgorithmException ex) {
        	ex.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
            	ex.printStackTrace();
            }
        }
        return null;
    }
}
