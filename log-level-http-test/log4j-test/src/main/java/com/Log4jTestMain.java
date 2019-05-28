package com;

import com.alibaba.fastjson.JSONObject;
import com.github.zw.log.LoggerManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Log4jTestMain {
    public static void main(String[] args) {
        SpringApplication.run(Log4jTestMain.class, args);
        System.out.println(JSONObject.toJSONString(LoggerManager.getLogConfig()));
    }
}
