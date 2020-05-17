package com.kinglong.acg.consts;

/**
 * 查询操作所用枚举
 *
 * @author haojinlong
 * @date 2020/3/20 17:25
 */
public enum QueryOperationEnum {

	/**
	 * 字段操作,该字段值取自cmt字段末尾数据,默认一个字段只可有一个操作
	 * {@code A} 全模糊查询 {@code param LIKE %?%}
	 */
	A("A"),

	/**
	 * 字段操作,该字段值取自cmt字段末尾数据,默认一个字段只可有一个操作
	 *  {@code B} 区间查询 {@code param>=? AND param<=?}
	 */
	B("B"),

	/**
	 * 字段操作,该字段值取自cmt字段末尾数据,默认一个字段只可有一个操作
	 * {@code Q} 普通精确查询 {@code param=?}
	 */
	Q("Q"),

	/**
	 * 字段操作,该字段值取自cmt字段末尾数据,默认一个字段只可有一个操作
	 * {@code L} 左前缀模糊查询 {@code param LIKE ?%}
	 */
	L("L"),

	/**
	 * 字段操作,该字段值取自cmt字段末尾数据,默认一个字段只可有一个操作
	 * {@code R} 右前缀模糊查询 {@code A LIKE %?}
	 */
	R("R"),

	/**
	 * 字段操作,该字段值取自cmt字段末尾数据,默认一个字段只可有一个操作
	 * 无任何操作
	 */
	N("N");


	private String value;

	QueryOperationEnum(String value) {
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
	public static QueryOperationEnum getByValue(String value) {
		for (QueryOperationEnum obj : QueryOperationEnum.values()) {
			if (obj.value.equals(value)) {
				return obj;
			}
		}

		return null;
	}


}
