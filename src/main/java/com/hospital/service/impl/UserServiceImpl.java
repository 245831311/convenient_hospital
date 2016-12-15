package com.hospital.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.common.ServiceException;
import com.hospital.constants.AppConstants;
import com.hospital.dao.LoginLogDao;
import com.hospital.dao.OrganizationDao;
import com.hospital.dao.UserDao;
import com.hospital.model.Organization;
import com.hospital.model.User;
import com.hospital.service.IUserService;
import com.hospital.util.Crypto43DES;
import com.hospital.util.IpUtil;
/**
 * 用户登陆接口实现
 * @author yubing
 *
 */
@Service("userService")
public class UserServiceImpl implements IUserService{
	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private HttpServletRequest request;
	
	@Resource(name="userDao")
	private UserDao userDao;
	
	@Resource(name="loginLogDao")
	private LoginLogDao loginLogDao;
	
	@Resource(name="organizationDao")
	private OrganizationDao organizationDao;
	
	@Override
	public String login(String username, String password, String code) throws ServiceException {
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
			return "用户名不存在!";
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("[Service.UserServiceImpl] login:oldPWD-->"+user.getPassword()+",newPassword-->"+password);
		}
		//2、验证密码
		if(StringUtils.isNotBlank(password)){
			password = new String(Crypto43DES.encrypt(password.getBytes()));
		}
		if(!user.getPassword().equals(password)){
			return "密码错误!";
		}
		
		//3、验证验证码
		if(logger.isDebugEnabled()){
			logger.debug("[Service.UserServiceImpl] login:oldCode-->"+validateCode+",rand-->"+code);
		}
		if(StringUtils.isNotBlank(validateCode) && !code.equals(validateCode)){
			return "验证码错误!";
		}
		
		//4、获取用户企业信息
		try {
			if(logger.isDebugEnabled()){
				logger.debug("[Service.UserServiceImpl] getOrganizationByUserId begin!userId:"+user.getUserId());
			}
			Organization organization = organizationDao.getOrganizationByUserId(user.getUserId());
			request.getSession().setAttribute(AppConstants.SESSION_ORGANIZATION, organization);
		} catch (Exception e) {
			logger.error("[Service.UserServiceImpl] getOrganizationByUserId excepiton!",e);
		}

		//5、保存当前用户到session
		request.getSession().setAttribute(AppConstants.SESSION_USER, user);
		
		//6、记录用户登j陆日志
		try {
			if(logger.isDebugEnabled()){
				logger.debug("[Service.UserServiceImpl] addLoginLog begin!userId:"+user.getUserId()+",ip:"+IpUtil.getClientIp(request));
			}
			loginLogDao.addLoginLog(user.getUserId(), IpUtil.getClientIp(request));
		} catch (Exception e) {
			logger.error("[Service.UserServiceImpl] addLoginLog excepiton!",e);
		}
		return "";
	}
	
	@Override
	public User getUserByUserName(String phonenum) throws ServiceException {
		User user = null;
		if(isMobile(phonenum)){
			user = userDao.getUserByPhoneNum(phonenum);
		}else if(isTelphone(phonenum)){
			user = userDao.getUserByTelphone(phonenum);
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
	
	private Boolean isMobile(String phonenum){
		String regex = "^(13|14|15|18)[0-9]{9}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(phonenum);
		// 字符串是否与正则表达式相匹配
		return matcher.matches();
	}
	
	private Boolean isTelphone(String telphone){
		//tel:"^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{3,}))?$",	// 电话号码的函数(包括验证国内区号,国际区号,分机号)
		String regex = "^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{3,}))?$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(telphone);
		// 字符串是否与正则表达式相匹配
		return matcher.matches();
	}

}
