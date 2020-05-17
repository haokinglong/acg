package com.kinglong.acg.consts;

/**
 * 是或否状态枚举
 *
 * @author haojinlong
 * @date 2020/3/20 17:25
 */
public enum YesOrNo {

	/**
	 * 否
	 */
	NO(0, "N"),

	/**
	 * 是
	 */
	YES(1, "Y");

	private Integer code;
	private String value;

	YesOrNo(Integer code, String value) {
		this.code = code;
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public Integer getCode() {
		return this.code;
	}

	/**
	 * 根据value获取对应的枚举
	 *
	 * @param value
	 * @return
	 */
	public static YesOrNo getByValue(String value) {
		for (YesOrNo obj : YesOrNo.values()) {
			if (obj.value.equals(value)) {
				return obj;
			}
		}

		return null;
	}

}