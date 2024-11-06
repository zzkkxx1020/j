package org.example.jworker.service.impl;

import org.example.jworker.model.entity.FlowsTask;
import org.example.jworker.dao.FlowsTaskMapper;
import org.example.jworker.service.IFlowsTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
