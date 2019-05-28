package com.github.test.controller;

import com.github.test.JsonResult;
import com.github.zw.log.LoggerBean;
import com.github.zw.log.LoggerManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class LogController {

    @RequestMapping("/get")
    public JsonResult get(){
        return JsonResult.success(LoggerManager.getLogConfig());
    }

    @RequestMapping("/set")
    public JsonResult set(LoggerBean loggerBean){
        return JsonResult.success(LoggerManager.setLogLevel(loggerBean));
    }
}
