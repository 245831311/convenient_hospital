package com.hospital.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hospital.common.Code;
import com.hospital.common.MybatisBaseDao;
import com.hospital.common.ServiceException;
import com.hospital.model.Agenda;
/**
 * 议程DAO
 * @author yubing
 *
 */
@Repository("agendaDao")
public class AgendaDao extends MybatisBaseDao<Object,String> {
	public boolean batchInsertAgendas(List<Agenda> agendas) throws ServiceException{
		try {
			getSqlSession().insert(getSqlName("batchInsertAgendas"), agendas);
		} catch (Exception e) {
			throw new ServiceException("UserDao.batchInsertAgendas occur exception!", e, Code.CODE_SERVER_ERROR, "");
		}
		
    	return true;
	}
}
