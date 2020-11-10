package ${packageName?lower_case}.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import ${packageName?lower_case}.entity.${camelName?cap_first};
import ${packageName?lower_case}.mapper.${camelName?cap_first}Mapper;
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
    private ${camelName?cap_first}Mapper ${camelName}Mapper;

}