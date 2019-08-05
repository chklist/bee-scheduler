package com.bee.scheduler.context.taskmodule;

import com.alibaba.fastjson.JSONObject;
import com.bee.scheduler.core.AbstractTaskModule;
import com.bee.scheduler.core.TaskExecutionContext;
import com.bee.scheduler.core.TaskExecutionResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author weiwei 该组件提供运行服务端脚本的功能
 */
public class ShellTaskModule extends AbstractTaskModule {
    private Log logger = LogFactory.getLog(ShellTaskModule.class);

    @Override
    public String getId() {
        return "ShellTaskModule";
    }

    @Override
    public String getName() {
        return "ShellTaskModule";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String getAuthor() {
        return "weiwei";
    }

    @Override
    public String getDescription() {
        return "该组件提供运行服务端脚本的功能";
    }

    @Override
    public String getParamTemplate() {
        return "{\r" +
                "    shell:''\r" +
                "}";
    }

    @Override
    public TaskExecutionResult run(TaskExecutionContext context) throws Exception {
        JSONObject taskParam = context.getParam();

        String shell = taskParam.getString("shell");

        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(shell);
        InputStream stderr = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(stderr);
        BufferedReader br = new BufferedReader(isr);
        String line;
        StringBuilder back = new StringBuilder();
        while ((line = br.readLine()) != null) {
            back.append(line).append("\r");
        }
        JSONObject data = new JSONObject();
        data.put("echo", back.toString());
        return TaskExecutionResult.success(data);
    }
}