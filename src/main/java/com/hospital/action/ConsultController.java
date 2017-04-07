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
import com.hospital.service.IConsultService;
import com.hospital.service.IUserService;
/**
 * 用户控制器
 */
@Controller
@RequestMapping("/consultController")
public class ConsultController extends BaseController{
	private Logger logger = Logger.getLogger(this.getClass());
	@Resource(name="consultService")
	private IConsultService consultService;
	
	@RequestMapping(value = "/searchDoctor", method = RequestMethod.POST)
	public void search(){
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			//搜索关键字
			String searchKey = this.getParamStr(request, "searchKey");
			//选择的科目
			String section = this.getParamStr(request, "section");
			//搜索医生
			result = consultService.searchDoctor(searchKey,section);
		} catch (Exception e) {
			//异常则打印日记
			logger.error("[Controller.ConsultController] ",e);
		}
		//以json形式返回前台
		this.renderJson(response, JSONObject.toJSONString(result));
	}
	
	
	
}
