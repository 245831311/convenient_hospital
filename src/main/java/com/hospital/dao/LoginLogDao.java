/*package com.hospital.dao;

import org.springframework.stereotype.Repository;

import com.hospital.common.Code;
import com.hospital.common.MybatisBaseDao;
import com.hospital.common.ServiceException;
import com.hospital.model.LoginLog;
import com.hospital.util.AddressUtil;
import com.hospital.util.IdWorkerUtil;

*//**
 * 用户登陆日志DAO
 * @author yubing
 *
 *//*
@Repository("loginLogDao")
public class LoginLogDao extends MybatisBaseDao<Object,String>{
	public boolean addLoginLog(String userId, String loginIp) throws ServiceException{
		try {
			LoginLog loginLog = new LoginLog();
			loginLog.setUserId(userId);
			loginLog.setIp(loginIp);
			loginLog.setTime(System.currentTimeMillis());
			long id = IdWorkerUtil.nextId();
			loginLog.setLoginLogId(String.valueOf(id));
			loginLog.setAddress(AddressUtil.getAddresses(loginIp, "utf-8"));
			
			getSqlSession().insert(getSqlName("insert"), loginLog);
		} catch (Exception e) {
			throw new ServiceException("LoginLogDao.addLoginLog exception!", e,Code.CODE_SERVER_ERROR,"");
		}
		
		return true;
	}
}
*/