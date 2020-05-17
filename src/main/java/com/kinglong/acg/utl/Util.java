package com.kinglong.acg.utl;

/**
 * 工具类
 *
 * @author haojinlong
 * @date 2020/3/20 17:25
 */
public class Util {

	/**
	 * 根据下划线获取驼峰命名
	 *
	 * @param args
	 * @return
	 */
	public static String getCamelName(String args) {
		StringBuilder result = new StringBuilder();
		if (args == null || args.isEmpty()) {
			return "";
		} else if (!args.contains("_")) {
			// 不含下划线,全部小写
			return args.toLowerCase();
		}

		String[] camels = args.split("_");
		for (String camel : camels) {
			// 跳过原始字符串中开头、结尾的下换线或双重下划线
			if (camel.isEmpty()) {
				continue;
			}

			if (result.length() == 0) {
				// 第一个驼峰片段,全部字母都小写
				result.append(camel.toLowerCase());
			} else {
				// 其他的驼峰片段,首字母大写
				result.append(camel.substring(0, 1).toUpperCase());
				result.append(camel.substring(1).toLowerCase());
			}
		}
		return result.toString();
	}

}
