<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.longfor.mapper.UserMapper">

    <resultMap id="BaseResultMap" type="${camelName?cap_first}">
        <id column="${primaryKey}" property="${primaryKeyCamelName}"/>
        <#list metaColumns as column>
            <#if column.name != primaryKey>
        <result column="${column.name?lower_case}" property="${column.camelName?uncap_first}"/>
            </#if>
        </#list>
    </resultMap>

    <sql id="Base_Column_List">
        <#list metaColumns as column>${column.name?lower_case}<#sep>, </#list>
    </sql>

</mapper>