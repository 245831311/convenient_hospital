/*package com.hospital.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hospital.common.Code;
import com.hospital.common.MybatisBaseDao;
import com.hospital.common.ServiceException;
import com.hospital.model.ResourceAlloc;

*//**
 * 资源分配DAO
 * @author yubing
 *
 *//*
@Repository("resourceAllocDao")
public class ResourceAllocDao extends MybatisBaseDao<Object,String>{
	public ResourceAlloc getResourcesByTimeSection(long orgId,
			long dateTime, Integer timeSection) throws ServiceException{
		ResourceAlloc resourceAlloc = new ResourceAlloc();
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("orgId",orgId);
			params.put("dateTime",dateTime);
			params.put("timeSection",timeSection);
			resourceAlloc = getSqlSession().selectOne(getSqlName("insert"), params);
		} catch (Exception e) {
			throw new ServiceException("ResourceDao.getResourcesByTimeSection exception!", e,Code.CODE_SERVER_ERROR,"");
		}
		
		return resourceAlloc;
	}
	
	public ResourceAlloc getMaxResourcesByTimeScope(long orgId,long dateTime, 
			int startColumn, int endColumn) throws ServiceException{
		ResourceAlloc resourceAlloc = new ResourceAlloc();
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("orgId",orgId);
			params.put("dateTime",dateTime);
			params.put("startColumn",startColumn);
			params.put("endColumn",endColumn);
			resourceAlloc = getSqlSession().selectOne(getSqlName("getMaxResourcesByTimeScope"), params);
		} catch (Exception e) {
			throw new ServiceException("ResourceDao.getMaxResourcesByTimeScope exception!", e,Code.CODE_SERVER_ERROR,"");
		}
		
		return resourceAlloc;
	}
}
*/