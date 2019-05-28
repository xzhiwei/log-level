package com.github.zw.log;

import java.util.List;
import java.util.Map;

public interface LoggerConfigAdapter {

    Map<String, Object> getLoggerConfig();

    List<LoggerBean> getLoggerList();

    Map<String,String> setLogLevel(List<LoggerBean>  data);

    String setLogLevel(LoggerBean  loggerBean);
}
