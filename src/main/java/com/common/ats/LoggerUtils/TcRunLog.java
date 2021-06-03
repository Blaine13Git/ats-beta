package com.common.ats.LoggerUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TcRunLog {
    public static final Logger debug = LoggerFactory.getLogger("TestCaseRuncDebugLogger");
    public static final Logger error = LoggerFactory.getLogger("TestCaseRunErrorLogger");
    public static final Logger info = LoggerFactory.getLogger("TestCaseRunInfoLogger");

    public static void info(String information) {
        info.info(information);
    }

    public static void error(String information) {
        error.error(information);
    }

    public static void debug(String information) {
        debug.info(information);
    }

    public static boolean isDebugEnabled() {
        return info.isDebugEnabled();
    }
}
