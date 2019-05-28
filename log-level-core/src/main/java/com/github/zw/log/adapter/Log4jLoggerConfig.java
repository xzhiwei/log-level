package com.github.zw.log.adapter;

import com.github.zw.log.LoggerBean;
import com.github.zw.log.LoggerConfigAdapter;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

public class Log4jLoggerConfig implements LoggerConfigAdapter {

    @Override
    public Map<String, Object> getLoggerConfig() {
        Map<String, Object> loggerMap = new HashMap<>();
        Enumeration enumeration = org.apache.log4j.LogManager.getCurrentLoggers();
        while (enumeration.hasMoreElements()) {
            org.apache.log4j.Logger logger = (org.apache.log4j.Logger) enumeration.nextElement();
            if (logger.getLevel() != null) {
                loggerMap.put(logger.getName(), logger);
            }
        }
        org.apache.log4j.Logger rootLogger = org.apache.log4j.LogManager.getRootLogger();
        loggerMap.put(rootLogger.getName(), rootLogger);
        return loggerMap;
    }

    @Override
    public List<LoggerBean> getLoggerList() {
        List<LoggerBean> loggerBeans = new ArrayList<>();
        for (Map.Entry<String, Object> entry : getLoggerConfig().entrySet()) {
            LoggerBean bean = new LoggerBean();
            bean.setName(entry.getKey());
                org.apache.log4j.Logger targetLogger = (org.apache.log4j.Logger) entry.getValue();
                bean.setLevel(targetLogger.getLevel().toString());
            loggerBeans.add(bean);
        }
        return loggerBeans;
    }

    @Override
    public Map<String, String> setLogLevel(List<LoggerBean> data) {
        Map<String, String> map = new HashMap<>();
        if(CollectionUtils.isNotEmpty(data)){
            for(LoggerBean loggerBean:data){
                map.put(loggerBean.getName(),setLogLevel(loggerBean));
            }
        }
        return map;
    }

    @Override
    public String setLogLevel(LoggerBean loggerbean) {
        org.apache.log4j.Logger logger = org.apache.log4j.LogManager.getLogger(loggerbean.getName());
        if (logger == null) {
            return "set level: " + loggerbean.getLevel()+ " error,需要修改日志级别的Logger不存在";
        }
        org.apache.log4j.Level targetLevel = org.apache.log4j.Level.toLevel(loggerbean.getLevel());
        logger.setLevel(targetLevel);
        return "set level: " + targetLevel.toString()+ " success";
    }
}
