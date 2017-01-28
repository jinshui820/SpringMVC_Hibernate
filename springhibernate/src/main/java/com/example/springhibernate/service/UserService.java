package com.example.springhibernate.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.springhibernate.pojo.UserInfo;

@Service("userService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class UserService extends BaseService {

	/**
	 * get test user information
	 * 
	 * @return UserInfo
	 */
	public UserInfo getUser() {
		String hql = " from UserInfo where id = :id";
		UserInfo user = (UserInfo) dataDao.getFirstObjectViaParam(hql, new String[] { "id" }, 1l);
		if (user == null) {
			addUser();
			user = dataDao.getObjectById(UserInfo.class, 1l);
		}
		return user;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void addUser() {
		UserInfo userInfo = new UserInfo();
		userInfo.setAge(10);
		userInfo.setCreate_time(new Date());
		userInfo.setGender("male");
		userInfo.setName("test");
		dataDao.addObject(userInfo);
	}

}
