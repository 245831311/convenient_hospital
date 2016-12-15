package com.hospital.service;

import java.util.List;

import com.hospital.common.ServiceException;
import com.hospital.model.ResourceMap;

/**
 * 资源映射服务接口
 * 
 * @author yubing
 *
 */
public interface IResourceMapService {
	/**
	 * 获取所有的资源
	 * 
	 * @author yubing
	 * @throws Exception
	 */
	public List<ResourceMap> getResourceMapList() throws ServiceException;
	
}
