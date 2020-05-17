package com.kinglong.acg.generate;

import com.kinglong.acg.config.GlobalConfig;
import com.kinglong.acg.core.MetaDatabase;
import com.kinglong.acg.core.MetaTable;
import com.kinglong.acg.utl.OracleUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * 执行类
 *
 * @author haojinlong
 * @date 2020/3/20 17:25
 */
@Slf4j
public class Action {

	private static Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);

	/**
	 * 是否为控制台打印
	 *
	 * <p>
	 * 一般控制台打印适合在修改完模板后方便进行测试使用,可以方便快捷的看到模板的修改效果
	 * </p>
	 */
	private static volatile boolean CONSOLE_OUT = true;

	/**
	 * 开始创建
	 *
	 * @param config       全局配置
	 * @param tbs          需要生成的表,传null的话会将该用户下所有的表所有的表生成代码
	 * @param isConsoleOut 是否为控制台打印,默认为true
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws TemplateException
	 */
	public void start(GlobalConfig config, List<MetaTable> tbs, boolean isConsoleOut) throws IOException, SQLException, ClassNotFoundException, TemplateException {
		CONSOLE_OUT = isConsoleOut;
		validate(config);

		cfg.setDirectoryForTemplateLoading(new File(config.getTemplatePath()));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

		MetaDatabase database = buildDatabaseInfo(config, tbs);

		List<MetaTable> tables = OracleUtil.getDatabase(database);

		createTables(config, tables);
	}

	/**
	 * 构建数据库信息
	 *
	 * @param config
	 * @param tbs
	 * @return
	 */
	public static MetaDatabase buildDatabaseInfo(GlobalConfig config, List<MetaTable> tbs) {
		MetaDatabase.MetaDatabaseBuilder databaseBuilder = MetaDatabase.builder()
				.url(config.getDbUrl())
				.instanceName(config.getDbInstanceName())
				.username(config.getDbUsername())
				.password(config.getDbPassword());

		if (!CollectionUtils.isEmpty(tbs)) {
			databaseBuilder.metaTables(tbs);
		}

		return databaseBuilder.build();
	}

	public static void createTables(GlobalConfig config, List<MetaTable> tables) throws IOException, TemplateException {
		for (MetaTable table : tables) {
			table.setAuthor(config.getAuthor());
			table.setPackageName(config.getPackageName());
			table.setDate(new Date());

			log.info("开始创建: " + table.getName());

			if (config.getEntityNeed() && !StringUtils.isBlank(config.getEntityPath())) {
				createEntity(table, config.getEntityPath());
				log.info("entity 创建完毕");
			}
			if (config.getDaoNeed() && !StringUtils.isBlank(config.getDaoPath())) {
				createDao(table, config.getDaoPath());
				log.info("dao 创建完毕");
			}
			if (config.getDaoImplNeed() && !StringUtils.isBlank(config.getDaoImplPath())) {
				createDaoImpl(table, config.getDaoImplPath());
				log.info("daoImpl 创建完毕");
			}
			if (config.getServiceNeed() && !StringUtils.isBlank(config.getServicePath())) {
				createService(table, config.getServicePath());
				log.info("service 创建完毕");
			}
			if (config.getServiceImplNeed() && !StringUtils.isBlank(config.getServiceImplPath())) {
				createServiceImpl(table, config.getServiceImplPath());
				log.info("serviceImpl 创建完毕");
			}
			if (config.getResourceNeed() && !StringUtils.isBlank(config.getResourcePath())) {
				createResource(table, config.getResourcePath());
				log.info("resource 创建完毕");
			}
			if (config.getVueNeed() && !StringUtils.isBlank(config.getVuePath())) {
				createVue(table, config.getVuePath());
				log.info("vue创建完毕");
			}

			log.info(table.getName() + "创建完毕");
		}
	}


	/**
	 * 创建vue
	 *
	 * @param table
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static void createVue(MetaTable table, String path) throws IOException, TemplateException {
		log.info("开始创建vue");

		Template temp = cfg.getTemplate("vue.ftl");
		File file = new File(path + "/" + table.getCamelNameToUppercase() + ".vue");

		generate(temp, file, table);
	}

	/**
	 * 创建实体类
	 *
	 * @param table
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static void createEntity(MetaTable table, String path) throws IOException, TemplateException {
		log.info("开始创建entity");

		Template temp = cfg.getTemplate("entity.ftl");
		File file = new File(path + "/" + table.getCamelNameToUppercase() + ".java");

		generate(temp, file, table);
	}

	/**
	 * 创建dao
	 *
	 * @param table
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static void createDao(MetaTable table, String path) throws IOException, TemplateException {
		log.info("开始创建Dao");

		Template temp = cfg.getTemplate("dao.ftl");
		File file = new File(path + "/" + table.getCamelNameToUppercase() + "Dao.java");

		generate(temp, file, table);
	}

	/**
	 * 创建dao实现
	 *
	 * @param table
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static void createDaoImpl(MetaTable table, String path) throws IOException, TemplateException {
		log.info("开始创建DaoImpl");

		Template temp = cfg.getTemplate("daoImpl.ftl");
		File file = new File(path + "/" + table.getCamelNameToUppercase() + "DaoImpl.java");

		generate(temp, file, table);
	}

	/**
	 * 创建service
	 *
	 * @param table
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static void createService(MetaTable table, String path) throws IOException, TemplateException {
		log.info("开始创建Service");

		Template temp = cfg.getTemplate("service.ftl");
		File file = new File(path + "/" + table.getCamelNameToUppercase() + "Service.java");

		generate(temp, file, table);
	}

	/**
	 * 创建serviceImpl
	 *
	 * @param table
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static void createServiceImpl(MetaTable table, String path) throws IOException, TemplateException {
		log.info("开始创建ServiceImpl");

		Template temp = cfg.getTemplate("serviceImpl.ftl");
		File file = new File(path + "/" + table.getCamelNameToUppercase() + "ServiceImpl.java");

		generate(temp, file, table);
	}

	/**
	 * 创建resource
	 *
	 * @param table
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static void createResource(MetaTable table, String path) throws IOException, TemplateException {
		log.info("开始创建Resource");

		Template temp = cfg.getTemplate("controller.ftl");
		File file = new File(path + "/" + table.getCamelNameToUppercase() + "Resource.java");

		generate(temp, file, table);
	}

	/**
	 * 写入文件
	 *
	 * @param temp  模板文件
	 * @param file  写入的文件
	 * @param table 表
	 * @throws IOException
	 * @throws TemplateException
	 */
	private static void generate(Template temp, File file, MetaTable table) throws IOException, TemplateException {
		if (CONSOLE_OUT) {
			Writer out = new OutputStreamWriter(System.out, StandardCharsets.UTF_8);
			temp.process(table, out);
		} else {
			try (OutputStream fos = new FileOutputStream(file); Writer out = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {
				temp.process(table, out);
			}
		}
	}

	/**
	 * 必要的参数校验
	 *
	 * @param globalConfig 全局配置
	 */
	private void validate(GlobalConfig globalConfig) {
		if (StringUtils.isBlank(globalConfig.getAuthor())) {
			throw new IllegalArgumentException("author 不能为空");
		}
		if (StringUtils.isBlank(globalConfig.getPackageName())) {
			throw new IllegalArgumentException("packageName 不能为空");
		}
		if (StringUtils.isBlank(globalConfig.getTemplatePath())) {
			throw new IllegalArgumentException("templatePath 不能为空");
		}
		if (StringUtils.isBlank(globalConfig.getDbUrl())) {
			throw new IllegalArgumentException("dbUrl 不能为空");
		}
		if (StringUtils.isBlank(globalConfig.getDbInstanceName())) {
			throw new IllegalArgumentException("dbInstanceName 不能为空");
		}
		if (StringUtils.isBlank(globalConfig.getDbUsername())) {
			throw new IllegalArgumentException("dbUsername 不能为空");
		}
		if (StringUtils.isBlank(globalConfig.getDbPassword())) {
			throw new IllegalArgumentException("dbPassword 不能为空");
		}

	}
}
