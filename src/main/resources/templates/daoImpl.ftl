package ${packageName?lower_case}.dao.impl;

import com.kinglong.dao.Pagination;
import ${packageName?lower_case}.entity.${camelName?cap_first};
import ${packageName?lower_case}.dao.${camelName?cap_first}Dao;
import org.springframework.stereotype.Repository;
import com.kinglong.lib.StringUtil;
import java.util.*;

/**
 * 描述：类 {@code ${camelName?cap_first}} ${cmt}DAO实现类
 *
 * @author ${author}
 * @date ${date?string('yyyy-MM-dd')}
 */
@Repository("${camelName?uncap_first}Dao")
public class ${camelName?cap_first}DaoImpl extends BaseDaoImpl<${camelName?cap_first}> implements ${camelName?cap_first}Dao {

    @Override
    public Pagination<${camelName?cap_first}> page(${queryParamsStr}Integer page, Integer limit) {
        StringBuilder sql = new StringBuilder("SELECT * FROM ${name?lower_case} WHERE 1=1");
        StringBuilder countSql = new StringBuilder("SELECT COUNT(1) FROM ${name?lower_case} WHERE 1=1");
        List<Object> param = new ArrayList<>(8);

    <#list metaColumns as column>
        <#if column.operation = "Q">
            <#if column.dataTypeStr = "String">
        if (StringUtil.isNotBlank(${column.camelName?uncap_first})) {
            <#else>
        if (null != ${column.camelName?uncap_first}) {
            </#if>
            sql.append(" AND ${column.name?lower_case}=?");
            countSql.append(" AND ${column.name?lower_case}=?");
            param.add(${column.camelName?uncap_first});
        }
        </#if>
        <#if column.operation = "L" || column.operation = "R" || column.operation = "A">
            <#if column.dataTypeStr = "String">
        if (StringUtil.isNotBlank(${column.camelName?uncap_first})) {
            <#else>
        if (null != ${column.camelName?uncap_first}) {
            </#if>
            sql.append(" AND ${column.name?lower_case} LIKE ?");
            countSql.append(" AND ${column.name?lower_case} LIKE ?");
            <#if column.operation = "L">
            param.add(${column.camelName?uncap_first} + "%");
            </#if>
            <#if column.operation = "R">
            param.add("%" + ${column.camelName?uncap_first});
            </#if>
            <#if column.operation = "A">
            param.add("%" + ${column.camelName?uncap_first} + "%");
            </#if>
        }
        </#if>
        <#if column.operation = "B">
        if (null != ${column.camelName?uncap_first}Start) {
            sql.append(" AND ${column.name?lower_case}>=?");
            countSql.append(" AND ${column.name?lower_case}>=?");
            param.add(${column.camelName?uncap_first}Start);
        }
        if (null != ${column.camelName?uncap_first}End) {
            sql.append(" AND ${column.name?lower_case}<=?");
            countSql.append(" AND ${column.name?lower_case}<=?");
            param.add(${column.camelName?uncap_first}End);
        }
        </#if>
    </#list>

        sql.append(" ORDER BY create_time DESC");

        Object[] temp = param.toArray();
        Pagination<${camelName?cap_first}> pageData = page(sql.toString(), page, limit, temp);
        pageData.setTotal(count(countSql.toString(), temp));

        return pageData;
    }

    @Override
    public ${camelName?cap_first} getOtherInfoById(Integer ${primaryKeyCamelName}) {
        return get("SELECT o.*, ca.name AS create_name, ua.name AS update_name,ca.dept_name AS creator_dept_name FROM ${name?lower_case} o LEFT JOIN admin ca ON o.create_by = ca.admin_id LEFT JOIN admin ua ON o.update_by = ua.admin_id WHERE o.${primaryKey?lower_case}=?", ${primaryKeyCamelName});
    }

    @Override
    public Integer countByAreaCode(String areaCode) {
        return count("SELECT count(*) FROM ${name?lower_case} WHERE area_code LIKE ?", areaCode + "%");
    }
}