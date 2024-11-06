package org.example.jworker.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 工作流节点表
 * </p>
 *
 * @author zzkkxx
 * @since 2024-11-05
 */
@Getter
@Setter
@TableName("flows_node")
@ApiModel(value = "FlowsNode对象", description = "工作流节点表")
public class FlowsNode implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value ="id",type = IdType.INPUT)
    private Integer id;

    @ApiModelProperty("节点所属规则id")
    @TableField("flows_rule_id")
    @NotNull
    private Integer flowsRuleId;

    @ApiModelProperty("该节点审批类型 0 会签  1 或签 2 执行")
    @TableField("node_type")
    @NotNull
    private Integer nodeType;

    @ApiModelProperty("上一节点id 首节点为空")
    @TableField("before_node_id")
    private Integer beforeNodeId;

    @ApiModelProperty("下一节点id 尾节点为空")
    @TableField("next_node_id")
    private Integer nextNodeId;

    @ApiModelProperty("该节点审核人id集合")
    @TableField("audit_id")
    private List<String> auditId;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty("更新类型 0更新顺序 1未更新顺序")
    @TableField()
    private Integer updateType;
}
