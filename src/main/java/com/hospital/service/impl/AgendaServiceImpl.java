package com.hospital.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.constants.AppConstants;
import com.hospital.dao.AgendaDao;
import com.hospital.model.Agenda;
import com.hospital.service.IAgendaService;
import com.hospital.util.DateUtil;
import com.hospital.util.IdWorkerUtil;
import com.hospital.util.RedisUtil;
import com.hospital.util.ResourcesUtil;
import com.hospital.util.SerializableUtils;
/**
 * 用户登陆日志服务实现类
 * 
 * @author yubing
 *
 */
@Service("agendaService")
public class AgendaServiceImpl implements IAgendaService{
	@Resource(name="agendaDao")
	private AgendaDao agendaDao;
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public void appendAgendaForCache(String time, String content, String staff) throws Exception {
		String sessionId = request.getSession().getId();
		Agenda agenda = new Agenda();
		agenda.setAgendaId(String.valueOf(IdWorkerUtil.nextId()));
		agenda.setTime(DateUtil.parseTimeNoMillis(time).getTime());
		agenda.setContent(content);
		agenda.setStaff(staff);
		
		String radisKey = ResourcesUtil.getValue(
				AppConstants.COMMON_PROPERTIES, AppConstants.REDIS_AGENDAS, new Object[]{sessionId});
		
		List<String> values = new ArrayList<String>();
		String[] arr = new String[values.size()];
		byte[] b1 = SerializableUtils.serialize(agenda);
		String value = new String(b1, AppConstants.CHARSET_ISO8859_1);
		
		if(RedisUtil.exists(radisKey)){//更新redis值集合
			long len = RedisUtil.llen(radisKey);
			values = RedisUtil.lrange(radisKey, 0, len);
			values.add(value);
			long count = RedisUtil.del(radisKey);
			if(count==1){
				RedisUtil.rpush(radisKey, values.toArray(arr));
			}
		}else{
			values.add(value);
			RedisUtil.rpush(radisKey, values.toArray(arr));
		}
	}

	@Override
	public List<Agenda> getAgendasFromCache() throws Exception {
		String sessionId = request.getSession().getId();
		String radisKey = ResourcesUtil.getValue(
				AppConstants.COMMON_PROPERTIES, AppConstants.REDIS_AGENDAS, new Object[]{sessionId});
		List<String> values = new ArrayList<String>();
		List<Agenda> agendas = new ArrayList<>();
		Agenda agenda = new Agenda();
		
		if(RedisUtil.exists(radisKey)){
			long len = RedisUtil.llen(radisKey);
			if(len>0){
				values = RedisUtil.lrange(radisKey, 0, len);
				for(String value : values){
					agenda = SerializableUtils.unserialize(
							value.getBytes(AppConstants.CHARSET_ISO8859_1), Agenda.class);
					if(agenda.getTime()!=null){
						agenda.setFormatTime(DateUtil.formatTimeNoMillis(agenda.getTime()));
					}
					agendas.add(agenda);
				}
			}
		}
		
		return agendas;
	}

	@Override
	public Agenda getAgendaFromCache(String agendaId) throws Exception {
		String sessionId = request.getSession().getId();
		String radisKey = ResourcesUtil.getValue(
				AppConstants.COMMON_PROPERTIES, AppConstants.REDIS_AGENDAS, new Object[]{sessionId});
		List<String> values = new ArrayList<String>();
		Agenda agenda = new Agenda();
		
		if(RedisUtil.exists(radisKey)){
			long len = RedisUtil.llen(radisKey);
			if(len>0){
				values = RedisUtil.lrange(radisKey, 0, len);
				for(String value : values){
					agenda = SerializableUtils.unserialize(value.getBytes(AppConstants.CHARSET_ISO8859_1), Agenda.class);
					if (agenda.getAgendaId()!=null && agenda.getAgendaId().equals(agendaId)) {
						if(agenda.getTime()!=null){
							agenda.setFormatTime(DateUtil.formatTimeNoMillis(agenda.getTime()));
						}
						return agenda;
					}
				}
			}
		}
		
		return null;
	}

	@Override
	public void delAgendaFromCache(String agendaId) throws Exception {
		String sessionId = request.getSession().getId();
		String radisKey = ResourcesUtil.getValue(
				AppConstants.COMMON_PROPERTIES, AppConstants.REDIS_AGENDAS, new Object[]{sessionId});
		List<String> values = new ArrayList<String>();
		String[] arr = new String[values.size()];
		Agenda agenda = null;
		
		if(RedisUtil.exists(radisKey)){//更新redis值集合
			long len = RedisUtil.llen(radisKey);
			if(len>0){
				values = RedisUtil.lrange(radisKey, 0, len);
				for(String value : values){
					agenda = SerializableUtils.unserialize(
							value.getBytes(AppConstants.CHARSET_ISO8859_1), Agenda.class);
					if (agenda.getAgendaId()!=null && agenda.getAgendaId().equals(agendaId)){
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
	public void modifyAgendaFromCache(String agendaId,String time, 
			String content, String staff) throws Exception {
		String sessionId = request.getSession().getId();
		Agenda entity = new Agenda();
		entity.setAgendaId(agendaId);
		entity.setTime(DateUtil.parseTimeNoMillis(time).getTime());
		entity.setContent(content);
		entity.setStaff(staff);
		String val = new String(SerializableUtils.serialize(entity),AppConstants.CHARSET_ISO8859_1);
		
		String radisKey = ResourcesUtil.getValue(
				AppConstants.COMMON_PROPERTIES, AppConstants.REDIS_AGENDAS, new Object[]{sessionId});
		
		List<String> values = new ArrayList<String>();
		String[] arr = new String[values.size()];
		Agenda agenda = null;
		if(RedisUtil.exists(radisKey)){//更新redis值集合
			long len = RedisUtil.llen(radisKey);
			if(len>0){
				values = RedisUtil.lrange(radisKey, 0, len);
				for(String value : values){
					agenda = SerializableUtils.unserialize(
							value.getBytes(AppConstants.CHARSET_ISO8859_1), Agenda.class);
					if (agenda.getAgendaId()!=null && agenda.getAgendaId().equals(agendaId)){
						values.remove(value);
						break;
					} 
				}
				
				long count = RedisUtil.del(radisKey);
				if(count==1){
					values.add(val);
					RedisUtil.rpush(radisKey, values.toArray(arr));
				}
			}
		}
	}

	@Override
	public boolean batchInsertAgendas(List<Agenda> agendas) throws Exception {
		return agendaDao.batchInsertAgendas(agendas);
	}
}
