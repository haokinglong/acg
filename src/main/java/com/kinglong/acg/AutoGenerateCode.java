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
		tbs.add(MetaTable.builder().name("sms_record").build());

		//配置1：数据库,文件路径配置
		GlobalConfig.GlobalConfigBuilder globalConfigBuilder = GlobalConfig.builder()
				//包名
				.packageName("com.kinglong")
				//作者姓名
				.author("haojinlong")
				// 模板路径 示例："/src/main/resources/templates"
				.templatePath(getRootPath())
				//示例： "jdbc:oracle:thin:@kinglong.net:1521/"
				.dbUrl("jdbc:oracle:thin:@btzh.net:1521/")
				//示例： "orcl"
				.dbInstanceName("orcl")
				//示例： "zz9x"
				.dbUsername("grid_sqet")
				//示例： "123456"
				.dbPassword("oA1cC0cH1eK2qP0f")


				//示例： "/Users/haojinlong/mycode/grid/grid-wgfw-api/src/main/java/com/kinglong/entity/business"
				.entityPath("/Users/haojinlong/mycode/grid/grid-wgfw-api/src/main/java/com/kinglong/entity")
				//示例： "/Users/haojinlong/mycode/grid/grid-wgfw-api/src/main/java/com/kinglong/dao"
				.daoPath("/Users/haojinlong/mycode/grid/grid-wgfw-api/src/main/java/com/kinglong/dao")
				//示例： "/Users/haojinlong/mycode/grid/grid-wgfw-api/src/main/java/com/kinglong/dao/impl"
				.daoImplPath("/Users/haojinlong/mycode/grid/grid-wgfw-api/src/main/java/com/kinglong/dao/impl")
				//示例： "/Users/haojinlong/mycode/grid/grid-wgfw-api/src/main/java/com/kinglong/service"
				.servicePath("/Users/haojinlong/mycode/grid/grid-wgfw-api/src/main/java/com/kinglong/service")
				//示例： "/Users/haojinlong/mycode/grid/grid-wgfw-api/src/main/java/com/kinglong/service/impl"
				.serviceImplPath("/Users/haojinlong/mycode/grid/grid-wgfw-api/src/main/java/com/kinglong/service/impl")
				//示例： "/Users/haojinlong/mycode/grid/grid-wgfw-api/src/main/java/com/kinglong/resource"
				.resourcePath("/Users/haojinlong/mycode/grid/grid-wgfw-api/src/main/java/com/kinglong/resource")
				//示例： "/Users/haojinlong/mycode/code-generate/acg/src/main/java/com/kinglong/acg/vue"
				.vuePath("/Users/haojinlong/mycode/code-generate/acg/src/main/java/com/kinglong/acg/vue");

		//配置2：哪些需要生成,哪些不要生成
		GlobalConfig globalConfig = globalConfigBuilder
				.entityNeed(true)
				.daoNeed(true)
				.daoImplNeed(true)
				.serviceNeed(true)
				.serviceImplNeed(true)
				.resourceNeed(true)
				.vueNeed(false)
				.build();

		Action action = new Action();
		//如果想生成数据库内全部表的代码,tbs传null即可
		action.start(globalConfig, tbs, true);
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
