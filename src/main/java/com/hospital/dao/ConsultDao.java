package com.hospital.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hospital.common.Code;
import com.hospital.common.MybatisBaseDao;
import com.hospital.common.ServiceException;
import com.hospital.model.Doctor;
/**
 * 咨询Dao
 * @author LHJ
 *
 */
@Repository("consultDao")
public class ConsultDao extends MybatisBaseDao<Object,String> {
	
	public List<Doctor> getDoctorList(String searchKey,String section) throws ServiceException{
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("searchKey", searchKey);
			map.put("section",section);
			return getSqlSession().selectList(getSqlName("getDoctorList"), map);
		} catch (Exception e) {
			throw new ServiceException("ConsultDao.getDoctorList occur exception!", e, Code.CODE_SERVER_ERROR, "");
		}
	}
}
