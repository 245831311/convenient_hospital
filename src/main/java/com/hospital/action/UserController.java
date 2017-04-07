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
import com.hospital.service.IUserService;
/**
 * 用户控制器
 */
@Controller
@RequestMapping("/userController")
public class UserController extends BaseController{
	private Logger logger = Logger.getLogger(this.getClass());
	@Resource(name="userService")
	private IUserService userService;
	
	/**
	 * 用户登陆逻辑
	 * 1、验证用户名
	 * 2、验证密码
	 * 3、验证验证码
	 * 4、保存当前用户到session
	 * 5、记录用户登陆日志
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void login(){
		Map<String,Object> result = null;
		try {
			//获取用户名、密码、验证码
			String username = this.getParamStr(request, "username");
			String password = this.getParamStr(request, "password");
			String code = this.getParamStr(request, "code");
			//调用业务层进行登录
			result = userService.login(username, password, code);
		} catch (Exception e) {
			//异常则打印日记
			logger.error("[Controller.UserController] validateUserName occur exception!",e);
		}
		//以json形式返回前台
		this.renderJson(response, JSONObject.toJSONString(result));
	}
	
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public void register(){
		Map<String,Object> result = null;
		try {
			String username = this.getParamStr(request, "username");
			String nickname = this.getParamStr(request, "nickname");
			String password = this.getParamStr(request, "password");
			String mobile = this.getParamStr(request, "mobile");
			String code = this.getParamStr(request, "code");
			
			result = userService.register(username,password,nickname,mobile,code);
		}catch(Exception e){
			logger.error("[Controller.UserController] register occur exception!",e);
		}
		//以json形式返回前台
		this.renderJson(response, JSONObject.toJSONString(result));
	}
	
	@RequestMapping(value = "/logOut")
	public void logOut(){
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			User user = (User) this.session.getAttribute("user");
			if(user != null){
				this.session.setAttribute("user", null);
				result.put("message", "logOut");
			}
		}catch(Exception e){
			logger.error("[Controller.UserController] logOut occur exception!",e);
		}
		//以json形式返回前台
		this.renderJson(response, JSONObject.toJSONString(result));
	}
	
}
