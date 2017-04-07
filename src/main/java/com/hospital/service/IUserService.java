package com.hospital.service;

import java.util.List;
import java.util.Map;

import com.hospital.common.ServiceException;
import com.hospital.model.User;

/**
 * 用户登陆接口
 * 
 */
public interface IUserService {
	/**
	 * @param phonenum
	 */
	public User getUserByUserName(String phonenum) throws ServiceException;
	
	/**
	 * 校验用户登陆
	 * 
	 * @param username
	 * @param password
	 * @param code
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> login(String username, String password, String code) throws ServiceException;
	
	/**
	 * 根据多个用户ID查询
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public List<User> getUsersByUserIds(String userIds) throws ServiceException;

	
	/**
	 * 用户注册
	 * @param code 
	 * @param mobile 
	 * @param nickname 
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> register(String username, String password, String nickname, String mobile, String code) throws ServiceException;

}
