package com.hospital.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hospital.common.Code;
import com.hospital.common.MybatisBaseDao;
import com.hospital.common.ServiceException;
import com.hospital.model.Organization;

/**
 * 用户登陆日志DAO
 * @author yubing
 *
 */
@Repository("organizationDao")
public class OrganizationDao extends MybatisBaseDao<Object,String>{
	public Organization getOrganizationByUserId(String userId) throws ServiceException{
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			return getSqlSession().selectOne(getSqlName("getOrganizationByUserId"), map);
		} catch (Exception e) {
			throw new ServiceException("OrganizationDao.getOrganizationByUserId exception!", e,Code.CODE_SERVER_ERROR,"");
		}
	}
}
