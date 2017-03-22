/*package com.hospital.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;

import com.hospital.common.ServiceException;
import com.hospital.dao.DepartmentDao;
import com.hospital.dao.UserDao;
import com.hospital.model.DepartTree;
import com.hospital.model.Department;
import com.hospital.model.ParentTree;
import com.hospital.model.TreeStructure;
import com.hospital.model.User;
import com.hospital.service.IDepartmentService;
import com.hospital.util.IdWorkerUtil;
import com.hospital.util.ReadExcel;

@Service("departmentService")
public class IDepartmentImpl implements IDepartmentService {
	private Logger logger = Logger.getLogger(this.getClass());
	@Resource
	private HttpServletRequest request;

	@Resource(name = "userDao")
	private UserDao userDao;

	@Resource(name = "departmentDao")
	private DepartmentDao departmentDao;

	private static int index = 1;

	@Override
	public List<String> departmentImport(String path, long org_Id) {
		List<String> list = null;
		Sheet sheet = ReadExcel.getSheet(path);
		long parentId = 0;
		if(sheet!=null){
			int rows = sheet.getPhysicalNumberOfRows();
			int cells = sheet.getRow(0).getPhysicalNumberOfCells();
			HashMap<String,CellRangeAddress> mergeCellMap = getMergedCellAddress(sheet);
			if(mergeCellMap!=null){
				list = insertColumn(sheet,mergeCellMap,org_Id,parentId,0,0,0,rows-1,rows,cells);
			}
		}
		return list;
	}

	@Override
	public List<String> checkDepartment(String path) {
		// TODO Auto-generated method stub
		List<String> list = null;
		Sheet sheet = ReadExcel.getSheet(path);
		long parentId = 0;
		if(sheet!=null){
			int rows = sheet.getPhysicalNumberOfRows();
			int cells = sheet.getRow(0).getPhysicalNumberOfCells();
			HashMap<String,CellRangeAddress> mergeCellMap = getMergedCellAddress(sheet);
			if(mergeCellMap!=null){
				list = checkSheetColumn(sheet,mergeCellMap,parentId,0,0,0,rows-1,rows,cells);
			}
		}
		return list;
	}
	
	@Override
	public List<String> getDepartmentValue(String path) {
		// TODO Auto-generated method stub
		List<String> list = null;
		Sheet sheet = ReadExcel.getSheet(path);
		long parentId = 0;
		if(sheet!=null){
			int rows = sheet.getPhysicalNumberOfRows();
			int cells = sheet.getRow(0).getPhysicalNumberOfCells();
			HashMap<String,CellRangeAddress> mergeCellMap = getMergedCellAddress(sheet);
			if(mergeCellMap!=null){
				list = checkSheetColumnValue(sheet,mergeCellMap,parentId,0,0,0,rows-1,rows,cells);
			}
		}
		return list;
	}
	
	@Override
	public List<TreeStructure> getTreeStructure(long orgId, long departId) {
		List<TreeStructure> list = getTreeStructureList(orgId,departId);
		return list;
	}
	
	*//**
	 * 检查导入的表是否符合格式规范
	 * 
	 * @param sheet
	 * @param mergeCellMap
	 * @param parentId
	 * @param begin_Column
	 * @param end_Column
	 * @param begin_row
	 * @param end_row
	 * @param rows
	 * @param cells
	 * @return
	 *//*
	private List<String> checkSheetColumn(Sheet sheet,
			HashMap<String, CellRangeAddress> mergeCellMap, long parentId,
			int begin_Column, int end_Column, int begin_row, int end_row,
			int rows, int cells) {
		List<String> result = new ArrayList<String>();
		if (end_Column < begin_Column) {
			return result;
		}
		if (end_row < begin_row) {
			return result;
		}
		if (end_Column >= cells) {
			return result;
		}
		if (end_row >= rows) {
			return result;
		}
		int cell_beginRow = begin_row;
		int cell_endRow = begin_row;
		int next_cellRow = begin_row;
		int next_cellcolumn = begin_Column;
		while (next_cellRow < end_row) {
			String name = ReadExcel.getCellValue(sheet.getRow(next_cellRow)
					.getCell(next_cellcolumn));
			String key = next_cellRow + "_" + next_cellcolumn;
			CellRangeAddress mergeCell = mergeCellMap.get(key);
			if (mergeCell == null) {
				cell_beginRow = next_cellcolumn;
				cell_endRow = next_cellcolumn;
				next_cellRow++;
			} else {
				if (mergeCell.getLastColumn() > end_Column
						|| mergeCell.getLastRow() > end_row) {
					char cLastColumn = 'A';
					cLastColumn += mergeCell.getLastColumn();
					char cFirstColumnColumn = 'A';
					cFirstColumnColumn += mergeCell.getFirstColumn();
					String message = "(" + cFirstColumnColumn + "列,"
							+ (mergeCell.getFirstRow() + 1) + "行到"
							+ cLastColumn + "列" + (mergeCell.getLastRow() + 1)
							+ "行)格式错误!请重新编辑!";
					result.add(message);
					return result;
				}
				next_cellRow = mergeCell.getLastRow() + 1;
				cell_beginRow = mergeCell.getFirstRow();
				cell_endRow = mergeCell.getLastRow();
			}
			*//**
			 * 数据库操作 查询判断是否存在 ，否则则插入数据
			 *//*
			long currId = index++;
			System.out.println("插入一条数据:父Id:" + parentId + ";单元格值为:" + name
					+ ";自身ID:" + currId);
			// 查询到的部门Id 或插入时的Id
			List<String> result1 = checkSheetColumn(sheet, mergeCellMap, currId,
					end_Column + 1, end_Column + 1, cell_beginRow, cell_endRow,
					rows, cells);
			result.addAll(result1);
		}
		return result;
	}

	*//**
	 * 检查导入内容是否符合内容规范
	 * 
	 * @param sheet
	 * @param mergeCellMap
	 * @param parentId
	 * @param begin_Column
	 * @param end_Column
	 * @param begin_row
	 * @param end_row
	 * @param rows
	 * @param cells
	 * @return
	 *//*
	private List<String> checkSheetColumnValue(Sheet sheet,
			HashMap<String, CellRangeAddress> mergeCellMap, long parentId,
			int begin_Column, int end_Column, int begin_row, int end_row,
			int rows, int cells) {
		List<String> result = new ArrayList<String>();
		if (end_Column < begin_Column) {
			return result;
		}
		if (end_row < begin_row) {
			return result;
		}
		if (end_Column >= cells) {
			return result;
		}
		if (end_row >= rows) {
			return result;
		}
		int cell_beginRow = begin_row;
		int cell_endRow = begin_row;
		int next_cellRow = begin_row;
		int next_cellcolumn = begin_Column;
		while (next_cellRow < end_row) {
			String name = ReadExcel.getCellValue(sheet.getRow(next_cellRow)
					.getCell(next_cellcolumn));
			String key = next_cellRow + "_" + next_cellcolumn;
			CellRangeAddress mergeCell = mergeCellMap.get(key);
			if (mergeCell == null) {
				cell_beginRow = next_cellcolumn;
				cell_endRow = next_cellcolumn;
				next_cellRow++;
			} else {
				if(name==null||"".equals(name)){
					name= " ";
					result.add(parentId+","+name);
					return result;
				}
				next_cellRow = mergeCell.getLastRow() + 1;
				cell_beginRow = mergeCell.getFirstRow();
				cell_endRow = mergeCell.getLastRow();
			}
			*//**
			 * 数据库操作 查询判断是否存在 ，否则则插入数据
			 *//*
			long currId = index++;
			System.out.println("插入一条数据:父Id:" + parentId + ";单元格值为:" + name
					+ ";自身ID:" + currId);
			// 查询到的部门Id 或插入时的Id
			List<String> result1 = checkSheetColumnValue(sheet, mergeCellMap, currId,
					end_Column + 1, end_Column + 1, cell_beginRow, cell_endRow,
					rows, cells);
			result.addAll(result1);
		}
		return result;
	}
	*//**
	 * 
	 * @param sheet
	 * @param mergeCellMap
	 * @param parentId
	 * @param begin_Column
	 * @param end_Column
	 * @param begin_row
	 * @param end_row
	 * @param rows
	 * @param cells
	 * @return
	 *//*
	private List<String> insertColumn(Sheet sheet,
			HashMap<String, CellRangeAddress> mergeCellMap, long orgId, long parentId,
			int begin_Column, int end_Column, int begin_row, int end_row,
			int rows, int cells) {
		List<String> result = new ArrayList<String>();
		if (end_Column < begin_Column) {
			return result;
		}
		if (end_row < begin_row) {
			return result;
		}
		if (end_Column >= cells) {
			return result;
		}
		if (end_row >= rows) {
			return result;
		}
		int cell_beginRow = begin_row;
		int cell_endRow = begin_row;
		int next_cellRow = begin_row;
		int next_cellcolumn = begin_Column;
		while (next_cellRow < end_row) {
			String name = ReadExcel.getCellValue(sheet.getRow(next_cellRow)
					.getCell(next_cellcolumn));
			String key = next_cellRow + "_" + next_cellcolumn;
			CellRangeAddress mergeCell = mergeCellMap.get(key);
			if (mergeCell == null) {
				cell_beginRow = next_cellcolumn;
				cell_endRow = next_cellcolumn;
				next_cellRow++;
			} else {
				if (mergeCell.getLastColumn() > end_Column
						|| mergeCell.getLastRow() > end_row) {
					char cLastColumn = 'A';
					cLastColumn += mergeCell.getLastColumn();
					char cFirstColumnColumn = 'A';
					cFirstColumnColumn += mergeCell.getFirstColumn();
					String message = "(" + cFirstColumnColumn + "列,"
							+ (mergeCell.getFirstRow() + 1) + "行到"
							+ cLastColumn + "列" + (mergeCell.getLastRow() + 1)
							+ "行)格式错误!请重新编辑!";
					result.add(message);
					return result;
				}
				next_cellRow = mergeCell.getLastRow() + 1;
				cell_beginRow = mergeCell.getFirstRow();
				cell_endRow = mergeCell.getLastRow();
			}
			
			long Id = IdWorkerUtil.nextId();
			//创建部门
			createDepartment(Id, orgId, name);
			//新增部门关系
			createDepartmentTree(Id, parentId, orgId);
			// 查询到的部门Id 或插入时的Id
			List<String> result1 = insertColumn(sheet, mergeCellMap, orgId, Id,
					end_Column + 1, end_Column + 1, cell_beginRow, cell_endRow,
					rows, cells);
			result.addAll(result1);
		}
		return result;
	}
	
	private HashMap<String,CellRangeAddress>  getMergedCellAddress(Sheet sheet){
		int sheetMergeCount = sheet.getNumMergedRegions();
		HashMap<String, CellRangeAddress> rangeAddress = new HashMap<String, CellRangeAddress>();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress cell_address = sheet.getMergedRegion(i);
			String key = cell_address.getFirstRow()+"_"+cell_address.getFirstColumn();
			rangeAddress.put(key, cell_address);
		}
		return rangeAddress;
	}
	*//**
	 * 创建部门信息
	 * 
	 * @param parentId
	 * @param orgId
	 * @param name
	 * @return
	 *//*
	private int createDepartment(long orgId, long departId, String name) {
		int result = 200;
		try {
			departmentDao.createDepartment(orgId, departId, name);
		} catch (Exception e) {
			e.printStackTrace();
			result = 500;
		}
		return result;
	}

	*//**
	 * 新增部门关系数据
	 * 
	 * @param departId
	 * @param parentId
	 * @param orgId
	 * @return
	 *//*
	private int createDepartmentTree(long departId, long parentId, long orgId) {
		int result = 200;
		try {
			departmentDao.createDepartmentTree(orgId, parentId, departId);
		} catch (Exception e) {
			e.printStackTrace();
			result = 500;
		}
		return result;
	}
    *//**
     * 构建企业通讯录树结构内容
     * @param orgId
     * @param departId
     * @return
     *//*
	private List<TreeStructure> getTreeStructureList(long orgId,long departId){
		List<TreeStructure> list = new ArrayList<TreeStructure>();
		try {
			List<DepartTree> tree_list = departmentDao.getDepartTree(orgId, departId);
			String departIds = "";
			if(tree_list!=null&&tree_list.size()>0){
				for(DepartTree tree:tree_list){
					String depart_id = tree.getDepartId()+"";
					departIds+=depart_id + ",";
				}
			}
			if(!"".equals(departIds)&&departIds.length()>1){
				departIds = departIds.substring(0,departIds.length()-1);
			}
			List<Department> departTree_list = departmentDao.getDepartment(orgId, departIds);
			if(departTree_list!=null&&departTree_list.size()>0){
				for(Department depart:departTree_list){
					TreeStructure ts = new TreeStructure();
					ts.setId(String.valueOf(depart.getDepartId()));
					ts.setTitle(depart.getName());
					ts.setMobile("");
					list.add(ts);
				}
			}
			List<User> user_list = userDao.getUserByDepartId(orgId, departId);
			if(user_list!=null&&user_list.size()>0){
				for(User user:user_list){
					TreeStructure ts = new TreeStructure();
					ts.setId(user.getUserId());
					ts.setTitle(user.getName());
					ts.setMobile(user.getMobile());
					list.add(ts);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<DepartTree> getParentIds(long orgId, long departId) {
		List<DepartTree> list = null;
		try {
			list = departmentDao.getParentDepartId(orgId, departId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public long getDepartId(long orgId, long userId) {
		long departId = 0;
		try {
			departId = departmentDao.getDepartId(orgId, userId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return departId;
	}
	
	

	*//**
	 * 判断是否创建部门
	 * 
	 * @param orgId
	 * @param name
	 * @return
	 *//*
	private String checkCreateDepartment(long orgId, String name) {
		String message = "";
		try {
			int departExist_result_code = isDepartmentExist(orgId, name);
			switch (departExist_result_code) {
			case 200:
				// 创建部门
				message = "insert";
				return message;
			case 201:
				// 部门已存在
				message = "continue";
				return message;
			case 506:
				// 部门数据异常,需提示用户去删除同一父节点下多个同名的部门
				message = "部门数据异常,所属部门下有多个名部门,同一所属部门下不能重复存在同名部门,请在系统中删除/编辑同一所属部门下的同名部门后再操作!";
				return message;
			case 500:
				message = "内部异常,请稍后再试!";
				return message;
			}

		} catch (Exception e) {
			e.printStackTrace();
			message = "内部异常,请稍后再试!";
		}
		return message;
	}

	*//**
	 * 判断父节点部门状态
	 * 
	 * @param parentId
	 * @param orgId
	 * @param name
	 * @return
	 *//*
	
	 * private int isParentNodeExist(long parentId, long orgId, String name) {
	 * int result = 0; try { int parentNodeExist = departmentDao
	 * .getDepartmentParentNodeExistByName(parentId, orgId, name); if
	 * (parentNodeExist == 1) { // 部门父节点唯一存在可创建部门数据 result = 200; } else if
	 * (parentNodeExist == 0) { // 部门父节点不存在 result = 404; } else if
	 * (parentNodeExist > 0) { // 部门父节点数据异常,需提示用户去删除多个同名父节点 result = 506; } }
	 * catch (Exception e) { e.printStackTrace(); result = 500; } return result;
	 * }
	 

	*//**
	 * 判断部门状态
	 * 
	 * @param orgId
	 * @param name
	 * @return
	 *//*
	private int isDepartmentExist(long orgId, String name) {
		int result = 0;
		try {
			List<Department> departmentExist = departmentDao
					.getDepartmentByName(orgId, name);
			if (departmentExist == null || departmentExist.size() == 0) {
				// 可以创建部门数据
				result = 200;
			} else if (departmentExist.size() == 1) {
				// 部门已存在不再创建部门数据
				result = 201;
			} else if (departmentExist.size() > 1) {
				// 部门数据异常,需提示用户去删除同一父节点下多个同名的部门
				result = 506;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 内部异常
			result = 500;
		}
		return result;
	}
}
*/