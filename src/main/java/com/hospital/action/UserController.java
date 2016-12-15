package com.hospital.action;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.hospital.common.Code;
import com.hospital.common.ResponseEntity;
import com.hospital.service.IUserService;
/**
 * 用户控制器
 * @author yubing
 *
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
	 * @author yubing
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void login(){
		ResponseEntity resEntity = new ResponseEntity();
		resEntity.setCode(Code.CODE_SERVER_ERROR);
		
		try {
			String username = this.getParamStr(request, "username");
			String password = this.getParamStr(request, "password");
			String code = this.getParamStr(request, "code");
			
			String errorMsg = userService.login(username, password, code);
			if(StringUtils.isNotBlank(errorMsg)){
				resEntity.setCode(Code.CODE_NOT_ACCEPTABLE);
				resEntity.setMessage(errorMsg);
			}else{
				resEntity.setCode(Code.CODE_OK);
			}
		} catch (Exception e) {
			logger.error("[Controller.UserController] validateUserName occur exception!",e);
		}
		
		this.renderJson(response, JSONObject.toJSONString(resEntity));
	}
}
