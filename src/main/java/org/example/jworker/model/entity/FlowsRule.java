package org.example.jworker.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 工作流规则主表
 * </p>
 *
 * @author zzkkxx
 * @since 2024-11-05
 */
@Getter
@Setter
@TableName("flows_rule")
@ApiModel(value = "FlowsRule对象", description = "工作流规则主表")
public class FlowsRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value ="id",type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("工作流name")
    @TableField("flows_name")
    @NotBlank
    private String flowsName;

    @ApiModelProperty("工作流type")
    @TableField("flows_type")
    @NotNull
    private Integer flowsType;

    @ApiModelProperty("工作流子类type")
    @TableField("flows_subtype")
    private Integer flowsSubtype;

    @ApiModelProperty("0 非默认/内置 1 默认/内置")
    @TableField("is_default")
    private Integer isDefault;

    @ApiModelProperty("0 启用 1关闭")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;
}
