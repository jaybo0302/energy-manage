/**
 * 
 */
package com.cdwoo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdwoo.common.CDPage;
import com.cdwoo.common.CDParam;
import com.cdwoo.dao.UserDao;
import com.cdwoo.entity.User;
/**
 * @author cd
 *
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	@Override
	public CDPage queryUserByPage(CDParam param) {
		CDPage page = new CDPage();
		page.setCount(param.getPageSize());
		page.setCurrentPage(param.getPageNo());
		page.setTotalCount(userDao.queryUserCount(param));
		List<Object> result= userDao.queryUserByPage(param);
		page.setData(result);
		return page;
	}
	@Override
	public User getUserById(String id) {
		return this.userDao.getUserById(id);
	}
	@Override
	public void addUser(User user) {
		this.userDao.addUser(user);
	}
	@Override
	public void editUser(User user) {
		this.userDao.editUser(user);
	}
	@Override
	public User getUserByUserName(String userName) {
		return this.userDao.getUserByUserName(userName);
	}
	@Override
	public void deleteUser(String id) {
		this.userDao.deleteUser(id);
	}
}
