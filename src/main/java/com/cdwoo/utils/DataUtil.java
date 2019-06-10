/**
 * 
 */
package com.cdwoo.utils;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.record.formula.Ptg;
import org.springframework.beans.factory.annotation.Autowired;

import com.cdwoo.common.CDLogger;

import gnu.io.SerialPort;

/**
 * @author cd
 *
 */
public class DataUtil {
	public static Map<Integer, Integer> ptMap = new HashMap<>();
	public static Map<Integer, Integer> ctMap = new HashMap<>();
	static {
		ptMap.put(1, 100);
		ptMap.put(2, 100);
		ptMap.put(3, 100);
		ptMap.put(4, 100);
		ptMap.put(5, 100);
		ptMap.put(6, 100);
		ptMap.put(7, 100);
		ptMap.put(8, 100);
		ptMap.put(9, 100);
		ptMap.put(10, 100);
		ptMap.put(11, 100);
		ptMap.put(12, 100);
		ptMap.put(13, 100);
		ptMap.put(14, 100);
		ptMap.put(15, 100);
		ptMap.put(16, 100);
		ptMap.put(17, 100);
		ptMap.put(18, 100);
		ptMap.put(19, 100);
		ptMap.put(20, 100);
		ptMap.put(21, 100);
		ptMap.put(22, 100);
		ptMap.put(23, 100);
		ptMap.put(24, 100);
		ptMap.put(25, 100);
		ptMap.put(26, 100);
		ptMap.put(27, 100);
		ptMap.put(28, 100);
		ptMap.put(29, 100);
		
		ctMap.put(1, 200);
		ctMap.put(2, 60);
		ctMap.put(3, 60);
		ctMap.put(4, 60);
		ctMap.put(5, 60);
		ctMap.put(6, 60);
		ctMap.put(7, 60);
		ctMap.put(8, 60);
		ctMap.put(9, 60);
		ctMap.put(10, 60);
		ctMap.put(11, 60);
		ctMap.put(12, 60);
		ctMap.put(13, 60);
		ctMap.put(14, 60);
		ctMap.put(15, 60);
		ctMap.put(16, 60);
		ctMap.put(17, 60);
		ctMap.put(18, 60);
		ctMap.put(19, 60);
		ctMap.put(20, 60);
		ctMap.put(21, 120);
		ctMap.put(22, 120);
		ctMap.put(23, 120);
		ctMap.put(24, 120);
		ctMap.put(25, 120);
		ctMap.put(26, 120);
		ctMap.put(27, 120);
		ctMap.put(28, 120);
		ctMap.put(29, 320);
	}
	
	/**
	 * 前面补0
	 * @param args
	 * @throws Exception 
	 */
	
	public static String add02front(String pre, int num) {
		for (int i=0; i< num; i++) {
			pre = ("0" + pre);
		}
		return pre;
	}
	
	
	/**根据a2的值获取该字段的16进制字符串
	 * @param a2
	 * @return
	 */
	public static String getA2Hex(int a2) {
		String framesBin = Integer.toBinaryString(a2);
		if (framesBin.length() < 16) {
			framesBin = add02front(framesBin, 16-framesBin.length());
		}
		String frame1Bin = framesBin.substring(8, 12);
		String frame2Bin = framesBin.substring(12, 16);
		String frame3Bin = framesBin.substring(4, 8);
		String frame4Bin = framesBin.substring(0, 4);
		String frame1Hex = Integer.toHexString(Integer.parseInt(frame1Bin, 2));
		String frame2Hex = Integer.toHexString(Integer.parseInt(frame2Bin, 2));
		String frame3Hex = Integer.toHexString(Integer.parseInt(frame3Bin, 2));
		String frame4Hex = Integer.toHexString(Integer.parseInt(frame4Bin, 2));
		return frame1Hex + "" + frame2Hex + " " + frame3Hex + "" + frame4Hex;
	}
	
	/**根据msa的值和类型获取该字段的16进制字符串
	 * @param msa type
	 * @return
	 */
	public static String getA3Hex(int msa, int type) {
		return Integer.toHexString(Integer.parseInt(Integer.toBinaryString(msa) + type, 2));
	}
	public static String getControlHex(int dir, int prm, int fcb, int fcv, int funccode) {
		String bin = String.valueOf(dir) + String.valueOf(prm) + String.valueOf(fcb) + String.valueOf(fcv);
		String funcBin = Integer.toBinaryString(funccode);
		if (funcBin.length() < 4) {
			funcBin = add02front(funcBin, 4-funcBin.length());
		}
		bin = (bin + funcBin);
		String hex = Integer.toHexString(Integer.parseInt(bin, 2));
		if (hex.length()==1) {
			hex = add02front(hex, 1);
		}
		return hex;
	}

	public static byte[] parityOfOdd(byte[] bytes, int parity) throws Exception{
		if (bytes == null || bytes.length == 0) {
			throw new Exception("数据错误");
		}
		if (!(parity == 0 || parity == 1)) {
			throw new Exception("参数错误");
		}
		
		byte[] _bytes = bytes;
		
		String s;
		char[] cs;
		
		int count;
		
		boolean lastIsOne;
		for(int i=0;i<_bytes.length;i++) {
			 s = Integer.toBinaryString((int)_bytes[i]);
			 cs = s.toCharArray();
			 count = 0;
			 lastIsOne = false;
			 for (int j =0;j<s.length();j++) {
				 if (cs[j] == '1') {
					 count ++;
					 lastIsOne = true;
				 }
				 if (j == (cs.length - 1)) {
					if (cs[j] == '1') {
						 lastIsOne = true;
					}else {
						 lastIsOne = false;
				    }
				 }
			}
			if (count % 2 == parity) {
				if (lastIsOne) {
					_bytes[i] = (byte)(_bytes[i]-0x01);
				} else {
					_bytes[i] = (byte) (_bytes[i] + 0x01);
				}
			}
			
		}
		return _bytes;
	}
	
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
	 public static byte[] toBytes(String str) {
         if (str == null || str.trim().equals("")) {
              return new byte[0];
         }
         byte[] bytes = new byte[str.length() / 2];
         for (int i = 0; i < str.length() / 2; i++) {
             String subStr = str.substring(i * 2, i * 2 + 2);
             bytes[i] = (byte) Integer.parseInt(subStr, 16);
         }
         return bytes;
    }
	/**
	 * 帧校验和函数
	 * @param frames
	 * @return
	 */
	public static String frameCheck(String[] frames) {
		int sum = 0;
		for (String str : frames) {
			sum += Integer.parseInt(str, 16);
		}
		String result = Long.toHexString(sum);
		if (result.length() == 1) {
			result = add02front(result, 1);
		} else if (result.length() > 2) {
			result = result.substring(result.length() - 2,result.length());
		}
		return result;
	}
	
	
	/**
	 * 根据帧字段获取长度
	 * @param frames
	 * @return
	 */
	public static int getLength(String [] frames) {
		String frame1 = frames[0];
		String frame2 = frames[1];
		
		String frame1Bin = Integer.toBinaryString(Integer.parseInt(frame1, 16));
		if (frame1Bin.length() < 8) {
			frame1Bin = add02front(frame1Bin, 8 - frame1Bin.length());
		}
		if (frame1Bin.endsWith("10")) {
			frame1Bin = frame1Bin.substring(0, frame1Bin.length()-2);
		}
		String frame2Bin = Integer.toBinaryString(Integer.parseInt(frame2, 16));
		String lengthBin = frame2Bin+frame1Bin;
		return Integer.parseInt(lengthBin, 2);		
	}
	
	
	/**
	 * 根据长度值获取该字段的十六进制字符串
	 * @param length
	 * @return
	 */
	public static String length2Hex(int length) {
		String lengthBin = Integer.toBinaryString(length);
		
		String frame1 = lengthBin;
		if (lengthBin.length() > 6) {
			frame1 = lengthBin.substring(lengthBin.length() - 6, lengthBin.length());
		}
		frame1 = frame1+"10";
		frame1 = Integer.toHexString(Integer.parseInt(frame1,2));
		if (frame1.length() < 2) {
			add02front(frame1, 1);
		}
		String frame2 = "00";
		if (lengthBin.length() > 6) {
			frame2 = lengthBin.substring(0,lengthBin.length()-6);
			frame2 = Integer.toHexString(Integer.parseInt(frame2,2));
			if (frame2.length() < 2) {
				frame2 = add02front(frame2, 1);
			}
		}
		return frame1 + " " + frame2;
	}
	
