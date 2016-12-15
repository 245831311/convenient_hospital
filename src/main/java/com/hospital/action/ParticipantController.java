package com.hospital.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hospital.common.Code;
import com.hospital.common.ResponseEntity;
import com.hospital.model.Participant;
import com.hospital.service.IParticipantService;
/**
 * 参会人员控制器
 * @author yubing
 *
 */
@Controller
@RequestMapping("/participantController")
public class ParticipantController extends BaseController{
	private Logger logger = Logger.getLogger(this.getClass());
	@Resource(name="participantService")
	private IParticipantService participantService;
	
	/**
	 * 根据sessionId从radis缓存中获取所有参会人员
	 * 
	 * @author yubing
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getParticipantsFromCache", method = RequestMethod.POST)
	public void getParticipantsFromCache(){
		ResponseEntity resEntity = new ResponseEntity();
		resEntity.setCode(Code.CODE_SERVER_ERROR);
		
		List<Participant> participants = new ArrayList<>();
		try {
			if(logger.isDebugEnabled()){
				logger.debug("[Controller.ParticipantController] getParticipantsFromCache begin!");
			}
			
			participants = participantService.getParticipantsFromCache();
			resEntity.setCode(Code.CODE_OK);
			Map<String,Object> receipt = new HashMap<String,Object>();
			receipt.put("Participants", participants);
			resEntity.setReceipt(receipt);
		} catch (Exception e) {
			logger.error("[Controller.ParticipantController] getParticipantsFromCache occur exception!",e);
		}
		
		this.renderJson(response, this.toJsonString(resEntity));
	}
	
	@RequestMapping(value = "/batchAddParticipantForCache", method = RequestMethod.POST)
	public void batchAddParticipantForCache(){
		ResponseEntity resEntity = new ResponseEntity();
		resEntity.setCode(Code.CODE_SERVER_ERROR);
		
		try {
			String userIds = this.getParamStr(request, "userIds");
			if(logger.isDebugEnabled()){
				logger.debug("[Controller.ParticipantController] batchAddParticipantForCache begin!userIds:"+userIds);
			}
			
			participantService.batchAddParticipantForCache(userIds);
			resEntity.setCode(Code.CODE_OK);
		} catch (Exception e) {
			logger.error("[Controller.ParticipantController] batchAddParticipantForCache occur exception!",e);
		}
		
		this.renderJson(response, this.toJsonString(resEntity));
	}
	
	@RequestMapping(value = "/delParticipantFromCache", method = RequestMethod.POST)
	public void delParticipantFromCache(){
		ResponseEntity resEntity = new ResponseEntity();
		resEntity.setCode(Code.CODE_SERVER_ERROR);
		
		try {
			String participantId = this.getParamStr(request, "participantId");
			if(logger.isDebugEnabled()){
				logger.debug("[Controller.ParticipantController] delParticipantFromCache begin!participantId:"+participantId);
			}
			
			participantService.delParticipantFromCache(participantId);
			resEntity.setCode(Code.CODE_OK);
		} catch (Exception e) {
			logger.error("[Controller.ParticipantController] delParticipantFromCache occur exception!",e);
		}
		
		this.renderJson(response, this.toJsonString(resEntity));
	}
	
	@RequestMapping(value = "/searchParticipantsFromCache", method = RequestMethod.POST)
	public void searchParticipantsFromCache(){
		List<Participant> participants = new ArrayList<>();
		ResponseEntity resEntity = new ResponseEntity();
		resEntity.setCode(Code.CODE_SERVER_ERROR);
		
		try {
			String searchKey = this.getParamStr(request, "searchKey");
			if(logger.isDebugEnabled()){
				logger.debug("[Controller.ParticipantController] searchParticipantsFromCache begin!searchKey:"+searchKey);
			}
			
			participantService.searchParticipantsFromCache(searchKey);
			resEntity.setCode(Code.CODE_OK);
			Map<String,Object> receipt = new HashMap<String,Object>();
			receipt.put("Participants", participants);
			resEntity.setReceipt(receipt);
		} catch (Exception e) {
			logger.error("[Controller.ParticipantController] searchParticipantsFromCache occur exception!",e);
		}
		
		this.renderJson(response, this.toJsonString(resEntity));
	}
}
