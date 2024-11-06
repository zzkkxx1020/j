package org.example.jworker.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 节点处理状态
 * </p>
 *
 * @author zzkkxx
 * @since 2024-11-05
 */
@Getter
@Setter
@TableName("task_node_state")
@ApiModel(value = "TaskNodeState对象", description = "节点处理状态")
public class TaskNodeState implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Integer id;

    @ApiModelProperty("任务id")
    @TableField("task_id")
    private Integer taskId;

    @ApiModelProperty("节点id")
    @TableField("node_id")
    private Integer nodeId;

    @ApiModelProperty("节点处理状态 0 通过 1 拒绝")
    @TableField("node_status")
    private Integer nodeStatus;

    @ApiModelProperty("处理人id")
    @TableField("user_id")
    private String userId;
}