	public static int hex2Length(String[] frames) {
		String frame1Hex = frames[0];
		String frame2Hex = frames[1];
		
		String frame1Bin = Integer.toBinaryString(Integer.parseInt(frame1Hex, 16));
		if (frame1Bin.length() < 8) {
			frame1Bin = add02front(frame1Bin, 8 - frame1Bin.length());
		}
		String frame2Bin = Integer.toBinaryString(Integer.parseInt(frame2Hex, 16));
		return Integer.parseInt(frame2Bin+frame1Bin.substring(0, frame1Bin.length() - 2), 2);
	}
	
	public static int hex2A2(String [] frames) {
		String frame1Hex = frames[0];
		String frame2Hex = frames[1];
		
		String frame1Bin = Integer.toBinaryString(Integer.parseInt(frame1Hex, 16));
		if (frame1Bin.length() < 8) {
			frame1Bin = add02front(frame1Bin, 8 - frame1Bin.length());
		}
		String frame2Bin = Integer.toBinaryString(Integer.parseInt(frame2Hex, 16));
		return Integer.parseInt(frame2Bin+frame1Bin, 2);
	}
	
	public static int hex2A3(String frame) {
		String frameBin = Integer.toBinaryString(Integer.parseInt(frame, 16));
		if (frameBin.length() < 8) {
			frameBin = add02front(frameBin, 8 - frameBin.length());
		}
		return Integer.parseInt(frameBin.substring(0, frameBin.length() - 1), 2);
	}
	
	public static String getA1(String[] frames) {
		String frame1 = frames[0];
		String frame2 = frames[1];
		return frame2 + " " + frame1;
	}
	
	public static int[] getSEQBin(String frame) {
		int[] result = new int[5];
		String seqBin = Integer.toBinaryString(Integer.parseInt(frame, 16));
		//tpv,fir,fin,con,rseq(pseq)
		if (seqBin.length() < 8) {
			seqBin = add02front(seqBin, 8 - seqBin.length());
		}
		result[0] = Integer.parseInt(seqBin.substring(0, 1));
		result[1] = Integer.parseInt(seqBin.substring(1, 2));
		result[2] = Integer.parseInt(seqBin.substring(2, 3));
		result[3] = Integer.parseInt(seqBin.substring(3, 4));
		result[4] = Integer.parseInt(seqBin.substring(4, 8), 2);
		return result;
	}
	
	/**
	 * F25 当前三相及总有/无功功率、功率因数，三相电压、电流、零序电流
	 * @param datas
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void f25(String[] datas, int deviceNo) throws ClassNotFoundException, SQLException{
		Map<String, Object> result = new HashMap<>();
		/**
		 * 68 3E 01 3E 01 68 88 10 03 03 00 F2 0C 7C 01 01 
		01 03 41 14 28 09 18 EE EE EE EE EE EE EE EE EE 
		EE EE EE EE EE EE EE EE EE EE EE EE EE EE EE EE 
		EE EE EE EE EE EE EE EE EE EE EE EE EE EE EE EE 
		EE EE EE EE EE EE EE EE EE EE EE EE EE EE EE EE 
		EE EE EE EE EE 60 16
		 */
		
//		String c = datas[6];
//		String a1 = datas[8] + datas[7];
//		int a2 = hex2A2(new String[] {datas[9], datas[10]});
//		int a3 = hex2A3(datas[11]);
//		String afn = datas[12];
//		String seq = datas[13];
//		int[] seqBin = getSEQBin(seq);
//		int tpv = seqBin[0];
//		int fin = seqBin[1];
//		int con = seqBin[2];
//		int rseq = seqBin[3];
//		int da1 = Integer.parseInt(datas[14], 16);
//		int da2 = Integer.parseInt(datas[15], 16);
//		int dt1 = Integer.parseInt(datas[16], 16);
//		int dt2 = Integer.parseInt(datas[17], 16);
//		int fn = da2*8+ Integer.toBinaryString(da1).length();
//		int pn = (dt2-1)*8+ Integer.toBinaryString(dt1).length();
		String dateTime = "20" + datas[4] + "-" + datas[3] + "-" + datas[2] + " " + datas[1] + ":" + datas[0];
		
