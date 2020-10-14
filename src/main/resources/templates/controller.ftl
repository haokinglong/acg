package ${packageName?lower_case}.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ${packageName?lower_case}.entity.${camelName?cap_first};
import ${packageName?lower_case}.service.${camelName?cap_first}Service;

/**
 * 描述：类 {@code ${camelName?cap_first}} ${cmt}API
 *
 * @author ${author}
 * @date ${date?string('yyyy-MM-dd')}
 */
@Api(tags = "${cmt} API")
@Slf4j
@RestController
@RequestMapping("/${camelName?uncap_first}s")
public class ${camelName?cap_first}Resource {

    @Autowired
    private ${camelName?cap_first}Service ${camelName?uncap_first}Service;


}