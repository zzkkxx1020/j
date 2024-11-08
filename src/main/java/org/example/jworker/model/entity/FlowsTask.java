package org.example.jworker.model.entity;

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
 * 任务表
 * </p>
 *
 * @author zzkkxx
 * @since 2024-11-05
 */
@Getter
@Setter
@TableName("flows_task")
@ApiModel(value = "FlowsTask对象", description = "任务表")
public class FlowsTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Integer id;

    @ApiModelProperty("执行任务名称")
    @TableField("task_name")
    @NotBlank
    private String taskName;

    @ApiModelProperty("所属任务规则id")
    @TableField("flows_rule_id")
    private Integer flowsRuleId;

    @ApiModelProperty("正在执行中的节点id")
    @TableField("step_node_id")
    @NotNull
    private Integer stepNodeId;

    @ApiModelProperty("任务所属业务数据id")
    @TableField("data_id")
    private Integer dataId;

    @ApiModelProperty("执行状态 0 进行中 1结束")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty("任务提交人")
    @TableField("task_user")
    @NotBlank
    private String taskUser;

    @ApiModelProperty("是否撤销(逻辑删除) 0 正常 1 删除")
    @TableField("task_del")
    @NotBlank
    private Integer taskDel;
}
