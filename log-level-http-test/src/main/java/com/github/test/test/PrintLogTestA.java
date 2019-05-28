package com.github.test.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PrintLogTestA {

    private static Logger logger = LoggerFactory.getLogger(PrintLogTestA.class);

    static {
        new Thread(new Runnable() {
            public void run() {
                while (true){
                    logger.info("print info log....");
                    logger.debug("print debug log ....");
                    logger.warn("print warn log.....");
                    logger.error("print error log.....");
                    try {
                        Thread.sleep(5*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
