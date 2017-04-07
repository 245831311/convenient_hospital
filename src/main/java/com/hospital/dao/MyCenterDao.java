package com.hospital.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hospital.common.Code;
import com.hospital.common.MybatisBaseDao;
import com.hospital.common.ServiceException;
import com.hospital.model.Doctor;
import com.hospital.model.Report;
/**
 * 个人中心Dao
 * @author LHJ
 *
 */
@Repository("MyCenterDao")
public class MyCenterDao extends MybatisBaseDao<Object,String> {
	

	public List<Report> getMyReportByUserName(String username) throws ServiceException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("username",username);
			return getSqlSession().selectList(getSqlName("getReportByUserName"), map);
		} catch (Exception e) {
			throw new ServiceException("MyCenterDao.getMyReportByUserName occur exception!", e, Code.CODE_SERVER_ERROR, "");
		}
	}

	public List<Report> getMyRegisterByUserName(String username) throws ServiceException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("username",username);
			return getSqlSession().selectList(getSqlName("getRegisterByUserName"), map);
		} catch (Exception e) {
			throw new ServiceException("MyCenterDao.getMyRegisterByUserName occur exception!", e, Code.CODE_SERVER_ERROR, "");
		}
	}
}
