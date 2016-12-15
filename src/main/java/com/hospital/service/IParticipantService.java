package com.hospital.service;

import java.util.List;

import com.hospital.common.ServiceException;
import com.hospital.model.Participant;

/**
 * 参会人服务接口
 * @author yubing
 *
 */
public interface IParticipantService {
	/**
	 * 根据sessionId从redis缓存中获取所有议程
	 * 
	 * @author yubing
	 * @return
	 * @throws Exception
	 */
	public List<Participant> getParticipantsFromCache() throws ServiceException;
	
	/**
	 * 批量添加参会人员到redis
	 * 
	 * @author yubing
	 * @param userIds
	 * @throws Exception
	 */
	public void batchAddParticipantForCache(String userIds) throws ServiceException;
	
	/**
	 * 根据参会人ID从redis中删除
	 * 
	 * @author yubing
	 * @param participantId
	 * @throws ServiceException
	 */
	public void delParticipantFromCache(String participantId) throws ServiceException;
	
	/**
	 * 根据参会人姓名或手机号码进行搜索
	 * 
	 * @author yubing
	 * @param searchKey
	 * @return
	 * @throws ServiceException
	 */
	public List<Participant> searchParticipantsFromCache(String searchKey) throws ServiceException;
	
	/**
	 * 批量更新参会人
	 * 
	 * @author yubing
	 * @param participatns
	 * @return
	 * @throws ServiceException
	 */
	public boolean batchInsertParticipants(List<Participant> participants) throws Exception;
}
