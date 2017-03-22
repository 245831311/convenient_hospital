/*package com.hospital.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hospital.model.Upload;

*//**
 * 会议材料上传日志服务接口
 * 
 * @author yubing
 *
 *//*
public interface IUploadService {
	*//**
	 * 在redis中添加会议材料上传日志
	 * 
	 * @param userId
	 * @param loginIp
	 * @return
	 *//*
	public void addUploadLogForCache(String fileName, String fileType,
			String fileSize, String filePath) throws Exception;
	
	*//**
	 * 从redis中获取会议材料
	 * 
	 * @author yubing
	 * @return
	 * @throws Exception
	 *//*
	public Upload getUploadLogForCache() throws Exception;
	
	*//**
	 * 公共上传
	 * 
	 * @author yubin
	 * @param destPath
	 * @return
	 * @throws Exception
	 *//*
	public Map<String,String> commonImport(HttpServletRequest hrequest, String destPath) throws Exception;
	
	*//**
	 * 持久化上传日志信息
	 * 
	 * @author yubing
	 * @param upload
	 * @return
	 * @throws Exception
	 *//*
	public boolean saveUploadLog(Upload upload) throws Exception;
}
*/