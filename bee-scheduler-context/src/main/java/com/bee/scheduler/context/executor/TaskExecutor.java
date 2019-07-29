package com.bee.scheduler.context.executor;

import com.alibaba.fastjson.JSONObject;
import com.bee.scheduler.context.TaskExecutionContextUtil;
import com.bee.scheduler.context.exception.TaskModuleNotFountException;
import com.bee.scheduler.core.AbstractTaskModule;
import com.bee.scheduler.core.TaskExecutionContext;
import com.bee.scheduler.core.TaskExecutionLogger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author weiwei
 */
public class TaskExecutor implements Job {
    private Log logger = LogFactory.getLog(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        boolean execSuccess = true;
        JobDetail jobDetail = context.getJobDetail();

        TaskExecutionContext taskExecutionContext = TaskExecutionContextUtil.convert(context);
        TaskExecutionLogger taskLogger = taskExecutionContext.getLogger();
        JSONObject taskParam = taskExecutionContext.getParam();
        try {
            logger.info("开始执行任务:" + jobDetail.getKey() + ",参数:" + taskParam.toString());
            taskLogger.info("开始执行任务 -> " + jobDetail.getKey());
            taskLogger.info("任务参数 -> " + taskParam.toString());
            AbstractTaskModule taskModule = TaskModuleRegistry.get(taskExecutionContext.getTaskModuleId());
            if (taskModule == null) {
                throw new TaskModuleNotFountException();
            }
            execSuccess = taskModule.run(taskExecutionContext);
        } catch (TaskModuleNotFountException e) {
            execSuccess = false;
            logger.error("任务[" + jobDetail.getKey() + "]未找到组件:" + taskExecutionContext.getTaskModuleId());
            taskLogger.error("任务[" + jobDetail.getKey() + "]未找到组件:" + taskExecutionContext.getTaskModuleId());
            throw new JobExecutionException("未找到组件:" + taskExecutionContext.getTaskModuleId());
        } catch (Throwable e) {
            execSuccess = false;
            logger.error("任务[" + jobDetail.getKey() + "]异常 -> " + e.getMessage(), e);
            taskLogger.error("执行任务异常 -> " + e.getMessage(), e);
            throw new JobExecutionException(e);
        } finally {
            logger.info("任务[" + jobDetail.getKey() + "]执行" + (execSuccess ? "成功" : "失败!"));
            taskLogger.info("执行任务结束 -> " + (execSuccess ? "成功" : "失败"));
        }
    }
}