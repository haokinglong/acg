package ${packageName?lower_case}.dao;

import ${packageName?lower_case}.entity.${camelName?cap_first};

/**
 * 描述：类 {@code ${camelName?cap_first}} ${cmt}DAO
 *
 * @author ${author}
 * @date ${date?string('yyyy-MM-dd')}
 */
public interface ${camelName?cap_first}Dao extends BaseDao<${camelName?cap_first}> {

    /**
     * 分页查询
     *
     * @param page 分页页码
     * @param limit 每页条数
     * @return
     */
    Pagination<${camelName?cap_first}> page(${queryParamsStr}Integer page, Integer limit);

    /**
     * 根据id获取包含其他信息的${cmt}
     *
     * @param ${primaryKeyCamelName}
     * @return
     */
    ${camelName?cap_first} getOtherInfoById(Integer ${primaryKeyCamelName});

    /**
     * 根据区域编码统计
     *
     * @param areaCode
     * @return
     */
    Integer countByAreaCode(String areaCode);

}