package com.hospital.service;

import java.util.List;

import com.hospital.common.ServiceException;
import com.hospital.model.User;

/**
 * 用户登陆接口
 * 
 * @author yubing
 *
 */
public interface IUserService {
	/**
	 * 验证用户登陆时输入的手机号或固定号码是否开通会议业务
	 * 
	 * @param phonenum
	 */
	public User getUserByUserName(String phonenum) throws ServiceException;
	
	/**
	 * 校验用户登陆
	 * 
	 * @author yubing
	 * @param username
	 * @param password
	 * @param code
	 * @return
	 * @throws ServiceException
	 */
	public String login(String username, String password, String code) throws ServiceException;
	
	/**
	 * 根据多个用户ID查询
	 * 
	 * @author yubing
	 * @param userIds
	 * @return
	 * @throws ServiceException
	 */
	public List<User> getUsersByUserIds(String userIds) throws ServiceException;
}
