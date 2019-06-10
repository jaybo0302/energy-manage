package com.cdwoo.utils;

public class HexUtil {
	public static byte[] hex2byte(String hex) {
        String digital = "0123456789ABCDEF";
        String hex1 = hex.replace(" ", "").toUpperCase();
        char[] hex2char = hex1.toCharArray();
        byte[] bytes = new byte[hex1.length() / 2];
        byte temp;
        for (int p = 0; p < bytes.length; p++) {
            temp = (byte) (digital.indexOf(hex2char[2 * p]) * 16);
            temp += digital.indexOf(hex2char[2 * p + 1]);
            bytes[p] = (byte) (temp & 0xff);
        }
        return bytes;
    }
	
	public static String hex2String(byte[] b, int length) {
    	StringBuffer sb = new StringBuffer();
    	for(int i = 0; i < length; i++) {
    		String hex = Integer.toHexString(0xFF & b[i]);
    		if(hex.length() == 1) {
    			sb.append("0");
    		}
    		sb.append(hex);
    		sb.append(" ");
    	}
    	String s = sb.toString();
    	return s;
    }
}
