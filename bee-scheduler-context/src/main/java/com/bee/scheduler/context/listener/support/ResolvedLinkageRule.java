package com.bee.scheduler.context.listener.support;

import com.alibaba.fastjson.JSONObject;
import com.bee.scheduler.context.model.TaskConfig;

/**
 * @author weiwei
 */
public class ResolvedLinkageRule {
    public enum Mode {Create, Trigger}

    private Mode mode;
    private String taskGroup;
    private String taskName;
    private TaskConfig taskConfig;
    private Integer delay;
    private String next;
    private Boolean condition;
    private JSONObject exports;

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public String getTaskGroup() {
        return taskGroup;
    }

    public void setTaskGroup(String taskGroup) {
        this.taskGroup = taskGroup;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public TaskConfig getTaskConfig() {
        return taskConfig;
    }

    public void setTaskConfig(TaskConfig taskConfig) {
        this.taskConfig = taskConfig;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public Boolean getCondition() {
        return condition;
    }

    public void setCondition(Boolean condition) {
        this.condition = condition;
    }

    public JSONObject getExports() {
        return exports;
    }

    public void setExports(JSONObject exports) {
        this.exports = exports;
    }

}
