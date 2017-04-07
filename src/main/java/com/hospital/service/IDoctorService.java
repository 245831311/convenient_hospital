package com.hospital.service;

import java.util.Map;


/**
 * 用户登陆接口
 * 
 */
public interface IDoctorService {

	Map<String, Object> getPatentById(String id);

	Map<String, Object> checkRegister(String username, String id);

	Map<String, Object> signReport(String username, String id, String content);

}
