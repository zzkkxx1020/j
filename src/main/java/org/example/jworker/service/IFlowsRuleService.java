package org.example.jworker.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.jworker.common.R.Result;
import org.example.jworker.model.entity.FlowsRule;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.jworker.model.query.FlowsRuleQuery;

/**
 * <p>
 * 工作流规则主表 服务类
 * </p>
 *
 * @author zzkkxx
 * @since 2024-11-05
 */
public interface IFlowsRuleService extends IService<FlowsRule> {

    /**
     * 新增规则
     */
    boolean addRule(FlowsRule flowsRule);

    /**
     * 删除规则
     */
    boolean deleteRule(Integer id);

    /**
     * 更新规则
     */
    boolean updateRule(FlowsRule flowsRule);

    /**
     * 根据id获取指定数据
     */
    FlowsRule getRuleById(Integer id);

    /**
     * 分页获取数据
     */
    IPage<FlowsRule> getRuleList(FlowsRuleQuery flowRuleQuery);


}
