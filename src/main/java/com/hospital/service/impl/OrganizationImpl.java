package com.hospital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hospital.dao.OrganizationDao;
import com.hospital.model.Organization;
import com.hospital.service.IOrganizationService;

/**
 * 企业服务接口实现类
 * @author yubing
 *
 */
@Service("organizationService")
public class OrganizationImpl implements IOrganizationService{
	@Resource(name="organizationDao")
	private OrganizationDao organizationDao;

	@Override
	public Organization getOrganizationByUserId(String userId) throws Exception {
		return organizationDao.getOrganizationByUserId(userId);
	}
}
