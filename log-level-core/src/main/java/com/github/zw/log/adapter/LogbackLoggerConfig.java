package com.github.zw.log.adapter;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.github.zw.log.LoggerBean;
import com.github.zw.log.LoggerConfigAdapter;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogbackLoggerConfig implements LoggerConfigAdapter {

    @Override
    public Map<String, Object> getLoggerConfig() {
    Map<String, Object> loggerMap = new HashMap<>();
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        for (Logger logger : loggerContext.getLoggerList()) {
            if (logger.getLevel() != null) {
                loggerMap.put(logger.getName(), logger);
            }
        }
        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        loggerMap.put(rootLogger.getName(), rootLogger);
        return loggerMap;
    }

    @Override
    public List<LoggerBean> getLoggerList() {
        List<LoggerBean> loggerBeans = new ArrayList<>();
        for (Map.Entry<String, Object> entry : getLoggerConfig().entrySet()) {
            LoggerBean bean = new LoggerBean();
            bean.setName(entry.getKey());
            Logger targetLogger = (Logger) entry.getValue();
            bean.setLevel(targetLogger.getLevel().toString());
            loggerBeans.add(bean);
        }
        return loggerBeans;
    }

    @Override
    public Map<String,String> setLogLevel(List<LoggerBean> loggerbeans) {
        Map<String,String> result = new HashMap<>();
        if(CollectionUtils.isNotEmpty(loggerbeans)){
            for (LoggerBean loggerbean : loggerbeans) {
                result.put(loggerbean.getName(), setLogLevel(loggerbean));
            }
        }
        return result;
    }

    @Override
    public String setLogLevel(LoggerBean loggerBean) {
        try {
            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            loggerContext.getLogger(loggerBean.getName()).setLevel(Level.toLevel(loggerBean.getLevel()));
        } catch (Exception e){
            return "set level: " + loggerBean.getLevel()+ " error : " + e.getMessage();
        }
        return "set level: " + loggerBean.getLevel()+ " success";
    }
}
