package com.bee.scheduler.consolenode.service.impl;

import com.bee.scheduler.consolenode.dao.DaoSupport;
import com.bee.scheduler.consolenode.model.ClusterSchedulerNode;
import com.bee.scheduler.consolenode.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    @Autowired
    private DaoSupport dao;

    @Override
    public List<ClusterSchedulerNode> getAllClusterScheduler(String schedulerName) {
        return dao.getClusterSchedulerNodes(schedulerName);
    }
}
