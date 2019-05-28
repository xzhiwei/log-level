package com.github.zw.log;

import java.util.List;

public class LogConfig {

    private String type;

    private List<LoggerBean> configs;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<LoggerBean> getConfigs() {
        return configs;
    }

    public void setConfigs(List<LoggerBean> configs) {
        this.configs = configs;
    }
}
