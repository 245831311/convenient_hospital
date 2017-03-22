/*package com.hospital.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hospital.common.Code;
import com.hospital.common.MybatisBaseDao;
import com.hospital.common.ServiceException;
import com.hospital.model.ResourceMap;

*//**
 * 资源映射DAO
 * @author yubing
 *
 *//*
@Repository("resourceMapDao")
public class ResourceMapDao extends MybatisBaseDao<Object,String>{
	public List<ResourceMap> getResourceMapList() throws ServiceException{
		List<ResourceMap> list = new ArrayList<>();
		try {
			list = getSqlSession().selectList(getSqlName("getResourceMapList"));
		} catch (Exception e) {
			throw new ServiceException("ResourceMapDao.getResourceMapList exception!", e,Code.CODE_SERVER_ERROR,"");
		}
		
		return list;
	}
}
*/