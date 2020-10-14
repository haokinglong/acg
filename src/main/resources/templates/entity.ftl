package ${packageName?lower_case}.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 描述：${cmt}实体类
 *
 * @author ${author}
 * @date ${date?string('yyyy-MM-dd')}
 */
@Setter
@Getter
@Alias("${camelName?cap_first}")
@ApiModel(description = "${cmt}")
public class ${camelName?cap_first} implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ${primaryKeyCmt}
     */
    @ApiModelProperty(value = "${primaryKeyCmt}")
    private ${primaryKeyType} ${primaryKeyCamelName};

<#list metaColumns as column>
<#if column.isPrimaryKey.value = "NO">
    /**
    * ${column.cmt}
    */
    @ApiModelProperty(value = "${column.cmt}")
    <#if column.nullable.value = "NO" && column.camelName?uncap_first != "createTime" && column.camelName?uncap_first != "updateTime">
        <#if column.nullable.value = "NO" && (column.dataTypeStr = "Integer" || column.dataTypeStr = "Long" || column.dataTypeStr = "Double")>
    @NotNull(message = "${column.cmt}不能为空")
        <#else>
    @NotBlank(message = "${column.cmt}不能为空")
    @Size(max = ${column.dataLength}, message = "超过${column.cmt}大小限制：${column.dataLength}")
        </#if>
    </#if>
    <#if column.camelName?uncap_first = "areaName">
    @NotBlank(message = "${column.cmt}不能为空")
    </#if>
    <#if column.nullable.value != "NO" && column.dataTypeStr = "String">
    @Size(max = ${column.dataLength}, message = "超过${column.cmt}大小限制：${column.dataLength}")
    </#if>
    private ${column.dataTypeStr} ${column.camelName?uncap_first};

</#if>
</#list>
}