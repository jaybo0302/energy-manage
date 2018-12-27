/**
 * 
 */
package com.cdwoo.common;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.util.ResourceUtils;

import com.cdwoo.utils.DBUtil;
import com.cdwoo.utils.DataUtil;
import com.cdwoo.utils.PropertiesUtil;
import com.cdwoo.utils.SerialUtil;
import gnu.io.SerialPort;

/**
 * @author cd
 *
 */
public class DataReceiveJob {
	public static Set<Integer> deviceSet = new HashSet<>();
	public static Map<Integer,String> deviceComMap = new HashMap<>();
	public static Map<String, SerialPort> spMap = new HashMap<>();
	public static Set<String> comSet = new HashSet<>();
	public static float time = 0;
	public static void start() throws NumberFormatException, FileNotFoundException {
		time = Float.parseFloat(PropertiesUtil.get(ResourceUtils.getFile("classpath:com/cdwoo/conf/constants/constants.properties").getPath(), "jobtime"));
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				getDeviceSet();
				try {
					f25Job();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 0, (int)time*60*1000);
	}
	public static void f25Job(){
		spMap.clear();
		comSet.clear();
		CDLogger.info("------------实例化串口通讯------------");
		for(Integer key : deviceComMap.keySet()) {
			if (comSet.contains(deviceComMap.get(key))) {
				continue;
			} else {
				SerialPort sp = null;
				try {
					sp = SerialUtil.openPort(deviceComMap.get(key), 9600, 8, 1, "Even");
				} catch (SerialPortParameterFailure | NotASerialPort | NoSuchPort | PortInUse e) {
					e.printStackTrace();
				}
				spMap.put(deviceComMap.get(key), sp);
				comSet.add(deviceComMap.get(key));
				CDLogger.info("端口 " + deviceComMap.get(key) + " 实例化");
			}
		}
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("orderNo", 25);
			params.put("msa", 121);
			params.put("no", 3);
			params.put("length", 12);
			params.put("dir", 0);
			params.put("prm", 1);
			params.put("fcb", 0);
			params.put("fcv", 0);
			params.put("funcCode", 11);
			params.put("tpv", 0);
			params.put("fir", 1);
			params.put("fin", 1);
			params.put("con", 1);
			params.put("pseq", 1);
			params.put("anf", "0c");
			for (int deviceNo : deviceSet) {
				params.put("deviceNo", deviceNo);
				if (spMap.get(deviceComMap.get(deviceNo))==null) {
					continue;
				}
				byte[] b = SerialUtil.sendToPort(spMap.get(deviceComMap.get(deviceNo)), DataUtil.hex2byte(DataUtil.getOrder(params).toUpperCase()));
				System.out.println(DataUtil.hex2String(b, b.length));
				if (b == null || b.length < 87) {
					//设备离线，保存设备离线日志
					DBUtil.saveOffline(deviceNo);
					continue;
				}
				
				String[] datas = DataUtil.hex2String(b, b.length).split(" ");
				if (!((datas.length - 8) == DataUtil.getLength(new String[] {datas[1],datas[2]}))) {
					return;
				}
				String [] datasCheck = new String[datas.length - 8];
				for (int i=0;i<datas.length - 8;i++) {
					datasCheck[i] = datas[i+6];
				}
				if (!datas[datas.length - 2].toUpperCase().equals(DataUtil.frameCheck(datasCheck).toUpperCase())) {
					return;
				}
				DataUtil.f25(DataUtil.hex2String(b, b.length).toUpperCase().replaceAll("EE", "00").split(" "), deviceNo);
			}
		} catch (SendDataToSerialPortFailure | SerialPortOutputStreamCloseFailure | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			for(SerialPort sp : spMap.values()) {
				SerialUtil.closePort(sp);
			}
 		}
	}
	
	public static void getDeviceSet() {
		//初始化设备集合
		CDLogger.info("=====================初始化设备集合===================");
		try {
			deviceSet.clear();
			deviceComMap.clear();
			ResultSet rs = DBUtil.getConnection().createStatement().executeQuery("select * from `electric-meter` where companyId = 1 and status = 0");
			while(rs.next()) {
				int no = rs.getInt("deviceNo");
				String com = rs.getString("port");
				CDLogger.info("设备号：" + no + "     端口号：" + com);
				deviceSet.add(no);
				deviceComMap.put(no, com);
			}
			rs.close();
		} catch (SQLException e) {
			CDLogger.error(e.toString());
		}
	}
}
