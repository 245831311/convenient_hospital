package com.hospital.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.hospital.constants.AppConstants;
import com.hospital.dao.UploadDao;
import com.hospital.model.Upload;
import com.hospital.model.User;
import com.hospital.service.IUploadService;
import com.hospital.util.IdWorkerUtil;
import com.hospital.util.RedisUtil;
import com.hospital.util.ResourcesUtil;
import com.hospital.util.SerializableUtils;
/**
 * 会议材料上传日志服务实现类
 * 
 * @author yubing
 *
 */
@Service("uploadService")
public class UploadServiceImpl implements IUploadService{
	@Resource(name="uploadDao")
	private UploadDao uploadDao;
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public void addUploadLogForCache(String fileName, String fileType,
			String fileSize, String filePath) throws Exception{
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute(AppConstants.SESSION_USER);
		
		Upload upload = new Upload();
		long id = IdWorkerUtil.nextId();
		upload.setUploadId(String.valueOf(id));
		upload.setFileName(fileName);
		upload.setFileType(fileType);
		upload.setFileSize(fileSize);
		upload.setFilePath(filePath);
		upload.setCreator(user.getUserId());
		upload.setCreateTime(System.currentTimeMillis());
		
		String redisKey = ResourcesUtil.getRedisKey(request, AppConstants.REDIS_UPLOADMEETINGFILELOG);
		if(StringUtils.isNotBlank(redisKey)){//清空redis之前上传的文件
			RedisUtil.del(redisKey);
		}
		byte[] b1 = SerializableUtils.serialize(upload);
		String value = new String(b1, AppConstants.CHARSET_ISO8859_1);
		RedisUtil.set(redisKey, value);
	}
	
	@Override
	public Upload getUploadLogForCache() throws Exception {
		String redisKey = ResourcesUtil.getRedisKey(request, AppConstants.REDIS_UPLOADMEETINGFILELOG);
		
		if(RedisUtil.exists(redisKey)){
			String redisValue = RedisUtil.get(redisKey);
			return SerializableUtils.unserialize(
					redisValue.getBytes(AppConstants.CHARSET_ISO8859_1), Upload.class);
		}
		
		return null;
	}
	
	@Override
	public Map<String,String> commonImport(HttpServletRequest hrequest, String tempPath) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		String destFile = null;
		tempPath = ResourcesUtil.getValue(
				AppConstants.COMMON_PROPERTIES, tempPath);
		
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(hrequest.getSession()
				.getServletContext());
		if (multipartResolver.isMultipart(hrequest)) {
			try {
				hrequest.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} 
			
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) hrequest;
			// 文件上传路径
			Iterator<String> iterator = multiRequest.getFileNames();
			if (iterator.hasNext()) {
				String uploadFileName = iterator.next();
				MultipartFile multipartfile = multiRequest.getFile(uploadFileName);
				File localFile = null;
                String name = multipartfile.getOriginalFilename();
                map.put("fileName", name);
                
                String fileType = name.substring(name.lastIndexOf(".")+1);
                map.put("fileType", fileType);
                
                String fileSize = String.valueOf(multipartfile.getSize());
                map.put("fileSize", fileSize);
                
                name = new Date().getTime()+"."+fileType;
            	if (name != null && name.length() > 0) {
            		localFile = new File(tempPath+File.separator+name);
                }
				// 上传
				try {
					File root = new File(localFile.toString());
					if (!root.getParentFile().exists()) {
						root.mkdirs();
					}
					multipartfile.transferTo(localFile);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				destFile = localFile.getAbsolutePath();
				map.put("filePath", destFile);
			}
		}
		
		return map;
	}

	@Override
	public boolean saveUploadLog(Upload upload) throws Exception {
		return uploadDao.saveUploadLog(upload);
	}

}
