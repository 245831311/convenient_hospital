package com.hospital.common;

import java.io.Serializable;
import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;

/**
 * 基于Mybatis的基础泛型DAO实现类。
 * 
 * @author 陈茂德
 * 		
 * @param <T>
 *            业务实体类型
 * @param <ID>
 *            ID类型 ，如：String、Long、Integer 等
 */
public class MybatisBaseDao<T, ID extends Serializable>{
	@Resource
	private SqlSession sqlSession;
	
	public static final String		SQLNAME_SEPARATOR		= ".";
															
	public static final String		SQL_SAVE				= "save";
	public static final String		SQL_UPDATE				= "update";
	public static final String		SQL_DELETE				= "delete";
	public static final String		SQL_GETBYID				= "getById";
	public static final String		SQL_DELETEBYID			= "deleteById";
	public static final String		SQL_DELETEBYIDS			= "deleteByIds";
	public static final String		SQL_FINDPAGEBY			= "findPageBy";
	public static final String		SQL_FINDLISTBY			= "findListBy";
	public static final String		SQL_FINDALL				= "findAll";
	public static final String		SQL_GETCOUNTBY			= "getCountBy";

	/**
	 * 不能用于SQL中的非法字符（主要用于排序字段名）
	 */
	public static final String[]	ILLEGAL_CHARS_FOR_SQL	= { ",", ";", " ", "\"", "%" };
	
	/**
	 * SqlMapping命名空间
	 */
	private String sqlNamespace = getDefaultSqlNamespace();
	
	
	
	public SqlSession getSqlSession(){
		return this.sqlSession;
	}
															

	/**
	 * 获取默认SqlMapping命名空间。 使用泛型参数中业务实体类型的全限定名作为默认的命名空间。 如果实际应用中需要特殊的命名空间，可由子类重写该方法实现自己的命名空间规则。
	 * 
	 * @return 返回命名空间字符串
	 */
	protected String getDefaultSqlNamespace() {
		return this.getClass().getName();
	}
	
	/**
	 * 将SqlMapping命名空间与给定的SqlMapping名组合在一起。
	 * 
	 * @param sqlName
	 *            SqlMapping名
	 * @return 组合了SqlMapping命名空间后的完整SqlMapping名
	 */
	protected String getSqlName(String sqlName) {
		return sqlNamespace + SQLNAME_SEPARATOR + sqlName;
	}
	
	/**
	 * 获取SqlMapping命名空间
	 * 
	 * @return SqlMapping命名空间
	 */
	protected String getSqlNamespace() {
		return sqlNamespace;
	}
	
	/**
	 * 设置SqlMapping命名空间。 此方法只用于注入SqlMapping命名空间，以改变默认的SqlMapping命名空间， 不能滥用此方法随意改变SqlMapping命名空间。
	 * 
	 * @param sqlNamespace
	 *            SqlMapping命名空间
	 */
	protected void setSqlNamespace(String sqlNamespace) {
		this.sqlNamespace = sqlNamespace;
	}
	
	/**
	 * 生成主键值。 默认情况下什么也不做； 如果需要生成主键，需要由子类重写此方法根据需要的方式生成主键值。
	 * 
	 * @param ob
	 *            要持久化的对象
	 */
	protected void generateId(T ob) {
	
	}
	
	
	/**
	 * 从给定字符串中将指定的非法字符串数组中各字符串过滤掉。
	 * 
	 * @param str
	 *            待过滤的字符串
	 * @param filterChars
	 *            指定的非法字符串数组
	 * @return 过滤后的字符串
	 */
	protected String filterIllegalChars(String str, String[] filterChars) {
		String rs = str;
		if (rs != null && filterChars != null) {
			for (String fc : filterChars) {
				if (fc != null && fc.length() > 0) {
					str = str.replaceAll(fc, "");
				}
			}
		}
		return rs;
	}
}
