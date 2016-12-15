package com.hospital.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.common.Code;
import com.hospital.common.ServiceException;
import com.hospital.constants.AppConstants;
import com.hospital.dao.ParticipantDao;
import com.hospital.model.Participant;
import com.hospital.model.User;
import com.hospital.service.IParticipantService;
import com.hospital.service.IUserService;
import com.hospital.util.IdWorkerUtil;
import com.hospital.util.RedisUtil;
import com.hospital.util.ResourcesUtil;
import com.hospital.util.SerializableUtils;
/**
 * 参会人服务实现类
 * @author yubing
 *
 */
@Service("participantService")
public class ParticipantServiceImpl implements IParticipantService{
	@Resource(name="participantDao")
	private ParticipantDao participantDao;
	@Resource(name="userService")
	private IUserService userService;
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 获取所有参会人列表
	 * 1、如果redis中有参会人列表，查询出所有。
	 * 2、如果redis中没有参会人列表，添加当前人员到参会人列表，并指定为主持人。
	 * 3、存放到redis。
	 * 
	 * @author yubing
	 */
	@Override
	public List<Participant> getParticipantsFromCache() throws ServiceException {
		String radisKey = ResourcesUtil.getRedisKey(request,AppConstants.REDIS_PARTICIPANTS);
		List<String> values = new ArrayList<String>();
		List<Participant> participants = new ArrayList<>();
		Participant participant = new Participant();
		
		if(RedisUtil.exists(radisKey)){//1、查询出所有参会人列表
			long len = RedisUtil.llen(radisKey);
			if(len>0){
				values = RedisUtil.lrange(radisKey, 0, len);
				for(String value : values){
					try {
						participant = SerializableUtils.unserialize(
								value.getBytes(AppConstants.CHARSET_ISO8859_1), Participant.class);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					participants.add(participant);
				}
			}
		}else{//2、添加当前人员到参会人列表，并指定为主持人
			Object sessionVal = ResourcesUtil.getSessionValue(request, AppConstants.SESSION_USER);
			if(sessionVal!=null){
				User user = (User)sessionVal;
				Participant entity = new Participant();
				entity.setParticipantId(String.valueOf(IdWorkerUtil.nextId()));
				entity.setUserId(user.getUserId());
				entity.setPhone(user.getMobile());
				entity.setName(user.getName());
				entity.setIsReserved(Participant.IsReserved.NOT_OBLIGATE.getValue());
				entity.setRole(Participant.Role.MASTER.getValue());
				entity.setIsInvited(Participant.IsInvited.YES.getValue());
				entity.setIsParticipated(Participant.IsParticipated.YES.getValue());
				participants.add(entity);
				
				//3、添加到redis
				List<String> list = new ArrayList<>();
				byte[] b1 = SerializableUtils.serialize(participant);
				try {
					String value = new String(b1, AppConstants.CHARSET_ISO8859_1);
					list.add(value);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				
				RedisUtil.rpush(radisKey, list.toArray(new String[list.size()]));
			}
		}
		
		return participants;
	}

	@Override
	public void batchAddParticipantForCache(String userIds) throws ServiceException {
		List<User> users = userService.getUsersByUserIds(userIds);
		Participant participant = null;
		List<String> list = new ArrayList<String>();
		byte[] b1 = null;
		String value = "";
		
		for(User user :users){
			participant = new Participant();
			participant.setParticipantId(String.valueOf(IdWorkerUtil.nextId()));
			participant.setUserId(user.getUserId());
			participant.setPhone(user.getMobile());
			participant.setName(user.getName());
			participant.setIsReserved(Participant.IsReserved.NOT_OBLIGATE.getValue());
			participant.setRole(Participant.Role.COMMON.getValue());
			participant.setIsInvited(Participant.IsInvited.YES.getValue());
			participant.setIsParticipated(Participant.IsParticipated.YES.getValue());
			
			b1 = SerializableUtils.serialize(participant);
			try {
				value = new String(b1, AppConstants.CHARSET_ISO8859_1);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			list.add(value);
		}
		
		List<String> values = new ArrayList<String>();		
		String radisKey = ResourcesUtil.getRedisKey(request,AppConstants.REDIS_PARTICIPANTS);
		if(RedisUtil.exists(radisKey)){//已添加的参会人员
			long len = RedisUtil.llen(radisKey);
			values = RedisUtil.lrange(radisKey, 0, len);
			long count = RedisUtil.del(radisKey);
			if(count==1){
				if(CollectionUtils.isNotEmpty(list)) list.removeAll(values);
				values.addAll(list);
				RedisUtil.rpush(radisKey, values.toArray(new String[values.size()]));
			}
		}else{
			RedisUtil.rpush(radisKey, list.toArray(new String[list.size()]));
		}
	}
	
	@Override
	public void delParticipantFromCache(String participantId) throws ServiceException{
		String radisKey = ResourcesUtil.getRedisKey(request,AppConstants.REDIS_PARTICIPANTS);
		List<String> values = new ArrayList<String>();
		String[] arr = new String[values.size()];
		Participant participant = null;
		
		if(RedisUtil.exists(radisKey)){//更新redis值集合
			long len = RedisUtil.llen(radisKey);
			if(len>0){
				values = RedisUtil.lrange(radisKey, 0, len);
				for(String value : values){
					try {
						participant = SerializableUtils.unserialize(
								value.getBytes(AppConstants.CHARSET_ISO8859_1), Participant.class);
					} catch (UnsupportedEncodingException e) {
						throw new ServiceException("ParticipantServiceImpl.delParticipantFromCache unserialize exception!", 
								e, Code.CODE_SERVER_ERROR, "");
					}
					if (participant.getParticipantId()!=null && 
							participant.getParticipantId().equals(participantId)){
						values.remove(value);
						break;
					}
				}
				
				long count = RedisUtil.del(radisKey);
				if(count==1){
					RedisUtil.rpush(radisKey, values.toArray(arr));
				}
			}
		}
	}
	
	@Override
	public List<Participant> searchParticipantsFromCache(String searchKey) throws ServiceException{
		List<Participant> list = new ArrayList<>();
		String radisKey = ResourcesUtil.getRedisKey(request,AppConstants.REDIS_PARTICIPANTS);
		List<String> values = new ArrayList<String>();
		Participant participant = new Participant();
		
		if(RedisUtil.exists(radisKey)){
			long len = RedisUtil.llen(radisKey);
			if(len>0){
				String phone = "",name = "";
				values = RedisUtil.lrange(radisKey, 0, len);
				for(String value : values){
					try {
						participant = SerializableUtils.unserialize(value.getBytes(AppConstants.CHARSET_ISO8859_1), Participant.class);
					} catch (UnsupportedEncodingException e) {
						throw new ServiceException("ParticipantServiceImpl.getParticipantFromCache unserialize exception!", 
								e, Code.CODE_SERVER_ERROR, "");
					}
					
					phone = participant.getPhone();
					name = participant.getName();
					if ((StringUtils.isNotBlank(phone)&&phone.contains(searchKey))
							||(StringUtils.isNotBlank(name)&&name.contains(searchKey))) {
						list.add(participant);
					}
				}
			}
		}
		
		return list;
	}

	@Override
	public boolean batchInsertParticipants(List<Participant> participants) throws Exception {
		return participantDao.batchInsertParticipants(participants);
	}
}
