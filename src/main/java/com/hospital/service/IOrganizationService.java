package com.hospital.service;

import com.hospital.model.Organization;
/**
 * 企业服务接口
 * @author yubing
 *
 */
public interface IOrganizationService {
	/**
	 * 根据用户ID获取企业信息
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Organization getOrganizationByUserId(String userId) throws Exception;
}
