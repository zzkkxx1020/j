package org.example.jworker.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.example.jworker.common.util.SysUtil;
import org.example.jworker.model.entity.FlowsNode;
import org.example.jworker.dao.FlowsNodeMapper;
import org.example.jworker.model.entity.FlowsTask;
import org.example.jworker.service.IFlowsNodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.jworker.service.IFlowsTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 工作流节点表 服务实现类
 * </p>
 *
 * @author zzkkxx
 * @since 2024-11-05
 */
@Service
public class FlowsNodeServiceImpl extends ServiceImpl<FlowsNodeMapper, FlowsNode> implements IFlowsNodeService {

    @Autowired
    private IFlowsTaskService iflowsTaskService;

    @Override
    public boolean addNode(FlowsNode flowsNode) {
        Integer id = SysUtil.getId();
        flowsNode.setCreateTime(LocalDateTime.now());
        flowsNode.setUpdateTime(LocalDateTime.now());
        boolean save;
        if (flowsNode.getBeforeNodeId() ==null){
            // 首节点
            flowsNode.setId(id);
            save = this.save(flowsNode);
        }else {
            // 已存在节点
            FlowsNode beforeNode = this.getById(flowsNode.getBeforeNodeId());
            beforeNode.setNextNodeId(id);
            this.updateById(beforeNode);
            flowsNode.setId(id);
            save = this.save(flowsNode);
        }
        return save;
    }