		//当前总有功功率 
		String activePowerStr = datas[7] + "." + datas[6] + datas[5];
		if (activePowerStr.toLowerCase().contains("e")) {
			activePowerStr = "0";
		} else {
			activePowerStr = String.valueOf(Float.parseFloat(activePowerStr)*ptMap.get(deviceNo)*ctMap.get(deviceNo));
		}
		//当前A相有功功率
		String aActivePowerStr = datas[10] + "." + datas[9] + datas[8];
		if (aActivePowerStr.toLowerCase().contains("e")) {
			aActivePowerStr = "0";
		} else {
			aActivePowerStr = String.valueOf(Float.parseFloat(aActivePowerStr)*ptMap.get(deviceNo)*ctMap.get(deviceNo));
		}
		//当前B相有功功率
		String bActivePowerStr = datas[13] + "." + datas[12] + datas[11];
		if (bActivePowerStr.toLowerCase().contains("e")) {
			bActivePowerStr = "0";
		} else {
			bActivePowerStr = String.valueOf(Float.parseFloat(bActivePowerStr)*ptMap.get(deviceNo)*ctMap.get(deviceNo));
		}
		//当前C相有功功率
		String cActivePowerStr = datas[16] + "." + datas[15] + datas[14];
		if (cActivePowerStr.toLowerCase().contains("e")) {
			cActivePowerStr = "0";
		} else {
			cActivePowerStr = String.valueOf(Float.parseFloat(cActivePowerStr)*ptMap.get(deviceNo)*ctMap.get(deviceNo));
		}
		//当前总无功功率 
		String reactivePowerStr = datas[19] + "." + datas[18] + datas[17];
		if (reactivePowerStr.toLowerCase().contains("e")) {
			reactivePowerStr = "0";
		} else {
			reactivePowerStr = String.valueOf(Float.parseFloat(reactivePowerStr)*ptMap.get(deviceNo)*ctMap.get(deviceNo));
		}
		//当前A相无功功率
		String aReactivePowerStr = datas[22] + "." + datas[21] + datas[20];
		if (aReactivePowerStr.toLowerCase().contains("e")) {
			aReactivePowerStr = "0";
		} else {
			aReactivePowerStr = String.valueOf(Float.parseFloat(aReactivePowerStr)*ptMap.get(deviceNo)*ctMap.get(deviceNo));
		}
		//当前B相无功功率
		String bReactivePowerStr = datas[25] + "." + datas[24] + datas[23];
		if (bReactivePowerStr.toLowerCase().contains("e")) {
			bReactivePowerStr = "0";
		} else {
			bReactivePowerStr = String.valueOf(Float.parseFloat(bReactivePowerStr)*ptMap.get(deviceNo)*ctMap.get(deviceNo));
		}
		//当前C相无功功率
		String cReactivePowerStr = datas[28] + "." + datas[27] + datas[26];
		if (cReactivePowerStr.toLowerCase().contains("e")) {
			cReactivePowerStr = "0";
		} else {
			cReactivePowerStr = String.valueOf(Float.parseFloat(cReactivePowerStr)*ptMap.get(deviceNo)*ctMap.get(deviceNo));
		}
		//当前总功率因数 
		String powerFactorStr = datas[30] + datas[29].substring(0, 1) + "." + datas[29].substring(1, 2);
		if (powerFactorStr.toLowerCase().contains("e")) {
			powerFactorStr = "0";
		}
		//当前A相功率因数
		String aPowerFactorStr = datas[32] + datas[31].substring(0, 1) + "." + datas[31].substring(1, 2);
		if (aPowerFactorStr.toLowerCase().contains("e")) {
			aPowerFactorStr = "0";
		}
		//当前B相功率因数
		String bPowerFactorStr = datas[34] + datas[33].substring(0, 1) + "." + datas[33].substring(1, 2);
		if (bPowerFactorStr.toLowerCase().contains("e")) {
			bPowerFactorStr = "0";
		}
		//当前C相功率因数
		String cPowerFactorStr = datas[36] + datas[35].substring(0, 1) + "." + datas[35].substring(1, 2);
		if (cPowerFactorStr.toLowerCase().contains("e")) {
			cPowerFactorStr = "0";
		}
		//当前A相电压    
		String aVStr = datas[38] + datas[37].substring(0, 1) + "." + datas[37].substring(1, 2);
		if (aVStr.toLowerCase().contains("e")) {
			aVStr = "0";
		} else {
			aVStr = String.valueOf(Float.parseFloat(aVStr)*ptMap.get(deviceNo));
		}
		//当前B相电压    
		String bVStr = datas[40] + datas[39].substring(0, 1) + "." + datas[39].substring(1, 2);
		if (bVStr.toLowerCase().contains("e")) {
			bVStr = "0";
		} else {
			bVStr = String.valueOf(Float.parseFloat(bVStr)*ptMap.get(deviceNo));
		}
		//当前C相电压    
		String cVStr = datas[42] + datas[41].substring(0, 1) + "." + datas[41].substring(1, 2);
		if (cVStr.toLowerCase().contains("e")) {
			cVStr = "0";
		} else {
			cVStr = String.valueOf(Float.parseFloat(cVStr)*ptMap.get(deviceNo));
		}
		//当前A相电流    
		String aAStr = datas[45] + datas[44].substring(0, 1) + "." + datas[44].substring(1, 2) + datas[43];
		if (aAStr.toLowerCase().contains("e")) {
			aAStr = "0";
		} else {
			aAStr = String.valueOf(Float.parseFloat(aAStr)*ctMap.get(deviceNo));
		}
		//当前B相电流    
		String bAStr = datas[48] + datas[47].substring(0, 1) + "." + datas[47].substring(1, 2) + datas[46];
		if (bAStr.toLowerCase().contains("e")) {
			bAStr = "0";
		} else {
			bAStr = String.valueOf(Float.parseFloat(bAStr)*ctMap.get(deviceNo));
		}
		//当前C相电流    
		String cAStr = datas[51] + datas[50].substring(0, 1) + "." + datas[50].substring(1, 2) + datas[49];
		if (cAStr.toLowerCase().contains("e")) {
			cAStr = "0";
		} else {
			cAStr = String.valueOf(Float.parseFloat(cAStr)*ctMap.get(deviceNo));
		}
		//当前零序电流   
		String zeroSequenceCurrentAStr = datas[54] + datas[53].substring(0, 1) + "." + datas[53].substring(1, 2) + datas[52];
		if (zeroSequenceCurrentAStr.toLowerCase().contains("e")) {
			zeroSequenceCurrentAStr = "0";
		} else {
			zeroSequenceCurrentAStr = String.valueOf(Float.parseFloat(zeroSequenceCurrentAStr)*ctMap.get(deviceNo));
		}
		//当前总视在功率 
		String apparentPowerStr = datas[57] + "." + datas[56] + datas[55];
		if (apparentPowerStr.toLowerCase().contains("e")) {
			apparentPowerStr = "0";
		} else {
			apparentPowerStr = String.valueOf(Float.parseFloat(apparentPowerStr)*ptMap.get(deviceNo)*ctMap.get(deviceNo));
		}
		//当前A相视在功率
		String apparentAPowerStr = datas[60] + "." + datas[59] + datas[58];
		if (apparentAPowerStr.toLowerCase().contains("e")) {
			apparentAPowerStr = "0";
		} else {
			apparentAPowerStr = String.valueOf(Float.parseFloat(apparentAPowerStr)*ptMap.get(deviceNo)*ctMap.get(deviceNo));
		}
		//当前B相视在功率
		String apparentBPowerStr = datas[63] + "." + datas[62] + datas[61];
		if (apparentBPowerStr.toLowerCase().contains("e")) {
			apparentBPowerStr = "0";
		} else {
			apparentBPowerStr = String.valueOf(Float.parseFloat(apparentBPowerStr)*ptMap.get(deviceNo)*ctMap.get(deviceNo));
		}
		//当前C相视在功率
		String apparentCPowerStr = datas[66] + "." + datas[65] + datas[64];
		if (apparentCPowerStr.toLowerCase().contains("e")) {
			apparentCPowerStr = "0";
		} else {
			apparentCPowerStr = String.valueOf(Float.parseFloat(apparentCPowerStr)*ptMap.get(deviceNo)*ctMap.get(deviceNo));
		}
		result.put("dateTime", dateTime);
		result.put("activePower", activePowerStr);
		result.put("aActivePower", aActivePowerStr);
		result.put("bActivePower", bActivePowerStr);
		result.put("cActivePower", cActivePowerStr);
		result.put("reactivePower", reactivePowerStr);
		result.put("aReactivePower", aReactivePowerStr);
		result.put("bReactivePower", bReactivePowerStr);
		result.put("cReactivePower", cReactivePowerStr);
		result.put("powerFactor", powerFactorStr);
		result.put("aPowerFactor", aPowerFactorStr);
		result.put("bPowerFactor", bPowerFactorStr);
		result.put("cPowerFactor", cPowerFactorStr);
		result.put("aV", aVStr);
		result.put("bV", bVStr);
		result.put("cV", cVStr);
		result.put("aA", aAStr);
		result.put("bA", bAStr);
		result.put("cA", cAStr);
		result.put("zeroSequenceCurrentA", zeroSequenceCurrentAStr);
		result.put("apparentPower", apparentPowerStr);
		result.put("apparentAPower", apparentAPowerStr);
		result.put("apparentBPower", apparentBPowerStr);
		result.put("apparentCPower", apparentCPowerStr);
		result.put("deviceNo", deviceNo);
		result.put("companyId", 2);
		DBUtil.save(result, "f25");
	}
	
	/**
	 * F31 当前A、B、C三相正/反向有功电能示值、组合无功1/2电能示值 
	 * @param datas
	 * @return
	 */
	public static void f31(String[] datas){
		Map<String, Object> result = new HashMap<>();
		/**
		 * 68 3E 01 3E 01 68 88 10 03 03 00 F2 0C 7C 01 01 
		01 03 41 14 28 09 18 EE EE EE EE EE EE EE EE EE 
		EE EE EE EE EE EE EE EE EE EE EE EE EE EE EE EE 
		EE EE EE EE EE EE EE EE EE EE EE EE EE EE EE EE 
		EE EE EE EE EE EE EE EE EE EE EE EE EE EE EE EE 
		EE EE EE EE EE 60 16
		 */
		if (!((datas.length - 8) == getLength(new String[] {datas[1],datas[2]}))) {
			return;
		}
		String [] datasCheck = new String[datas.length - 8];
		for (int i=0;i<datas.length - 8;i++) {
			datasCheck[i] = datas[i+6];
		}
		if (!datas[datas.length - 2].toUpperCase().equals(frameCheck(datasCheck).toUpperCase())) {
			return;
		}
		String c = datas[6];
		String a1 = datas[8] + datas[7];
		int a2 = hex2A2(new String[] {datas[9], datas[10]});
		int a3 = hex2A3(datas[11]);
		String afn = datas[12];
		String seq = datas[13];
		int[] seqBin = getSEQBin(seq);
		int tpv = seqBin[0];
		int fin = seqBin[1];
		int con = seqBin[2];
		int rseq = seqBin[3];
		int da1 = Integer.parseInt(datas[14], 16);
		int da2 = Integer.parseInt(datas[15], 16);
		int dt1 = Integer.parseInt(datas[16], 16);
		int dt2 = Integer.parseInt(datas[17], 16);
		int fn = da2*8+ Integer.toBinaryString(da1).length();
		int pn = (dt2-1)*8+ Integer.toBinaryString(dt1).length();
		String dateTime = "20" + datas[22] + "-" + datas[21] + "-" + datas[20] + " " + datas[19] + ":" + datas[18];
		//当前A相正向有功电能示值
		String aPositiveActivePowerIndicationStr = datas[27] + datas[26] + datas[25] + "." + datas[24] + datas[23];
		//当前A相反向有功电能示值
		String aNegativeActivePowerIndicationStr = datas[32] + datas[31] + datas[30] + "." + datas[29] + datas[28];
		//当前A相组合无功1电能示值= 无该项数据
		String a1ReactivePowerStr = datas[36] + datas[35] + datas[34] + "." + datas[33];
		//当前A相组合无功2电能示值= 无该项数据
		String a2ReactivePowerStr = datas[40] + datas[39] + datas[38] + "." + datas[37];
		
		//当前B相正向有功电能示值
		String bPositiveActivePowerIndicationStr = datas[45] + datas[44] + datas[43] + "." + datas[42] + datas[41];
		//当前B相反向有功电能示值
		String bNegativeActivePowerIndicationStr = datas[50] + datas[49] + datas[48] + "." + datas[47] + datas[46];
		//当前B相组合无功1电能示值= 无该项数据
		String b1ReactivePowerStr = datas[54] + datas[53] + datas[52] + "." + datas[51];
		//当前B相组合无功2电能示值= 无该项数据
		String b2ReactivePowerStr = datas[58] + datas[57] + datas[56] + "." + datas[55];
		
		//当前C相正向有功电能示值
		String cPositiveActivePowerIndicationStr = datas[63] + datas[62] + datas[61] + "." + datas[60] + datas[59];
		//当前C相反向有功电能示值
		String cNegativeActivePowerIndicationStr = datas[68] + datas[67] + datas[66] + "." + datas[65] + datas[64];
		//当前C相组合无功1电能示值= 无该项数据
		String c1ReactivePowerStr = datas[72] + datas[71] + datas[70] + "." + datas[69];
		//当前C相组合无功2电能示值= 无该项数据
		String c2ReactivePowerStr = datas[76] + datas[75] + datas[74] + "." + datas[73];
		
		result.put("dateTime", dateTime);
		result.put("aPositiveActivePowerIndication", aPositiveActivePowerIndicationStr);
		result.put("aNegativeActivePowerIndication", aNegativeActivePowerIndicationStr);
		result.put("a1ReactivePower", a1ReactivePowerStr);
		result.put("a2ReactivePower", a2ReactivePowerStr);
		result.put("bPositiveActivePowerIndication", bPositiveActivePowerIndicationStr);
		result.put("bNegativeActivePowerIndication", bNegativeActivePowerIndicationStr);
		result.put("b1ReactivePower", b1ReactivePowerStr);
		result.put("b2ReactivePower", b2ReactivePowerStr);
		result.put("cPositiveActivePowerIndication", cPositiveActivePowerIndicationStr);
		result.put("cNegativeActivePowerIndication", cNegativeActivePowerIndicationStr);
		result.put("c1ReactivePower", c1ReactivePowerStr);
		result.put("c2ReactivePower", c2ReactivePowerStr);
	}
	
	/**
	 * F33 当前正向有/无功电能示值、一/四象限无功电能示值（总、费率1~M） 
	 * @param datas
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void f33(String[] datas, int deviceNo) throws ClassNotFoundException, SQLException{
		Map<String, Object> result = new HashMap<>();
		/**
		 * 68 3E 01 3E 01 68 88 10 03 03 00 F2 0C 7C 01 01 
		01 03 41 14 28 09 18 EE EE EE EE EE EE EE EE EE 
		EE EE EE EE EE EE EE EE EE EE EE EE EE EE EE EE 
		EE EE EE EE EE EE EE EE EE EE EE EE EE EE EE EE 
		EE EE EE EE EE EE EE EE EE EE EE EE EE EE EE EE 
		EE EE EE EE EE 60 16
		 */
		String dateTime = "20" + datas[4] + "-" + datas[3] + "-" + datas[2] + " " + datas[1] + ":" + datas[0];
		int rate = Integer.parseInt(datas[5], 16);
		if (dateTime.toLowerCase().contains("e")) {
			return;
		}
		//当前正向有功总电能示值     
		String positiveActivePowerIndicationStr = datas[10] + datas[9] + datas[8] + "." + datas[7] + datas[6];
		if (positiveActivePowerIndicationStr.toLowerCase().contains("e")) {
			positiveActivePowerIndicationStr = "0";
		} else {
			positiveActivePowerIndicationStr = String.valueOf(Float.parseFloat(positiveActivePowerIndicationStr)*ptMap.get(deviceNo)*ctMap.get(deviceNo));
		}
		//当前费率1正向有功总电能示值
		String rate1PositiveActivePowerIndicationStr = datas[15] + datas[14] + datas[13] + "." + datas[12] + datas[11];
		if (rate1PositiveActivePowerIndicationStr.toLowerCase().contains("e")) {
			rate1PositiveActivePowerIndicationStr = "0";
		}
		//当前费率2正向有功总电能示值
		String rate2PositiveActivePowerIndicationStr = datas[20] + datas[19] + datas[18] + "." + datas[17] + datas[16];
		if (rate2PositiveActivePowerIndicationStr.toLowerCase().contains("e")) {
			rate2PositiveActivePowerIndicationStr = "0";
		}
		//当前费率3正向有功总电能示值
		String rate3PositiveActivePowerIndicationStr = datas[25] + datas[24] + datas[23] + "." + datas[22] + datas[21];
		if (rate3PositiveActivePowerIndicationStr.toLowerCase().contains("e")) {
			rate3PositiveActivePowerIndicationStr = "0";
		}
		//当前费率4正向有功总电能示值
		String rate4PositiveActivePowerIndicationStr = datas[30] + datas[29] + datas[28] + "." + datas[27] + datas[26];
		if (rate4PositiveActivePowerIndicationStr.toLowerCase().contains("e")) {
			rate4PositiveActivePowerIndicationStr = "0";
		}
		//当前正向无功（组合无功1）总电能示值     
		String positiveReactivePowerIndicationStr  = datas[34] + datas[33] + datas[32] + "." + datas[31];
		if (positiveReactivePowerIndicationStr.toLowerCase().contains("e")) {
			positiveReactivePowerIndicationStr = "0";
		}
		//当前费率1正向无功（组合无功1）总电能示值
		String rate1PositiveReactivePowerIndicationStr  = datas[38] + datas[37] + datas[36] + "." + datas[35];
		if (rate1PositiveReactivePowerIndicationStr.toLowerCase().contains("e")) {
			rate1PositiveReactivePowerIndicationStr = "0";
		}
		//当前费率2正向无功（组合无功1）总电能示值
		String rate2PositiveReactivePowerIndicationStr  = datas[42] + datas[41] + datas[40] + "." + datas[39];
		if (rate2PositiveReactivePowerIndicationStr.toLowerCase().contains("e")) {
			rate2PositiveReactivePowerIndicationStr = "0";
		}
		//当前费率3正向无功（组合无功1）总电能示值
		String rate3PositiveReactivePowerIndicationStr  = datas[46] + datas[45] + datas[44] + "." + datas[43];
		if (rate3PositiveReactivePowerIndicationStr.toLowerCase().contains("e")) {
			rate3PositiveReactivePowerIndicationStr = "0";
		}
		//当前费率4正向无功（组合无功1）总电能示值
		String rate4PositiveReactivePowerIndicationStr  = datas[50] + datas[49] + datas[48] + "." + datas[47];
		if (rate4PositiveReactivePowerIndicationStr.toLowerCase().contains("e")) {
			rate4PositiveReactivePowerIndicationStr = "0";
		}
		//当前一象限无功总电能示值     
		String firstQuadrantReactivePowerIndicationStr = datas[54] + datas[53] + datas[52] + "." + datas[51];
		if (firstQuadrantReactivePowerIndicationStr.toLowerCase().contains("e")) {
			firstQuadrantReactivePowerIndicationStr = "0";
		}
		//当前一象限费率1无功总电能示值
		String rate1FirstQuadrantReactivePowerIndicationStr = datas[58] + datas[57] + datas[56] + "." + datas[55];
		if (rate1FirstQuadrantReactivePowerIndicationStr.toLowerCase().contains("e")) {
			rate1FirstQuadrantReactivePowerIndicationStr = "0";
		}
		//当前一象限费率2无功总电能示值
		String rate2FirstQuadrantReactivePowerIndicationStr = datas[62] + datas[61] + datas[60] + "." + datas[59];
		if (rate2FirstQuadrantReactivePowerIndicationStr.toLowerCase().contains("e")) {
			rate2FirstQuadrantReactivePowerIndicationStr = "0";
		}
		//当前一象限费率3无功总电能示值
		String rate3FirstQuadrantReactivePowerIndicationStr = datas[66] + datas[65] + datas[64] + "." + datas[63];
		if (rate3FirstQuadrantReactivePowerIndicationStr.toLowerCase().contains("e")) {
			rate3FirstQuadrantReactivePowerIndicationStr = "0";
		}
		//当前一象限费率4无功总电能示值
		String rate4FirstQuadrantReactivePowerIndicationStr = datas[70] + datas[69] + datas[68] + "." + datas[67];
		if (rate4FirstQuadrantReactivePowerIndicationStr.toLowerCase().contains("e")) {
			rate4FirstQuadrantReactivePowerIndicationStr = "0";
		}
		//当前四象限无功总电能示值     
		String fourthQuadrantReactivePowerIndicationStr = datas[74] + datas[73] + datas[72] + "." + datas[71];
		if (fourthQuadrantReactivePowerIndicationStr.toLowerCase().contains("e")) {
			fourthQuadrantReactivePowerIndicationStr = "0";
		}
		//当前四象限费率1无功总电能示值
		String rate1FourthQuadrantReactivePowerIndicationStr = datas[78] + datas[77] + datas[76] + "." + datas[75];
		if (rate1FourthQuadrantReactivePowerIndicationStr.toLowerCase().contains("e")) {
			rate1FourthQuadrantReactivePowerIndicationStr = "0";
		}
		//当前四象限费率2无功总电能示值
		String rate2FourthQuadrantReactivePowerIndicationStr = datas[82] + datas[81] + datas[80] + "." + datas[79];
		if (rate2FourthQuadrantReactivePowerIndicationStr.toLowerCase().contains("e")) {
			rate2FourthQuadrantReactivePowerIndicationStr = "0";
		}
		//当前四象限费率3无功总电能示值
		String rate3FourthQuadrantReactivePowerIndicationStr = datas[86] + datas[85] + datas[84] + "." + datas[83];
		if (rate3FourthQuadrantReactivePowerIndicationStr.toLowerCase().contains("e")) {
			rate3FourthQuadrantReactivePowerIndicationStr = "0";
		}
		//当前四象限费率4无功总电能示值
		String rate4FourthQuadrantReactivePowerIndicationStr = datas[90] + datas[89] + datas[88] + "." + datas[87];
		if (rate4FourthQuadrantReactivePowerIndicationStr.toLowerCase().contains("e")) {
			rate4FourthQuadrantReactivePowerIndicationStr = "0";
		}
		result.put("dateTime", dateTime);
		result.put("rate", rate);
		result.put("positiveActivePowerIndication", positiveActivePowerIndicationStr);
		result.put("rate1PositiveActivePowerIndication", rate1PositiveActivePowerIndicationStr);
		result.put("rate2PositiveActivePowerIndication", rate2PositiveActivePowerIndicationStr);
		result.put("rate3PositiveActivePowerIndication", rate3PositiveActivePowerIndicationStr);
		result.put("rate4PositiveActivePowerIndication", rate4PositiveActivePowerIndicationStr);
		result.put("positiveReactivePowerIndication", positiveReactivePowerIndicationStr);
		result.put("rate1PositiveReactivePowerIndication", rate1PositiveReactivePowerIndicationStr);
		result.put("rate2PositiveReactivePowerIndication", rate2PositiveReactivePowerIndicationStr);
		result.put("rate3PositiveReactivePowerIndication", rate3PositiveReactivePowerIndicationStr);
		result.put("rate4PositiveReactivePowerIndication", rate4PositiveReactivePowerIndicationStr);
		result.put("firstQuadrantReactivePowerIndication", firstQuadrantReactivePowerIndicationStr);
		result.put("rate1FirstQuadrantReactivePowerIndication", rate1FirstQuadrantReactivePowerIndicationStr);
		result.put("rate2FirstQuadrantReactivePowerIndication", rate2FirstQuadrantReactivePowerIndicationStr);
		result.put("rate3FirstQuadrantReactivePowerIndication", rate3FirstQuadrantReactivePowerIndicationStr);
		result.put("rate4FirstQuadrantReactivePowerIndication", rate4FirstQuadrantReactivePowerIndicationStr);
		result.put("fourthQuadrantReactivePowerIndication", fourthQuadrantReactivePowerIndicationStr);
		result.put("rate1FourthQuadrantReactivePowerIndication", rate1FourthQuadrantReactivePowerIndicationStr);
		result.put("rate2FourthQuadrantReactivePowerIndication", rate2FourthQuadrantReactivePowerIndicationStr);
		result.put("rate3FourthQuadrantReactivePowerIndication", rate3FourthQuadrantReactivePowerIndicationStr);
		result.put("rate4FourthQuadrantReactivePowerIndication", rate4FourthQuadrantReactivePowerIndicationStr);
		result.put("deviceNo", deviceNo);
		result.put("companyId", 2);
		DBUtil.save(result, "f33");
	}
	
	
	/**
	 * F34 当前反向有/无功电能示值、二/三象限无功电能示值（总、费率1~M）
	 * @param datas
	 * @return
	 */
	public static void f34(String[] datas){
		Map<String, Object> result = new HashMap<>();
		/**
		 * 68 3E 01 3E 01 68 88 10 03 03 00 F2 0C 7C 01 01 
		01 03 41 14 28 09 18 EE EE EE EE EE EE EE EE EE 
		EE EE EE EE EE EE EE EE EE EE EE EE EE EE EE EE 
		EE EE EE EE EE EE EE EE EE EE EE EE EE EE EE EE 
		EE EE EE EE EE EE EE EE EE EE EE EE EE EE EE EE 
		EE EE EE EE EE 60 16
		 */
		if (!((datas.length - 8) == getLength(new String[] {datas[1],datas[2]}))) {
			return;
		}
		String [] datasCheck = new String[datas.length - 8];
		for (int i=0;i<datas.length - 8;i++) {
			datasCheck[i] = datas[i+6];
		}
		if (!datas[datas.length - 2].toUpperCase().equals(frameCheck(datasCheck).toUpperCase())) {
			return;
		}
		String c = datas[6];
		String a1 = datas[8] + datas[7];
		int a2 = hex2A2(new String[] {datas[9], datas[10]});
		int a3 = hex2A3(datas[11]);
		String afn = datas[12];
		String seq = datas[13];
		int[] seqBin = getSEQBin(seq);
		int tpv = seqBin[0];
		int fin = seqBin[1];
		int con = seqBin[2];
		int rseq = seqBin[3];
		int da1 = Integer.parseInt(datas[14], 16);
		int da2 = Integer.parseInt(datas[15], 16);
		int dt1 = Integer.parseInt(datas[16], 16);
		int dt2 = Integer.parseInt(datas[17], 16);
		int fn = da2*8+ Integer.toBinaryString(da1).length();
		int pn = (dt2-1)*8+ Integer.toBinaryString(dt1).length();
		String dateTime = "20" + datas[22] + "-" + datas[21] + "-" + datas[20] + " " + datas[19] + ":" + datas[18];
		int rate = Integer.parseInt(datas[23], 16);
		if (dateTime.toLowerCase().contains("e")) {
			return;
		}
		//		当前反向有功总电能示值     
		String negativeActivePowerIndicationStr = datas[28] + datas[27] + datas[26] + "." + datas[25] + datas[24];
		if (negativeActivePowerIndicationStr.toLowerCase().contains("e")) {
			negativeActivePowerIndicationStr = "0";
		}
		//		当前费率1反向有功总电能示值
		String rate1NegativeActivePowerIndicationStr = datas[33] + datas[32] + datas[31] + "." + datas[30] + datas[29];
		if (rate1NegativeActivePowerIndicationStr.toLowerCase().contains("e")) {
			rate1NegativeActivePowerIndicationStr = "0";
		}
		//		当前费率2反向有功总电能示值
		String rate2NegativeActivePowerIndicationStr = datas[38] + datas[37] + datas[36] + "." + datas[35] + datas[34];
		if (rate2NegativeActivePowerIndicationStr.toLowerCase().contains("e")) {
			rate2NegativeActivePowerIndicationStr = "0";
		}
		//		当前费率3反向有功总电能示值
		String rate3NegativeActivePowerIndicationStr = datas[43] + datas[42] + datas[41] + "." + datas[40] + datas[39];
		
		if (rate3NegativeActivePowerIndicationStr.toLowerCase().contains("e")) {
			rate3NegativeActivePowerIndicationStr = "0";
		}
		//		当前费率4反向有功总电能示值
		String rate4NegativeActivePowerIndicationStr = datas[48] + datas[47] + datas[46] + "." + datas[45] + datas[44];
		if (rate4NegativeActivePowerIndicationStr.toLowerCase().contains("e")) {
			rate4NegativeActivePowerIndicationStr = "0";
		}
		//		当前反向无功（组合无功2）总电能示值     
		String negativeReactivePowerIndicationStr  = datas[52] + datas[51] + datas[50] + "." + datas[49];
		if (negativeReactivePowerIndicationStr.toLowerCase().contains("e")) {
			negativeReactivePowerIndicationStr = "0";
		}
		//		当前费率1反向无功（组合无功2）总电能示值
		String rate1NegativeReactivePowerIndicationStr  = datas[56] + datas[55] + datas[54] + "." + datas[53];
		if (rate1NegativeReactivePowerIndicationStr.toLowerCase().contains("e")) {
			rate1NegativeReactivePowerIndicationStr = "0";
		}
		//		当前费率2反向无功（组合无功2）总电能示值
		String rate2NegativeReactivePowerIndicationStr  = datas[60] + datas[59] + datas[58] + "." + datas[57];
		if (rate2NegativeReactivePowerIndicationStr.toLowerCase().contains("e")) {
			rate2NegativeReactivePowerIndicationStr = "0";
		}
		//		当前费率3反向无功（组合无功2）总电能示值
		String rate3NegativeReactivePowerIndicationStr  = datas[61] + datas[62] + datas[63] + "." + datas[64];
		if (rate3NegativeReactivePowerIndicationStr.toLowerCase().contains("e")) {
			rate3NegativeReactivePowerIndicationStr = "0";
		}
		//		当前费率4反向无功（组合无功2）总电能示值
		String rate4NegativeReactivePowerIndicationStr  = datas[68] + datas[67] + datas[66] + "." + datas[65];
		if (rate4NegativeReactivePowerIndicationStr.toLowerCase().contains("e")) {
			rate4NegativeReactivePowerIndicationStr = "0";
		}
		//		当前二象限无功总电能示值     
		String secondQuadrantReactivePowerIndicationStr = datas[72] + datas[71] + datas[70] + "." + datas[69];
		if (secondQuadrantReactivePowerIndicationStr.toLowerCase().contains("e")) {
			secondQuadrantReactivePowerIndicationStr = "0";
		}
		//		当前二象限费率1无功总电能示值
		String rate1SecondQuadrantReactivePowerIndicationStr = datas[76] + datas[75] + datas[74] + "." + datas[73];
		if (rate1SecondQuadrantReactivePowerIndicationStr.toLowerCase().contains("e")) {
			rate1SecondQuadrantReactivePowerIndicationStr = "0";
		}
		//		当前二象限费率2无功总电能示值
		String rate2SecondQuadrantReactivePowerIndicationStr = datas[80] + datas[79] + datas[78] + "." + datas[77];
		if (rate2SecondQuadrantReactivePowerIndicationStr.toLowerCase().contains("e")) {
			rate2SecondQuadrantReactivePowerIndicationStr = "0";
		}
		//		当前二象限费率3无功总电能示值
		String rate3SecondQuadrantReactivePowerIndicationStr = datas[84] + datas[83] + datas[82] + "." + datas[81];
		if (rate3SecondQuadrantReactivePowerIndicationStr.toLowerCase().contains("e")) {
			rate3SecondQuadrantReactivePowerIndicationStr = "0";
		}
		//		当前二象限费率4无功总电能示值
		String rate4SecondQuadrantReactivePowerIndicationStr = datas[88] + datas[87] + datas[86] + "." + datas[85];
		if (rate4SecondQuadrantReactivePowerIndicationStr.toLowerCase().contains("e")) {
			rate4SecondQuadrantReactivePowerIndicationStr = "0";
		}
		//		当前三象限无功总电能示值     
		String thirdQuadrantReactivePowerIndicationStr = datas[92] + datas[91] + datas[90] + "." + datas[89];
		if (thirdQuadrantReactivePowerIndicationStr.toLowerCase().contains("e")) {
			thirdQuadrantReactivePowerIndicationStr = "0";
		}
		//		当前三象限费率1无功总电能示值
		String rate1ThirdQuadrantReactivePowerIndicationStr = datas[96] + datas[95] + datas[94] + "." + datas[93];
		if (rate1ThirdQuadrantReactivePowerIndicationStr.toLowerCase().contains("e")) {
			rate1ThirdQuadrantReactivePowerIndicationStr = "0";
		}
		//		当前三象限费率2无功总电能示值
		String rate2ThirdQuadrantReactivePowerIndicationStr = datas[100] + datas[99] + datas[98] + "." + datas[97];
		if (rate2ThirdQuadrantReactivePowerIndicationStr.toLowerCase().contains("e")) {
			rate2ThirdQuadrantReactivePowerIndicationStr = "0";
		}
		//		当前三象限费率3无功总电能示值
		String rate3ThirdQuadrantReactivePowerIndicationStr = datas[104] + datas[103] + datas[102] + "." + datas[101];
		if (rate3ThirdQuadrantReactivePowerIndicationStr.toLowerCase().contains("e")) {
			rate3ThirdQuadrantReactivePowerIndicationStr = "0";
		}
		//		当前三象限费率4无功总电能示值
		String rate4ThirdQuadrantReactivePowerIndicationStr = datas[108] + datas[107] + datas[106] + "." + datas[105];
		if (rate4ThirdQuadrantReactivePowerIndicationStr.toLowerCase().contains("e")) {
			rate4ThirdQuadrantReactivePowerIndicationStr = "0";
		}
		result.put("dateTime", dateTime);
		result.put("rate", rate);
		result.put("negativeActivePowerIndication", negativeActivePowerIndicationStr);
		result.put("rate1NegativeActivePowerIndication", rate1NegativeActivePowerIndicationStr);
		result.put("rate2NegativeActivePowerIndication", rate2NegativeActivePowerIndicationStr);
		result.put("rate3NegativeActivePowerIndication", rate3NegativeActivePowerIndicationStr);
		result.put("rate4NegativeActivePowerIndication", rate4NegativeActivePowerIndicationStr);
		result.put("negativeReactivePowerIndication", negativeReactivePowerIndicationStr);
		result.put("rate1NegativeReactivePowerIndication", rate1NegativeReactivePowerIndicationStr);
		result.put("rate2NegativeReactivePowerIndication", rate2NegativeReactivePowerIndicationStr);
		result.put("rate3NegativeReactivePowerIndication", rate3NegativeReactivePowerIndicationStr);
		result.put("rate4NegativeReactivePowerIndication", rate4NegativeReactivePowerIndicationStr);
		result.put("secondQuadrantReactivePowerIndication", secondQuadrantReactivePowerIndicationStr);
		result.put("rate1SecondQuadrantReactivePowerIndication", rate1SecondQuadrantReactivePowerIndicationStr);
		result.put("rate2SecondQuadrantReactivePowerIndication", rate2SecondQuadrantReactivePowerIndicationStr);
		result.put("rate3SecondQuadrantReactivePowerIndication", rate3SecondQuadrantReactivePowerIndicationStr);
		result.put("rate4SecondQuadrantReactivePowerIndication", rate4SecondQuadrantReactivePowerIndicationStr);
		result.put("thirdQuadrantReactivePowerIndication", thirdQuadrantReactivePowerIndicationStr);
		result.put("rate1ThirdQuadrantReactivePowerIndication", rate1ThirdQuadrantReactivePowerIndicationStr);
		result.put("rate2ThirdQuadrantReactivePowerIndication", rate2ThirdQuadrantReactivePowerIndicationStr);
		result.put("rate3ThirdQuadrantReactivePowerIndication", rate3ThirdQuadrantReactivePowerIndicationStr);
		result.put("rate4ThirdQuadrantReactivePowerIndication", rate4ThirdQuadrantReactivePowerIndicationStr);
	}
	
	/**
	 * F35 当月正向有/无功最大需量及发生时间（总、费率1~M） 
	 * @param datas 数据帧
	 * @return 数据集
	 */
	public static void f35(String[] datas){
		Map<String, Object> result = new HashMap<>();
		/**
		 * 68 3E 01 3E 01 68 88 10 03 03 00 F2 0C 7C 01 01 
		01 03 41 14 28 09 18 EE EE EE EE EE EE EE EE EE 
		EE EE EE EE EE EE EE EE EE EE EE EE EE EE EE EE 
		EE EE EE EE EE EE EE EE EE EE EE EE EE EE EE EE 
		EE EE EE EE EE EE EE EE EE EE EE EE EE EE EE EE 
		EE EE EE EE EE 60 16
		 */
		if (!((datas.length - 8) == getLength(new String[] {datas[1],datas[2]}))) {
			return;
		}
		String [] datasCheck = new String[datas.length - 8];
		for (int i=0;i<datas.length - 8;i++) {
			datasCheck[i] = datas[i+6];
		}
		if (!datas[datas.length - 2].toUpperCase().equals(frameCheck(datasCheck).toUpperCase())) {
			return;
		}
		String c = datas[6];
		String a1 = datas[8] + datas[7];
		int a2 = hex2A2(new String[] {datas[9], datas[10]});
		int a3 = hex2A3(datas[11]);
		String afn = datas[12];
		String seq = datas[13];
		int[] seqBin = getSEQBin(seq);
		int tpv = seqBin[0];
		int fin = seqBin[1];
		int con = seqBin[2];
		int rseq = seqBin[3];
		int da1 = Integer.parseInt(datas[14], 16);
		int da2 = Integer.parseInt(datas[15], 16);
		int dt1 = Integer.parseInt(datas[16], 16);
		int dt2 = Integer.parseInt(datas[17], 16);
		int fn = da2*8+ Integer.toBinaryString(da1).length();
		int pn = (dt2-1)*8+ Integer.toBinaryString(dt1).length();
		String dateTime = "20" + datas[22] + "-" + datas[21] + "-" + datas[20] + " " + datas[19] + ":" + datas[18];
		if (dateTime.toLowerCase().contains("e")) {
			return;
		}
		int rate = Integer.parseInt(datas[23], 16);
		//当月正向有功总最大需量   
		String positiveActivePowerIndicatioMaxRequestStr = datas[26] + "." + datas[25] + datas[24];
		if (positiveActivePowerIndicatioMaxRequestStr.toLowerCase().contains("e")) {
			positiveActivePowerIndicatioMaxRequestStr = "0";
		}
		//当月正向有功费率 1最大需量
		String rate1PositiveActivePowerIndicatioMaxRequestStr = datas[29] + "." + datas[28] + datas[27];
		if (rate1PositiveActivePowerIndicatioMaxRequestStr.toLowerCase().contains("e")) {
			rate1PositiveActivePowerIndicatioMaxRequestStr = "0";
		}
		//当月正向有功费率 2最大需量
		String rate2PositiveActivePowerIndicatioMaxRequestStr = datas[32] + "." + datas[31] + datas[30];
		if (rate2PositiveActivePowerIndicatioMaxRequestStr.toLowerCase().contains("e")) {
			rate2PositiveActivePowerIndicatioMaxRequestStr = "0";
		}
		//当月正向有功费率 3最大需量
		String rate3PositiveActivePowerIndicatioMaxRequestStr = datas[35] + "." + datas[34] + datas[33];
		if (rate3PositiveActivePowerIndicatioMaxRequestStr.toLowerCase().contains("e")) {
			rate3PositiveActivePowerIndicatioMaxRequestStr = "0";
		}
		//当月正向有功费率 4最大需量
		String rate4PositiveActivePowerIndicatioMaxRequestStr = datas[38] + "." + datas[37] + datas[36];
		if (rate4PositiveActivePowerIndicatioMaxRequestStr.toLowerCase().contains("e")) {
			rate4PositiveActivePowerIndicatioMaxRequestStr = "0";
		}
		//当月正向有功总最大需量发生时间      
		String positiveActivePowerIndicatioMaxRequestTimeStr = datas[42] + "-" + datas[41] + " " + datas[40] + ":" + datas[39];
		if (positiveActivePowerIndicatioMaxRequestTimeStr.toLowerCase().contains("e")) {
			return;
		}
		//当月正向有功费率 1最大需量发生时间  
		String rate1PositiveActivePowerIndicatioMaxRequesTimeStr = datas[46] + "-" + datas[45] + " " + datas[44] + ":" + datas[43];
		if (rate1PositiveActivePowerIndicatioMaxRequesTimeStr.toLowerCase().contains("e")) {
			return;
		}
		//当月正向有功费率 2最大需量发生时间  
		String rate2PositiveActivePowerIndicatioMaxRequesTimeStr = datas[50] + "-" + datas[49] + " " + datas[48] + ":" + datas[47];
		if (rate2PositiveActivePowerIndicatioMaxRequesTimeStr.toLowerCase().contains("e")) {
			return;
		}
		//当月正向有功费率 3最大需量发生时间  
		String rate3PositiveActivePowerIndicatioMaxRequesTimeStr = datas[54] + "-" + datas[53] + " " + datas[52] + ":" + datas[51];
		if (rate3PositiveActivePowerIndicatioMaxRequesTimeStr.toLowerCase().contains("e")) {
			return;
		}
		//当月正向有功费率 4最大需量发生时间  
		String rate4PositiveActivePowerIndicatioMaxRequesTimeStr = datas[58] + "-" + datas[57] + " " + datas[56] + ":" + datas[55];
		if (rate4PositiveActivePowerIndicatioMaxRequesTimeStr.toLowerCase().contains("e")) {
			return;
		}
		//当月正向无功总最大需量    
		String positiveReactivePowerIndicatioMaxRequestStr = datas[61] + "." + datas[60] + datas[59];
		if (positiveReactivePowerIndicatioMaxRequestStr.toLowerCase().contains("e")) {
			positiveReactivePowerIndicatioMaxRequestStr = "0";
		}
		//当月正向无功费率 1最大需量
		String rate1PositiveReactivePowerIndicatioMaxRequestStr = datas[64] + "." + datas[63] + datas[62];
		if (rate1PositiveReactivePowerIndicatioMaxRequestStr.toLowerCase().contains("e")) {
			rate1PositiveReactivePowerIndicatioMaxRequestStr = "0";
		}
		//当月正向无功费率 2最大需量
		String rate2PositiveReactivePowerIndicatioMaxRequestStr = datas[67] + "." + datas[66] + datas[65];
		if (rate2PositiveReactivePowerIndicatioMaxRequestStr.toLowerCase().contains("e")) {
			rate2PositiveReactivePowerIndicatioMaxRequestStr = "0";
		}
		//当月正向无功费率 3最大需量
		String rate3PositiveReactivePowerIndicatioMaxRequestStr = datas[70] + "." + datas[69] + datas[68];
		if (rate3PositiveReactivePowerIndicatioMaxRequestStr.toLowerCase().contains("e")) {
			rate3PositiveReactivePowerIndicatioMaxRequestStr = "0";
		}
		//当月正向无功费率 4最大需量
		String rate4PositiveReactivePowerIndicatioMaxRequestStr = datas[73] + "." + datas[72] + datas[71];	
		if (rate4PositiveReactivePowerIndicatioMaxRequestStr.toLowerCase().contains("e")) {
			rate4PositiveReactivePowerIndicatioMaxRequestStr = "0";
		}
		//当月正向无功总最大需量发生时间      
		String positiveReactivePowerIndicatioMaxRequestTimeStr = datas[77] + "-" + datas[76] + " " + datas[75] + ":" + datas[74];
		if (positiveReactivePowerIndicatioMaxRequestTimeStr.toLowerCase().contains("e")) {
			return;
		}
		//当月正向无功费率 1最大需量发生时间  
		String rate1PositiveReactivePowerIndicatioMaxRequesTimeStr = datas[81] + "-" + datas[80] + " " + datas[79] + ":" + datas[78];
		if (rate1PositiveReactivePowerIndicatioMaxRequesTimeStr.toLowerCase().contains("e")) {
			return;
		}
		//当月正向无功费率 2最大需量发生时间  
		String rate2PositiveReactivePowerIndicatioMaxRequesTimeStr = datas[85] + "-" + datas[84] + " " + datas[83] + ":" + datas[82];
		if (rate2PositiveReactivePowerIndicatioMaxRequesTimeStr.toLowerCase().contains("e")) {
			return;
		}
		//当月正向无功费率 3最大需量发生时间  
		String rate3PositiveReactivePowerIndicatioMaxRequesTimeStr = datas[89] + "-" + datas[88] + " " + datas[87] + ":" + datas[86];
		if (rate3PositiveReactivePowerIndicatioMaxRequesTimeStr.toLowerCase().contains("e")) {
			return;
		}
		//当月正向无功费率 4最大需量发生时间  
		String rate4PositiveReactivePowerIndicatioMaxRequesTimeStr = datas[93] + "-" + datas[92] + " " + datas[91] + ":" + datas[90];
		if (rate4PositiveReactivePowerIndicatioMaxRequesTimeStr.toLowerCase().contains("e")) {
			return;
		}
		result.put("dateTime", dateTime);
		result.put("rate", rate);
		result.put("positiveActivePowerIndicatioMaxRequest", positiveActivePowerIndicatioMaxRequestStr);
		result.put("rate1PositiveActivePowerIndicatioMaxRequest", rate1PositiveActivePowerIndicatioMaxRequestStr);
		result.put("rate2PositiveActivePowerIndicatioMaxRequest", rate2PositiveActivePowerIndicatioMaxRequestStr);
		result.put("rate3PositiveActivePowerIndicatioMaxRequest", rate3PositiveActivePowerIndicatioMaxRequestStr);
		result.put("rate4PositiveActivePowerIndicatioMaxRequest", rate4PositiveActivePowerIndicatioMaxRequestStr);
		result.put("positiveActivePowerIndicatioMaxRequestTime", positiveActivePowerIndicatioMaxRequestTimeStr);
		result.put("rate1PositiveActivePowerIndicatioMaxRequesTime", rate1PositiveActivePowerIndicatioMaxRequesTimeStr);
		result.put("rate2PositiveActivePowerIndicatioMaxRequesTime", rate2PositiveActivePowerIndicatioMaxRequesTimeStr);
		result.put("rate3PositiveActivePowerIndicatioMaxRequesTime", rate3PositiveActivePowerIndicatioMaxRequesTimeStr);
		result.put("rate4PositiveActivePowerIndicatioMaxRequesTime", rate4PositiveActivePowerIndicatioMaxRequesTimeStr);
		result.put("positiveReactivePowerIndicatioMaxRequest", positiveReactivePowerIndicatioMaxRequestStr);
		result.put("rate1PositiveReactivePowerIndicatioMaxRequest", rate1PositiveReactivePowerIndicatioMaxRequestStr);
		result.put("rate2PositiveReactivePowerIndicatioMaxRequest", rate2PositiveReactivePowerIndicatioMaxRequestStr);
		result.put("rate3PositiveReactivePowerIndicatioMaxRequest", rate3PositiveReactivePowerIndicatioMaxRequestStr);
		result.put("rate4PositiveReactivePowerIndicatioMaxRequest", rate4PositiveReactivePowerIndicatioMaxRequestStr);
		result.put("positiveReactivePowerIndicatioMaxRequestTime", positiveReactivePowerIndicatioMaxRequestTimeStr);
		result.put("rate1PositiveReactivePowerIndicatioMaxRequesTime", rate1PositiveReactivePowerIndicatioMaxRequesTimeStr);
		result.put("rate2PositiveReactivePowerIndicatioMaxRequesTime", rate2PositiveReactivePowerIndicatioMaxRequesTimeStr);
		result.put("rate3PositiveReactivePowerIndicatioMaxRequesTime", rate3PositiveReactivePowerIndicatioMaxRequesTimeStr);
		result.put("rate4PositiveReactivePowerIndicatioMaxRequesTime", rate4PositiveReactivePowerIndicatioMaxRequesTimeStr);
	}
	
	
	public static String getOrder(Map<String, Object> params) {
		int orderNo = Integer.parseInt(String.valueOf(params.get("orderNo")));
		int msa = Integer.parseInt(String.valueOf(params.get("msa")));
		int terminalNo = Integer.parseInt(String.valueOf(params.get("terminalNo")));
		int no = Integer.parseInt(String.valueOf(params.get("no")));
		int length = Integer.parseInt(String.valueOf(params.get("length")));
		int dir = Integer.parseInt(String.valueOf(params.get("dir")));
		int prm = Integer.parseInt(String.valueOf(params.get("prm")));
		int fcb = Integer.parseInt(String.valueOf(params.get("fcb")));
		int fcv = Integer.parseInt(String.valueOf(params.get("fcv")));
		int funcCode = Integer.parseInt(String.valueOf(params.get("funcCode")));
		int tpv = Integer.parseInt(String.valueOf(params.get("tpv")));
		int fir = Integer.parseInt(String.valueOf(params.get("fir")));
		int fin = Integer.parseInt(String.valueOf(params.get("fin")));
		int con = Integer.parseInt(String.valueOf(params.get("con")));
		int pseq = Integer.parseInt(String.valueOf(params.get("pseq")));
		String zcode = String.valueOf(params.get("zcode"));
		String afn = String.valueOf(params.get("afn"));
		StringBuffer sb = new StringBuffer(512);
		StringBuffer sbCheck = new StringBuffer(512);
		sb.append("68 ");
		sb.append(length2Hex(length) + " " + length2Hex(length) + " 68 ");
		sb.append(getControlHex(dir, prm, fcb, fcv, funcCode) + " ");
		sbCheck.append(getControlHex(dir, prm, fcb, fcv, funcCode) + " ");
		sb.append(zcode.substring(2, 4) + " " + zcode.substring(0, 2) + " ");
		sbCheck.append(zcode.substring(2, 4) + " " + zcode.substring(0, 2) + " ");
		sb.append(getA2Hex(terminalNo) + " ");
		sbCheck.append(getA2Hex(terminalNo) + " ");
		sb.append(getA3Hex(msa, 0) + " ");
		sbCheck.append(getA3Hex(msa, 0) + " ");
		sb.append(getDat(no,orderNo,tpv,fir,fin,con,pseq,afn) + " ");
		sbCheck.append(getDat(no,orderNo,tpv,fir,fin,con,pseq,afn) + " ");
		sb.append(frameCheck(sbCheck.toString().split(" ")) + " 16");
		return sb.toString();
	}
	public static String add02back(String pre, int no) {
		for (int i=0;i<no;i++) {
			pre  = (pre + "0");
		}
		return pre;
	}
	public static String getDat(int no, int order, int tpv, int fir, int fin, int con, int pseq, String afn) {
		String da1Hex = Integer.toHexString(Integer.parseInt(String.valueOf((int)Math.pow(2, no%8-1))));
		if (da1Hex.length() <2) {
			da1Hex = add02front(da1Hex, 1);
		}
		String da2Hex = Integer.toHexString(no/8+1);
		if (no == 0) {
			da2Hex = "00";
		}
		if (da2Hex.length() <2) {
			da2Hex = add02front(da2Hex, 1);
		}
		String dt1Hex = Integer.toHexString(Integer.parseInt(String.valueOf((int)Math.pow(2, order%8-1))));
		if (dt1Hex.length() <2) {
			dt1Hex = add02front(dt1Hex, 1);
		}
		String dt2Hex = Integer.toHexString(order/8);
		if (dt2Hex.length() <2) {
			dt2Hex = add02front(dt2Hex, 1);
		}
		String seq = Integer.toHexString(Integer.parseInt(tpv + "" + fir + "" + fin + "" + con, 2)) + Integer.toHexString(pseq);
		return afn + " " + seq + " " + da1Hex + " " + da2Hex + " " + dt1Hex + " " + dt2Hex;
		
	}
	
	public static String hex2String(byte[] b) {
    	StringBuffer sb = new StringBuffer();
    	for(int i = 0; i < b.length; i++) {
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
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Map<String, Object> paramsHeart = new HashMap<>();
		paramsHeart.put("orderNo", 1);
		paramsHeart.put("msa", 121);
		paramsHeart.put("zcode", "0310");
		paramsHeart.put("terminalNo", Integer.parseInt("1"));
		paramsHeart.put("no", 0);
		paramsHeart.put("length", 12);
		paramsHeart.put("dir", 0);
		paramsHeart.put("prm", 1);
		paramsHeart.put("fcb", 0);
		paramsHeart.put("fcv", 0);
		paramsHeart.put("funcCode", 0);
		paramsHeart.put("tpv", 0);
		paramsHeart.put("fir", 1);
		paramsHeart.put("fin", 1);
		paramsHeart.put("con", 0);
		paramsHeart.put("pseq", 0);
		paramsHeart.put("afn", "00");
		System.out.println(getOrder(paramsHeart));
		System.out.println(hex2String(toBytes(getOrder(paramsHeart).replaceAll(" ", ""))));
		System.out.println(hex2String(hex2byte("68 32 00 32 00 68 40 10 03 01 00 f2 00 60 00 00 01 00 A7 16")));
	}
}
