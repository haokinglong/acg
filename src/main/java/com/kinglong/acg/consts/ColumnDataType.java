package com.kinglong.acg.consts;

/**
 * 数据库字段数据类型
 *
 * @author haojinlong
 * @date 2020/3/20 17:25
 * @see <a href="https://docs.oracle.com/cd/B28359_01/server.111/b28318/datatype.htm">docs.oracle</a>
 */
public enum ColumnDataType {

	ORACLE_CHAR("CHAR", "String", 0),
	ORACLE_VARCHAR2("VARCHAR2", "String", 1),
	ORACLE_VARCHAR("VARCHAR", "String", 2),
	ORACLE_NCHAR("NCHAR", "String", 3),
	ORACLE_NVARCHAR2("NVARCHAR2", "String", 4),
	ORACLE_NUMBER("NUMBER", "Integer", 5),

	ORACLE_BINARY_FLOAT("BINARY_FLOAT", "Float", 6),
	ORACLE_BINARY_DOUBLE("BINARY_DOUBLE", "Double", 7),
	ORACLE_DATE("DATE", "Date", 8),
	ORACLE_TIMESTAMP("TIMESTAMP", "Date", 9),
	ORACLE_BLOB("BLOB", "String", 10),
	ORACLE_CLOB("CLOB", "String", 11),
	ORACLE_NCLOB("NCLOB", "String", 12),
	ORACLE_BFILE("BFILE", "String", 13),
	ORACLE_RAW("RAW", "String", 14);

	private String name;
	private String codeName;
	private int value;

	private ColumnDataType(String name, String codeName, int value) {
		this.codeName = codeName;
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getCodeName() {
		return codeName;
	}

	public int getValue() {
		return value;
	}

	public static String getName(int value) {
		for (ColumnDataType c : ColumnDataType.values()) {
			if (c.getValue() == value) {
				return c.name;
			}
		}
		return null;
	}

	public static Integer getValue(String name) {
		for (ColumnDataType c : ColumnDataType.values()) {
			if (c.getName().equals(name)) {
				return c.value;
			}
		}
		return null;
	}

	public static ColumnDataType getColumnDataType(String name) {
		for (ColumnDataType c : ColumnDataType.values()) {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}

}