    @Override
    public boolean deleteNode(Integer id) {
        FlowsTask flowsTask = iflowsTaskService.getOne(new LambdaQueryWrapper<FlowsTask>().eq(FlowsTask::getStepNodeId, id),Boolean.FALSE);
        if (flowsTask==null){
            FlowsNode flowsNode = this.getById(id);
            if (flowsNode.getBeforeNodeId()==null && flowsNode.getNextNodeId() != null) {
                // 是首节点且有下一节点
                FlowsNode nextNode = this.getById(flowsNode.getNextNodeId());
                this.update(new UpdateWrapper<FlowsNode>().lambda().eq(FlowsNode::getId, nextNode.getId()).set(FlowsNode::getBeforeNodeId, null).set(FlowsNode::getUpdateTime, LocalDateTime.now()));
            }else if (flowsNode.getBeforeNodeId()!=null && flowsNode.getNextNodeId() !=null){
                // 是中间节点
                FlowsNode beforeNode = this.getById(flowsNode.getBeforeNodeId());
                FlowsNode nextNode = this.getById(flowsNode.getNextNodeId());
                beforeNode.setNextNodeId(nextNode.getId());
                beforeNode.setUpdateTime(LocalDateTime.now());
                nextNode.setBeforeNodeId(beforeNode.getId());
                nextNode.setUpdateTime(LocalDateTime.now());
                this.updateById(beforeNode);
                this.updateById(nextNode);
            } else if (flowsNode.getBeforeNodeId()!=null && flowsNode.getNextNodeId() ==null){
                // 是尾节点
                FlowsNode beforeNode = this.getById(flowsNode.getBeforeNodeId());
                this.update(new UpdateWrapper<FlowsNode>().lambda().eq(FlowsNode::getId,beforeNode.getId()).set(FlowsNode::getNextNodeId,null).set(FlowsNode::getUpdateTime, LocalDateTime.now()));
            }
            return this.removeById(id);
        }else {
            return Boolean.FALSE;
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean updateNode(List<FlowsNode> flowsNodes) {
        for (FlowsNode flowsNode : flowsNodes) {
            FlowsTask flowsTask = iflowsTaskService.getOne(new LambdaQueryWrapper<FlowsTask>().eq(FlowsTask::getStepNodeId, flowsNode.getId()),Boolean.FALSE);
            if (flowsTask==null){
                if (flowsNode.getUpdateType()==0){
                    // 更新顺序
                    if(this.deleteNode(flowsNode.getId())){
                        if(flowsNode.getBeforeNodeId()==null){
                            // 头节点更新
                            FlowsNode beforeNode = this.getOne(new LambdaQueryWrapper<FlowsNode>().eq(FlowsNode::getFlowsRuleId, flowsNode.getFlowsRuleId()).isNull(FlowsNode::getBeforeNodeId));
                            beforeNode.setBeforeNodeId(flowsNode.getId());
                            beforeNode.setUpdateTime(LocalDateTime.now());
                            this.updateById(beforeNode);
                        } else if (flowsNode.getBeforeNodeId() != null && flowsNode.getNextNodeId() != null) {
                            // 中间节点更新
                            FlowsNode beforeNode = this.getOne(new LambdaQueryWrapper<FlowsNode>().eq(FlowsNode::getFlowsRuleId, flowsNode.getFlowsRuleId()).eq(FlowsNode::getId, flowsNode.getBeforeNodeId()));
                            FlowsNode nextNode = this.getOne(new LambdaQueryWrapper<FlowsNode>().eq(FlowsNode::getFlowsRuleId, flowsNode.getFlowsRuleId()).eq(FlowsNode::getId, flowsNode.getNextNodeId()));
                            beforeNode.setNextNodeId(flowsNode.getId());
                            beforeNode.setUpdateTime(LocalDateTime.now());
                            nextNode.setBeforeNodeId(flowsNode.getId());
                            nextNode.setUpdateTime(LocalDateTime.now());
                            this.updateById(beforeNode);
                            this.updateById(nextNode);
                        } else if (flowsNode.getBeforeNodeId() != null && flowsNode.getNextNodeId()== null) {
                            // 尾节点更新
                            FlowsNode nextNode = this.getOne(new LambdaQueryWrapper<FlowsNode>().eq(FlowsNode::getFlowsRuleId, flowsNode.getFlowsRuleId()).isNull(FlowsNode::getNextNodeId));
                            nextNode.setNextNodeId(flowsNode.getId());
                            nextNode.setUpdateTime(LocalDateTime.now());
                            this.updateById(nextNode);
                        }
                        flowsNode.setUpdateTime(LocalDateTime.now());
                        this.save(flowsNode);
                    }else {
                        throw new RuntimeException("节点更新失败");
                    }
                }else {
                    // 不跟新顺序
                    flowsNode.setUpdateTime(LocalDateTime.now());
                    this.updateById(flowsNode);
                }
            }else {
                throw new RuntimeException("节点存在任务");
            }
        }
        return false;
    }

    @Override
    public FlowsNode getNodeById(Integer id) {
        return this.getById(id);
    }

    @Override
    public List<FlowsNode> getNodeListByRuleId(Integer ruleId) {
        List<FlowsNode> list = this.list(new LambdaQueryWrapper<FlowsNode>().eq(FlowsNode::getFlowsRuleId, ruleId));
        if (list.isEmpty()){
            return null;
        }else {
            return sortNode(list);
        }
    }


    /**
     * node排序
     * @param list
     * @return
     */
    private List<FlowsNode> sortNode(List<FlowsNode> list){
        // 步骤 1: 将 FlowsNode 存入 Map 中，键为 ID
        Map<Integer, FlowsNode> nodeMap = new HashMap<>();
        for (FlowsNode node : list) {
            nodeMap.put(node.getId(), node);
        }
        // 步骤 2: 找到起始节点（没有被其他节点引用的节点）
        Set<Integer> referencedIds = new HashSet<>();
        for (FlowsNode node : list) {
            if (node.getNextNodeId() != null) {
                referencedIds.add(node.getNextNodeId());
            }
        }
        FlowsNode startNode = null;
        for (FlowsNode node : list) {
            if (!referencedIds.contains(node.getId())) {
                startNode = node;
                break;
            }
        }
        // 步骤 3: 按链表顺序排序
        List<FlowsNode> sortedList = new ArrayList<>();
        while (startNode != null) {
            sortedList.add(startNode);
            startNode = nodeMap.get(startNode.getNextNodeId());
        }
        return sortedList;
    }
}
