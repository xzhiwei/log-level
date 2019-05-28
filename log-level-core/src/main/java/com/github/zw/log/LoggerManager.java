package com.github.zw.log;

import com.alibaba.fastjson.JSONObject;
import com.github.zw.log.adapter.Log4j2LoggerConfig;
import com.github.zw.log.adapter.Log4jLoggerConfig;
import com.github.zw.log.adapter.LogbackLoggerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoggerManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerManager.class);;

    private static String loggerType = LogType.UNKNOWN.name();

    private static Map<String,Class<? extends LoggerConfigAdapter>> LOGGER_ADAPTERS = new ConcurrentHashMap<>();

    private static LoggerConfigAdapter instance = null;

    static {
        getLoggerType();
        LOGGER_ADAPTERS.put(LogType.LOG4J.name(), Log4jLoggerConfig.class);
        LOGGER_ADAPTERS.put(LogType.LOG4J2.name(), Log4j2LoggerConfig.class);
        LOGGER_ADAPTERS.put(LogType.LOGBACK.name(), LogbackLoggerConfig.class);
    }

    private static void getLoggerType() {
        String type = StaticLoggerBinder.getSingleton().getLoggerFactoryClassStr();
        if (LogConstant.LOG4J_LOGGER_FACTORY.equalsIgnoreCase(type)) {
            loggerType = LogType.LOG4J.name();
        } else if (LogConstant.LOGBACK_LOGGER_FACTORY.equalsIgnoreCase(type)) {
            loggerType = LogType.LOGBACK.name();
        } else if (LogConstant.LOG4J2_LOGGER_FACTORY.equalsIgnoreCase(type)) {
            loggerType = LogType.LOG4J2.name();
        }
        LOGGER.info("LoggerFactoryClassStr: {}, logger type: {}",type,loggerType);
    }

    public static Map<String,String> setLogLevel(List<LoggerBean> data){
        if(getInstance()){
            Map<String,String> result =  instance.setLogLevel(data);
            LOGGER.info("set log level result: {}", JSONObject.toJSONString(result));
        }
        return null;
    }

    public static String setLogLevel(LoggerBean  loggerBean){
        if(getInstance()){
            String result = instance.setLogLevel(loggerBean);
            LOGGER.info("set log level success: {}", JSONObject.toJSONString(loggerBean));
            return result;
        }
        LOGGER.info("set log level error: {}", JSONObject.toJSONString(loggerBean));
        return "fail";
    }

    public static void setLoggerAdapter(String name, Class<? extends LoggerConfigAdapter> clz){
        LOGGER_ADAPTERS.put(name,clz);
        loggerType = name;
    }

    public static LogConfig getLogConfig(){
        LogConfig config = new LogConfig();
        config.setType(loggerType);
        if(getInstance()){
            config.setConfigs( instance.getLoggerList());
        }
        return config;
    }

    private static boolean getInstance(){
        if(instance == null){
            synchronized (LoggerManager.class){
                if(instance == null){
                    try {
                        if(LOGGER_ADAPTERS.containsKey(loggerType)) {
                            instance = LOGGER_ADAPTERS.get(loggerType).newInstance();
                        }
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        return instance != null;
    }
}
