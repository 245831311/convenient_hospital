package com.hospital.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.hospital.common.Code;
import com.hospital.common.ResponseEntity;
import com.hospital.model.User;
import com.hospital.service.IConsultService;
import com.hospital.service.IMyCenterService;
import com.hospital.service.IUserService;
/**
 * 用户控制器
 */
@Controller
@RequestMapping("/myCenterController")
public class MyCenterController extends BaseController{
	private Logger logger = Logger.getLogger(this.getClass());
	@Resource(name="myCenterService")
	private IMyCenterService myCenterService;
	
	@RequestMapping(value = "/getMyReport")
	public void getMyReport(){
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			//session中是否存在user对象
			User user = (User) this.session.getAttribute("user");
			if(user == null){
				result.put("message","noLogin");
			}else{
				//通过用户名获取报告
				String username = user.getUserName();
				result = myCenterService.getMyReoptByUserName(username);
			}
		} catch (Exception e) {
			logger.error("[Controller.MyCenterController] getMyReport",e);
		}
		//以json形式返回前台
		this.renderJson(response, JSONObject.toJSONString(result));
	}
	
	@RequestMapping(value = "/getMyRegister")
	public void getMyRegister(){
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			User user = (User) this.session.getAttribute("user");
			if(user == null){
				result.put("message","noLogin");
			}else{
				String username = user.getUserName();
				result = myCenterService.getMyRegisterByUserName(username);
			}
		} catch (Exception e) {
			logger.error("[Controller.MyCenterController] getMyRegister",e);
		}
		//以json形式返回前台
		this.renderJson(response, JSONObject.toJSONString(result));
	}
	
	
	
}
