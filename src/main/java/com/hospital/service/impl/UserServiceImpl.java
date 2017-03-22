package com.hospital.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.common.ServiceException;
import com.hospital.constants.AppConstants;
import com.hospital.dao.UserDao;
import com.hospital.model.User;
import com.hospital.service.IUserService;
import com.hospital.util.CheckUtils;
import com.hospital.util.Crypto43DES;
/**
 * 用户登陆接口实现
 */
@Service("userService")
public class UserServiceImpl implements IUserService{
	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private HttpServletRequest request;
	
	@Resource(name="userDao")
	private UserDao userDao;
	
	@Override
	public Map<String,Object> login(String username, String password, String code) throws ServiceException {
		Map<String,Object> map = new HashMap<String,Object>();
		boolean result = true;
		String msg = "";
		String validateCode = "";
		Object codeStr = request.getSession().getAttribute("validateCode");
		if(codeStr!=null){
			validateCode = (String)codeStr;
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("[Service.UserServiceImpl] login:getUserByUserName begin! username-->"+username);
		}
		//1、验证用户名
		User user = this.getUserByUserName(username);
		if(user==null){
			msg = "用户名不存在!";
			result = false;
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("[Service.UserServiceImpl] login:oldPWD-->"+user.getPassword()+",newPassword-->"+password);
		}
		//2、验证密码
		if(StringUtils.isNotBlank(password)){
			password = new String(Crypto43DES.encrypt(password.getBytes()));
		}
		if(!user.getPassword().equals(password)){
			msg = "密码错误!";
			result = false;
		}
		
		//3、验证验证码
		if(logger.isDebugEnabled()){
			logger.debug("[Service.UserServiceImpl] login:oldCode-->"+validateCode+",rand-->"+code);
		}
		if(StringUtils.isNotBlank(validateCode) && !code.equals(validateCode)){
			msg = "验证码错误!";
			result = false;
		}
		
		//4、保存当前用户到session
		request.getSession().setAttribute(AppConstants.SESSION_USER, user);
		msg = "登陆成功!";
		map.put("result", result);
		map.put("message", msg);
		return map;
	}
	
	@Override
	public User getUserByUserName(String phonenum) throws ServiceException {
		User user = null;
		if(CheckUtils.isMobileNO(phonenum)){
			user = userDao.getUserByPhoneNum(phonenum);
		}
		return user;
	}
	
	@Override
	public List<User> getUsersByUserIds(String userIds) throws ServiceException {
		List<User> list = new ArrayList<>();
		try {
			userDao.getUsersByUserIds(userIds);
		} catch (Exception e) {
			logger.error("[Service.UserServiceImpl] getUsersByUserIds excepiton!",e);
		}
		return list;
	}

	@Override
	public Map<String, Object> register(String username, String password) throws ServiceException {
		Map<String,Object> map = new HashMap<String,Object>();
		boolean result = true;
		String msg = "";
		User user = this.getUserByUserName(username);
		
		if(user != null){
			msg = "用户名已存在!";
			result = false;
		}else{
			password = new String(Crypto43DES.encrypt(password.getBytes()));
		}
		
		
		return null;
	}
	
}
