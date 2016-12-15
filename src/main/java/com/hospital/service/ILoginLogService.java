package com.hospital.service;
/**
 * 用户登陆日志服务接口
 * 
 * @author yubing
 *
 */
public interface ILoginLogService {
	/**
	 * 记录用户登陆日志
	 * 
	 * @param userId
	 * @param loginIp
	 * @return
	 */
	public boolean addLoginLog(String userId, String loginIp) throws Exception;
}
