package org.example.jworker.model.vo;

import lombok.Data;
import org.example.jworker.model.entity.FlowsNode;
import org.example.jworker.model.entity.FlowsRule;
import org.example.jworker.model.entity.FlowsTask;

import java.util.List;

@Data
public class FlowsTaskShowVO extends FlowsTask {

    /**
     * 对应规则
     */
    private FlowsRule flowsRule;

    /**
     * 处理工作流
     */
    private List<FlowsNode> nodeTree;

    /**
     * 处理记录
     */

}
