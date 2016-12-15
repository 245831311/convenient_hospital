package com.hospital.service;

import com.hospital.common.ServiceException;
import com.hospital.model.ResourceAlloc;

/**
 * 资源分配服务接口
 * 
 * @author yubing
 *
 */
public interface IResourceAllocService {
	/**
	 * 根据时间区间获取资源分配
	 * 
	 * @author yubing
	 * @param dateTime 日期，精确到天
	 * @param timeSection 时间区间
	 * @throws Exception
	 */
	public ResourceAlloc getResourcesByTimeSection(long dateTime, Integer timeSection) throws ServiceException;
	
	/**
	 * 根据指定的时间范围获取分配资源数量最大的时间段对象
	 * 
	 * @author yubing
	 * @param dateTime
	 * @param timeSection
	 * @return
	 * @throws ServiceException
	 */
	public ResourceAlloc getMaxResourcesByTimeScope(long dateTime, double realLong) throws Exception;
	
}
