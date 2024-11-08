package org.example.jworker.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.bytebuddy.asm.Advice;
import org.example.jworker.common.constant.FlowsTaskEnum;
import org.example.jworker.common.util.SysUtil;
import org.example.jworker.model.entity.FlowsNode;
import org.example.jworker.model.entity.FlowsRule;
import org.example.jworker.model.entity.FlowsTask;
import org.example.jworker.dao.FlowsTaskMapper;
import org.example.jworker.model.query.FlowsTaskHandle;
import org.example.jworker.model.query.FlowsTaskQuery;
import org.example.jworker.model.vo.FlowsTaskShowVO;
import org.example.jworker.service.IFlowsNodeService;
import org.example.jworker.service.IFlowsTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
        FlowsTask flowsTask = this.getOne(new LambdaQueryWrapper<FlowsTask>().eq(FlowsTask::getId, flowTaskHandle.getId()).eq(FlowsTask::getStatus, FlowsTaskEnum.TASK_STATUS_EXECUTE.getId()), Boolean.FALSE);
        if (flowsTask == null){
            throw new RuntimeException("任务不存在...");
        }
        // 处理任务
        // 通过 推送下一节点的处理人
        // 拒绝


        return false;
    }

    @Override
    public IPage<FlowsTask> getTaskList(FlowsTaskQuery flowTaskQuery) {
        Page<FlowsTask> page = new Page<>(flowTaskQuery.getPageNumber(), flowTaskQuery.getPageSize());
        // TODO: 需要条件可以再补充
        return this.page(page);
    }

    @Override
    public FlowsTaskShowVO getTaskById(Integer id) {

        return null;
    }


}
