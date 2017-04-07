package com.hospital.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.hospital.model.Doctor;
import com.hospital.model.User;
import com.hospital.service.IDoctorService;
/**
 * 用户控制器
 */
@Controller
@RequestMapping("/doctorController")
public class DoctorController extends BaseController{
	private Logger logger = Logger.getLogger(this.getClass());
	@Resource(name="doctorService")
	private IDoctorService doctorService;
	
	
	@RequestMapping(value = "/getPatent")
	public void getPatent(){
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			Doctor doctor = (Doctor) this.session.getAttribute("doctor");
			if(doctor == null){
				result.put("message", "noLogin");
			}else{
				result = doctorService.getPatentById(doctor.getId());
			}
		}catch(Exception e){
			logger.error("[Controller.UserController] logOut occur exception!",e);
		}
		//以json形式返回前台
		this.renderJson(response, JSONObject.toJSONString(result));
	}
	
	/**
	 * 审阅挂号状态
	 */
	@RequestMapping(value = "/checkRegister")
	public void checkRegister(){
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			Doctor doctor = (Doctor) this.session.getAttribute("doctor");
			if(doctor == null){
				result.put("message", "noLogin");
			}else{
				//获取某用户的用户名
				String username = this.getParamStr(request, "username");
				//审阅挂号状态
				result = doctorService.checkRegister(username,doctor.getId());
			}
			//通过病人的用户名来
		}catch(Exception e){
			logger.error("[Controller.DoctorController] checkRegister occur exception!",e);
		}
		//以json形式返回前台
		this.renderJson(response, JSONObject.toJSONString(result));
	}
	
	/**
	 * 填写报告
	 */
	@RequestMapping(value = "/signReport")
	public void signReport(){
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			Doctor doctor = (Doctor) this.session.getAttribute("doctor");
			if(doctor == null){
				result.put("message", "noLogin");
			}else{
				//获取某用户的用户名
				String username = this.getParamStr(request, "username");
				//填写内容
				String content = this.getParamStr(request, "username");
				//调用存储报告内容
				result = doctorService.signReport(username,doctor.getId(),content);
			}
		}catch(Exception e){
			logger.error("[Controller.DoctorController] signReport occur exception!",e);
		}
		//以json形式返回前台
		this.renderJson(response, JSONObject.toJSONString(result));
	}
	
}
