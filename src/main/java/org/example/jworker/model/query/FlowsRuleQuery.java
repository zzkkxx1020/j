package org.example.jworker.model.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.example.jworker.common.config.BasicQuery;

@Data
public class FlowsRuleQuery extends BasicQuery {

    @ApiModelProperty("规则名称")
    private String search;
}
