package com.kinglong.acg;

import com.kinglong.acg.config.GlobalConfig;
import com.kinglong.acg.core.MetaTable;
import com.kinglong.acg.generate.Action;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

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
        tbs.add(MetaTable.builder().name("user").build());

        //配置1：数据库,文件路径配置
        GlobalConfig.GlobalConfigBuilder globalConfigBuilder = GlobalConfig.builder()
            //包名
            .packageName("com.honey")
            //作者姓名
            .author("haojinlong")
            // 模板路径 示例："/src/main/resources/templates"
            .templatePath(getRootPath())
            //示例： "jdbc:mysql://127.0.0.1:3306/"
            .dbUrl("jdbc:mysql://127.0.0.1:3306/")
            //示例： "wstro"
            .schema("wstro")
            //示例： "root"
            .dbUsername("root")
            //示例： "123456"
            .dbPassword("123456")

            .entityPath("/Users/haojinlong/mycode/honey/src/main/java/com/honey/badger/demo/dao/entity")
            .daoPath("/Users/haojinlong/mycode/honey/src/main/java/com/honey/badger/demo/dao/mapper")
            .daoImplPath("/Users/haojinlong/mycode/honey/src/main/resources/mapper")
            .servicePath("/Users/haojinlong/mycode/honey/src/main/java/com/honey/badger/demo/service")
            .serviceImplPath("/Users/haojinlong/mycode/honey/src/main/java/com/honey/badger/demo/service/impl")
            .resourcePath("/Users/haojinlong/mycode/honey/src/main/java/com/honey/badger/demo/controller");

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
