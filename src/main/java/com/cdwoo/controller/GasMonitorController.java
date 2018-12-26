/**
 * 
 */
package com.cdwoo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdwoo.common.CDLogger;
import com.cdwoo.common.CDResult;
import com.cdwoo.common.Constants;
import com.cdwoo.entity.User;
import com.cdwoo.service.GasMonitorService;
import com.cdwoo.service.UserService;
import com.cdwoo.utils.Base64Utils;
import com.cdwoo.utils.DateUtil;
import com.cdwoo.utils.RSAUtils;

/**
 * @author cd
 *
 */
@Controller
@RequestMapping("/gas_monitor")
public class GasMonitorController {
	@Autowired
    private GasMonitorService gasMonitorService;
	@Autowired
	private UserService userService;

    @ResponseBody
    @RequestMapping("/index")
    public Object index(HttpServletRequest req) {
    	if (req.getSession().getAttribute(Constants.USER_CONTEXT) == null) {
			return CDResult.fail("login time out");
		}
        return CDResult.success(gasMonitorService.getGasInfo(((User)req.getSession().getAttribute(Constants.USER_CONTEXT)).getCompanyId()));
    }
    
    @ResponseBody
    @RequestMapping("data4App")
    public Object data4App (HttpServletRequest request) {
    	String page = request.getParameter("page");
    	String token = request.getParameter("token");
		String deToken="";
		try {
			deToken = RSAUtils.decryptDataOnJava(new String(Base64Utils.decode(token)), Constants.PRIVATEKEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	int no = 1;
    	int companyId = 0;
    	try {
			no = Integer.parseInt(page);
			companyId = Integer.parseInt(deToken.split("##")[2]);
		} catch (Exception e) {
			CDLogger.error(e.toString());
			return CDResult.fail("invalid token");
		}
    	return CDResult.success(gasMonitorService.getGasInfo4App(no, companyId));
    }

    @ResponseBody
    @RequestMapping("warnData4App")
    public Object warnData4App (HttpServletRequest request) {
    	String start = request.getParameter("start");
    	String end = request.getParameter("end");
    	String deviceId = request.getParameter("deviceNo");
    	String token = request.getParameter("token");
    	String deToken="";
    	String companyId = "";
		try {
			deToken = RSAUtils.decryptDataOnJava(new String(Base64Utils.decode(token)), Constants.PRIVATEKEY);
			companyId = deToken.split("##")[2];
		} catch (Exception e) {
			CDLogger.error(e.toString());
			return CDResult.fail("invalid token");
		}
    	return CDResult.success(gasMonitorService.getGasWarn4App(start, end, deviceId, companyId));
    }
    
    @ResponseBody
    @RequestMapping("setPosition")
    public Object setPosition (HttpServletRequest request) {
    	String deviceNo = request.getParameter("deviceNo");
    	String position = request.getParameter("position");
    	int companyId = ((User)request.getSession().getAttribute(Constants.USER_CONTEXT)).getCompanyId();
    	gasMonitorService.setPosition(deviceNo, position, companyId);
    	return true;
    }
    
    @ResponseBody
	@RequestMapping("login4APP")
	public Object login4App(HttpServletRequest req, HttpServletResponse rep) {
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
        User dbUser = userService.getUserByUserName(userName);
        if(dbUser == null){
            return CDResult.fail("无此用户名");
        } else if(!(dbUser.getPassword()).equals(password)){
            return CDResult.fail("用户名或密码错误");
        } else {
        	//生成token返回
        	try {
				return CDResult.success(Base64Utils.encode(RSAUtils.encryptedDataOnJava(DateUtil.sdf_datetime_format.format(new Date()) + "##" + dbUser.getUserName() + "##" + dbUser.getCompanyId(),
						Constants.PUBLICKEY).getBytes()));
			} catch (Exception e) {
				e.printStackTrace();
				return CDResult.fail("system error");
			}
        }
	}
	
	@ResponseBody
	@RequestMapping("checkToken")
	public Object checkToken(HttpServletRequest req) {
		String token = req.getParameter("token");
		String deToken="";
		try {
			deToken = RSAUtils.decryptDataOnJava(new String(Base64Utils.decode(token)), Constants.PRIVATEKEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] tokens = deToken.split("##");
		if (tokens.length < 3) {
			return CDResult.fail("invalid token");
		}
		if (DateUtil.getDoubleMargin(DateUtil.sdf_datetime_format.format(new Date()), tokens[0]) > 7) {
			return CDResult.fail("login time out");
		}
		if (!String.valueOf(userService.getUserByUserName(tokens[1]).getCompanyId()).equals(tokens[2])) {
			return CDResult.fail("invalid token");
		}
		return CDResult.success();
	}
}
