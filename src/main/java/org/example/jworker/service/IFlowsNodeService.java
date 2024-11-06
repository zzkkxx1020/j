package org.example.jworker.service;

import org.example.jworker.model.entity.FlowsNode;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 工作流节点表 服务类
 * </p>
 *
 * @author zzkkxx
 * @since 2024-11-05
 */
public interface IFlowsNodeService extends IService<FlowsNode> {

    /**
     * 新增节点
     */
    boolean addNode(FlowsNode flowsNode);

    /**
     * 删除节点
     */
    boolean deleteNode(Integer id);

    /**
     * 修改节点
     */
    boolean updateNode(List<FlowsNode> flowsNode);

    /**
     * 根据id查找节点
     */
    FlowsNode getNodeById(Integer id);

    /**
     * 根据规则id查询节点列表
     */
    List<FlowsNode> getNodeListByRuleId(Integer ruleId);
}
