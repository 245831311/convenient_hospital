package com.hospital.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hospital.common.ServiceException;
import com.hospital.dao.ResourceMapDao;
import com.hospital.model.ResourceMap;
import com.hospital.service.IResourceMapService;
/**
 * 资源映射接口实现类
 * 
 * @author yubing
 *
 */
@Service("resourceMapService")
public class ResourceMapServiceImpl implements IResourceMapService{
	@Resource(name="resourceMapDao")
	private ResourceMapDao resourceMapDao;

	@Override
	public List<ResourceMap> getResourceMapList() throws ServiceException {
		return resourceMapDao.getResourceMapList();	
	}
	
	/*@Autowired
	private HttpServletRequest request;*/

}
