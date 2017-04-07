package com.hospital.service;

import java.util.Map;

import com.hospital.common.ServiceException;



/**
 * 个人中心接口
 * 
 */
public interface IMyCenterService {

	public Map<String, Object> getMyReoptByUserName(String username) throws ServiceException;

	public Map<String, Object> getMyRegisterByUserName(String username)throws ServiceException;

}
