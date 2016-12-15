package com.hospital.service;

import java.util.List;

import com.hospital.model.DepartTree;
import com.hospital.model.ParentTree;
import com.hospital.model.TreeStructure;

public interface IDepartmentService {
	
    /**
     * 导入组织架构
     * @return
     */
	public List<String> departmentImport(String path, long org_Id);
	/**
	 * 校验导入的组织架构格式是否正确
	 * @param path
	 * @return
	 */
	public List<String> checkDepartment(String path);
	/**
	 * 获取导入组织架构的内容
	 * @param path
	 * @return
	 */
	public List<String> getDepartmentValue(String path);
	
	public List<TreeStructure> getTreeStructure(long orgId,long departId);
	
	public List<DepartTree> getParentIds(long orgId,long departId);
		
	public long getDepartId(long orgId,long userId);
	
}
