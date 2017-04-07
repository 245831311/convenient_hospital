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
import com.hospital.dao.DoctorDao;
import com.hospital.dao.UserDao;
import com.hospital.model.User;
import com.hospital.service.IDoctorService;
import com.hospital.service.IUserService;
import com.hospital.util.Base64Utils;
import com.hospital.util.CheckUtils;
import com.hospital.util.ConstantUtils;
import com.hospital.util.Crypto43DES;
import com.hospital.util.EncryptUtil;
/**
 * 用户登陆接口实现
 */
@Service("doctorService")
public class DoctorServiceImpl implements IDoctorService{
	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private HttpServletRequest request;
	
	@Resource(name="doctorDao")
	private DoctorDao doctorDao;

	@Override
	public Map<String, Object> getPatentById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> checkRegister(String username, String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
