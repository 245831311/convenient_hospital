/*package com.hospital.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hospital.common.Code;
import com.hospital.common.MybatisBaseDao;
import com.hospital.common.ServiceException;
import com.hospital.util.IdWorkerUtil;

*//**
 * 会议DAO
 * @author yubing
 * 
 *//*
@Repository("meetingDao")
public class MeetingDao extends MybatisBaseDao<Object,String>{
	*//**
	 * 发起即时会议
	 * 
	 * @author yubing
	 * @param sponsorType
	 * @param mediaType
	 * @param subject
	 * @param relaLong
	 * @return
	 * @throws ServiceException
	 *//*
	public String addImmediateMeeting(Map<String,Object> params) throws ServiceException{
		long id = IdWorkerUtil.nextId();
		try {
			params.put("meetingId", String.valueOf(id));
			getSqlSession().insert(getSqlName("insert"), params);
		} catch (Exception e) {
			throw new ServiceException("LoginLogDao.addImmediateMeeting exception!", e,Code.CODE_SERVER_ERROR,"");
		}
		
		return String.valueOf(id);
	}
	
}
*/