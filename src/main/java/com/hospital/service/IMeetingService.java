/*package com.hospital.service;

*//**
 * 会议服务接口
 * 
 * @author yubing
 *
 *//*
public interface IMeetingService {
	*//**
	 * 新增会议
	 * 
	 * @author yubing
	 * 
	 * @param sponsorType 会议发起方式，1即时会议，2预约会议
	 * @param mediaType 会议媒体类型，0：视频会议，1：语音会议
	 * @param subject 会议主题
	 * @param relaLong 实际会议时长
	 * @param code 华为会议编码
	 * @throws Exception
	 *//*
	public String addMeeting(Integer sponsorType, Integer mediaType, 
			String subject, double relaLong , String code, String sponPwd) throws Exception;
	
	*//**
	 * 发起会议
	 * 
	 * @author yubing
	 * @param sponsorType
	 * @param mediaType
	 * @param subject
	 * @param relaLong
	 * @param code
	 * @throws Exception
	 *//*
	public void startMeeting(Integer sponsorType, Integer mediaType, 
			String subject, double realLong) throws Exception;
	
}
*/