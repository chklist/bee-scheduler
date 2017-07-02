package com.bee.lemon.web;

import com.bee.lemon.model.HttpResponseBodyWrapper;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class SettingsController {
    @Autowired
    private Scheduler scheduler;

    @ResponseBody
    @GetMapping("/settings")
    HttpResponseBodyWrapper settings() throws Exception {
        HashMap<Object, Object> model = new HashMap<>();
        return new HttpResponseBodyWrapper(scheduler.getMetaData());
    }
}