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
import com.hospital.dao.UserDao;
import com.hospital.model.Doctor;
import com.hospital.model.User;
import com.hospital.service.IConsultService;
import com.hospital.service.IUserService;
import com.hospital.util.Base64Utils;
import com.hospital.util.CheckUtils;
import com.hospital.util.ConstantUtils;
import com.hospital.util.Crypto43DES;
import com.hospital.util.EncryptUtil;
/**
 * 用户登陆接口实现
 */
@Service("consultService")
public class ConsultServiceImpl implements IConsultService{
	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private HttpServletRequest request;
	
	@Resource(name="consultDao")
	private ConsultDao consultDao;

	@Override
	public Map<String, Object> searchDoctor(String searchKey,String section) throws ServiceException {
		Map<String,Object> map = new HashMap<String,Object>();
		if(logger.isDebugEnabled()){
			logger.debug("[Service.ConsultServiceImpl] searchKey-->"+searchKey+",section-->"+section);
		}
		List<Doctor> list = consultDao.getDoctorList(searchKey,section);
		map.put("list", list);
		return map;
	}
	
	
}
