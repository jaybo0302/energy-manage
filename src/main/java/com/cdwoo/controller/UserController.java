/**
 * 
 */
package com.cdwoo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdwoo.common.CDPage;
import com.cdwoo.common.CDParam;
import com.cdwoo.common.CDResult;
import com.cdwoo.common.Constants;
import com.cdwoo.entity.User;
import com.cdwoo.service.UserService;

/**
 * @author cd
 *
 */
@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@ResponseBody
	@RequestMapping("queryUserByPage")
	public CDResult queryUserByPage(CDParam userParam, HttpServletRequest req) {
		if (req.getSession().getAttribute(Constants.USER_CONTEXT) == null) {
			return CDResult.fail("login time out");
		}
		userParam.setCompanyId(((User)req.getSession().getAttribute(Constants.USER_CONTEXT)).getCompanyId());
		userParam.setRoleId(((User)req.getSession().getAttribute(Constants.USER_CONTEXT)).getRoleId());
		return CDResult.success(this.userService.queryUserByPage(userParam));
	}
	
	@RequestMapping("getEditPage")
	public String getEditPage(@RequestParam("id") String id, HttpServletRequest req) {
		User user = this.userService.getUserById(id);
		req.setAttribute("u", user);
		return "jsp/user_edit";
	}
	
	@ResponseBody
	@RequestMapping("addUser")
	public CDResult addUser(User user) {
		try {
			if (this.userService.getUserByUserName(user.getRealName()) != null) {
				return CDResult.fail("该用户名已经存在");
			}
			user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
			this.userService.addUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			return CDResult.fail("添加失败");
		}
		return CDResult.success();
	}
	
	@ResponseBody
	@RequestMapping("editUser")
	public CDResult editUser(User user) {
		try {
			if (user.getPassword() != null && !"".equals(user.getPassword())) {
				user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
			}
			this.userService.editUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			return CDResult.fail("编辑失败");
		}
		return CDResult.success();
	}
	
	@ResponseBody
	@RequestMapping("deleteUser")
	public CDResult deleteUser(@RequestParam("id")String id) {
		try {
			this.userService.deleteUser(id);
		} catch (Exception e) {
			e.printStackTrace();
			return CDResult.fail("删除失败");
		}
		return CDResult.success();
	}
}
