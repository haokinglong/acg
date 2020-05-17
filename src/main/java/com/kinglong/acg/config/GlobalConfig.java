package com.kinglong.acg.config;

import lombok.Builder;
import lombok.Data;

/**
 * 全局配置类
 *
 * @author haojinlong
 * @date 2020/3/20 17:30
 */
@Data
@Builder
public class GlobalConfig {

	/**
	 * 作者
	 */
	private String author;

	/**
	 * 包名
	 */
	private String packageName;

	/**
	 * 模板路径
	 */
	private String templatePath;

	/**
	 * 数据库连接：jdbc:oracle:thin:@kinglong.net:1521/
	 */
	private String dbUrl;

	/**
	 * 数据库实例名：orcl
	 */
	private String dbInstanceName;
	/**
	 * 数据库用户名
	 */
	private String dbUsername;
	/**
	 * 数据库密码
	 */
	private String dbPassword;

	/**
	 * 实体类代码生成路径
	 */
	private String entityPath;

	/**
	 * dao接口类代码生成路径
	 */
	private String daoPath;

	/**
	 * dao实现类代码生成路径
	 */
	private String daoImplPath;

	/**
	 * 服务接口类代码生成路径
	 */
	private String servicePath;

	/**
	 * 服务接口实现类代码生成路径
	 */
	private String serviceImplPath;

	/**
	 * resource层代码生成路径
	 */
	private String resourcePath;

	/**
	 * vue代码生成路径
	 */
	private String vuePath;

	/**
	 * 是否需要生成实体类
	 */
	private Boolean entityNeed;

	/**
	 * 是否需要生成dao接口类
	 */
	private Boolean daoNeed;

	/**
	 * 是否需要生成dao接口实现类
	 */
	private Boolean daoImplNeed;

	/**
	 * 是否需要生成服务接口类
	 */
	private Boolean serviceNeed;

	/**
	 * 是否需要生成服务接口实现类
	 */
	private Boolean serviceImplNeed;

	/**
	 * 是否需要生成resource类
	 */
	private Boolean resourceNeed;

	/**
	 * 是否需要生成vue
	 */
	private Boolean vueNeed;
}
