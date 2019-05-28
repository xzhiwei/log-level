package com.github.zw.log.adapter;

import com.github.zw.log.LoggerBean;
import com.github.zw.log.LoggerConfigAdapter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Log4j2LoggerConfig implements LoggerConfigAdapter {

    @Override
    public Map<String, Object> getLoggerConfig() {
        Map<String, Object> loggerMap = new HashMap<>();

        org.apache.logging.log4j.core.LoggerContext loggerContext = (org.apache.logging.log4j.core.LoggerContext) org.apache.logging.log4j.LogManager.getContext(false);
        Map<String, org.apache.logging.log4j.core.config.LoggerConfig> map = loggerContext.getConfiguration().getLoggers();
        for (org.apache.logging.log4j.core.config.LoggerConfig loggerConfig : map.values()) {
            String key = loggerConfig.getName();
            if (StringUtils.isBlank(key)) {
                key = "root";
            }
            loggerMap.put(key, loggerConfig);
        }
        return loggerMap;
    }

    @Override
    public List<LoggerBean> getLoggerList() {
        List<LoggerBean> loggerBeans = new ArrayList<>();
        for (Map.Entry<String, Object> entry : getLoggerConfig().entrySet()) {
            LoggerBean bean = new LoggerBean();
            bean.setName(entry.getKey());
            org.apache.logging.log4j.core.config.LoggerConfig targetLogger = (org.apache.logging.log4j.core.config.LoggerConfig) entry.getValue();
            bean.setLevel(targetLogger.getLevel().toString());
            loggerBeans.add(bean);
        }
        return loggerBeans;
    }

    @Override
    public Map<String, String> setLogLevel(List<LoggerBean> data) {
        Map<String,String> map = new HashMap<>();
        org.apache.logging.log4j.core.LoggerContext logContext = (org.apache.logging.log4j.core.LoggerContext) org.apache.logging.log4j.LogManager.getContext(false);
        for(LoggerBean loggerbean:data){
            org.apache.logging.log4j.core.config.LoggerConfig loggerConfig = logContext.getConfiguration().getLoggers().get(loggerbean.getName());
            if (loggerConfig != null) {
                org.apache.logging.log4j.Level targetLevel = org.apache.logging.log4j.Level.toLevel(loggerbean.getLevel());
                loggerConfig.setLevel(targetLevel);
            } else {
                org.apache.logging.log4j.core.config.Configuration configuration = logContext.getConfiguration();
                loggerConfig = new org.apache.logging.log4j.core.config.LoggerConfig(loggerbean.getName(), org.apache.logging.log4j.Level.getLevel(loggerbean.getLevel()), false);
                configuration.addLogger(loggerbean.getName(), loggerConfig);
                org.apache.logging.log4j.core.config.LoggerConfig parentConfig = loggerConfig.getParent();
                do {
                    for (Map.Entry<String, org.apache.logging.log4j.core.Appender> entry : parentConfig.getAppenders().entrySet()) {
                        loggerConfig.addAppender(entry.getValue(), null, null);
                    }
                    parentConfig = parentConfig.getParent();
                } while (null != parentConfig && parentConfig.isAdditive());
            }
            map.put(loggerbean.getName(),"set level: " + loggerbean.getLevel() + " success");
        }
        logContext.updateLoggers(); // This causes all Loggers to refetch information from their LoggerConfig.
       return map;
    }

    @Override
    public String setLogLevel(LoggerBean loggerbean) {
        org.apache.logging.log4j.core.LoggerContext logContext = (org.apache.logging.log4j.core.LoggerContext) org.apache.logging.log4j.LogManager.getContext(false);
        org.apache.logging.log4j.core.config.LoggerConfig loggerConfig = logContext.getConfiguration().getLoggers().get(loggerbean.getName());
        if (loggerConfig != null) {
            org.apache.logging.log4j.Level targetLevel = org.apache.logging.log4j.Level.toLevel(loggerbean.getLevel());
            loggerConfig.setLevel(targetLevel);
        } else {
            org.apache.logging.log4j.core.config.Configuration configuration = logContext.getConfiguration();
            loggerConfig = new org.apache.logging.log4j.core.config.LoggerConfig(loggerbean.getName(), org.apache.logging.log4j.Level.getLevel(loggerbean.getLevel()), false);
            configuration.addLogger(loggerbean.getName(), loggerConfig);
            org.apache.logging.log4j.core.config.LoggerConfig parentConfig = loggerConfig.getParent();
            do {
                for (Map.Entry<String, org.apache.logging.log4j.core.Appender> entry : parentConfig.getAppenders().entrySet()) {
                    loggerConfig.addAppender(entry.getValue(), null, null);
                }
                parentConfig = parentConfig.getParent();
            } while (null != parentConfig && parentConfig.isAdditive());
        }
        logContext.updateLoggers(); // This causes all Loggers to refetch information from their LoggerConfig.
        return "set level: " + loggerbean.getLevel() + " success";
    }
}
