package org.example.jworker.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.jworker.common.R.Result;
import org.example.jworker.model.entity.FlowsNode;
import org.example.jworker.model.entity.FlowsRule;
import org.example.jworker.dao.FlowsRuleMapper;
import org.example.jworker.model.entity.FlowsTask;
import org.example.jworker.model.query.FlowsRuleQuery;
import org.example.jworker.service.IFlowsNodeService;
import org.example.jworker.service.IFlowsRuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.jworker.service.IFlowsTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 工作流规则主表 服务实现类
 * </p>
 *
 * @author zzkkxx
 * @since 2024-11-05
 */
@Service
public class FlowsRuleServiceImpl extends ServiceImpl<FlowsRuleMapper, FlowsRule> implements IFlowsRuleService {

    @Autowired
    private IFlowsTaskService iFlowsTaskService;

    @Autowired
    private IFlowsNodeService iFlowsNodeService;

    @Override
    public boolean addRule(FlowsRule flowsRule) {
        flowsRule.setCreateTime(LocalDateTime.now());
        flowsRule.setUpdateTime(LocalDateTime.now());
        return this.save(flowsRule);
    }

    @Override
    public boolean deleteRule(Integer id) {
        long count = iFlowsTaskService.count(new QueryWrapper<FlowsTask>().lambda().eq(FlowsTask::getFlowsRuleId, id));
        boolean remove;
        if (count == 0){
            // 不存在任务 可以删除
            iFlowsNodeService.remove(new QueryWrapper<FlowsNode>().lambda().eq(FlowsNode::getFlowsRuleId, id));
            remove = this.removeById(id);
        }else {
            // 存在任务无法删除
            remove = Boolean.FALSE;
        }
        return remove;
    }

    @Override
    public boolean updateRule(FlowsRule flowsRule) {
        long count = iFlowsTaskService.count(new QueryWrapper<FlowsTask>().lambda().eq(FlowsTask::getFlowsRuleId, flowsRule.getId()));
        boolean tag;
        if (count == 0){
            // 不存在任务 可以修改
            tag = this.updateById(flowsRule);
        }else {
            // 存在任务无法修改
            tag = Boolean.FALSE;
        }
        return tag;
    }

    @Override
    public FlowsRule getRuleById(Integer id) {
        return this.getById(id);
    }

    @Override
    public IPage<FlowsRule> getRuleList(FlowsRuleQuery flowRuleQuery) {
        Page<FlowsRule> page = new Page<>(flowRuleQuery.getPageNumber(), flowRuleQuery.getPageSize());
        IPage<FlowsRule> flowsRulePage;
        if (StrUtil.isBlank(flowRuleQuery.getSearch())) {
            flowsRulePage = this.page(page, new LambdaQueryWrapper<FlowsRule>().like(FlowsRule::getFlowsName, flowRuleQuery.getSearch()).orderByDesc(FlowsRule::getCreateTime));
        }else {
            flowsRulePage = this.page(page,new LambdaQueryWrapper<FlowsRule>().orderByDesc(FlowsRule::getCreateTime));
        }
        return flowsRulePage;
    }
}
