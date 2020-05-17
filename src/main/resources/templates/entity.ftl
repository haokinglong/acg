package ${packageName?lower_case}.entity;

import com.kinglong.dao.annotation.Persist;
import com.kinglong.dao.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.kinglong.core.validates.CommonPostGroup;
import com.kinglong.core.validates.CommonPutGroup;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：${cmt}实体类
 *
 * @author ${author}
 * @date ${date?string('yyyy-MM-dd')}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "${cmt}")
@Table(table = "${name?lower_case}", idField = "${primaryKeyCamelName}", idColumn = "${primaryKey?lower_case}", sequence = "seq_${name?lower_case}")
public class ${camelName?cap_first} implements Serializable {

    private static final long serialVersionUID = 1L;

<#list metaColumns as column>
    <#if primaryKey = column.name >
    @Persist({Persist.UNUPDATABLE })
    </#if>
    <#if column.camelName?uncap_first = "createTime" || column.camelName?uncap_first = "createBy">
    @Persist({ Persist.UNUPDATABLE })
    </#if>
    @ApiModelProperty(value = "${column.cmt}")
    <#if column.nullable.value = "N"  && primaryKey != column.name  && column.camelName?uncap_first != "createTime" && column.camelName?uncap_first != "createBy"&& column.camelName?uncap_first != "updateTime" && column.camelName?uncap_first != "updateBy">
        <#if column.nullable.value = "N" && column.dataTypeStr = "Integer" && primaryKey != column.name>
    @Min(value = -1, message = "${column.cmt}不能小于0", groups = {CommonPostGroup.class, CommonPutGroup.class})
        <#elseif column.nullable.value = "N" && column.dataTypeStr = "Double" && primaryKey != column.name >
    @Min(value = -1.0, message = "${column.cmt}不能小于0", groups = {CommonPostGroup.class, CommonPutGroup.class})
        <#elseif column.nullable.value = "N" && column.dataTypeStr = "Date" && primaryKey != column.name >
    @NotNull(message = "${column.cmt}不能为空", groups = {CommonPostGroup.class, CommonPutGroup.class})
        <#else>
    @NotBlank(message = "${column.cmt}不能为空", groups = {CommonPostGroup.class, CommonPutGroup.class})
    @Size(max = ${column.dataLength}, message = "超过${column.cmt}大小限制：${column.dataLength}", groups = {CommonPostGroup.class, CommonPutGroup.class})
        </#if>
    </#if>
    <#if column.camelName?uncap_first = "areaName">
    @NotBlank(message = "${column.cmt}不能为空", groups = {CommonPostGroup.class, CommonPutGroup.class})
    </#if>
    <#if column.nullable.value != "N" && column.dataTypeStr = "String">
    @Size(max = ${column.dataLength}, message = "超过${column.cmt}大小限制：${column.dataLength}", groups = {CommonPostGroup.class, CommonPutGroup.class})
    </#if>
    private ${column.dataTypeStr} ${column.camelName?uncap_first};

</#list>

    @ApiModelProperty(value = "创建人姓名")
    @Persist({ Persist.UNINSERTABLE, Persist.UNUPDATABLE })
    private String createName;
    @ApiModelProperty(value = "更新时间")
    @Persist({ Persist.UNINSERTABLE, Persist.UNUPDATABLE })
    private String updateName;
    @ApiModelProperty(value = "创建人部门名称")
    @Persist({ Persist.UNINSERTABLE, Persist.UNUPDATABLE })
    private String creatorDeptName;
}