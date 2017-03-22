/*package com.hospital.dao;

import org.springframework.stereotype.Repository;

import com.hospital.common.Code;
import com.hospital.common.MybatisBaseDao;
import com.hospital.common.ServiceException;
import com.hospital.model.Upload;
import com.hospital.util.IdWorkerUtil;

*//**
 * 会议材料上传日志DAO
 * @author yubing
 *
 *//*
@Repository("uploadDao")
public class UploadDao extends MybatisBaseDao<Object,String>{
	public boolean addUploadLog(String fileName, String fileType,
			String fileSize, String filePath, String creator) throws ServiceException{
		try {
			Upload upload = new Upload();
			long id = IdWorkerUtil.nextId();
			upload.setUploadId(String.valueOf(id));
			upload.setFileName(fileName);
			upload.setFileType(fileType);
			upload.setFileSize(fileSize);
			upload.setFilePath(filePath);
			upload.setCreator(creator);
			upload.setCreateTime(System.currentTimeMillis());
			getSqlSession().insert(getSqlName("insert"), upload);
		} catch (Exception e) {
			throw new ServiceException("LoginLogDao.addUploadLog exception!", e,Code.CODE_SERVER_ERROR,"");
		}
		
		return true;
	}
	
	public boolean saveUploadLog(Upload upload) throws ServiceException{
		try {
			getSqlSession().insert(getSqlName("insert"), upload);
		} catch (Exception e) {
			throw new ServiceException("LoginLogDao.saveUploadLog exception!", e,Code.CODE_SERVER_ERROR,"");
		}
		
		return true;
	}
}
*/