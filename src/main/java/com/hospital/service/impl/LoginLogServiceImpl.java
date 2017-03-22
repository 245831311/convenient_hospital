/*package com.hospital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hospital.dao.LoginLogDao;
import com.hospital.service.ILoginLogService;
*//**
 * 用户登陆日志服务实现类
 * 
 * @author yubing
 *
 *//*
@Service("loginLogService")
public class LoginLogServiceImpl implements ILoginLogService{
	@Resource(name="loginLogDao")
	private LoginLogDao loginLogDao;
	
	@Override
	public boolean addLoginLog(String userId, String loginIp) throws Exception{
		return loginLogDao.addLoginLog(userId, loginIp);
	}

}
*/