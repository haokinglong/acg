package ${packageName?lower_case}.resource;

import com.kinglong.core.annotation.NeedLogin;
import com.kinglong.entity.Admin;
import com.kinglong.lib.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import com.kinglong.core.validates.CommonPostGroup;
import com.kinglong.core.validates.CommonPutGroup;
import org.springframework.validation.annotation.Validated;
import com.kinglong.core.util.AreaUtil;
import java.util.Date;
import com.kinglong.dao.Pagination;
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
@RequestMapping("/api/${camelName?uncap_first}s")
public class ${camelName?cap_first}Resource {

    @Autowired
    private ${camelName?cap_first}Service ${camelName?uncap_first}Service;

    /**
     * 分页获取${cmt}
     *
     * @param page 分页页码
     * @param limit 每页条数
     * @param token 访问令牌
     * @return
     */
    @NeedLogin
    @ApiOperation("分页获取${cmt}")
    @GetMapping("/page")
    public Pagination<${camelName?cap_first}> page(${queryParamsStr}@RequestParam Integer page, @RequestParam Integer limit, @RequestHeader String token) {
        Admin admin = RedisCacheUtil.getAdminByToken(token, Admin.class);
        areaCode = AreaUtil.getAreaCode(admin.getAreaCode(), areaCode);

        return ${camelName?uncap_first}Service.page(${queryParamsExcludeTypeStr}page, limit);
    }

    /**
     * 根据id获取指定的${cmt}
     *
     * @param ${primaryKeyCamelName}
     * @return
     */
    @NeedLogin
    @ApiOperation("根据id获取指定的${cmt}")
    @GetMapping(value = "/{${primaryKeyCamelName}}")
    public ${camelName?cap_first} get(@PathVariable Integer ${primaryKeyCamelName}) {
        return ${camelName?uncap_first}Service.get(${primaryKeyCamelName});
    }

    /**
     * 添加${cmt}
     *
     * @param ${camelName?uncap_first}
     * @param token 访问令牌
     */
    @NeedLogin
    @ApiOperation("添加${cmt}")
    @PostMapping
    public void add(@RequestBody @Validated(CommonPostGroup.class) ${camelName?cap_first} ${camelName?uncap_first}, @RequestHeader String token) {
        Admin admin = RedisCacheUtil.getAdminByToken(token, Admin.class);
        ${camelName?uncap_first}.setCreateBy(admin.getAdminId());
        ${camelName?uncap_first}.setUpdateBy(admin.getAdminId());

        ${camelName?uncap_first}Service.add(${camelName?uncap_first});
    }
    
    /**
     * 修改${cmt}
     *
     * @param ${primaryKeyCamelName} 主键id
     * @param ${camelName?uncap_first}
     * @param token 访问令牌
     */
    @NeedLogin
    @ApiOperation("修改${cmt}")
    @PutMapping(value = "/{${primaryKeyCamelName}}")
    public void update(@PathVariable Integer ${primaryKeyCamelName}, @RequestBody @Validated(CommonPutGroup.class) ${camelName?cap_first} ${camelName?uncap_first}, @RequestHeader String token) {
        Admin admin = RedisCacheUtil.getAdminByToken(token, Admin.class);
        ${camelName?uncap_first}.setUpdateBy(admin.getAdminId());
        ${camelName?uncap_first}.setUpdateTime(new Date());
        ${camelName?uncap_first}.set${primaryKeyCamelName?cap_first}(${primaryKeyCamelName});

        ${camelName?uncap_first}Service.update(${camelName?uncap_first});
    }
    
    /**
     * 删除${cmt}
     *
     * @param ${primaryKeyCamelName} 主键id
     */
    @NeedLogin
    @ApiOperation("删除${cmt}")
    @DeleteMapping(value = "/{${primaryKeyCamelName}}")
    public void delete(@PathVariable Integer ${primaryKeyCamelName}) {
        ${camelName?uncap_first}Service.delete(${primaryKeyCamelName});
    }

}