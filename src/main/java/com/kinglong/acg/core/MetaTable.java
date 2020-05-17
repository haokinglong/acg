package com.kinglong.acg.core;

import com.kinglong.acg.utl.OracleUtil;
import com.kinglong.acg.utl.Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 元数据库表实体
 *
 * @author haojinlong
 * @date 2020/3/20 17:25
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetaTable implements Serializable {

	private static final long serialVersionUID = -6382277241979405646L;

	/**
	 * 表名
	 */
	private String name;

	/**
	 * 表的主键：比如 demo_id
	 */
	private String primaryKey;

	/**
	 * 表的注释
	 */
	private String cmt;

	/**
	 * 表名的驼峰命名
	 */
	private String camelName;

	/**
	 * 驼峰命名的表名： 第一个字母大写
	 */
	private String camelNameToUppercase;

	/**
	 * 主键的驼峰命名:比如
	 */
	private String primaryKeyCamelName;

	/**
	 * 用于拼接实现类里的page接口参数,有参数类型的字符串
	 * <pre>{@code
	 *      Pagination<Demo> page(String param1, String param2, String param3, String param4, Integer page, Integer limit);
	 * }</pre>
	 * <p>
	 * {@link OracleUtil#listColumns(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
	 * <p>
	 * 其中的第109行代码
	 */
	private String queryParamsStr;

	/**
	 * 用于拼接各层page接口访问的参数签名字段,为排除参数类型的拼接字符串
	 * <pre>{@code
	 *      return demoDao.page(param1, param2, param3, param4, page, limit);
	 * }</pre>
	 * <p>
	 * {@link OracleUtil#listColumns(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
	 * <p>
	 * 其中的第110行代码
	 */
	private String queryParamsExcludeTypeStr;

	/**
	 * 表内所含的列
	 */
	private List<MetaColumn> metaColumns;

	/**
	 * 包名
	 */
	private String packageName;

	private String author;

	private Date date;


	public String getCamelName() {
		return Util.getCamelName(name);
	}

	public String getCamelNameToUppercase() {
		return getCamelName().substring(0, 1).toUpperCase() + getCamelName().substring(1);
	}

	public String getPrimaryKeyCamelName() {
		return Util.getCamelName(primaryKey);
	}
}
