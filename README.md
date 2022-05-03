## 简介:
该项目为后端代码生成工具,可自定义快速生成三层结构的代码,自定义程度高,相较于`mybatis-plus`主要的区别在于自行实现了数据库的数据读取,而且是基于最基本的数据库`JDBC`连接方案,所以使用起来很小巧,一个`main`方法即可调用

## 快速开始

在`AutoGenerateCode`启动类中填写相关信息即可生成相关代码

## 温馨提示

1.重复启动`main()`方法,代码是会被覆盖的,使用时请谨慎对待.

2.使用前务必根据新框架生成目录结构,否则会报错或者引用错误

3.如果觉得目前版本不适合自己的系统需求,可自行进行修改,具体的技术点可以参考`freemarker`语法.模板文件位置在`src/main/resources/templates`

下面是我的github地址，有兴趣的可以过去clone一下，现在还只支持oracle数据库， 后面有空了再添加一下mysql的支持。 其实原理不难，该工具主要的工作就是在对数据库的数据进行处理，`freemarker`的使用仅仅只是在最后的文件生成部分。所以，有兴趣的boy可以借鉴这个已有的数据处理逻辑，改造为符合自己公司的代码规范的工具。 如果要修改的话，主要修改的就是各模板，先写一份自己规范的代码，将模板替换即可。如果用心研究的话，我相信半天足以。

## 代码片段
```java
/**
 * 代码自动生成
 *
 * @author haojinlong
 * @date 2020/9/20 17:30
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
            .packageName("com.honey.badger.demo")
            //作者姓名
            .author("hjl")
            // 模板路径 示例："/src/main/resources/templates"
            .templatePath(getRootPath())
            //示例： "jdbc:mysql://127.0.0.1:3306/"
            .dbUrl("jdbc:mysql://127.0.0.1:3306/")
            //示例： "wstro"
            .schema("account")
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
```

## 2022.5.3更新:
基于原版只支持`oracle`的基础上,新增了可支持`mysql`的版本新分支`mysql`
