package com.kinglong.acg.core;

import com.kinglong.acg.consts.ColumnDataType;
import com.kinglong.acg.consts.QueryOperationEnum;
import com.kinglong.acg.consts.YesOrNo;
import com.kinglong.acg.utl.Util;
import lombok.Data;

import java.io.Serializable;

/**
 * 元数据库字段实体
 *
 * @author haojinlong
 * @date 2020/3/20 17:25
 */
@Data
public class MetaColumn implements Serializable {

	private static final long serialVersionUID = -6751439316464412419L;

	/**
	 * 字段注释
	 */
	private String cmt;

	/**
	 * 字段名：比如 type_id
	 */
	private String name;

	/**
	 * 数据库字段数据类型
	 * <p>
	 * {@link ColumnDataType}
	 */
	private ColumnDataType dataType;

	/**
	 * 数据类型的字符串形式,便于在模板内引用
	 */
	private String dataTypeStr;
	/**
	 * 字段名的驼峰命名
	 */
	private String camelName;

	/**
	 * 字段要求的长度
	 */
	private Integer dataLength;

	/**
	 * 字段要求的精度
	 */
	private Integer dataScale;

	/**
	 * 字段是否为空
	 * <p>
	 * {@link YesOrNo}
	 */
	private YesOrNo nullable;

	/**
	 * 字段操作,该字段值取自cmt字段末尾数据,默认一个字段只可有一个操作,目前的操作有:
	 * <p>
	 * {@code Q} 普通精确查询 {@code A=?}
	 * {@code L} 左前缀模糊查询 {@code A LIKE ?%}
	 * {@code R} 右前缀模糊查询 {@code A LIKE %?}
	 * {@code A} 全模糊查询 {@code A LIKE %?%}
	 * {@code B} 区间查询 {@code A>=? AND A<=?}
	 * <p>
	 * 如果需要某个字段出现在分页查询的条件中,请在数据库字段的备注末尾添加 "Q/L/R/A/B" 即可
	 * <p>
	 * 比如字段注释为: 姓名(A)
	 * 则表示姓名字段需要全模糊查询,并且需要出现在分页查询条件中
	 * <p>
	 * 比如字段注释为: 姓名
	 * 则表示姓名字段不需要查询,并不会出现在分页查询条件中
	 * </p>
	 *
	 * @see MetaColumn#operation
	 */
	private QueryOperationEnum operation;


	public String getCamelName() {
		return Util.getCamelName(name);
	}

	public String getDataTypeStr() {
		return dataType.getCodeName();
	}
}
