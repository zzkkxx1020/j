package org.example.jworker.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.jworker.model.entity.FlowsTask;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.jworker.model.query.FlowsTaskHandle;
import org.example.jworker.model.query.FlowsTaskQuery;
import org.example.jworker.model.vo.FlowsTaskShowVO;

/**
 * <p>
 * 任务表 服务类
 * </p>
 *
 * @author zzkkxx
 * @since 2024-11-05
 */
public interface IFlowsTaskService extends IService<FlowsTask> {

    /**
     * 新增任务
     */
    public boolean addTask(FlowsTask flowsTask);

    /**
     * 撤销任务
     */
    public boolean cancelTask(Integer id);

    /**
     * 处理任务
     */
    public boolean handleTask(FlowsTaskHandle flowTaskHandle);

    /**
     * 分页查询任务列表
     */
    public IPage<FlowsTask> getTaskList(FlowsTaskQuery flowTaskQuery);

    /**
     * 产看任务详情
     */
    public FlowsTaskShowVO getTaskById(Integer id);

}
