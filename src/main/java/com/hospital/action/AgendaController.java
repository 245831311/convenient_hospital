/*package com.hospital.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hospital.common.Code;
import com.hospital.common.ResponseEntity;
import com.hospital.service.IAgendaService;
*//**
 * 用户控制器
 * @author yubing
 *
 *//*
@Controller
@RequestMapping("/agendaController")
public class AgendaController extends BaseController{
	private Logger logger = Logger.getLogger(this.getClass());
	@Resource(name="agendaService")
	private IAgendaService agendaService;
	
	*//**
	 * 发起会议中添加议程，保存到radis缓存，需要提前生成议程ID方便界面编辑。
	 * key为登陆用户的sessionId+”-”+agendas, value为List<Agenda>。
	 * 
	 * @author yubing
	 * @param request
	 * @param response
	 *//*
	@RequestMapping(value = "/addAgendaForCache", method = RequestMethod.POST)
	public void addAgendaForCache(HttpServletRequest request, HttpServletResponse response){
		ResponseEntity resEntity = new ResponseEntity();
		resEntity.setCode(Code.CODE_SERVER_ERROR);
		
		try {
			String time = this.getParamStr(request, "time");
			String content = this.getParamStr(request, "content");
			String staff = this.getParamStr(request, "staff");
			
			if(logger.isDebugEnabled()){
				logger.debug("[Controller.AgendaController] addAgendaForCache begin!time:"+time
						+",content:"+content+",staff:"+staff);
			}
			
			agendaService.appendAgendaForCache(time, content, staff);
		} catch (Exception e) {
			logger.error("[Controller.AgendaController] addAgendaForCache occur exception!",e);
			this.renderJson(response, this.buildDefaultErrorRespone());
			return;
		}
		
		this.renderJson(response, this.buildDefaultSuccessRespone());
	}
	
	*//**
	 * 根据sessionId从radis缓存中获取所有议程
	 * 
	 * @author yubing
	 * @return
	 * @throws Exception
	 *//*
	@RequestMapping(value = "/getAgendasFromCache", method = RequestMethod.POST)
	public void getAgendasFromCache(){
		ResponseEntity resEntity = new ResponseEntity();
		resEntity.setCode(Code.CODE_SERVER_ERROR);
		
		List<Agenda> agendas = new ArrayList<>();
		try {
			if(logger.isDebugEnabled()){
				logger.debug("[Controller.AgendaController] getAgendasFromCache begin!");
			}
			
			agendas = agendaService.getAgendasFromCache();
			resEntity.setCode(Code.CODE_OK);
			Map<String,Object> receipt = new HashMap<String,Object>();
			receipt.put("agendas", agendas);
			resEntity.setReceipt(receipt);
		} catch (Exception e) {
			logger.error("[Controller.AgendaController] getAgendasFromCache occur exception!",e);
		}
		
		this.renderJson(response, this.toJsonString(resEntity));
	}
	
	@RequestMapping(value = "/getAgendaFromCache", method = RequestMethod.POST)
	public void getAgendaFromCache(@RequestParam("agendaId") String agendaId){
		
		ResponseEntity resEntity = new ResponseEntity();
		resEntity.setCode(Code.CODE_SERVER_ERROR);
		
		if(agendaId==null){
			logger.warn("[Controller.AgendaController] getAgendaFromCache agendaId required!");
			resEntity.setMessage("会议议程ID不能为空!");
			this.renderJson(response, this.toJsonString(resEntity));
		}
		
		Agenda agenda = new Agenda();
		try {
			if(logger.isDebugEnabled()){
				logger.debug("[Controller.AgendaController] getAgendaFromCache begin!");
			}
			
			agenda = agendaService.getAgendaFromCache(agendaId);
			resEntity.setCode(Code.CODE_OK);
			Map<String,Object> receipt = new HashMap<String,Object>();
			receipt.put("agenda", agenda);
			resEntity.setReceipt(receipt);
		} catch (Exception e) {
			logger.error("[Controller.AgendaController] getAgendaFromCache occur exception!",e);
		}
		
		this.renderJson(response, this.toJsonString(resEntity));
	}
	
	@RequestMapping(value = "/delAgendaFromCache", method = RequestMethod.POST)
	public void delAgendaFromCache(@RequestParam("agendaId") String agendaId){
		ResponseEntity resEntity = new ResponseEntity();
		resEntity.setCode(Code.CODE_SERVER_ERROR);
		
		if(agendaId==null){
			logger.warn("[Controller.AgendaController] delAgendaFromCache agendaId required!");
			resEntity.setMessage("会议议程ID不能为空!");
			this.renderJson(response, this.toJsonString(resEntity));
		}
		
		try {
			if(logger.isDebugEnabled()){
				logger.debug("[Controller.AgendaController] delAgendaFromCache begin!");
			}
			
			agendaService.delAgendaFromCache(agendaId);
			resEntity.setCode(Code.CODE_OK);
		} catch (Exception e) {
			logger.error("[Controller.AgendaController] delAgendaFromCache occur exception!",e);
		}
		
		this.renderJson(response, this.toJsonString(resEntity));
	}
	
	@RequestMapping(value = "/modifyAgendaFromCache", method = RequestMethod.POST)
	public void modifyAgendaFromCache(@RequestParam("agendaId") String agendaId){
		String time = this.getParamStr(request, "time"),
				content = this.getParamStr(request, "content"), 
				staff = this.getParamStr(request, "staff");
		
		ResponseEntity resEntity = new ResponseEntity();
		resEntity.setCode(Code.CODE_SERVER_ERROR);
		
		if(agendaId==null){
			logger.warn("[Controller.AgendaController] modifyAgendaFromCache agendaId required!");
			resEntity.setMessage("会议议程ID不能为空!");
			this.renderJson(response, this.toJsonString(resEntity));
		}
		
		try {
			if(logger.isDebugEnabled()){
				logger.debug("[Controller.AgendaController] modifyAgendaFromCache begin!");
			}
			
			agendaService.modifyAgendaFromCache(agendaId, time, content, staff);
			resEntity.setCode(Code.CODE_OK);
		} catch (Exception e) {
			logger.error("[Controller.AgendaController] modifyAgendaFromCache occur exception!",e);
		}
		
		this.renderJson(response, this.toJsonString(resEntity));
	}
}
*/