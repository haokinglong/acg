package com.kinglong.acg.consts;

/**
 * 数据库字段数据类型
 *
 * @author haojinlong
 * @date 2020/3/20 17:25
 * @see <a href="https://docs.MYSQL.com/cd/B28359_01/server.111/b28318/datatype.htm">docs.MYSQL</a>
 */
public enum ColumnDataType {

	MYSQL_CHAR("char", "String"),
	MYSQL_VARCHAR("varchar", "String"),
	MYSQL_TINYTEXT("tinytext", "String"),
	MYSQL_BLOB("blob", "String"),
	MYSQL_TINYBLOB("tinyblob", "String"),
	MYSQL_TEXT("text", "String"),
	MYSQL_MEDIUMBLOB("mediumblob", "String"),
	MYSQL_MEDIUMTEXT("mediumtext", "String"),
	MYSQL_LONGBLOB("longblob", "String"),
	MYSQL_LONGTEXT("longtext", "String"),

	MYSQL_DATE("date", "Date"),
	MYSQL_TIMESTAMP("timestamp", "Date"),
	MYSQL_DATETIME("datetime", "Date"),
	MYSQL_TIME("time", "Date"),
	MYSQL_YEAR("year", "Integer"),

	MYSQL_TINYINT("tinyint", "Integer"),
	MYSQL_SMALLINT("smallint", "Integer"),
	MYSQL_MEDIUMINT("mediumint", "Integer"),
	MYSQL_INT("int", "Integer"),
	MYSQL_INTEGER("integer", "Integer"),
	MYSQL_BIGINT("bigint", "Long"),
	MYSQL_FLOAT("float", "Float"),
	MYSQL_DOUBLE("double", "Double");

	private String name;
	private String codeName;

	private ColumnDataType(String name, String codeName) {
		this.codeName = codeName;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getCodeName() {
		return codeName;
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