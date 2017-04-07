package com.hospital.service;

import java.util.Map;

import com.hospital.common.ServiceException;


/**
 * 用户登陆接口
 * 
 */
public interface IConsultService {

	public Map<String, Object> searchDoctor(String searchKey, String section) throws ServiceException;

}
