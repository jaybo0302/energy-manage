/**
 * 
 */
package com.cdwoo.utils;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.ResourceUtils;

import com.cdwoo.common.CDLogger;
import com.cdwoo.common.DataReceiveJob;

import java.util.Set;

/**
 * @author cd
 *
 */
public class DBUtil {
	private static Connection conn;
	private static String JDBCURL;
	private static String DBUSERNAME;
	private static String DBPASSWORD;
	static {
		try {
			JDBCURL = PropertiesUtil.get(ResourceUtils.getFile("classpath:com/cdwoo/conf/constants/constants.properties").getPath(), "receive.jdbc.url");
			DBUSERNAME = PropertiesUtil.get(ResourceUtils.getFile("classpath:com/cdwoo/conf/constants/constants.properties").getPath(), "receive.jdbc.username");
			DBPASSWORD = PropertiesUtil.get(ResourceUtils.getFile("classpath:com/cdwoo/conf/constants/constants.properties").getPath(), "receive.jdbc.password");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static Connection getConnection(){
		if (conn == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(JDBCURL,DBUSERNAME,DBPASSWORD);
				return conn;
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return conn;
		}
	}
	
	public static void save(Map<String, Object> dataMap, String tableName) throws ClassNotFoundException, SQLException {
		StringBuffer sbKey = new StringBuffer(512);
		StringBuffer sbValue = new StringBuffer(512);
		Set<String> keySet = dataMap.keySet();
		for(String key : keySet) {
			sbKey.append("`,`" + key);
			if ("dateTime".equals(key)) {
				sbValue.append(",'" + String.valueOf(dataMap.get(key)) + "'");
			} else {
				sbValue.append("," + String.valueOf(dataMap.get(key)));
			}
		}
		String insertSql = "insert into " + tableName + " (" + sbKey.substring(2, sbKey.length()) + "`)"
				+"values("+sbValue.substring(1, sbValue.length()) + ")";
		getConnection().createStatement().execute(insertSql);
	}
	
	public static void saveOffline(int deviceNo) {
		try {
			ResultSet rs = getConnection().createStatement().executeQuery("select * from `offline-log` where companyId = 1 and deviceNo = " + deviceNo + " order by lastUpdateTime desc limit 1");
			if (rs.next()) {
				if ((new Date().getTime() - rs.getTimestamp("lastUpdateTime").getTime()) > DataReceiveJob.time*3/2*60*1000) {
					getConnection().createStatement().execute("insert into `offline-log` (deviceNo,companyId,createTime,lastUpdateTime) value ("+deviceNo+",1,now(),now())");
				} else {
					getConnection().createStatement().execute("update `offline-log` set lastUpdateTime = now() where id = " + rs.getInt("id"));
				}
			} else {
				getConnection().createStatement().execute("insert into `offline-log` (deviceNo,companyId,createTime,lastUpdateTime) value ("+deviceNo+",1,now(),now())");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			CDLogger.error("离线信息记录失败");
		}
	}
}
