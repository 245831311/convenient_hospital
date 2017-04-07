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
import com.hospital.dao.ConsultDao;
import com.hospital.dao.MyCenterDao;
import com.hospital.dao.UserDao;
import com.hospital.model.Doctor;
import com.hospital.model.Report;
import com.hospital.model.User;
import com.hospital.service.IConsultService;
import com.hospital.service.IMyCenterService;
import com.hospital.service.IUserService;
import com.hospital.util.Base64Utils;
import com.hospital.util.CheckUtils;
import com.hospital.util.ConstantUtils;
import com.hospital.util.Crypto43DES;
import com.hospital.util.EncryptUtil;
/**
 * 个人中心业务
 */
@Service("myCenterService")
public class MyCenterServiceImpl implements IMyCenterService{
	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private HttpServletRequest request;
	
	@Resource(name="MyCenterDao")
	private MyCenterDao myCenterDao;


	@Override
	public Map<String, Object> getMyReoptByUserName(String username) throws ServiceException {
		Map<String,Object> map = new HashMap<String,Object>();
		//打印日记
		if(logger.isDebugEnabled()){
			logger.debug("[Service.MyCenterServiceImpl] username-->"+username);
		}
		//获取个人报告
		List<Report> list = myCenterDao.getMyReportByUserName(username);
		map.put("list", list);
		map.put("result", true);
		return map;
	}


	@Override
	public Map<String, Object> getMyRegisterByUserName(String username)
			throws ServiceException {
		Map<String,Object> map = new HashMap<String,Object>();
		if(logger.isDebugEnabled()){
			logger.debug("[Service.MyCenterServiceImpl] username-->"+username);
		}
		List<Report> list = myCenterDao.getMyRegisterByUserName(username);
		map.put("list", list);
		map.put("result", true);
		return map;
	}
	
	
}
