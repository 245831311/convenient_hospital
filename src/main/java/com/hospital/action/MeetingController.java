package com.hospital.action;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hospital.common.Code;
import com.hospital.common.ResponseEntity;
import com.hospital.common.ServiceException;
import com.hospital.service.IMeetingService;

/**
 * 会议服务控制器
 * @author yubing
 *
 */
@Controller
@RequestMapping("/meetingController")
public class MeetingController extends BaseController{
	private Logger logger = Logger.getLogger(this.getClass());
	@Resource(name="meetingService")
	private IMeetingService meetingService;
	
	@RequestMapping(value = "/addMeeting", method = RequestMethod.POST)
	public void addMeeting(){
		try {
			int sponsorType = this.getParameterToInt(request, "sponsorType"); 
			int mediaType = this.getParameterToInt(request, "mediaType"); 
			int realLong = this.getParameterToInt(request, "relaLong"); 
			String subject = this.getParamStr(request, "subject"); 
			String code = this.getParamStr(request, "code"); 
			meetingService.addMeeting(sponsorType, mediaType, subject, realLong, code,"");
		} catch (Exception e) {
			logger.error("[Controller.UploadController] importMeetingFileForCache occur exception!",e);
		}
	}
	
	/**
	 * 发起会议
	 */
	@RequestMapping(value = "/startMeeting", method = RequestMethod.POST)
	public void startMeeting(){
		ResponseEntity resEntity = new ResponseEntity();
		try {
			Integer sponsorType = this.getParameterToIntRetNull(request, "sponsorType"); 
			Integer mediaType = this.getParameterToIntRetNull(request, "mediaType"); 
			Double realLong = this.getParameterToDoubleRetNull(request, "relaLong"); 
			String subject = this.getParamStr(request, "subject"); 
			
			meetingService.startMeeting(sponsorType, mediaType, subject, realLong);
			resEntity.setCode(Code.CODE_OK);
		} catch (Exception e) {
			resEntity.setCode(Code.CODE_SERVER_ERROR);
			logger.error("[Controller.UploadController] importMeetingFileForCache occur exception!",e);
			if(e instanceof ServiceException){
				ServiceException se = (ServiceException)e;
				if(se.getErrorCode()==Code.CODE_LIMIT_ERROR){//资源不足，跳转到预约会议页面
					resEntity.setCode(Code.CODE_LIMIT_ERROR);
				}
			}
		}
		
		this.renderJson(response, this.toJsonString(resEntity));
	}
	
}
