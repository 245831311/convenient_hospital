package com.hospital.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hospital.common.Code;
import com.hospital.common.MybatisBaseDao;
import com.hospital.common.ServiceException;
import com.hospital.model.User;
/**
 * 用户DAO
 * @author yubing
 *
 */
@Repository("userDao")
public class UserDao extends MybatisBaseDao<Object,String> {
	public User getUserByPhoneNum(String mobile) throws ServiceException{
		User user = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mobile", mobile);
			user = getSqlSession().selectOne(getSqlName("getUserByMobile"), map);
		} catch (Exception e) {
			throw new ServiceException("UserDao.getUserByPhoneNum occur exception!", e, Code.CODE_SERVER_ERROR, "");
		}
		
    	return user;
	}
	
	public User getUserByTelphone(String telphone) throws ServiceException{
		User user = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("telphone", telphone);
			user = getSqlSession().selectOne(getSqlName("getUserByTelPhone"), map);
		} catch (Exception e) {
			throw new ServiceException("UserDao.getUserByTelphone occur exception!", e, Code.CODE_SERVER_ERROR, "");
		}
		
    	return user;
	}
	
	public List<User> getUsersByUserIds(String userIds) throws ServiceException{
		List<User> users = new ArrayList<>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userIds", userIds);
			users = getSqlSession().selectList(getSqlName("getUsersByUserIds"), map);
		} catch (Exception e) {
			throw new ServiceException("UserDao.getUsersByUserIds occur exception!", e, Code.CODE_SERVER_ERROR, "");
		}
		
		return users;
	}

	
	public List<User> getUserByDepartId(long orgId, long departId) throws ServiceException{
		List<User> list = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orgId", orgId);
			map.put("departId", departId);
			list = getSqlSession().selectList(getSqlName("getUserByDepartId"), map);
		} catch (Exception e) {
			throw new ServiceException("UserDao.getUserByDepartId occur exception!", e, Code.CODE_SERVER_ERROR, "");
		}
		return list;
	}

	public void register(String username, String password, String nickname,
			String mobile) {
		// TODO Auto-generated method stub
		
	}

}
