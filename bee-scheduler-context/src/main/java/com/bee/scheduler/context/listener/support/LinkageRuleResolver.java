package com.bee.scheduler.context.listener.support;

import com.alibaba.fastjson.JSONObject;
import com.bee.scheduler.context.model.TaskConfig;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class LinkageRuleResolver {
    private ExpressionParser elParser = new SpelExpressionParser();


    public ResolvedLinkageRule resolve(JSONObject linkageRule, JSONObject variables) {
        ResolvedLinkageRule result = new ResolvedLinkageRule();
        result.setMode(ResolvedLinkageRule.Mode.valueOf(linkageRule.getString("mode")));
        result.setDelay(linkageRule.getInteger("delay"));

        Object task = linkageRule.get("task");
        if (result.getMode() == ResolvedLinkageRule.Mode.Create) {
            if (!(task instanceof JSONObject)) {
                throw new RuntimeException("task must be json object when mode is create");
            }
            result.setTaskConfig(((JSONObject) task).toJavaObject(TaskConfig.class));
        } else if (result.getMode() == ResolvedLinkageRule.Mode.Trigger) {
            if (!(task instanceof String)) {
                throw new RuntimeException("task must be a task key when mode is trigger");
            }
            String[] taskKey = ((String) task).split(".");
            result.setTaskGroup(taskKey[0]);
            result.setTaskName(taskKey[1]);
        } else {
            throw new RuntimeException("mode can only be 'create' or 'trigger'");
        }

        EvaluationContext evaluationContext = new StandardEvaluationContext();
        if (variables != null) {
            variables.keySet().forEach(key -> {
                evaluationContext.setVariable(key, variables.get(key));
            });
        }

        String conditionEl = linkageRule.getString("condition");
        result.setConditionEl(conditionEl);
        
        Boolean condition = elParser.parseExpression(conditionEl).getValue(evaluationContext, Boolean.class);
        result.setCondition(condition);


        String exportsEl = linkageRule.getString("exports");
        JSONObject exports = elParser.parseExpression(exportsEl).getValue(evaluationContext, JSONObject.class);
        result.setExports(exports);

        return result;
    }


    public static void main(String[] args) {

        ExpressionParser elParser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("content", "hello");
        Expression expression = elParser.parseExpression("'aa'.length()");
        Object value = expression.getValue(context);
        System.out.println("value = " + value);


//        LinkageRuleResolver linkageRuleResolver = new LinkageRuleResolver();
//        JSONObject variables = new JSONObject();
//        variables.put("content", "helloworld");
//        String el = "{\n" +
//                "    \"mode\":\"Create\",\n" +
//                "    \"delay\":1000,\n" +
//                "    \"next\":\"[]\",\n" +
//                "    \"task\":{},\n" +
//                "    \"condition\":\"'#content'.length() > 2\",\n" +
//                "    \"exports\":\"{'aa':1,'bb':2}\"\n" +
//                "}";
//        ResolvedLinkageRule result = linkageRuleResolver.resolve(JSONObject.parseObject(el), variables);
//        System.out.println("result = " + result);
    }

}