package com.kinglong.acg;

import com.kinglong.acg.config.GlobalConfig;
import com.kinglong.acg.core.MetaTable;
import com.kinglong.acg.generate.Action;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 代码自动生成
 *
 * @author haojinlong
 * @date 2020/3/18 08:27
 */
@Slf4j
public class AutoGenerateCode {

	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, TemplateException {
		List<MetaTable> tbs = new ArrayList<>();
		//如果有多张表,请在这添加多张表的信息,表名即可,大小写均可
		tbs.add(MetaTable.builder().name("rel_app_alm_result").build());

		//配置1：数据库,文件路径配置
		GlobalConfig.GlobalConfigBuilder globalConfigBuilder = GlobalConfig.builder()
				//包名
				.packageName("com.longfor")
				//作者姓名
				.author("haojinlong")
				// 模板路径 示例："/src/main/resources/templates"
				.templatePath(getRootPath())
				//示例： "jdbc:mysql://192.168.26.129:3306/"
				.dbUrl("jdbc:mysql://rm-2zeh720m62s4v01f9.mysql.rds.aliyuncs.com:3306/")
				//示例： "wstro"
				.schema("lx3_db")
				//示例： "root"
				.dbUsername("dbadmin")
				//示例： "123456"
				.dbPassword("1qaz!QAZ")


				.entityPath("E:/sourcecode/dragonletter-appplat/src/main/java/com/longfor/entity")
				.daoPath("E:/sourcecode/dragonletter-appplat/src/main/java/com/longfor/mapper")
				.daoImplPath("E:/sourcecode/dragonletter-appplat/src/main/resources/mapper")
				.servicePath("E:/sourcecode/dragonletter-appplat/src/main/java/com/longfor/service")
				.serviceImplPath("E:/sourcecode/dragonletter-appplat/src/main/java/com/longfor/service/impl")
				.resourcePath("E:/sourcecode/dragonletter-appplat/src/main/java/com/longfor/controller");

		//配置2：哪些需要生成,哪些不要生成
		GlobalConfig globalConfig = globalConfigBuilder
				.entityNeed(true)
				.daoNeed(true)
				.daoImplNeed(true)
				.serviceNeed(true)
				.serviceImplNeed(true)
				.resourceNeed(true)
				.build();

		Action action = new Action();
		//如果想生成数据库内全部表的代码,tbs传null即可
		action.start(globalConfig, tbs, false);
	}

	/**
	 * Gets the root path of server.
	 *
	 * @return the root path
	 */
	public static String getRootPath() {
		return Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("templates")).getPath();
	}
}
