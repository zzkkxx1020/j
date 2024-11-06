package org.example.jworker.service.impl;

import org.example.jworker.model.entity.TaskNodeState;
import org.example.jworker.dao.TaskNodeStateMapper;
import org.example.jworker.service.ITaskNodeStateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 节点处理状态 服务实现类
 * </p>
 *
 * @author zzkkxx
 * @since 2024-11-05
 */
@Service
public class TaskNodeStateServiceImpl extends ServiceImpl<TaskNodeStateMapper, TaskNodeState> implements ITaskNodeStateService {

}
