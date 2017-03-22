/*package com.hospital.service.impl;


import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.common.ServiceException;
import com.hospital.constants.AppConstants;
import com.hospital.dao.ResourceAllocDao;
import com.hospital.model.Organization;
import com.hospital.model.ResourceAlloc;
import com.hospital.service.IResourceAllocService;
*//**
 * 资源分配服务接口实现类
 * 
 * @author yubing
 *
 *//*
@Service("resourceService")
public class ResourceAllocServiceImpl implements IResourceAllocService{
	private Logger logger = Logger.getLogger(this.getClass());
	@Resource(name="resourceAllocDao")
	private ResourceAllocDao resourceAllocDao;
	
	@Autowired
	private HttpServletRequest request;

	@Override
	public ResourceAlloc getResourcesByTimeSection(long dateTime, Integer timeSection) throws ServiceException {
		Organization organization = 
				(Organization)request.getSession().getAttribute(AppConstants.SESSION_ORGANIZATION);
		Long orgId = organization.getOrgId();
		return resourceAllocDao.getResourcesByTimeSection(orgId, dateTime, timeSection);
	}

	*//**
	 * 算法，按照1天分为48个时间段。
	 * 例如：0~0.5处于时间段1,0~1处于时间段1,2.
	 * 0.2~1.2 处于时间段1,2,3
	 * 
	 * @author yubing
	 *//*
	@Override
	public ResourceAlloc getMaxResourcesByTimeScope(long dateTime, double realLong) throws Exception {
		Organization organization = 
				(Organization)request.getSession().getAttribute(AppConstants.SESSION_ORGANIZATION);
		Long orgId = organization.getOrgId();
		//根据指定的时间范围计算出时间段
		Calendar nowtime = new GregorianCalendar();
		int hour = nowtime.get(Calendar.HOUR_OF_DAY);
		int minute = nowtime.get(Calendar.MINUTE);
		int second = nowtime.get(Calendar.SECOND);
		double startHour = hour + minute/60.0 + second/3600.0;
		double endHour = startHour + 1.5;
		
		if(logger.isDebugEnabled()){
			logger.debug("ResourceAllocServiceImpl.getMaxResourcesByTimeScope startHour:"+startHour+",endHour:"+endHour);
		}
		int startColumn = new Double(Math.floor(startHour*2)).intValue();
		int endColumn = new Double(Math.ceil(endHour*2)).intValue();
		if(logger.isDebugEnabled()){
			logger.debug("ResourceAllocServiceImpl.getMaxResourcesByTimeScope startColumn:"+startColumn+",endColumn:"+endColumn);
		}
		
		return resourceAllocDao.getMaxResourcesByTimeScope(orgId, dateTime, startColumn, endColumn);
	}
}
*/