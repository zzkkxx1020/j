package org.example.jworker.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "FlowsTaskHandle", description = "任务处理")
public class FlowsTaskHandle {

    @NotNull
    @ApiModelProperty("任务id")
    private Integer id;

    @NotBlank
    @ApiModelProperty("处理人id")
    private String userId;

    @NotNull
    @ApiModelProperty("处理结果")
    private Integer status;

    @ApiModelProperty("备注")
    private String remark;

    @NotNull
    @ApiModelProperty("规则id")
    private Integer ruleId;

    @NotNull
    @ApiModelProperty("处理节点id")
    private Integer nodeId;
}
