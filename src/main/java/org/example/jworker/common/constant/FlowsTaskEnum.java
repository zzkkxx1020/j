package org.example.jworker.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 任务执行使用枚举
 */
@Getter
@AllArgsConstructor
public enum FlowsTaskEnum {
    TASK_STATUS_EXECUTE(0,"执行中"),
    TASK_STATUS_END(1,"执行结束"),
    TASK_DEL_OFF(0,"正常提交"),
    TASK_DEL_ON(1,"撤销"),
    TASK_REJECTED(1,"拒绝"),
    TASK_PASS(0,"通过"),
    NODE_TYPE0(0,"会签"),
    NODE_TYPE1(1,"或签"),
    NODE_TYPE2(2,"执行"),



    ;
    private Integer id;
    private String name;

    FlowsTaskEnum(int i, String name) {
    }
}
