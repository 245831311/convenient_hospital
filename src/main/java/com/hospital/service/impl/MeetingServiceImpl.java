/*package com.hospital.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.common.Code;
import com.hospital.common.ServiceException;
import com.hospital.constants.AppConstants;
import com.hospital.dao.MeetingDao;
import com.hospital.model.Agenda;
import com.hospital.model.Meeting;
import com.hospital.model.Organization;
import com.hospital.model.Participant;
import com.hospital.model.ResourceAlloc;
import com.hospital.model.ResourceMap;
import com.hospital.model.Upload;
import com.hospital.model.User;
import com.hospital.service.IAgendaService;
import com.hospital.service.IMeetingService;
import com.hospital.service.IParticipantService;
import com.hospital.service.IResourceAllocService;
import com.hospital.service.IResourceMapService;
import com.hospital.service.IUploadService;
import com.hospital.suntek.util.Conference;
import com.hospital.suntek.util.ConferenceManager;
import com.hospital.suntek.util.Conference.AddressEntry;
import com.hospital.suntek.util.Conference.Attendee;
import com.hospital.util.DateUtil;
import com.hospital.util.IdWorkerUtil;
import com.hospital.util.RedisUtil;
import com.hospital.util.ResourcesUtil;
import com.hospital.util.SerializableUtils;
*//**
 * 会议服务接口实现类
 * 
 * @author yubing
 *
 *//*
@Service("meetingService")
public class MeetingServiceImpl implements IMeetingService{
	private Logger logger = Logger.getLogger(this.getClass());
	
	ConferenceManager mgr = ConferenceManager.getInstance();
	
	@Resource(name="meetingDao")
	private MeetingDao meetingDao;
	@Resource(name="resourceMapService")
	private IResourceMapService resourceMapService;
	@Resource(name="resourceService")
	private IResourceAllocService resourceService;
	@Resource(name="participantService")
	private IParticipantService participantService;
	@Resource(name="agendaService")
	private IAgendaService agendaService;
	@Resource(name="uploadService")
	private IUploadService uploadService;
	@Autowired
	private HttpServletRequest request;

	@Override
	public String addMeeting(Integer sponsorType, Integer mediaType, 
			String subject, double relaLong, String code, String sponPwd) throws Exception {
		Map<String,Object> params = new HashMap<>();
		User user = (User)request.getSession().getAttribute(AppConstants.SESSION_USER);
		Organization organization = 
				(Organization)request.getSession().getAttribute(AppConstants.SESSION_ORGANIZATION);
		if(sponsorType!=null && 
				sponsorType.intValue()==Meeting.SponsorType.ImmediateMeeting.getValue()){//即时会议
			params.put("sponsorType", sponsorType);
			params.put("mediaType", mediaType);
			params.put("subject", subject);
			params.put("orgId", organization.getOrgId());
			params.put("code", code);
			params.put("realLong", relaLong);
			params.put("realStartTime", System.currentTimeMillis());
			params.put("realEndTime", System.currentTimeMillis()+relaLong*60*60*1000);
			params.put("sponsorId", user.getUserId());
			params.put("status", Meeting.Status.PROCING);
			params.put("sponPwd", sponPwd);
		}
		
		return meetingDao.addImmediateMeeting(params);
	}

	*//**
	 * 1、预分配可用资源时间段，若请求时间段内资源不可用，则显示所有不可用时间段置灰
	 * 2、更新redis中预留参会人员
	 * 3、组装数据结构请求会管系统
	 * 4、请求成功，返回数据结构。提取数据插表
	 * 5、删除redis缓存
	 * 6、TODO 事务控制，要求发起会议在同一事务内完成，否则一起回滚。
	 *
	 *@author yubing
	 *//*
	@Override
	public void startMeeting(Integer sponsorType, Integer mediaType, 
			String subject, double realLong) throws Exception {
		//1、预分配可用资源时间段资源
		if(logger.isDebugEnabled()){
			logger.debug("startMeeting.getParticipantsFromCache begin!");
		}
		List<Participant> participants = participantService.getParticipantsFromCache();
		if(CollectionUtils.isEmpty(participants)){
			logger.warn("startMeeting.getParticipantsFromCache is empty!");
			throw new ServiceException("请添加参会人员!", Code.CODE_NOT_ACCEPTABLE, "");
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("startMeeting.prepareResources begin!realLong:"+realLong+",participants size:"+participants.size());
		}
		List<ResourceMap> canUseResources = prepareResources(realLong,participants.size());
		if(CollectionUtils.isEmpty(canUseResources)){
			logger.warn("startMeeting.prepareResources canUseResources is empty!");
			throw new ServiceException("startMeeting.prepareResources exception! canUseResources is empty!",
					Code.CODE_LIMIT_ERROR, "");
		}
		
		//2、更新redis中预留参会人员
		if(logger.isDebugEnabled()){
			logger.debug("startMeeting.updatePrepareParticipants begin!canUseResources size:"+canUseResources.size()+",participants:"+participants.size());
		}
		updatePrepareParticipants(canUseResources,participants);
		
		//3、组装数据结构请求会管系统
		if(logger.isDebugEnabled()){
			logger.debug("startMeeting.buildConferenceInfo begin!sponsorType:"+sponsorType+",mediaType:"+mediaType+",subject:"+subject+",realLong:"+realLong);
		}
		Conference entity = buildConferenceInfo(participants, canUseResources, sponsorType, mediaType, subject, realLong);
		
		//4、请求成功，返回数据结构。
		mgr.getVersion();// 获取最高支持版本
		// 请求华为mediax接口创建即时会议
		if (mgr.createConference(entity) != 0) {
			logger.warn("createConference failed!");
			throw new ServiceException("createConference failed!", Code.CODE_SERVER_ERROR, "");
		}
		//持久化发起会议相关表数据。
		persistenceData(participants,entity, sponsorType, mediaType, subject, realLong);//持久化会议数据
		
		//5、清空redis会议缓存
		if(logger.isDebugEnabled()){
			logger.debug("startMeeting.delMeetingRedisKey begin!");
		}
		try {
			delMeetingRedisKey();
		} catch (Exception e) {
			logger.error("startMeeting.delMeetingRedisKey exception!", e);
		}
	}

	*//**
	 * 持久化会议数据
	 * 1、更新会议表华为会议编号和主持人密码，持久化会议
	 * 2、持久化会议议程
	 * 3、更新议会者密码，持久化参会人列表
	 * 4、持久化上传会议材料日志
	 * 
	 * @author yubing
	 * @param participants 与会人列表
	 * @param entity 创建会议实体，返回创建结果(含华为视频编码、主持人密码、普通参会人密码等)
	 * @param sponsorType
	 * @param mediaType
	 * @param subject
	 * @param realLong
	 *//*
	private void persistenceData(List<Participant> participants, Conference entity, 
			Integer sponsorType, Integer mediaType, String subject, double realLong) throws Exception{
		//1、更新会议表华为会议编号和主持人密码，持久化会议。数据暂未返回
		String meetingId = "";
		try {
			meetingId = this.addMeeting(sponsorType, mediaType, subject, realLong, entity.confId, entity.chairPwd);
		} catch (Exception e) {
			logger.error("meetingService.persistenceData-->addMeeting exception!", e);
		}
		
		//2、持久化会议议程
		List<Agenda> agendas = agendaService.getAgendasFromCache();
		for(Agenda agenda :agendas){
			agenda.setMeetingId(meetingId);
		}
		agendaService.batchInsertAgendas(agendas);
		
		//3、更新议会者密码，持久化参会人列表
		Integer role = 0;
		for (Participant participant : participants) {
			participant.setMettingId(meetingId);
			role = participant.getRole();
			participant.setPassword(entity.generalPwd);
			if(role!=null && role.intValue()==Participant.Role.MASTER.getValue()){
				participant.setPassword(entity.chairPwd);
			}
		}
		participantService.batchInsertParticipants(participants);
		
		//4、持久化上传会议材料日志
		Upload upload  =uploadService.getUploadLogForCache();
		upload.setCreateTime(System.currentTimeMillis());
		upload.setRecordId(meetingId);
		uploadService.saveUploadLog(upload);
	}

	*//**
	 * 清空redis会议缓存
	 * 1、删除议会者列表
	 * 2、删除会议议程
	 * 3、删除会议材料
	 * 
	 * @author yubing
	 *//*
	private void delMeetingRedisKey() throws Exception{
		String participants = ResourcesUtil.getRedisKey(request, AppConstants.REDIS_PARTICIPANTS);
		String agendas = ResourcesUtil.getRedisKey(request, AppConstants.REDIS_AGENDAS);
		String uploadMeetingFileLog = ResourcesUtil.getRedisKey(request, AppConstants.REDIS_UPLOADMEETINGFILELOG);
		RedisUtil.del(participants);
		RedisUtil.del(agendas);
		RedisUtil.del(uploadMeetingFileLog);
	}

	*//**
	 * 组装数据结构请求会管系统
	 * 
	 * @author yubing
	 * @param participants 参会人员列表(含预留)
	 * @param canUseResources 可分配资源列表(含预留)
	 * @param sponsorType 会议发起方式，1即时会议，2预约会议
	 * @param mediaType 会议媒体类型，0：视频会议，1：语音会议
	 * @param subject 会议主题
	 * @param relaLong 实际会议时长
	 * @return
	 *//*
	private Conference buildConferenceInfo(List<Participant> participants, List<ResourceMap> canUseResources, Integer sponsorType, Integer mediaType,
			String subject, double relaLong) throws Exception{
		Conference entity = new Conference();
		entity.subject = subject;
		entity.startTime = System.currentTimeMillis();
		entity.timeLen = new Double(relaLong*60*60*1000).longValue();
		entity.size = participants.size();
		
		List<String> mediaTypes = new ArrayList<String>();
		if(mediaType!=null){
			mediaTypes.add(mediaType.toString());
		}else{
			mediaTypes.add(String.valueOf(Meeting.MediaType.VIDEO));
		}
		entity.mediaTypes = mediaTypes;
		
		Map<String,ResourceMap> map = new HashMap<>();
		for(ResourceMap resourceMap : canUseResources){
			if(resourceMap!=null){
				map.put(resourceMap.getResourceId(), resourceMap);
			}
		}
		
		//add attendees
		List<Attendee> attendeeList = new ArrayList<>();
		entity.attendees = attendeeList;
		
		List<AddressEntry> addressEntryList = null;AddressEntry addressEntry = null;
		Attendee attendee = null;
		Integer role = null;
		ResourceMap rm = null;
		for(Participant e : participants){
			rm = map.get(e.getResourceId());//资源对象
			attendee = new Attendee();
			attendeeList.add(attendee);
			
			attendee.attendeeName = e.getName();
			role = e.getRole();
			attendee.conferenceRole = Conference.CONFERENCE_ROLE_GENERAL;
			if(role!=null && role.intValue()==Participant.Role.MASTER.getValue()){//主持人
				attendee.conferenceRole = Conference.CONFERENCE_ROLE_CHAIR;
				entity.mediaxAccount = rm.getMediaxAccount();
				entity.mediaxPwd = rm.getMediaxPassword();
			}
			attendee.attendeeType = "normal";
			
			addressEntryList = new ArrayList<>();
			attendee.addressEntry = addressEntryList;
			addressEntry = new AddressEntry();
			addressEntryList.add(addressEntry);
			addressEntry.type = Conference.ADDRESS_ENTRY_TYPE_PHONE;
			if(rm!=null){
				addressEntry.address = rm.getImsAccount();
			}
		}
		
		return entity;
	}

	*//**
	 * 更新redis中预留参会人员
	 * 1、预留参会人员基数为1.1， Math.ceil(参会人数*1.1)-参会人数 = 预留参会人数
	 * 2、创建预留参会人员。
	 * 3、添加到redis。
	 * 4、为每个参会人员分配资源ID。
	 * 
	 * @author yubing
	 * @param canUseResources
	 * @param participants
	 *//*
	private void updatePrepareParticipants(List<ResourceMap> canUseResources,
			List<Participant> participants) throws Exception{
		List<String> list = new ArrayList<>();
		Participant participant = null;
		byte[] by = null;
		String value = "";
		
		//1、创建预留参会人员。
		for(int i=0,j=canUseResources.size()-participants.size();i<j;i++){
			participant = new Participant();
			participant.setParticipantId(String.valueOf(IdWorkerUtil.nextId()));
			participant.setIsReserved(Participant.IsReserved.OBLIGATE.getValue());
			participant.setRole(Participant.Role.COMMON.getValue());
			participant.setIsInvited(Participant.IsInvited.NO.getValue());
			participant.setIsParticipated(Participant.IsParticipated.NO.getValue());
			participants.add(participant);
			
			by = SerializableUtils.serialize(participant);
			try {
				value = new String(by, AppConstants.CHARSET_ISO8859_1);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			list.add(value);
		}
		
		//2、添加到redis。
		if(CollectionUtils.isNotEmpty(list)){
			String radisKey = ResourcesUtil.getRedisKey(request, AppConstants.REDIS_PARTICIPANTS);
			if(RedisUtil.exists(radisKey)){//已添加的参会人员
				long len = RedisUtil.llen(radisKey);
				List<String> values = RedisUtil.lrange(radisKey, 0, len);
				long count = RedisUtil.del(radisKey);
				if(count==1){
					values.addAll(list);
					RedisUtil.rpush(radisKey, values.toArray(new String[values.size()]));
				}
			}
		}
		
		//4、为每个参会人员分配资源ID。
		if(canUseResources.size()!=participants.size()){
			logger.warn("please init obligate person!");
			throw new ServiceException("please init obligate person!",Code.CODE_SERVER_ERROR, "");
		}
		
		int index = 0;
		ResourceMap resourceMap = null;
		for(Participant entity : participants){
			resourceMap = canUseResources.get(index++);
			if(resourceMap!=null){
				entity.setResourceId(resourceMap.getResourceId());
			}
		}
	}

	*//**
	 * 预分配可用资源时间段
	 * 
	 * 1、根据时间区间获取资源分配
	 * 2、获取所有的资源
	 * 3、获取需要分配的资源数
	 * 		(需要分配的资源数=参会人数*1.1  向上取整，多出的资源数为预留参会人员，需要使用标识为标识)
	 * 4、判断当前时间区间是否还有空闲资源,如有空闲资源去除已分配的资源，从剩余资源中分配需要的资源数
	 * 
	 * @author yubing
	 * @param realLong
	 * @return
	 * @throws Exception
	 *//*
	private List<ResourceMap> prepareResources(double realLong, int requiredCount) throws Exception {
		List<ResourceMap> preResources = new ArrayList<>();
		
		//1、根据时间区间获取资源分配
		long dateTime = DateUtil.parseDate(new Date(), "yyyy-MM-dd").getTime();
		ResourceAlloc resourceAlloc = resourceService.getMaxResourcesByTimeScope(dateTime, realLong);
		int allocCount = 0;//已分配资源数量(指定时间范围内分配资源最多的一个时间段资源数量)
		if(resourceAlloc!=null){
			allocCount = resourceAlloc.getAllocCount();
		}
		
		//2、获取所有的资源
		List<ResourceMap> resourceMaps = resourceMapService.getResourceMapList();
		if(CollectionUtils.isNotEmpty(resourceMaps)){
			//3、获取需要分配的资源数
			Double j = Math.ceil((requiredCount*AppConstants.GET_RESOURCE_BASE));
			requiredCount = j.intValue();
			
			//4、从剩余资源中分配需要的资源数
			if(allocCount>0){//指定时间区间有资源被占用
				if(resourceMaps.size()-allocCount<requiredCount){
					logger.warn("no more resources!please select other timeSection!");
					throw new ServiceException("no more resources!please select other timeSection!",
							Code.CODE_LIMIT_ERROR, "");
				}
				
				String usedResources = resourceAlloc.getAllocSets();
				if(StringUtils.isNotBlank(usedResources)){
					String[] usedResourceArr = usedResources.split(",");
					List<String> list = new ArrayList<String>(Arrays.asList(usedResourceArr));
					if(CollectionUtils.isNotEmpty(list)){
						resourceMaps.removeAll(list);
					}
				}
			}
				
			//直接从所有资源中获取需要的前几个资源
			if(resourceMaps.size()<requiredCount){
				logger.warn("no more resources!please select other timeSection!");
				throw new ServiceException("no more resources!",Code.CODE_LIMIT_ERROR, "");
			}
			int index = 0;
			for(ResourceMap resource : resourceMaps){
				if(++index>requiredCount) break;
				preResources.add(resource);
			}
		}
		
		return preResources;
	}
}
*/