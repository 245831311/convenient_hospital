/*package com.hospital.service;

import java.util.List;

import com.hospital.model.Agenda;

*//**
 * 会议议程服务接口
 * 
 * @author yubing
 *
 *//*
public interface IAgendaService {
	*//**
	 * 记录用户登陆日志
	 * 
	 * @param userId
	 * @param loginIp
	 * @return
	 *//*
	public void appendAgendaForCache(String time, String content, String staff) throws Exception;
	
	*//**
	 * 根据sessionId从redis缓存中获取所有议程
	 * 
	 * @author yubing
	 * @return
	 * @throws Exception
	 *//*
	public List<Agenda> getAgendasFromCache() throws Exception;
	
	*//**
	 * 从redis获取会议议程详情
	 * 
	 * @param agendaId
	 * @return
	 * @throws Exception
	 *//*
	public Agenda getAgendaFromCache(String agendaId) throws Exception;
	
	*//**
	 * 根据sessionId和议程ID从radis缓存中删除议程。
	 * 
	 * @param agendaId
	 * @throws Exception
	 *//*
	public void delAgendaFromCache(String agendaId) throws Exception;
	
	*//**
	 * 根据sessionId和议程ID从radis缓存中编辑议程内容。
	 * 
	 * @param agendaId
	 * @throws Exception
	 *//*
	public void modifyAgendaFromCache(String agendaId,String time, String content, String staff) throws Exception;
	
	*//**
	 * 批量新增会议议程
	 * 
	 * @author yubing
	 * @param agends
	 * @return
	 * @throws Exception
	 *//*
	public boolean batchInsertAgendas(List<Agenda> agendas) throws Exception;
}
*/