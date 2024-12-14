package org.example.jworker.model.vo;

import lombok.Data;
import org.example.jworker.model.entity.FlowsTask;

import java.util.List;

@Data
public class FlowsTaskShowVO{

    /**
     * 任务详情
     */
    private FlowsTask flowsTask;

    /**
     * 处理记录
     */

    private List<FlowsNodeVO> flowsNodeVOs;
}
