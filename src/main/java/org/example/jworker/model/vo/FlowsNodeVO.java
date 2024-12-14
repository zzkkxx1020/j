package org.example.jworker.model.vo;

import lombok.Data;
import org.example.jworker.model.entity.FlowsNode;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FlowsNodeVO extends FlowsNode {

    /**
     * 节点处理结果
     */
    private Integer nodeStatus;

    /**
     * 节点处理集合
     */
    private List<NodeDisposal> nodeDisposals;

    @Data
    public static class NodeDisposal {
        /*
         * 处理人id
         */
        private String disposalId;


        /**
         * 处理时间
         */
        private LocalDateTime disposalTime;

        /**
         * 节点处理结果
         */
        private Integer nodeStatus;
    }
}
