package ${packageName?lower_case}.service.impl;

import com.kinglong.dao.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import ${packageName?lower_case}.entity.${camelName?cap_first};
import ${packageName?lower_case}.dao.${camelName?cap_first}Dao;
import ${packageName?lower_case}.service.${camelName?cap_first}Service;

/**
* 描述：类 {@code ${camelName?cap_first}} ${cmt}服务接口实现类
*
* @author ${author}
* @date ${date?string('yyyy-MM-dd')}
*/
@Slf4j
@Service
public class ${camelName?cap_first}ServiceImpl implements ${camelName?cap_first}Service {

    @Autowired
    private ${camelName?cap_first}Dao ${camelName}Dao;

    @Override
    public Pagination<${camelName?cap_first}> page(${queryParamsStr}Integer page, Integer limit){
        return ${camelName?uncap_first}Dao.page(${queryParamsExcludeTypeStr}page, limit);
    }

    @Override
    public ${camelName?cap_first} get(Integer ${primaryKeyCamelName}){
        return ${camelName?uncap_first}Dao.getOtherInfoById(${primaryKeyCamelName});
    }

    @Override
    public void add(${camelName?cap_first} ${camelName?uncap_first}) {
        ${camelName?uncap_first}Dao.add(${camelName?uncap_first});
    }

    @Override
    public void update(${camelName?cap_first} ${camelName?uncap_first}) {
        ${camelName?uncap_first}Dao.update(${camelName?uncap_first});
    }

    @Override
    public void delete(Integer ${primaryKeyCamelName}) {
        ${camelName?uncap_first}Dao.delete(${primaryKeyCamelName});
    }
}