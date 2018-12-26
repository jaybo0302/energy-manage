/**
 * 
 */
package com.cdwoo.controller;

import com.cdwoo.common.Constants;
import com.cdwoo.utils.Base64Utils;
import com.cdwoo.utils.RSAUtils;

/**
 * @author cd
 *
 */
public class Test {
	public static void main(String[] args) throws Exception {
		System.out.println(RSAUtils.decryptDataOnJava(new String(Base64Utils.decode(Base64Utils.encode("i/gdMbVifvNACRIUljdratySBJYAAz6jfb18EQQOJlBTPpz2Zt7/OMvWrercY11VqOv3cBNwZFqd5VA8aBjj/go+wwWoItagWGbHB/gyran1AXdk5VUEImA6a9thApDrRfpjWwjOqRaLHJcIoGXRL3xLLHyLiX9Kyko5eBxVVsU=".getBytes()))), Constants.PRIVATEKEY));
		System.out.println(Base64Utils.encode("i/gdMbVifvNACRIUljdratySBJYAAz6jfb18EQQOJlBTPpz2Zt7/OMvWrercY11VqOv3cBNwZFqd5VA8aBjj/go+wwWoItagWGbHB/gyran1AXdk5VUEImA6a9thApDrRfpjWwjOqRaLHJcIoGXRL3xLLHyLiX9Kyko5eBxVVsU=".getBytes()));
	}
}
