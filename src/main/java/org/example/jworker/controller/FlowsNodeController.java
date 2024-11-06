package org.example.jworker.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.jworker.common.R.Result;
import org.example.jworker.model.entity.FlowsNode;
import org.example.jworker.service.IFlowsNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 工作流节点表 前端控制器
 * </p>
 *
 * @author zzkkxx
 * @since 2024-11-05
 */
@Api("工作流节点")
@RestController
@RequestMapping("/flowsNode")
public class FlowsNodeController {

    @Autowired
    private IFlowsNodeService flowsNodeService;


    /**
     * 新增节点
     */
    @ApiOperation("新增工作流节点")
    @PostMapping(value = "/addNode")
    public Result<Boolean> addNode(@RequestBody FlowsNode flowsNode) {
        if (flowsNode.getAuditId().isEmpty()){
            return Result.failed("节点审核人id不可为空");
        }
        return Result.judge(flowsNodeService.addNode(flowsNode));
    }

    /**
     * 删除节点
     */
    @ApiOperation("删除工作流节点")
    @PostMapping(value = "/deleteNode")
    public Result<Boolean> deleteNode(Integer id) {
        boolean deleted = flowsNodeService.deleteNode(id);
        if (deleted){
            return Result.success();
        }else {
            return Result.failed("该节点存在任务");
        }
    }

    /**
     * 修改工作流节点
     */
    @ApiOperation(value = "更新工作流节点",notes = "支持批量更新")
    @PostMapping(value = "/updateNode")
    public Result<Boolean> updateNode(@RequestBody List<FlowsNode> flowsNode) {
        try {
            boolean updateNode = flowsNodeService.updateNode(flowsNode);
            return Result.judge(updateNode);
        } catch (Exception e) {
            return Result.failed(e.getMessage());
        }
    }

    /**
     * 根据id查询节点
     */
    @ApiOperation("根据id查询节点")
    @GetMapping(value = "/getNodeById")
    public Result<FlowsNode> getNodeById(Integer id) {
        FlowsNode nodeById = flowsNodeService.getNodeById(id);
        return Result.success(nodeById);
    }

    /**
     * 根据规则id查询节点列表
     */
    @ApiOperation("根据规则id查询节点列表")
    @GetMapping(value = "/getNodeListByRuleId")
    public Result<Object> getNodeListByRuleId(Integer ruleId) {
        List<FlowsNode> nodeListByRuleId = flowsNodeService.getNodeListByRuleId(ruleId);
        return Result.success(nodeListByRuleId);
    }
}
