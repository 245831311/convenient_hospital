package com.bkybk.service;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.hospital.model.Agenda;
import com.hospital.service.IAgendaService;
/**
 * 直接测试service,推荐
 * 
 * @author yubing
 *
 */
public class AgendaServiceTest2  extends BasicTest{
	@Resource(name="agendaService")
    private IAgendaService agendaService;
	
    @Test
    public void appendAgendaForCache() {
    	try {
			agendaService.appendAgendaForCache("15:00", "我要添加会议啊！", "张三");
		} catch (Exception e) {
			e.printStackTrace();
		};
    }
    
    @Test
    public void getAgendasFromCache() {
    	try {
    		//HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(); 
    		//request.setAttribute("age", 23);
    		List<Agenda> agendas = agendaService.getAgendasFromCache();
    		System.out.println(agendas);
		} catch (Exception e) {
			e.printStackTrace();
		};
    }
    
    @Test
    public void getAgendaFromCache(){
    	Agenda agenda = null;
    	try {
			agenda = agendaService.getAgendaFromCache("798001059258695680");
			System.out.println(JSONObject.toJSONString(agenda));
		} catch (Exception e) {
			e.printStackTrace();
		};
    }
    
    @Test
    public void delAgendaFromCache(){
    	Agenda agenda = null;
    	try {
			agendaService.delAgendaFromCache("798006315505942528");
			System.out.println(JSONObject.toJSONString(agenda));
		} catch (Exception e) {
			e.printStackTrace();
		};
    }
    
    @Test
    public void modifyAgendaFromCache(){
    	Agenda agenda = null;
    	try {
			agendaService.modifyAgendaFromCache("798006315505942528","15:00", "我要添加会议啊！", "李四");
			System.out.println(JSONObject.toJSONString(agenda));
		} catch (Exception e) {
			e.printStackTrace();
		};
    }
}
