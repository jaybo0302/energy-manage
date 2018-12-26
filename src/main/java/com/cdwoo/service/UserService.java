package com.cdwoo.service;

import com.cdwoo.common.CDPage;
import com.cdwoo.common.CDParam;
import com.cdwoo.entity.User;

public interface UserService {
	CDPage queryUserByPage(CDParam param);
	User getUserById(String id);
	void addUser(User user);
	void editUser(User user);
	User getUserByUserName(String userName);
	void deleteUser(String id);
}
