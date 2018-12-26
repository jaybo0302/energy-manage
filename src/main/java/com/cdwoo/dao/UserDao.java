package com.cdwoo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cdwoo.common.CDParam;
import com.cdwoo.entity.User;

public interface UserDao {
	long queryTransformerCount(CDParam param);
	List<Object> queryUserByPage(CDParam param);
	long queryUserCount(CDParam param);
	User getUserById(@Param("id")String id);
	void addUser(User user);
	void editUser(User user);
	User getUserByUserName(@Param("userName")String userName);
	void deleteUser(@Param("id")String id);
}
