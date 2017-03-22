/*package com.hospital.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.hospital.common.Code;
import com.hospital.common.ResponseEntity;
import com.hospital.constants.AppConstants;
import com.hospital.model.DepartTree;
import com.hospital.model.ParentTree;
import com.hospital.model.TreeStructure;
import com.hospital.service.IDepartmentService;
import com.hospital.service.IUserService;
*//**
 * 部门控制器
 *//*
@Controller
@RequestMapping("/departmentController")
public class DepartmentController extends BaseController{
	private Logger logger = Logger.getLogger(this.getClass());
	@Resource(name="departmentService")
	private IDepartmentService departmentService;
	
	*//**
	 * 导入组织架构
	 * 1、校验导入文件格式
	 * 2、校验导入文件内容
	 * 3、将组织架构数据导入数据库
	 * @author 
	 * @param request
	 * @param response
	 *//*
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public void exportDepartment(){
		ResponseEntity resEntity = new ResponseEntity();
		resEntity.setCode(Code.CODE_SERVER_ERROR);
		String path = this.getParamStr(request, "path");
		Object org_Id = this.session.getAttribute(AppConstants.SESSION_ORGANIZATION);
		long orgId = 798069832187641856l;
		if(org_Id!=null){
			orgId = Long.parseLong(org_Id+"");
		}
		try {
			
			List<String> errorMsg = departmentService.checkDepartment(path);
			if(errorMsg!=null&&errorMsg.size()>0){
				resEntity.setCode(Code.CODE_NOT_ACCEPTABLE);
				String msg = "";
				for(String str:errorMsg){
					msg+=str;
				}
				resEntity.setMessage(msg);
			}else{
				departmentService.departmentImport(path, orgId);
				resEntity.setCode(Code.CODE_OK);
			}
		} catch (Exception e) {
			logger.error("[Controller.departmentController] export occur exception!",e);
		}
		
		this.renderJson(response, JSONObject.toJSONString(resEntity));
	}
	
	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	public void showTreeStructure(){
		ResponseEntity resEntity = new ResponseEntity();
		resEntity.setCode(Code.CODE_SERVER_ERROR);
		Map<String,Object> map = new HashMap<String, Object>();
		String json = "[";
		try{
			String depart_Id = this.getParamStr(request, "departId");
			//Object org_Id = this.session.getAttribute(AppConstants.SESSION_ORGANIZATION);
			long orgId = 798069832187641856l;
			if(org_Id!=null){
				orgId = Long.parseLong(org_Id+"");
			}
			long departId = 0;
			if(depart_Id!=null){
				departId = Long.parseLong(depart_Id);
			}
			List<TreeStructure> list = departmentService.getTreeStructure(orgId, departId);
			if(list!=null&&list.size()>0){
				for(TreeStructure tree:list){
					json += tree.toJSON()+",";
				}
			}
			resEntity.setCode(Code.CODE_OK);
		}catch(Exception e){
			logger.error("[Controller.departmentController] tree occur exception!",e);
		}
		if(json.length()>2){
			json = json.substring(0,json.length()-1);
		}
		json +="]";
		map.put("tree", json);
		resEntity.setReceipt(map);
		this.renderJson(response, JSONObject.toJSONString(resEntity));
	}
	
	@RequestMapping(value = "/checkDepartId", method = RequestMethod.POST)
	public void checkDepartIdExist(){
		ResponseEntity resEntity = new ResponseEntity();
		resEntity.setCode(Code.CODE_SERVER_ERROR);
		Map<String,Object> map = new HashMap<String, Object>();
		try{
			String depart_Id = this.getParamStr(request, "departId");
			String mobile = this.getParamStr(request, "mobile");
			//Object org_Id = this.session.getAttribute(AppConstants.SESSION_ORGANIZATION);
			long orgId = 798069832187641856l;
			if(org_Id!=null){
				orgId = Long.parseLong(org_Id+"");
			}
			long departId = 0;
			String parentDepartIds = "";
			if(depart_Id!=null){
				departId = Long.parseLong(depart_Id);
			}
			if(mobile!=null&&!"".equals(mobile.trim())){
				departId = departmentService.getDepartId(orgId, departId);
				parentDepartIds = departId+",";
			}
			List<DepartTree> list = departmentService.getParentIds(orgId, departId);
			
			if(list!=null&&list.size()>0){
				for(DepartTree dt:list){
					parentDepartIds += dt.getParentDepartId()+",";
				}
			}
			if(!"".equals(parentDepartIds)&&parentDepartIds.length()>1){
				parentDepartIds = parentDepartIds.substring(0,parentDepartIds.length()-1);
			}
			map.put("parentDepartIds", parentDepartIds.split(","));
			resEntity.setCode(Code.CODE_OK);
			resEntity.setReceipt(map);
		}catch(Exception e){
			logger.error("[Controller.departmentController] checkDepartId occur exception!",e);
		}
		this.renderJson(response, JSONObject.toJSONString(resEntity));
	}
}
*/