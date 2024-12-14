package org.example.jworker.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.jworker.common.constant.FlowsTaskEnum;
import org.example.jworker.common.util.SysUtil;
import org.example.jworker.dao.FlowsTaskMapper;
import org.example.jworker.model.entity.FlowsNode;
import org.example.jworker.model.entity.FlowsTask;
import org.example.jworker.model.entity.TaskNodeState;
import org.example.jworker.model.query.FlowsTaskHandle;
import org.example.jworker.model.query.FlowsTaskQuery;
import org.example.jworker.model.vo.FlowsNodeVO;
import org.example.jworker.model.vo.FlowsTaskShowVO;
import org.example.jworker.service.IFlowsNodeService;
import org.example.jworker.service.IFlowsTaskService;
import org.example.jworker.service.ITaskNodeStateService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 任务表 服务实现类
 * </p>
 *
 * @author zzkkxx
 * @since 2024-11-05
 */
@Service
public class FlowsTaskServiceImpl extends ServiceImpl<FlowsTaskMapper, FlowsTask> implements IFlowsTaskService {

    @Autowired
    private IFlowsNodeService iFlowsNodeService;

    @Autowired
    private ITaskNodeStateService iTasksService;

    @Override
    public boolean addTask(FlowsTask flowsTask) {
        flowsTask.setId(SysUtil.getId());
        flowsTask.setStatus(FlowsTaskEnum.TASK_STATUS_EXECUTE.getId());
        FlowsNode node = iFlowsNodeService.getOne(new LambdaQueryWrapper<FlowsNode>().eq(FlowsNode::getFlowsRuleId, flowsTask.getFlowsRuleId()).isNull(FlowsNode::getBeforeNodeId));
        flowsTask.setStepNodeId(node.getId());
        flowsTask.setTaskDel(FlowsTaskEnum.TASK_DEL_OFF.getId());
        flowsTask.setCreateTime(LocalDateTime.now());
        flowsTask.setUpdateTime(LocalDateTime.now());
        return this.save(flowsTask);
    }

