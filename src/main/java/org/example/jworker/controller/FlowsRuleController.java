package org.example.jworker.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.jworker.common.R.Result;
import org.example.jworker.model.entity.FlowsRule;
import org.example.jworker.model.query.FlowsRuleQuery;
import org.example.jworker.service.IFlowsRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 工作流规则主表 前端控制器
 * </p>
 *
 * @author zzkkxx
 * @since 2024-11-05
 */
@Api("工作流规则")
@RestController
@RequestMapping("/flowsRule")
public class FlowsRuleController {

    @Autowired
    private IFlowsRuleService iFlowsRuleService;


    /**
     * 新增规则
     */
    @ApiOperation("新增工作流规则")
    @PostMapping("/addRule")
    public Result<Boolean> addRule(@RequestBody FlowsRule flowsRule) {
        boolean b = iFlowsRuleService.addRule(flowsRule);
        return Result.judge(b);
    }

    /**
     * 删除规则
     */
    @ApiOperation("删除工作流规则")
    @PostMapping("/deleteRule")
    public Result<Boolean> deleteRule(Integer id) {
        boolean b = iFlowsRuleService.deleteRule(id);
        if (!b){
            return Result.failed("存在任务 无法删除.");
        }
        return Result.success();
    }

    /**
     * 更新规则
     */
    @ApiOperation("更新工作流规则")
    @PostMapping("/updateRule")
    public Result<Boolean> updateRule(@RequestBody FlowsRule flowsRule) {
        boolean b = iFlowsRuleService.updateRule(flowsRule);
        if (!b){
            return Result.failed("存在任务 无法更新.");
        }
        return Result.success();
    }

    /**
     * 查询规则
     */
    @ApiOperation("查询工作流规则")
    @GetMapping("/getRuleById")
    public Result<FlowsRule> getRuleById(Integer id) {
        FlowsRule ruleById = iFlowsRuleService.getRuleById(id);
        return Result.success(ruleById);
    }

    @ApiOperation("查询规则列表")
    @PostMapping("/getRuleList")
    public Result<Object> getRuleList(@RequestBody FlowsRuleQuery flowRuleQuery) {
        return Result.success(iFlowsRuleService.getRuleList(flowRuleQuery));
    }

}
