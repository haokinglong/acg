package ${packageName?lower_case}.service;

import com.kinglong.dao.Pagination;
import ${packageName?lower_case}.entity.${camelName?cap_first};

/**
* 描述：类 {@code ${camelName?cap_first}} ${cmt}服务接口
*
* @author ${author}
* @date ${date?string('yyyy-MM-dd')}
*/
public interface ${camelName?cap_first}Service {

    /**
     * 分页查询
     *
     * @param page 分页页码
     * @param limit 每页条数
     * @return
     */
    Pagination<${camelName?cap_first}> page(${queryParamsStr}Integer page, Integer limit);

    /**
     * 根据主键id获取
     *
     * @param ${primaryKeyCamelName} 主键id
     * @return
     */
    ${camelName?cap_first} get(Integer ${primaryKeyCamelName});

    /**
     * 新增
     *
     * @param ${camelName?uncap_first}
     * @return
     */
    void add(${camelName?cap_first} ${camelName?uncap_first});

    /**
     * 修改
     *
     * @param ${camelName?uncap_first}
     * @return
     */
    void update(${camelName?cap_first} ${camelName?uncap_first});

    /**
     * 删除
     *
     * @param ${primaryKeyCamelName} 主键id
     * @return
     */
    void delete(Integer ${primaryKeyCamelName});
}