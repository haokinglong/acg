package com.kinglong.acg.consts;

/**
 * 核心枚举
 *
 * @author haojinlong
 * @date 2020/3/20 17:25
 */
public enum CoreEnum {

	/**
	 * 数据类型
	 */
	DATA_TYPE("data_type"),

	/**
	 * 数据长度
	 */
	DATA_LENGTH("data_length"),

	/**
	 * 数据精度
	 */
	DATA_SCALE("data_scale"),

	/**
	 * 是否为空
	 */
	NULLABLE("nullable"),

	/**
	 * 主键
	 */
	PRIMARY_KEY("primary_key"),

	/**
	 * 数据注释
	 */
	COMMENTS("comments"),

	/**
	 * 表名
	 */
	TABLE_NAME("table_name"),

	/**
	 * 字段名称
	 */
	COLUMN_NAME("column_name"),

	/**
	 * 字段列表
	 */
	COLUMN_LIST("COLUMN_LIST"),

	/**
	 * 需要作为查询条件的字段所拼成的字符串
	 */
	QUERY_PARAMS_STR("QUERY_PARAMS_STR"),

	/**
	 * 字段名称
	 */
	QUERY_PARAMS_EXCLUDE_TYPE_STR("QUERY_PARAMS_EXCLUDE_TYPE_STR");

	private String value;

	CoreEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	/**
	 * 根据value获取对应的枚举
	 *
	 * @param value
	 * @return
	 */
	public static CoreEnum getByValue(String value) {
		for (CoreEnum obj : CoreEnum.values()) {
			if (obj.value.equals(value)) {
				return obj;
			}
		}

		return null;
	}


}