    @Override
    public boolean cancelTask(Integer id) {
        boolean update = this.update(new LambdaUpdateWrapper<FlowsTask>().eq(FlowsTask::getFlowsRuleId, id).set(FlowsTask::getTaskDel, FlowsTaskEnum.TASK_DEL_ON.getId()));
        // TODO: 记录日志  后面看看怎么写吧
        return update;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean handleTask(FlowsTaskHandle flowTaskHandle) {
        FlowsTask flowsTask = this.getOne(new LambdaQueryWrapper<FlowsTask>().eq(FlowsTask::getId, flowTaskHandle.getId()), Boolean.FALSE);
        if (flowsTask == null){
            throw new RuntimeException("任务不存在...");
        }
        if (flowsTask.getStatus().equals(FlowsTaskEnum.TASK_STATUS_END.getId())){
            throw new RuntimeException("任务已结束");
        }
        FlowsNode node = iFlowsNodeService.getNodeById(flowsTask.getStepNodeId());
        // 处理任务
        if (flowTaskHandle.getStatus().equals(FlowsTaskEnum.TASK_PASS.getId())){
            // 通过 推送下一节点的处理人
            if (node.getNodeType().equals(FlowsTaskEnum.NODE_TYPE0.getId())){
                // 会签 节点全部处理人都要处理 有一人拒绝则本节点处理结束 todo: 暂时处理本任务结束
                List<String> list = iTasksService.listObjs(new LambdaQueryWrapper<TaskNodeState>().select(TaskNodeState::getUserId)
                        .eq(TaskNodeState::getTaskId, flowTaskHandle.getId())
                        .eq(TaskNodeState::getNodeId, node.getId()));
                node.getAuditId().removeAll(list);
                if (node.getAuditId().size()==1 && node.getAuditId().get(0).equals(flowTaskHandle.getUserId())){
                    // 最后一位处理
                    TaskNodeState taskNodeState = new TaskNodeState();
                    taskNodeState.setTaskId(flowTaskHandle.getId());
                    taskNodeState.setNodeId(node.getId());
                    taskNodeState.setUserId(flowTaskHandle.getUserId());
                    taskNodeState.setNodeStatus(FlowsTaskEnum.TASK_PASS.getId());
                    iTasksService.save(taskNodeState);
                    // 本节点处理结束 记录日志 给下节点处理人推送消息 修改节点状态
                    if (node.getNextNodeId()!=null) {
                        flowsTask.setStepNodeId(node.getNextNodeId());
                        flowsTask.setUpdateTime(LocalDateTime.now());
                        this.updateById(flowsTask);

                    }else {
                        flowsTask.setStatus(FlowsTaskEnum.TASK_STATUS_END.getId());
                        flowsTask.setUpdateTime(LocalDateTime.now());
                        flowsTask.setStepNodeId(null);
                        this.updateById(flowsTask);
                    }
                    // 推送消息
                }else {
                    // 中间处理
                    TaskNodeState taskNodeState = new TaskNodeState();
                    taskNodeState.setTaskId(flowTaskHandle.getId());
                    taskNodeState.setNodeId(node.getId());
                    taskNodeState.setUserId(flowTaskHandle.getUserId());
                    taskNodeState.setNodeStatus(FlowsTaskEnum.TASK_PASS.getId());
                    iTasksService.save(taskNodeState);
                }
            }else if(node.getNodeType().equals(FlowsTaskEnum.NODE_TYPE1.getId())){
                // 或签 一人处理即可
                TaskNodeState taskNodeState = new TaskNodeState();
                taskNodeState.setTaskId(flowTaskHandle.getId());
                taskNodeState.setNodeId(node.getId());
                taskNodeState.setUserId(flowTaskHandle.getUserId());
                taskNodeState.setNodeStatus(FlowsTaskEnum.TASK_PASS.getId());
                iTasksService.save(taskNodeState);
                // 本节点处理结束 记录日志 给下节点处理人推送消息 修改节点状态
                if (node.getNextNodeId()!=null) {
                    flowsTask.setStepNodeId(node.getNextNodeId());
                    flowsTask.setUpdateTime(LocalDateTime.now());
                    this.updateById(flowsTask);

                }else {
                    flowsTask.setStatus(FlowsTaskEnum.TASK_STATUS_END.getId());
                    flowsTask.setUpdateTime(LocalDateTime.now());
                    flowsTask.setStepNodeId(null);
                    this.updateById(flowsTask);
                }

                // 推送消息
            }
        }else if (flowTaskHandle.getStatus().equals(FlowsTaskEnum.TASK_REJECTED.getId())){
            TaskNodeState taskNodeState = new TaskNodeState();
            taskNodeState.setTaskId(flowTaskHandle.getId());
            taskNodeState.setNodeId(node.getId());
            taskNodeState.setUserId(flowTaskHandle.getUserId());
            iTasksService.save(taskNodeState);
            flowsTask.setStatus(FlowsTaskEnum.TASK_STATUS_END.getId());
            flowsTask.setUpdateTime(LocalDateTime.now());
            flowsTask.setStepNodeId(null);
            this.updateById(flowsTask);
            // 推送消息
        }
        return true;
    }

    @Override
    public IPage<FlowsTask> getTaskList(FlowsTaskQuery flowTaskQuery) {
        Page<FlowsTask> page = new Page<>(flowTaskQuery.getPageNumber(), flowTaskQuery.getPageSize());
        // TODO: 需要条件可以再补充
        return this.page(page);
    }

    @Override
    public FlowsTaskShowVO getTaskById(Integer id) {
        FlowsTaskShowVO flowsTaskShowVO = new FlowsTaskShowVO();
        FlowsTask flowsTask = this.getById(id);
        flowsTaskShowVO.setFlowsTask(flowsTask);
        List<FlowsNode> listByRuleId = iFlowsNodeService.getNodeListByRuleId(flowsTask.getFlowsRuleId());
        List<FlowsNodeVO> flowsNodeVOS = new ArrayList<>();
        for (FlowsNode flowsNode : listByRuleId) {
            FlowsNodeVO flowsNodeVO = new FlowsNodeVO();
            BeanUtils.copyProperties(flowsNode,flowsNodeVO);
            LambdaQueryWrapper<TaskNodeState> wrapper = new LambdaQueryWrapper<TaskNodeState>()
                    .eq(TaskNodeState::getTaskId, flowsTask.getId())
                    .eq(TaskNodeState::getNodeId, flowsNode.getId()).orderByDesc(TaskNodeState::getCreateTime);
            List<TaskNodeState> list = iTasksService.list(wrapper);
            if (!list.isEmpty()){
                List<FlowsNodeVO.NodeDisposal> nodeDisposals = new ArrayList<>();
                for (TaskNodeState nodeState : list) {
                    FlowsNodeVO.NodeDisposal nodeDisposal = new FlowsNodeVO.NodeDisposal();
                    nodeDisposal.setDisposalId(nodeState.getUserId());
                    nodeDisposal.setDisposalTime(nodeState.getCreateTime());
                    nodeDisposal.setNodeStatus(nodeState.getNodeStatus());
                    nodeDisposals.add(nodeDisposal);
                }
                flowsNodeVO.setNodeDisposals(nodeDisposals);
            }
            flowsNodeVOS.add(flowsNodeVO);
            if (flowsTask.getStepNodeId()!=null && flowsNode.getId().equals(flowsTask.getStepNodeId())){
                continue;
            }
        }
        flowsTaskShowVO.setFlowsNodeVOs(flowsNodeVOS);
        return flowsTaskShowVO;
    }


}
