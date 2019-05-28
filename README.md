# log-level
### 日志级别动态调整,支持框架如下：

   * logback
   * log4j
   * log4j2
  
### 设置日志级别：

    ```java
    LoggerManager.setLogLevel(loggerBean)        
    ```
### 获取日志级别配置：
    ```
    LoggerManager.getLogConfig()
    ```
### 实现方式
  * log4j2:  com.github.zw.log.adapter.Log4j2LoggerConfig
  * logback: com.github.zw.log.adapter.LogbackLoggerConfig
  * log4j: com.github.zw.log.adapter.Log4jLoggerConfig