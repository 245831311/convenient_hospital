/*package com.hospital.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hospital.common.Code;
import com.hospital.common.MybatisBaseDao;
import com.hospital.common.ServiceException;
import com.hospital.model.DepartTree;
import com.hospital.model.Department;
import com.hospital.model.ParentTree;
import com.hospital.util.ChineseToPinyin;
import com.hospital.util.IdWorkerUtil;

@Repository("departmentDao")
public class DepartmentDao extends MybatisBaseDao<Object, String> {
	*//**
	 * 根据departId,orgId,name判断部门父节点是否存在
	 * @param departId 父节点部门Id
	 * @param orgId 企业Id
	 * @param name 当前节点名称 (当前部门名称)
	 * @return
	 * @throws ServiceException
	 *//*
	public int getDepartmentParentNodeExistByName(long departId, long orgId, String name)throws ServiceException {
		int result = 0;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("departId", departId);
			map.put("orgId", orgId);
			map.put("name", name);
			result = getSqlSession().selectOne(getSqlName("selectParentNodeExistByName"),
					map);
		} catch (Exception e) {
			throw new ServiceException("DepartmentDao.getDepartmentExistByName occur exception!", e, Code.CODE_SERVER_ERROR, "");
		}
		return result;
	}
	
	*//**
	 * 根据部门Id集合查询部门信息
	 * @param orgId
	 * @param departIds
	 * @return
	 * @throws ServiceException
	 *//*
	public List<Department> getDepartment(long orgId, String departIds)throws ServiceException {
		List<Department> list = null;
		try {
			if(departIds!=null&&!"".equals(departIds.trim())){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orgId", orgId);
			map.put("departIds", departIds);
			list = getSqlSession().selectList(getSqlName("selectDepartment"),
					map);
			}
		} catch (Exception e) {
			throw new ServiceException("DepartmentDao.getDepartment occur exception!", e, Code.CODE_SERVER_ERROR, "");
		}
		return list;
	}
	*//**
	 * 根据部门名称拼音缩写查询部门信息
	 * @param orgId
	 * @param orgShortName
	 * @return
	 * @throws ServiceException
	 *//*
	public List<Department> getDepartmentByOrgShortName(long orgId, String orgShortName)throws ServiceException {
		List<Department> list = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orgId", orgId);
			map.put("orgShortName", orgShortName);
			list = getSqlSession().selectList(getSqlName("selectByOrgShortName"),
					map);
		} catch (Exception e) {
			throw new ServiceException("DepartmentDao.getDepartmentExistByName occur exception!", e, Code.CODE_SERVER_ERROR, "");
		}
		return list;
	}
	*//**
	 * 根据部门名称查询部门信息
	 * @param orgId
	 * @param name
	 * @return
	 * @throws ServiceException
	 *//*
	public List<Department> getDepartmentByName(long orgId, String name)throws ServiceException {
		List<Department> list = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orgId", orgId);
			map.put("name", name);
			list = getSqlSession().selectList(getSqlName("selectByName"),
					map);
		} catch (Exception e) {
			throw new ServiceException("DepartmentDao.getDepartmentExistByName occur exception!", e, Code.CODE_SERVER_ERROR, "");
		}
		return list;
	}
	
	public List<DepartTree> getDepartTree(long orgId, long parentDepartId)throws ServiceException {
		List<DepartTree> list = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orgId", orgId);
			map.put("parentDepartId", parentDepartId);
			list = getSqlSession().selectList(getSqlName("selectDepartTree"),
					map);
		} catch (Exception e) {
			throw new ServiceException("DepartmentDao.getDepartTree occur exception!", e, Code.CODE_SERVER_ERROR, "");
		}
		return list;
	}
	*//**
	 * 根据 orgId,userId获取所在部门Id
	 * @param orgId
	 * @param userId
	 * @return
	 * @throws ServiceException
	 *//*
	public long getDepartId(long orgId, long userId)throws ServiceException {
		long departId = 0;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orgId", orgId);
			map.put("userId", userId);
			departId = getSqlSession().selectOne(getSqlName("selectDepartId"),
					map);
		} catch (Exception e) {
			throw new ServiceException("DepartmentDao.getParentDepartId occur exception!", e, Code.CODE_SERVER_ERROR, "");
		}
		return departId;
	}
	*//**
	 * 获取所有父节点
	 * @param orgId
	 * @param departId
	 * @return
	 * @throws ServiceException
	 *//*
	public List<DepartTree> getParentDepartId(long orgId, long departId)throws ServiceException {
		List<DepartTree> list = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("org_id", orgId);
			map.put("parent_depart_id", departId);
			list = getSqlSession().selectList(getSqlName("selectparentDepartId"),
					map);
		} catch (Exception e) {
			throw new ServiceException("DepartmentDao.getParentDepartId occur exception!", e, Code.CODE_SERVER_ERROR, "");
		}
		return list;
	}
	*//**
	 * 根据部门名称模糊查询部门信息
	 * @param orgId
	 * @param name
	 * @return
	 * @throws ServiceException
	 *//*
	public List<Department> getDepartmentLikeName(long orgId, String name)throws ServiceException {
		List<Department> list = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orgId", orgId);
			map.put("name", name);
			list = getSqlSession().selectList(getSqlName("selectLikeName"),
					map);
		} catch (Exception e) {
			throw new ServiceException("DepartmentDao.getDepartmentExistByName occur exception!", e, Code.CODE_SERVER_ERROR, "");
		}
		return list;
	}
	*//**
	 * 创建部门
	 * @param orgId
	 * @param departId
	 * @param name
	 * @throws ServiceException
	 *//*
	public void createDepartment(long orgId, long departId, String name)throws ServiceException {
		try {
			Department depart = new Department();
			String orgShortName = ChineseToPinyin.getPinYinHeadChar(name);
			if(departId==0){
				long Id = IdWorkerUtil.nextId();
				depart.setDepartId(Id);
			}else{
				depart.setDepartId(departId);	
			}
			depart.setOrgId(orgId);
			depart.setCreateTime(System.currentTimeMillis());
			depart.setName(name);
			depart.setOrgShortName(orgShortName);
			depart.setValid(1);
			getSqlSession().insert(getSqlName("createDepartment"), depart);
		} catch (Exception e) {
			throw new ServiceException("DepartmentDao.getDepartmentExistByName occur exception!", e, Code.CODE_SERVER_ERROR, "");
		}
	}
	*//**
	 * 新增部门关系
	 * @param orgId
	 * @param parentDepartId
	 * @param departId
	 * @throws ServiceException
	 *//*
	public void createDepartmentTree(long orgId, long parentDepartId, long departId)throws ServiceException {
		try {
			DepartTree depart_tree = new DepartTree();
			long departTreeId = IdWorkerUtil.nextId();
			depart_tree.setDepartTreeId(departTreeId);
			depart_tree.setDepartId(departId);
			depart_tree.setOrgId(orgId);
			depart_tree.setParentDepartId(parentDepartId);
			getSqlSession().insert(getSqlName("createDepartTree"), depart_tree);
		} catch (Exception e) {
			throw new ServiceException("DepartmentDao.getDepartmentExistByName occur exception!", e, Code.CODE_SERVER_ERROR, "");
		}
	}
}
*/